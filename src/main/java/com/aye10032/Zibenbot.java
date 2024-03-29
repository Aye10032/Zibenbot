package com.aye10032;

import com.aye10032.Functions.*;
import com.aye10032.Functions.funcutil.CQMsg;
import com.aye10032.Functions.funcutil.IFunc;
import com.aye10032.Functions.funcutil.MsgType;
import com.aye10032.NLP.DataCollect;
import com.aye10032.TimeTask.DragraliaTask;
import com.aye10032.TimeTask.SimpleSubscription;
import com.aye10032.Utils.ExceptionUtils;
import com.aye10032.Utils.IDNameUtil;
import com.aye10032.Utils.SeleniumUtils;
import com.aye10032.Utils.TimeUtil.ITimeAdapter;
import com.aye10032.Utils.TimeUtil.SubscriptManager;
import com.aye10032.Utils.TimeUtil.TimeTaskPool;
import org.meowy.cqp.jcq.entity.*;
import org.meowy.cqp.jcq.event.JcqAppAbstract;
import org.openqa.selenium.WebDriver;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * 本文件是JCQ插件的主类<br>
 * <br>
 * <p>
 * 注意修改json中的class来加载主类，如不设置则利用appid加载，最后一个单词自动大写查找<br>
 * 例：appid(com.example.demo) 则加载类 com.example.Demo<br>
 * 文档地址： https://gitee.com/Sobte/JCQ-CoolQ <br>
 * 帖子：https://cqp.cc/t/37318 <br>
 * 辅助开发变量: {@link JcqAppAbstract#CQ CQ}({@link org.meowy.cqp.jcq.entity.CoolQ 酷Q核心操作类}),
 * {@link JcqAppAbstract#CC CC}({@link org.meowy.cqp.jcq.message.CQCode 酷Q码操作类}),
 * 具体功能可以查看文档
 */
public class Zibenbot extends JcqAppAbstract implements ICQVer, IMsg, IRequest {

    public static Proxy proxy = null;
    /**
     * 老的方式依然支持，也就是不强行定构造方法也行
     */
    public static Logger logger = Logger.getLogger("zibenbot");
    //时间任务池
    public TimeTaskPool pool = new TimeTaskPool();
    public SubscriptManager subManager = new SubscriptManager(this);
    public TeamspeakBot teamspeakBot;
    public BotConfigFunc config;
    public FuncEnableFunc enableCollFunc;
    public List<Long> enableGroup = new ArrayList<>();
    /**
     * 关于新版：本版本只是为了测试下新做的插件能不能正常运行，并不包含任何 “新” 内容
     * 新：指代 打包，调试运行
     * 新版改了整体架构，内部改动非常大，使用上 除了包名改动别无区别
     * 关于包名：可以通过批量替换将老程序里的[com.sobte]全部替换成[org.meowy]即可
     */

    List<IFunc> registerFunc = new ArrayList<IFunc>();
    FileHandler fh;

    {

        //fromGroup == 995497677L
        // || fromGroup == 792666782L
        // || fromGroup == 517709950L
        // || fromGroup == 295904863
        // || fromGroup == 947657871
        // || fromGroup == 456919710L
        // || fromGroup == 792797914L
        enableGroup.add(995497677L);
        enableGroup.add(792666782L);
        enableGroup.add(517709950L);
        enableGroup.add(295904863L);
        enableGroup.add(947657871L);
        enableGroup.add(456919710L);
        enableGroup.add(792797914L);
        enableGroup.add(814843368L);
        enableGroup.add(1098042439L);
    }

    {
        appDirectory = "res";
        SeleniumUtils.setup(appDirectory + "\\ChromeDriver\\chromedriver.exe");
    }


    public Zibenbot() {

    }

    public List<IFunc> getRegisterFunc() {
        return registerFunc;
    }

    /**
     * 使用新的方式加载CQ （建议使用这种方式）
     *
     * @param CQ CQ初始化
     */
    public Zibenbot(CoolQ CQ) {
        super(CQ);
    }

    public static Proxy getProxy() {
        Socket s = new Socket();
        SocketAddress add = new InetSocketAddress("127.0.0.1", 1080);
        try {
            s.connect(add, 1000);
            proxy = new Proxy(Proxy.Type.SOCKS, InetSocketAddress.createUnresolved("127.0.0.1", 1080));
        } catch (IOException e) {
            //连接超时需要处理的业务逻辑
        }
        try {
            s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return proxy;
    }

    /**
     * 用main方法调试可以最大化的加快开发效率，检测和定位错误位置<br/>
     * 以下就是使用Main方法进行测试的一个简易案例
     *
     * @param args 系统参数
     */
    public static void main(String[] args) {

        // 要测试主类就先实例化一个主类对象
        Zibenbot demo = new Zibenbot();
        // 获取当前酷Q操作对象
        CoolQ CQ = demo.getCoolQ();
        CQ.logInfo("[JCQ] TEST Demo", "测试启动");// 现在就可以用CQ变量来执行任何想要的操作了
        // 下面对主类进行各方法测试,按照JCQ运行过程，模拟实际情况
        demo.startup();// 程序运行开始 调用应用初始化方法
        demo.enable();// 程序初始化完成后，启用应用，让应用正常工作
        // 开始模拟发送消息
        // 模拟私聊消息
        // 开始模拟QQ用户发送消息，以下QQ全部编造，请勿添加
        demo.privateMsg(0, 10001, 2234567819L, "nmsl", 0);
        demo.privateMsg(0, 10002, 2222222224L, "喵呜喵呜喵呜", 0);
        demo.privateMsg(0, 10003, 2111111334L, "可以给我你的微信吗", 0);
        demo.privateMsg(0, 10004, 3111111114L, "今天天气真好", 0);
        demo.privateMsg(0, 10005, 3333333334L, "你好坏，都不理我QAQ", 0);
        // 模拟群聊消息
        // 开始模拟群聊消息
        demo.groupMsg(1, 10006, 3456789012L, 3333333334L, "", ".禁言 [CQ:at,qq=348802256] 10", 0);
        demo.groupMsg(1, 10008, 3456789012L, 11111111114L, "", "小喵呢，出来玩玩呀", 0);
        demo.groupMsg(1, 10009, 427984429L, 3333333334L, "", "[CQ:at,qq=2222222224] 来一起玩游戏，开车开车", 0);
        demo.groupMsg(1, 10010, 427984429L, 3333333334L, "", "好久不见啦 [CQ:at,qq=11111111114]", 0);
        demo.groupMsg(1, 10011, 427984429L, 11111111114L, "", "qwq 有没有一起开的\n[CQ:at,qq=3333333334]你玩嘛", 0);
        // ......
        // 依次类推，可以根据实际情况修改参数，和方法测试效果
        // 以下是收尾触发函数
        // demo.disable();// 实际过程中程序结束不会触发disable，只有用户关闭了此插件才会触发
        demo.exit();// 最后程序运行结束，调用exit方法
    }

    public int toPrivateMsg(long clientId, String msg) {
        return CQ.sendPrivateMsg(clientId, msg);
    }

    public int toGroupMsg(long groupId, String msg) {
        return CQ.sendGroupMsg(groupId, msg);
    }

    public int toTeamspeakMsg(String msg) {
        teamspeakBot.api.sendChannelMessage(msg);
        return 1;
    }

    public int replyMsg(CQMsg fromMsg, String msg) {
        if (fromMsg.isGroupMsg()) {
            Zibenbot.logger.log(Level.INFO,
                    String.format("回复群[%s]成员[%s]消息:%s",
                            IDNameUtil.getGroupNameByID(fromMsg.fromGroup, getCoolQ().getGroupList()),
                            IDNameUtil.getGroupMemberNameByID(fromMsg.fromGroup, fromMsg.fromClient, CQ),
                            msg));
            return CQ.sendGroupMsg(fromMsg.fromGroup, msg);
        } else if (fromMsg.isPrivateMsg()) {
            Zibenbot.logger.log(Level.INFO,
                    String.format("回复成员[%s]消息:%s",
                            fromMsg.fromClient,
                            msg));
            return CQ.sendPrivateMsg(fromMsg.fromClient, msg);
        } else if (fromMsg.isTeamspealMsg()) {
            Zibenbot.logger.log(Level.INFO,
                    String.format("回复ts频道[%s]消息:%s",
                            fromMsg.fromGroup,
                            msg));
            teamspeakBot.api.sendChannelMessage(msg);
            return 1;
        }
        return 0;
    }

    /**
     * 打包后将不会调用 请不要在此事件中写其他代码
     *
     * @return 返回应用的ApiVer、Appid
     */
    @Override
    public String appInfo() {
        // 应用AppID,规则见 http://d.cqp.me/Pro/开发/基础信息#appid
        String AppID = "com.aye10032.zibenbot";// 记住编译后的文件和json也要使用appid做文件名
        /**
         * 本函数【禁止】处理其他任何代码，以免发生异常情况。
         * 如需执行初始化代码请在 startup 事件中执行（Type=1001）。
         */
        return CQAPIVER + "," + AppID;
    }

    /**
     * 酷Q启动 (Type=1001)<br>
     * 本方法会在酷Q【主线程】中被调用。<br>
     * 请在这里执行插件初始化代码。<br>
     * 请务必尽快返回本子程序，否则会卡住其他插件以及主程序的加载。
     *
     * @return 请固定返回0
     */
    @Override
    public int startup() {
        // 获取应用数据目录(无需储存数据时，请将此行注释)
        appDirectory = CQ.getAppDirectory();
        // 返回如：D:\CoolQ\data\app\org.meowy.cqp.jcq\data\app\com.example.demo\
        // 应用的所有数据、配置【必须】存放于此目录，避免给用户带来困扰。

        //建立时间任务池 这里就两个任务 如果任务多的话 可以新建类进行注册

        SeleniumUtils.setup(appDirectory + "\\ChromeDriver\\chromedriver.exe");

        try {
            File log_dir = new File(appDirectory + "\\log\\");
            if (!log_dir.exists()) {
                log_dir.mkdirs();
            }
            // This block configure the logger with handler and formatter
            fh = new FileHandler(appDirectory + "\\log\\" + new Date().toString().replace(" ", "_").replace(":", "_") + ".log");
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);

            // the following statement is used to log any messages
            logger.info("My first log");

        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ITimeAdapter maiyaoCycle = date1 -> {
            Calendar c = Calendar.getInstance();
            c.setTime(date1);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);
            int hour = c.get(Calendar.HOUR_OF_DAY);
            if (0 <= hour && hour < 6) {
                c.set(Calendar.HOUR_OF_DAY, 6);
            } else if (6 <= hour && hour < 12) {
                c.set(Calendar.HOUR_OF_DAY, 12);
            } else if (12 <= hour && hour < 18) {
                c.set(Calendar.HOUR_OF_DAY, 18);
            } else {
                c.set(Calendar.HOUR_OF_DAY, 0);
                c.setTime(new Date(c.getTimeInMillis() + 86400 * 1000));
            }

            return c.getTime();
        };


        Zibenbot.logger.log(Level.INFO, "registe time task start");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date date = calendar.getTime();
        //创建订阅器对象
        SimpleSubscription maiyao = new SimpleSubscription(this, maiyaoCycle,
                getCQImg(appDirectory + "/image/提醒买药小助手.jpg")) {
            private final static String NAME = "提醒买药小助手";
            @Override
            public String getName() {
                return NAME;
            }
        };
        subManager.setTiggerTime(date);
        subManager.addSubscribable(maiyao);
        subManager.addSubscribable(new DragraliaTask(this) {
            private final static String NAME = "龙约公告转发小助手";
            @Override
            public String getName() {
                return NAME;
            }
        });
        //把订阅管理器注册进线程池
        pool.add(subManager);
        //把订阅管理器注册进可用的模块里
        registerFunc.add(subManager);

        Zibenbot.logger.log(Level.INFO, "registe time task end");
        //改成了手动注册
        Zibenbot.logger.log(Level.INFO, "registe func start");

        registerFunc.add(config = new BotConfigFunc(this));
        registerFunc.add(enableCollFunc = new FuncEnableFunc(this));
        registerFunc.add(new CubeFunc(this));
        registerFunc.add(new BanFunc(this));
        registerFunc.add(new DianGuaiFunc(this));
        registerFunc.add(new EatFunc(this));
        registerFunc.add(new FangZhouDiaoluoFunc(this));
        registerFunc.add(new liantongFunc(this));
        registerFunc.add(new nmslFunc(this));
        registerFunc.add(new PixivFunc(this));
        registerFunc.add(new BiliFunc(this));
        registerFunc.add(new RedStoneFunc(this));
        registerFunc.add(new ScreenshotFunc(this));
        registerFunc.add(new DragraliaNewsFunc(this));
        registerFunc.add(new DraSummonSimulatorFunc(this));
        registerFunc.add(new PaomianFunc(this));
        registerFunc.add(new SendGroupFunc(this));
        registerFunc.add(new INMFunc(this));
        registerFunc.add(new DataCollect(this));

        //对功能进行初始化
        for (IFunc func : registerFunc) {
            try {
                func.setUp();
            } catch (Exception e) {
                logger.warning(() -> "初始化：" + func.getClass().getName() + "出现异常");
            }
        }
        Zibenbot.logger.log(Level.INFO, "registe func end");

        //创建teamspeakbot对象
        teamspeakBot = new TeamspeakBot(this);
        try {
            teamspeakBot.setup();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 酷Q退出 (Type=1002)<br>
     * 本方法会在酷Q【主线程】中被调用。<br>
     * 无论本应用是否被启用，本函数都会在酷Q退出前执行一次，请在这里执行插件关闭代码。
     *
     * @return 请固定返回0，返回后酷Q将很快关闭，请不要再通过线程等方式执行其他代码。
     */
    @Override
    public int exit() {
        for (WebDriver driver : SeleniumUtils.webDrivers) {
            try {
                SeleniumUtils.closeDriver(driver);
            } catch (Exception e) {
                //ignore
            }
        }
        return 0;
    }

    /**
     * 应用已被启用 (Type=1003)<br>
     * 当应用被启用后，将收到此事件。<br>
     * 如果酷Q载入时应用已被启用，则在 {@link #startup startup}(Type=1001,酷Q启动) 被调用后，本函数也将被调用一次。<br>
     * 如非必要，不建议在这里加载窗口。
     *
     * @return 请固定返回0。
     */
    @Override
    public int enable() {
        enable = true;
        return 0;
    }

    /**
     * 应用将被停用 (Type=1004)<br>
     * 当应用被停用前，将收到此事件。<br>
     * 如果酷Q载入时应用已被停用，则本函数【不会】被调用。<br>
     * 无论本应用是否被启用，酷Q关闭前本函数都【不会】被调用。
     *
     * @return 请固定返回0。
     */
    @Override
    public int disable() {
        enable = false;
        return 0;
    }

    /**
     * 私聊消息 (Type=21)<br>
     * 本方法会在酷Q【线程】中被调用。<br>
     *
     * @param subType 子类型，11/来自好友 1/来自在线状态 2/来自群 3/来自讨论组
     * @param msgId   消息ID
     * @param fromQQ  来源QQ
     * @param msg     消息内容
     * @param font    字体
     * @return 返回值*不能*直接返回文本 如果要回复消息，请调用api发送<br>
     * 这里 返回  {@link IMsg#MSG_INTERCEPT MSG_INTERCEPT} - 截断本条消息，不再继续处理<br>
     * 注意：应用优先级设置为"最高"(10000)时，不得使用本返回值<br>
     * 如果不回复消息，交由之后的应用/过滤器处理，这里 返回  {@link IMsg#MSG_IGNORE MSG_IGNORE} - 忽略本条消息
     */
    @Override
    public int privateMsg(int subType, int msgId, long fromQQ, String msg, int font) {
        // 这里处理消息
        //        CQ.sendPrivateMsg(fromClient, "你发送了这样的消息：" + msg + "\n来自Java插件");
        //        try {
        //            CQ.sendPrivateMsg(fromClient, CC.image(new SetuUtil(appDirectory).getImage
        // ()));
        //        } catch (IOException e) {
        //            e.printStackTrace();
        //        }
        CQMsg cqMsg = new CQMsg(subType, msgId, -1, fromQQ, null, msg, font, MsgType.PRIVATE_MSG);
        for (IFunc func : registerFunc) {
            try {
                func.run(cqMsg);
            } catch (Exception e) {
                replyMsg(cqMsg, "运行出错：" + e + "\n" + ExceptionUtils.printStack(e));
            }
        }
        return MSG_IGNORE;
    }

    /**
     * 群消息 (Type=2)<br>
     * 本方法会在酷Q【线程】中被调用。<br>
     *
     * @param subType       子类型，目前固定为1
     * @param msgId         消息ID
     * @param fromGroup     来源群号
     * @param fromQQ        来源QQ号
     * @param fromAnonymous 来源匿名者
     * @param msg           消息内容
     * @param font          字体
     * @return 关于返回值说明, 见 {@link #privateMsg 私聊消息} 的方法
     */
    @Override
    public int groupMsg(int subType, int msgId, long fromGroup, long fromQQ, String fromAnonymous, String msg, int font) {
        // 如果消息来自匿名者
        Anonymous anonymous = null;
        if (fromQQ == 80000000L && !"".equals(fromAnonymous)) {
            // 将匿名用户信息放到 anonymous 变量中
            anonymous = CQ.getAnonymous(fromAnonymous);
        }
        CQMsg cqMsg = new CQMsg(subType, msgId, fromGroup, fromQQ, anonymous, msg, font,
                MsgType.GROUP_MSG);
        runFuncs(cqMsg);
        return MSG_IGNORE;
    }

    public void runFuncs(CQMsg cqMsg){
        if (enableGroup.contains(cqMsg.fromGroup)) {
            for (IFunc func : registerFunc) {
                if (enableCollFunc.isEnable(cqMsg.fromGroup, func)) {
                    try {
                        func.run(cqMsg);
                    } catch (Exception e) {
                        replyMsg(cqMsg, "运行出错：" + e + "\n" + ExceptionUtils.printStack(e));
                    }
                }
            }
        }
    }

    /**
     * 讨论组消息 (Type=4)<br>
     * 本方法会在酷Q【线程】中被调用。<br>
     *
     * @param subtype     子类型，目前固定为1
     * @param msgId       消息ID
     * @param fromDiscuss 来源讨论组
     * @param fromQQ      来源QQ号
     * @param msg         消息内容
     * @param font        字体
     * @return 关于返回值说明, 见 {@link #privateMsg 私聊消息} 的方法
     */
    @Override
    public int discussMsg(int subtype, int msgId, long fromDiscuss, long fromQQ, String msg, int font) {
        // 这里处理消息

        return MSG_IGNORE;
    }

    public int teamspeakMsg(long fromGroup, long fromClient, String msg) {
        // 如果消息来自匿名者
        CQMsg cqMsg = new CQMsg(-1, -1, fromGroup, fromClient, null, msg, -1, MsgType.TEAMSPEAK_MSG);
        for (IFunc func : registerFunc) {
            try {
                func.run(cqMsg);
            } catch (Exception e) {
                replyMsg(cqMsg, "运行出错：" + e + "\n" + ExceptionUtils.printStack(e));
            }
        }
        return MSG_IGNORE;
    }

    /**
     * 群文件上传事件 (Type=11)<br>
     * 本方法会在酷Q【线程】中被调用。<br>
     *
     * @param subType   子类型，目前固定为1
     * @param sendTime  发送时间(时间戳)// 10位时间戳
     * @param fromGroup 来源群号
     * @param fromQQ    来源QQ号
     * @param file      上传文件信息
     * @return 关于返回值说明, 见 {@link #privateMsg 私聊消息} 的方法
     */
    @Override
    public int groupUpload(int subType, int sendTime, long fromGroup, long fromQQ, String file) {
        GroupFile groupFile = CQ.getGroupFile(file);
        if (groupFile == null) { // 解析群文件信息，如果失败直接忽略该消息
            return MSG_IGNORE;
        }
        // 这里处理消息
        return MSG_IGNORE;
    }

    /**
     * 群事件-管理员变动 (Type=101)<br>
     * 本方法会在酷Q【线程】中被调用。<br>
     *
     * @param subtype        子类型，1/被取消管理员 2/被设置管理员
     * @param sendTime       发送时间(时间戳)
     * @param fromGroup      来源群号
     * @param beingOperateQQ 被操作QQ
     * @return 关于返回值说明, 见 {@link #privateMsg 私聊消息} 的方法
     */
    @Override
    public int groupAdmin(int subtype, int sendTime, long fromGroup, long beingOperateQQ) {
        // 这里处理消息

        return MSG_IGNORE;
    }

    /**
     * 群事件-群成员减少 (Type=102)<br>
     * 本方法会在酷Q【线程】中被调用。<br>
     *
     * @param subtype        子类型，1/群员离开 2/群员被踢
     * @param sendTime       发送时间(时间戳)
     * @param fromGroup      来源群号
     * @param fromQQ         操作者QQ(仅子类型为2时存在)
     * @param beingOperateQQ 被操作QQ
     * @return 关于返回值说明, 见 {@link #privateMsg 私聊消息} 的方法
     */
    @Override
    public int groupMemberDecrease(int subtype, int sendTime, long fromGroup, long fromQQ, long beingOperateQQ) {
        // 这里处理消息

        return MSG_IGNORE;
    }

    /**
     * 群事件-群成员增加 (Type=103)<br>
     * 本方法会在酷Q【线程】中被调用。<br>
     *
     * @param subtype        子类型，1/管理员已同意 2/管理员邀请
     * @param sendTime       发送时间(时间戳)
     * @param fromGroup      来源群号
     * @param fromQQ         操作者QQ(即管理员QQ)
     * @param beingOperateQQ 被操作QQ(即加群的QQ)
     * @return 关于返回值说明, 见 {@link #privateMsg 私聊消息} 的方法
     */
    @Override
    public int groupMemberIncrease(int subtype, int sendTime, long fromGroup, long fromQQ, long beingOperateQQ) {
        // 这里处理消息
        CQ.logInfo("fromGroup", "" + fromGroup);
        CQ.logInfo("fromClient", "" + fromQQ);
        CQ.logInfo("beingOperateQQ", "" + beingOperateQQ);
        return MSG_IGNORE;
    }

    /**
     * 好友事件-好友已添加 (Type=201)<br>
     * 本方法会在酷Q【线程】中被调用。<br>
     *
     * @param subtype  子类型，目前固定为1
     * @param sendTime 发送时间(时间戳)
     * @param fromQQ   来源QQ
     * @return 关于返回值说明, 见 {@link #privateMsg 私聊消息} 的方法
     */
    @Override
    public int friendAdd(int subtype, int sendTime, long fromQQ) {
        // 这里处理消息

        return MSG_IGNORE;
    }

    /**
     * 请求-好友添加 (Type=301)<br>
     * 本方法会在酷Q【线程】中被调用。<br>
     *
     * @param subtype      子类型，目前固定为1
     * @param sendTime     发送时间(时间戳)
     * @param fromQQ       来源QQ
     * @param msg          附言
     * @param responseFlag 反馈标识(处理请求用)
     * @return 关于返回值说明, 见 {@link #privateMsg 私聊消息} 的方法
     */
    @Override
    public int requestAddFriend(int subtype, int sendTime, long fromQQ, String msg, String responseFlag) {
        // 这里处理消息

        /**
         * REQUEST_ADOPT 通过
         * REQUEST_REFUSE 拒绝
         */

        // CQ.setFriendAddRequest(responseFlag, REQUEST_ADOPT, null); // 同意好友添加请求
        CQ.setFriendAddRequest(responseFlag, REQUEST_ADOPT, null);
        return MSG_IGNORE;
    }

    /**
     * 请求-群添加 (Type=302)<br>
     * 本方法会在酷Q【线程】中被调用。<br>
     *
     * @param subtype      子类型，1/他人申请入群 2/自己(即登录号)受邀入群
     * @param sendTime     发送时间(时间戳)
     * @param fromGroup    来源群号
     * @param fromQQ       来源QQ
     * @param msg          附言
     * @param responseFlag 反馈标识(处理请求用)
     * @return 关于返回值说明, 见 {@link #privateMsg 私聊消息} 的方法
     */
    @Override
    public int requestAddGroup(int subtype, int sendTime, long fromGroup, long fromQQ, String msg, String responseFlag) {
        // 这里处理消息

        /**
         * REQUEST_ADOPT 通过
         * REQUEST_REFUSE 拒绝
         * REQUEST_GROUP_ADD 群添加
         * REQUEST_GROUP_INVITE 群邀请
         */
		/*if(subtype == 1){ // 本号为群管理，判断是否为他人申请入群
			CQ.setGroupAddRequest(responseFlag, REQUEST_GROUP_ADD, REQUEST_ADOPT, null);// 同意入群
		}
		if(subtype == 2){
			CQ.setGroupAddRequest(responseFlag, REQUEST_GROUP_INVITE, REQUEST_ADOPT, null);// 同意进受邀群
		}*/
        if(subtype == 2){
            CQ.setGroupAddRequest(responseFlag, REQUEST_GROUP_INVITE, REQUEST_ADOPT, null);
        }
        return MSG_IGNORE;
    }

    /**
     * 群事件-群禁言 (Type=104)<br>
     * 本方法会在酷Q【线程】中被调用。<br>
     *
     * @param subType        子类型，1/被解禁 2/被禁言
     * @param sendTime       发送时间(时间戳)
     * @param fromGroup      来源群号
     * @param fromQQ         操作者QQ
     * @param beingOperateQQ 被操作QQ(若为全群禁言/解禁，则本参数为 0)
     * @param duration       禁言时长(单位 秒，仅子类型为2时可用)
     * @return 关于返回值说明, 见 {@link #privateMsg 私聊消息} 的方法
     */
    public int groupBan(int subType, int sendTime, long fromGroup, long fromQQ, long beingOperateQQ, long duration) {
        // 这里处理消息

        return 0;
    }

    /**
     * 本函数会在JCQ【线程】中被调用。
     *
     * @return 固定返回0
     */
    public int menuA() {
        JOptionPane.showMessageDialog(null, "这是测试菜单A，可以在这里加载窗口");
        return 0;
    }

    /**
     * 本函数会在酷Q【线程】中被调用。
     *
     * @return 固定返回0
     */
    public int menuB() {
        JOptionPane.showMessageDialog(null, "这是测试菜单B，可以在这里加载窗口");
        return 0;
    }

    public String getCQImg(String filePath){
        try {
            return getCQCode().image(new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "图片读取失败";
    }

    public int test(CQMsg cqMsg) {
        if (cqMsg.isGroupMsg()) {
            return groupMsg(cqMsg.subType, cqMsg.msgId, cqMsg.fromGroup, cqMsg.fromClient, "", cqMsg.msg, cqMsg.font);
        } else if (cqMsg.isPrivateMsg()) {
            return privateMsg(cqMsg.subType, cqMsg.msgId, cqMsg.fromClient, cqMsg.msg, cqMsg.font);
        } else {
            return teamspeakMsg(cqMsg.fromGroup, cqMsg.fromClient, cqMsg.msg);
        }
    }

}
