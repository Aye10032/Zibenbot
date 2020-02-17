package com.aye10032.Functions;

import org.meowy.cqp.jcq.entity.Anonymous;

public class CQMsg {

    public int subType = -1;
    public int msgId = -1;
    public long fromGroup = -1;
    public long fromQQ = -1;
    public Anonymous anonymous;
    public String msg;
    public int font;

    public CQMsg(int subType, int msgId, long fromGroup, long fromQQ, Anonymous fromAnonymous, String msg,
                 int font){
        this.subType = subType;
        this.msgId = msgId;
        this.fromGroup = fromGroup;
        this.fromQQ = fromQQ;
        this.anonymous = fromAnonymous;
        this.msg = msg;
        this.font = font;

    }


}
