package com.aye10032;

import org.meowy.cqp.jcq.entity.CoolQ;

public class sendMSG {
    CoolQ CQ;

    public sendMSG(CoolQ CQ) {
        this.CQ = CQ;
    }

    public void send(long qq, String msg) {
        CQ.sendPrivateMsg(qq, msg);
    }

}
