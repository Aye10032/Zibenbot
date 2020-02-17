package com.aye10032.Functions;

import com.aye10032.Zibenbot;

import java.io.File;
import java.io.IOException;

/**
 * @author Dazo66
 */
public class liantongFunc extends BaseFunc {

    public liantongFunc(Zibenbot zibenbot) {
        super(zibenbot);
    }

    public void setUp(Zibenbot zibenbot) {

    }

    public void run(CQMsg CQmsg) {
        if (CQmsg.msg.contains("炼铜")) {
            try {
                CQ.sendGroupMsg(CQmsg.fromGroup, CC.image(new File(zibenbot.appDirectory + "\\image\\liantong.jpg")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
