package com.aye10032.Functions;

import com.aye10032.Zibenbot;

import java.io.File;
import java.io.IOException;
import java.util.Random;

/**
 * @author Dazo66
 */
public class CFOPFunc extends BaseFunc {

    private Random random;

    public CFOPFunc(Zibenbot zibenbot) {
        super(zibenbot);
        random = new Random(System.currentTimeMillis());
    }

    public void setUp() {

    }

    public void run(CQMsg CQmsg) {
        if (CQmsg.msg.startsWith("CFOP")||CQmsg.msg.startsWith("cfop")) {
            try {
                if (CQmsg.isTeamspealMsg()) {
                    zibenbot.replyMsg(CQmsg, "ts频道无法发图片，请从群聊或者私聊获取");
                    return;
                }
                if (CQmsg.msg.contains("F2L")||CQmsg.msg.contains("f2l")) {
                    zibenbot.replyMsg(CQmsg, zibenbot.getCQCode().image(new File(zibenbot.appDirectory + "\\image\\CFOP2.jpg")));
                } else if (CQmsg.msg.contains("OLL")||CQmsg.msg.contains("oll")) {
                    zibenbot.replyMsg(CQmsg, zibenbot.getCQCode().image(new File(zibenbot.appDirectory + "\\image\\CFOP3.jpg")));
                } else if (CQmsg.msg.contains("PLL")||CQmsg.msg.contains("pll")) {
                    zibenbot.replyMsg(CQmsg, zibenbot.getCQCode().image(new File(zibenbot.appDirectory + "\\image\\CFOP4.jpg")));
                } else {
                    zibenbot.replyMsg(CQmsg,
                            zibenbot.getCQCode().image(new File(zibenbot.appDirectory + "\\image\\CFOP1.jpg"))
                                    +zibenbot.getCQCode().image(new File(zibenbot.appDirectory + "\\image\\CFOP2.jpg"))
                                    +zibenbot.getCQCode().image(new File(zibenbot.appDirectory + "\\image\\CFOP3.jpg"))
                                    +zibenbot.getCQCode().image(new File(zibenbot.appDirectory + "\\image\\CFOP4.jpg")));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
