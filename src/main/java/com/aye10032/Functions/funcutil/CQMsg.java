package com.aye10032.Functions.funcutil;

import com.dazo66.command.interfaces.ICommand;
import org.meowy.cqp.jcq.entity.Anonymous;

/**
 * CQMsg的包装对象 每个传入的消息都进行包装后交由模块执行
 * @author Dazo66
 */
public class CQMsg implements ICommand {

    public int subType = -1;
    public int msgId = -1;
    public long fromGroup = -1;
    public long fromClient = -1;
    public Anonymous anonymous;
    public String msg;
    public int font;
    public MsgType type;

    public CQMsg(int subType, int msgId, long fromGroup, long fromClient, Anonymous fromAnonymous, String msg, int font, MsgType type) {
        this.subType = subType;
        this.msgId = msgId;
        this.fromGroup = fromGroup;
        this.fromClient = fromClient;
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

    @Override
    public String[] getCommandPieces() {
        return msg.trim().replaceAll(" +", " ").split(" ");
    }

    /**
     * 生成一个测试用的CQmsg对象
     *
     * @param testMsg
     * @return
     */
    public static CQMsg getTempMsg(String testMsg){
        return new CQMsg(-1, -1, 995497677L, 2375985957L, null, testMsg, -1, MsgType.GROUP_MSG);
    }
}
