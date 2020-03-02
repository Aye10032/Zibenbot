package com.aye10032.Functions;

import com.aye10032.Utils.HttpUtils;
import com.aye10032.Utils.TimeUtil.TimedTask;
import com.aye10032.Zibenbot;
import com.dazo66.test.DiaoluoType;
import com.dazo66.test.Module;
import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.aye10032.Utils.TimeUtil.TimeConstant.PER_DAY;
import static com.dazo66.test.Module.getModules;
import static com.dazo66.test.Module.getVers;

/**
 * @author Dazo66
 */
public class FangZhouDiaoluoFunc extends BaseFunc {

    private DiaoluoType type;
    private Module module;
    private List<DiaoluoType.HeChenType> name_idList;
    private String arkonegraphFile;
    private String cacheFile;

    public FangZhouDiaoluoFunc(Zibenbot zibenbot) {
        super(zibenbot);
        if (zibenbot != null) {
            arkonegraphFile = zibenbot.appDirectory + "/Arkonegraph.png";
            cacheFile = zibenbot.appDirectory + "/cacheFile.json";
        } else {
            arkonegraphFile = "/Arkonegraph.png";
            cacheFile = "/cacheFile.json";
        }
    }

    public FangZhouDiaoluoFunc() {
        this(null);
    }

    @Override
    public void setUp() {
        update();
        TimedTask task = new TimedTask();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 20);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date date = calendar.getTime();
        task.setTiggerTime(date).setCycle(PER_DAY).setRunnable(this::update);
        zibenbot.pool.add(task);
    }

    @Override
    public void run(CQMsg CQmsg) {
        String msg = CQmsg.msg.trim();
        if (msg.startsWith(".方舟素材")) {
            String[] strings = msg.split(" ");
            int len = strings.length;
            if (len >= 2) {
                loop:
                for (int i = 1; i < len; i++) {
                    boolean flag = false;
                    for (DiaoluoType.HeChenType type : name_idList) {
                        if (type.isThis(strings[i])) {
                            flag = true;
                            if (type.calls.length == 0) {
                                if (zibenbot != null) {
                                    zibenbot.replyGroupMsg(CQmsg, module.getString(this.type.getMaterialFromID(type.id)));
                                } else {
                                    System.out.println(module.getString(this.type.getMaterialFromID(type.id)));
                                }
                            } else {
                                StringBuilder s = new StringBuilder();
                                List<String> strings1 = getCalls(name_idList, type);
                                for (int i1 = 0; i1 < strings1.size(); i1++) {
                                    s.append(module.getString(this.type.getMaterialFromID(strings1.get(i1))));
                                    if (i1 != strings1.size() - 1) {
                                        s.append("\n\n");
                                    }
                                }
                                if (zibenbot != null) {
                                    zibenbot.replyGroupMsg(CQmsg, s.toString());
                                } else {
                                    System.out.println(s.toString());
                                }
                                break loop;
                            }
                        }
                    }
                    if (!flag) {
                        if (zibenbot != null) {
                            zibenbot.replyGroupMsg(CQmsg, "找不到素材：【" + strings[i] + "】");
                        } else {
                            System.out.println("找不到素材：【" + strings[i] + "】");
                        }
                    }
                }
            } else {
                try {
                    if (zibenbot != null) {
                        zibenbot.replyGroupMsg(CQmsg, zibenbot.getCQCode().image(new File(arkonegraphFile)));
                    } else {
                        System.out.println(arkonegraphFile);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void update() {
        System.out.println(arkonegraphFile);
        Zibenbot.logger.info("fangzhoudiaoluo update start");
        File file = new File(cacheFile);
        Gson gson = new Gson();
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        DiaoluoType diaoluoType = null;
        try {
            for (int i = 1; i <= 5; i++) {
                InputStream stream = HttpUtils.getStringFromNet("https://arkonegraph.herokuapp.com/materials/tier/" + String.valueOf(i), client);
                if (diaoluoType == null) {
                    diaoluoType = gson.fromJson(new InputStreamReader(stream), DiaoluoType.class);
                } else {
                    diaoluoType.material = ArrayUtils.addAll(diaoluoType.material, gson.fromJson(new InputStreamReader(stream), DiaoluoType.class).material);
                }
                stream.close();

            }
            this.type = diaoluoType;
            InputStream stream = HttpUtils.getStringFromNet("https://gitee.com/aye10032/Zibenbot/raw/master/res/dangzhoudiaoluo/name-id.txt", client);
            List<String> strings = IOUtils.readLines(new InputStreamReader(stream));
            List<DiaoluoType.HeChenType> list = new ArrayList<>();
            for (String s : strings) {
                if ("".equals(s.trim()) || s.startsWith("//")) {
                    //忽略注释和空行
                } else {
                    List<String> modules = getModules(s);
                    String s1 = modules.get(0);
                    list.add(new DiaoluoType.HeChenType(s1.trim(), getVers(modules.get(1)).toArray(new String[]{}), getVers(modules.get(2)).toArray(new String[]{})));
                }
            }
            name_idList = list;
            stream.close();
            Module.update();
            module = Module.module;

            //更新图片
            HttpUtils.download("https://gitee.com/aye10032/Zibenbot/raw/master/res/dangzhoudiaoluo/Arkonegraph.png", arkonegraphFile, client);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Zibenbot.logger.info("fangzhoudiaoluo update end");
    }

    public List<String> getCalls(List<DiaoluoType.HeChenType> all, DiaoluoType.HeChenType type) {
        List<String> strings = new ArrayList<>();
        if (type.calls.length == 0) {
            strings.add(type.id);
            return strings;
        } else {
            for (String c : type.calls) {
                for (DiaoluoType.HeChenType type1 : all) {
                    if (type1.id.equals(c)) {
                        for (String s : getCalls(all, type1)) {
                            if (!strings.contains(s)) {
                                strings.add(s);
                            }
                        }
                    }
                }
            }
        }
        return strings;
    }

}
