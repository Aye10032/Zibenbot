package com.aye10032.Functions;

import com.aye10032.TimeTask.DragraliaTask;
import com.aye10032.Utils.Config;
import com.aye10032.Utils.ConfigLoader;
import com.aye10032.Zibenbot;

/**
 * @author Dazo66
 */
public class DragraliaNewsFunc extends BaseFunc {

    private long last = 0L;
    private DragraliaTask task;

    public DragraliaNewsFunc(Zibenbot zibenbot) {
        super(zibenbot);
    }

    @Override
    public void setUp() {
        this.task = new DragraliaTask(zibenbot);
        this.task.loader = new ConfigLoader<>(zibenbot.appDirectory + "/dragraliaFunc.json", Config.class);
        this.task.config = this.task.loader.load();
    }

    @Override
    public void run(CQMsg CQmsg) {
        String s = CQmsg.msg;
        if (".龙约公告".equals(s)) {
            if (System.currentTimeMillis() - last > 120 * 1000) {
                task.config.set("last_data", "114514");
                task.cqMsg = CQmsg;
                task.runnable.run();
                last = System.currentTimeMillis();
            } else {
                replyMsg(CQmsg, "操作过于频繁，请" + (120 * 1000 - (System.currentTimeMillis() - last))/1000 + "秒后再试。");
            }
        }
    }
}
