package com.aye10032.Functions;

import com.aye10032.Zibenbot;

import java.io.File;
import java.io.IOException;
import java.util.Random;

/**
 * @author Dazo66
 */
public class liantongFunc extends BaseFunc {

    private Random random;

    public liantongFunc(Zibenbot zibenbot) {
        super(zibenbot);
        random = new Random(System.currentTimeMillis());
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
                if (random.nextDouble() > 0.2) {
                    zibenbot.replyMsg(CQmsg, zibenbot.getCQCode().image(new File(zibenbot.appDirectory + "\\image\\liantong.jpg")));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
