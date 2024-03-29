package com.aye10032.Functions;

import com.aye10032.Functions.funcutil.BaseFunc;
import com.aye10032.Functions.funcutil.CQMsg;
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
            if (cqMsg.isGroupMsg() || cqMsg.isPrivateMsg()) {
                zibenbot.replyMsg(cqMsg, zibenbot.getCQCode().at(cqMsg.fromClient) + cqMsg.msg);
            } else {
                zibenbot.replyMsg(cqMsg, "@" + zibenbot.teamspeakBot.api.getClientInfo((int)cqMsg.fromClient).getLoginName() + cqMsg.msg);
            }
        }
    }
}
