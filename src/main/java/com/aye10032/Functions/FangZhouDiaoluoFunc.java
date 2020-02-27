package com.aye10032.Functions;

import com.aye10032.Zibenbot;

import java.io.File;
import java.io.IOException;

/**
 * @author Dazo66
 */
public class FangZhouDiaoluoFunc extends BaseFunc {



    public FangZhouDiaoluoFunc(Zibenbot zibenbot) {
        super(zibenbot);
    }

    public void setUp() {
    }

    public void run(CQMsg CQmsg) {
        if (CQmsg.msg.startsWith(".方舟素材")) {
            try {
                zibenbot.replyGroupMsg(CQmsg, zibenbot.getCQCode().image(new File(zibenbot.appDirectory + "\\image\\素材掉落.png")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
