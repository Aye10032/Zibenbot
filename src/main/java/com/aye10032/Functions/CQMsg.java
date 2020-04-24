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
    public MsgType type;

    public CQMsg(int subType, int msgId, long fromGroup, long fromClient, Anonymous fromAnonymous, String msg, int font, MsgType type) {
        this.subType = subType;
        this.msgId = msgId;
        this.fromGroup = fromGroup;
        this.fromQQ = fromClient;
        this.anonymous = fromAnonymous;
        this.msg = msg;
        this.font = font;
        this.type = type;
    }

    public boolean isGroupMsg(){
        return type == MsgType.GROUP_MSG;
    }

    public boolean isPrivateMsg(){
        return type == MsgType.PRIVATE_MSG;
    }

    public boolean isTeamspealMsg(){
        return type == MsgType.TEAMSPEAK_MSG;
    }

    public static CQMsg getTempMsg(String testMsg){
        return new CQMsg(-1, -1, -1, -1, null, testMsg, -1, MsgType.TEAMSPEAK_MSG);
    }
}
