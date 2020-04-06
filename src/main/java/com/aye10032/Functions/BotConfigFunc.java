package com.aye10032.Functions;

import com.aye10032.Utils.Config;
import com.aye10032.Utils.ConfigListener;
import com.aye10032.Utils.ConfigLoader;
import com.aye10032.Zibenbot;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dazo66
 */
public class BotConfigFunc extends BaseFunc {

    private String configFile;
    private Gson gson;
    private Config config;
    private ConfigLoader<Config> loader;
    private List<ConfigListener> listeners = new ArrayList<>();

    public String getConfig(String key, String dafule) {
        return this.config.getWithDafault(key, dafule);
    }

    public BotConfigFunc(Zibenbot zibenbot) {
        super(zibenbot);
        if (zibenbot == null) {
            configFile = "/bot_config.json";
        } else {
            configFile = zibenbot.appDirectory + "/bot_config.json";
        }
        loader = new ConfigLoader<>(configFile, Config.class);

    }

    public void addListener(ConfigListener listener){
        listeners.add(listener);
    }

    @Override
    public void setUp() {
        config = loader.load();
    }

    @Override
    public void run(CQMsg CQmsg) {
        if (CQmsg.msg.startsWith(".setconfig") || CQmsg.msg.startsWith(".setConfig")) {
            String[] strings = CQmsg.msg.split(" ", 3);
            if (strings.length == 3) {
                config.set(strings[1], strings[2]);
                loader.save(config);
                for (ConfigListener listener : listeners) {
                    if (strings[1].equals(listener.getKey())) {
                        try {
                            listener.run();
                        } catch (Exception e) {
                            zibenbot.replyMsg(CQmsg, "监听器运行出错：" + e.getMessage());
                        }
                    }
                }
                zibenbot.replyMsg(CQmsg, "已将[" + strings[1] + "]设置为[" + strings[2] + "]");
            } else {
                zibenbot.replyMsg(CQmsg, "设置参数不足!");
            }
        }
        if (CQmsg.msg.startsWith(".getconfig") || CQmsg.msg.startsWith(".getConfig")) {
            String[] strings = CQmsg.msg.split(" ", 2);
            if (strings.length == 2) {
                if ("all".equals(strings[1])) {
                    StringBuilder builder = new StringBuilder();
                    builder.append("configs：\n");
                    loop1:
                    for (String key : config.map.keySet()) {
                        builder.append("    ").append(key).append(" : ").append(config.map.get(key));
                        for (ConfigListener listener : listeners) {
                            if (listener.getKey().equals(key)) {
                                builder.append(" (has listener)");
                                break loop1;
                            }
                        }
                        builder.append("\n");
                    }
                    zibenbot.replyMsg(CQmsg, builder.toString());
                }else {
                    String s = config.get(strings[1]);
                    zibenbot.replyMsg(CQmsg, "[" + strings[1] + "]=" + (s == null ? "null" : s));
                }
            }
        }
    }

}
