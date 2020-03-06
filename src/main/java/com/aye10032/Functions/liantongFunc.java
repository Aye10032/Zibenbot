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

    public void setUp() {

    }

    public void run(CQMsg CQmsg) {
        if (CQmsg.msg.contains("炼铜")) {
            try {
                if (CQmsg.isTeamspealMsg()){
                    zibenbot.replyMsg(CQmsg, "ts频道无法发图片，请从群聊或者私聊获取");
                    return;
                }
                zibenbot.replyMsg(CQmsg, zibenbot.getCQCode().image(new File(zibenbot.appDirectory + "\\image\\liantong.jpg")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
