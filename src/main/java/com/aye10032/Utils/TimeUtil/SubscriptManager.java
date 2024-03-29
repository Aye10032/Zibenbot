package com.aye10032.Utils.TimeUtil;

import com.aye10032.Functions.funcutil.CQMsg;
import com.aye10032.Functions.funcutil.IFunc;
import com.aye10032.Functions.funcutil.MsgType;
import com.aye10032.Utils.ConfigLoader;
import com.aye10032.Zibenbot;
import com.google.gson.reflect.TypeToken;
import org.meowy.cqp.jcq.entity.CoolQ;
import org.meowy.cqp.jcq.message.CQCode;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;

/**
 * 订阅器的控制类
 * 与时间线程池相联系
 * 也能响应对应聊天语句
 *
 * @author Dazo66
 */
public class SubscriptManager extends TimedTaskBase implements IFunc {

    protected Zibenbot zibenbot;
    protected String appDirectory;
    protected CoolQ CQ;
    protected CQCode CC;
    private Date last;
    /**
     * 各个途径的订阅信息 每次从配置中读取
     */
    private Map<MsgType, Map<String, List<Long>>> subscriptMap;
    /**
     * 所有的订阅器
     */
    private List<ISubscribable> allSubscription = Collections.synchronizedList(new ArrayList<>());
    /**
     * 暂存的下一次要运行的东西
     */
    private List<ISubscribable> next = Collections.synchronizedList(new ArrayList<>());

    private List<ISubscribable> getCurrentTiggerSub(){
        return getCurrentTiggerSub(getTiggerTime());
    }

    private List<ISubscribable> getCurrentTiggerSub(Date current) {
        List<ISubscribable> ret = Collections.synchronizedList(new ArrayList<>());
        Date begin = getBegin();
        for (ISubscribable s : allSubscription) {
            Date date2 = (Date) begin.clone();
            while (true) {
                if (date2.getTime() < current.getTime()) {
                    date2 = s.getNextTime(date2);
                } else if (date2.getTime() == current.getTime()) {
                    ret.add(s);
                    break;
                } else {
                    break;
                }
            }
        }
        return ret;
    }

    public SubscriptManager(Zibenbot zibenbot) {
        this.zibenbot = zibenbot;
        if (zibenbot == null) {
            appDirectory = "";
            CQ = null;
            CC = null;
        } else {
            CQ = zibenbot.getCoolQ();
            CC = zibenbot.getCQCode();
            appDirectory = zibenbot.appDirectory;
        }
    }

    public Map<Date, List<ISubscribable>> getFutureTasks(int count) {
        Map<Date, List<ISubscribable>> map = new LinkedHashMap<>();
        if (count > 0) {
            map.put(getTiggerTime(), getNextTiggerSub());
        }
        Date date = getTiggerTime();
        Date temp;
        for (int i = 1; i < count; i++) {
            map.put(temp = getNextTiggerTimeFrom((Date) date.clone())
                    , getNextTiggerSubFrom((Date) date.clone()));
            date = (Date) temp.clone();
        }
        return map;
    }

    /**
     * 刷新所有的订阅器的收件人
     * 只在需要调用的时候 调用
     */
    private void flushRecipients() {
        for (ISubscribable s : allSubscription) {
            s.setRecipients(getRecipients(s));
        }
    }

    /**
     * 返回指定的订阅器的收件人列表
     *
     * @param s 指定的订阅器
     * @retrun 收件人列表
     */

    public List<CQMsg> getRecipients(ISubscribable s) {
        List<CQMsg> recipients = Collections.synchronizedList(new ArrayList<>());
        for (MsgType type : MsgType.values()) {
            if (subscriptMap.get(type) != null) {
                List<Long> temp = subscriptMap.get(type).get(s.getName());
                if (temp != null) {
                    for (Long l : temp) {
                        //收集所有要发送的收件人
                        recipients.add(getCqmsg(type, l));
                    }
                }
            }
        }
        return recipients;
    }

    /**
     * 禁用了这个方法
     *
     * @param cycle unuse
     * @return this
     */
    @Override
    public TimedTaskBase setCycle(ITimeAdapter cycle) {
        return this;
    }

    /**
     * 得到这次要触发的时间
     * 与这次要运行的订阅有关
     *
     * @return date 下次要运行的时间对象
     */
    @Override
    public Date getTiggerTime() {
        return super.getTiggerTime();
    }

    /**
     * 这个方法应该由{@link com.aye10032.Utils.TimeUtil.TimeFlow}在运行这个任务时调用
     * 得到这次之后的触发时间
     * 与下次要运行的订阅有关
     *
     * @return date 这次之后的触发时间
     */
    @Override
    public Date getNextTiggerTime() {
        Date date = null;
        for (ISubscribable s : allSubscription) {
            Date temp = TimeConstant.getNextTimeFromNow(getTiggerTime(), s);
            if (date == null || temp.before(date)) {
                date = temp;
            }
        }
        return date;
    }

    /**
     * 禁用了这个方法
     *
     * @param times unuse
     * @return this
     */
    @Override
    public TimedTaskBase setTimes(int times) {
        return super.setTimes(-1);
    }

    private Date getNextTiggerTimeFrom(Date from) {
        Date date = null;
        for (ISubscribable s : allSubscription) {
            Date temp = s.getNextTime((Date) from.clone());
            if (date == null || temp.before(date)) {
                date = temp;
            }
        }
        return date;
    }

    /**
     * 初始化 在{@link Zibenbot}中调用
     */
    @Override
    public void setUp() {
        subscriptMap = load();
        flushRecipients();
    }

    /**
     * [sub/订阅/unsub/取消订阅] [Subscribable Name]
     * 不带参数返回所有可用的和已用的Subscribable
     *
     * @param CQmsg 传入的消息
     */
    @Override
    public void run(CQMsg CQmsg) {
        String[] msgs = CQmsg.msg.trim().split(" ");
        Boolean sw = null;
        if ("sub".equals(msgs[0]) || "订阅".equals(msgs[0])) {
            sw = true;
        } else if ("unsub".equals(msgs[0]) || "取消订阅".equals(msgs[0])) {
            sw = false;
        }
        if (sw != null) {
            subscriptMap = load();
            if (msgs.length == 1) {
                StringBuilder builder = new StringBuilder();
                builder.append("当前已订阅的有：\n");
                for (ISubscribable subscription : allSubscription) {
                    if (hasSub(CQmsg, subscription)) {
                        builder.append("\t").append(subscription.getName()).append("\n");
                    }
                }
                builder.append("当前未订阅的有：\n");
                for (int i = 0; i < allSubscription.size(); i++) {
                    if (!hasSub(CQmsg, allSubscription.get(i))) {
                        builder.append("\t").append(allSubscription.get(i).getName());
                        if (i == allSubscription.size() - 1) {
                            builder.append("\n");
                        }
                    }
                }
                //重新读取后刷新收件人
                flushRecipients();
                replyMsg(CQmsg, builder.toString());
            } else if (msgs.length == 2) {
                if ("调试".equals(msgs[1]) || "debug".equals(msgs[1])) {
                    StringBuilder builder = new StringBuilder();
                    Map<Date, List<ISubscribable>> map = getFutureTasks(10);
                    String TAB_STRING = "                    ";
                    for (Date date : map.keySet()) {
                        List<ISubscribable> list = map.get(date);
                        DateFormat format = new SimpleDateFormat("MM/dd HH:mm");
                        if (list.size() >= 1) {
                            builder.append(format.format(date));
                            builder.append(" ");
                            builder.append(list.get(0).getName()).append("\n");
                        }
                        for (int i = 1; i < list.size(); i++) {
                            builder.append(TAB_STRING).append(list.get(i).getName());
                            builder.append("\n");
                        }
                    }
                    if (builder.length() == 0) {
                        builder.append("当前队列中没有任务");
                    }
                    replyMsg(CQmsg, builder.toString());
                    return;
                }
                boolean flag = false;
                for (ISubscribable s : allSubscription) {
                    if (msgs[1].equals(s.getName())) {
                        flag = true;
                        break;
                    }
                }
                if (flag) {
                    if (sw) {
                        subscribe(CQmsg, msgs[1]);
                        replyMsg(CQmsg, String.format("【%s】 已订阅 【%s】", CQmsg.isGroupMsg() ?
                                "群:" + CQmsg.fromGroup : "用户:" + CQmsg.fromClient, msgs[1]));
                    } else {

                        unSubscribe(CQmsg, msgs[1]);
                        replyMsg(CQmsg, String.format("【%s】 已取消订阅 【%s】", CQmsg.isGroupMsg() ?
                                "群:" + CQmsg.fromGroup : "用户:" + CQmsg.fromClient, msgs[1]));
                    }
                    //订阅后刷新收件人
                    flushRecipients();
                } else {
                    replyMsg(CQmsg, "找不到这个订阅器：" + msgs[1]);
                }
            } else {
                replyMsg(CQmsg, "参数有误");
            }
        }
    }

    /**
     * 得到接下来需要运行的Subscribable
     *
     * @return 可能有多个Subscribable同时触发
     */
    public List<ISubscribable> getNextTiggerSub() {
        List<ISubscribable> ret = Collections.synchronizedList(new ArrayList<>());
        Date date = null;
        for (ISubscribable s : allSubscription) {
            Date temp = TimeConstant.getNextTimeFromNow(getBegin(), s);
            if (date == null || temp.before(date)) {
                date = temp;
                ret.clear();
                ret.add(s);
            } else if (date.equals(temp)) {
                ret.add(s);
            }
        }
        return ret;
    }

    private List<ISubscribable> getNextTiggerSubFrom(Date from) {
        List<ISubscribable> ret = Collections.synchronizedList(new ArrayList<>());
        Date date = null;
        for (ISubscribable s : allSubscription) {
            Date temp = s.getNextTime((Date) from.clone());
            if (date == null || temp.before(date)) {
                date = temp;
                ret.clear();
                ret.add(s);
            } else if (date.equals(temp)) {
                ret.add(s);
            }
        }
        return ret;
    }

    /**
     * 查询用户是否已经订阅了
     *
     * @param id      包含用户类型和用户id
     * @param subName Subscribable名字
     * @return boolean
     */
    public boolean hasSub(MsgType type, Long id, String subName) {
        switch (type) {
            case GROUP_MSG:
                return subscriptMap.computeIfAbsent(type, k -> new HashMap<>()).computeIfAbsent(subName, k -> new ArrayList<>()).contains(id);
            case TEAMSPEAK_MSG:
                return subscriptMap.computeIfAbsent(type, k -> new HashMap<>()).computeIfAbsent(subName, k -> new ArrayList<>()).contains(id);
            case PRIVATE_MSG:
                return subscriptMap.computeIfAbsent(type, k -> new HashMap<>()).computeIfAbsent(subName, k -> new ArrayList<>()).contains(id);
            default:
                return false;
        }
    }

    /**
     * 查询用户是否已经订阅了
     *
     * @param msg     包含用户类型和用户id
     * @param subName Subscribable对象
     * @return boolean
     */
    public boolean hasSub(CQMsg msg, ISubscribable subName) {
        return hasSub(msg, subName.getName());
    }

    /**
     * 查询用户是否已经订阅了
     *
     * @param msg     包含用户类型和用户id
     * @param subName Subscribable对象
     * @return boolean
     */
    public boolean hasSub(CQMsg msg, String subName) {
        switch (msg.type) {
            case GROUP_MSG:
                return hasSub(msg.type, msg.fromGroup, subName);
            case TEAMSPEAK_MSG:
                return hasSub(msg.type, msg.fromGroup, subName);
            case PRIVATE_MSG:
                return hasSub(msg.type, msg.fromClient, subName);
            default:
                return false;
        }
    }

    /**
     * 对传进来的消息来源添加订阅
     *
     * @param sub   包含用户类型和用户id
     * @param cqMsg Subscribable对象
     */
    public void subscribe(CQMsg cqMsg, String sub) {
        switch (cqMsg.type) {
            case GROUP_MSG:
                subscribe(cqMsg.type, cqMsg.fromGroup, sub);
                return;
            case TEAMSPEAK_MSG:
                subscribe(cqMsg.type, cqMsg.fromGroup, sub);
                return;
            case PRIVATE_MSG:
                subscribe(cqMsg.type, cqMsg.fromClient, sub);
                return;
            default:
        }
    }

    /**
     * 对传进来的消息来源添加订阅
     *
     * @param type 消息类型
     * @param id   用户id
     * @param sub  Subscribable名字
     */
    public void subscribe(MsgType type, Long id, String sub) {
        if (!hasSub(type, id, sub)) {
            subscriptMap.computeIfAbsent(type, k -> new HashMap<>()).computeIfAbsent(sub,
                    v -> new ArrayList<>()).add(id);
            save(subscriptMap);
        }
    }

    public void unSubscribe(CQMsg cqMsg, String sub) {
        switch (cqMsg.type) {
            case GROUP_MSG:
                unSubscribe(cqMsg.type, cqMsg.fromGroup, sub);
                return;
            case TEAMSPEAK_MSG:
                unSubscribe(cqMsg.type, cqMsg.fromGroup, sub);
                return;
            case PRIVATE_MSG:
                unSubscribe(cqMsg.type, cqMsg.fromClient, sub);
                return;
            default:
        }
    }

    /**
     * 对传进来的消息来源取消订阅
     *
     * @param type 消息类型
     * @param id   用户id
     * @param sub  Subscribable名字
     */
    public void unSubscribe(MsgType type, Long id, String sub) {
        if (hasSub(type, id, sub)) {
            subscriptMap.computeIfAbsent(type, k -> new HashMap<>()).computeIfAbsent(sub,
                    v -> new ArrayList<>()).remove(id);
            save(subscriptMap);
        }
    }

    /**
     * 添加可订阅的对象
     *
     * @param subscription 可订阅的对象
     */
    public void addSubscribable(ISubscribable subscription) {
        if (!allSubscription.contains(subscription)) {
            allSubscription.add(subscription);
            setTiggerTime(getBegin());
            setTiggerTime(getNextTiggerTime());
            if (zibenbot.pool.isContain(this)) {
                zibenbot.pool.flush();
            }
        } else {
            Zibenbot.logger.log(Level.WARNING, "不允许重复的订阅名字");
        }
    }

    /**
     * 加载订阅状态
     *
     * @return map
     */
    public Map<MsgType, Map<String, List<Long>>> load() {
        return Collections.synchronizedMap(ConfigLoader.load(zibenbot.appDirectory + CINFIG_PATH,
                new TypeToken<Map<MsgType, Map<String, List<Long>>>>() {
        }.getType()));
    }

    /**
     * 保存订阅状态
     *
     * @param saved 订阅状态
     */
    public void save(Map<MsgType, Map<String, List<Long>>> saved) {
        ConfigLoader.save(zibenbot.appDirectory + CINFIG_PATH, new TypeToken<Map<MsgType,
                Map<String, List<Long>>>>() {
        }.getType(), saved);
    }

    public void replyMsg(CQMsg fromMsg, String msg) {
        if (zibenbot != null) {
            zibenbot.replyMsg(fromMsg, msg);
        } else {
            System.out.println(msg);
        }

    }

    @Override
    public void run(Date current) {
        //得到这次要运行的
        List<ISubscribable> list = getCurrentTiggerSub(current);
        //对下次要运行的订阅进行循环
        for (ISubscribable s : list) {
            if (s.getRecipients() != null && !s.getRecipients().isEmpty()) {
                //运行各个订阅器
                Zibenbot.logger.log(Level.INFO, "SubscriptManager run start:" + s.toString() + current);
                s.run();
            }
        }
        //清除暂存的下次要运行的订阅器
        next.clear();
    }

    private final static String EMPTY = "";
    private final static String CINFIG_PATH = "/Subscript.json";

    private static CQMsg getCqmsg(MsgType type, Long id) {
        switch (type) {
            case TEAMSPEAK_MSG:
                return new CQMsg(-1, -1, id, -1, null, EMPTY, -1, type);
            case PRIVATE_MSG:
                return new CQMsg(-1, -1, -1, id, null, EMPTY, -1, type);
            case GROUP_MSG:
                return new CQMsg(-1, -1, id, -1, null, EMPTY, -1, type);
            default:
                return null;
        }
    }
}
