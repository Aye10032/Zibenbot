package com.aye10032.Functions;

import com.aye10032.Zibenbot;

/**
 * @author Dazo66
 */
public class nmslFunc extends BaseFunc {


    public nmslFunc(Zibenbot zibenbot) {
        super(zibenbot);
    }

    public void setUp() {
        //pass
    }

    public void run(CQMsg cqMsg) {
        if (cqMsg.msg.contains("nmsl")) {
            zibenbot.replyGroupMsg(cqMsg, zibenbot.getCQCode().at(cqMsg.fromQQ) + cqMsg.msg);
        }
    }
}
