package com.aye10032.Functions.funcutil;

import com.aye10032.Zibenbot;
import org.meowy.cqp.jcq.entity.CoolQ;
import org.meowy.cqp.jcq.message.CQCode;

/**
 * 基础的func的类 包装了一些可用的方法
 * @author Dazo66
 */
public abstract class BaseFunc implements IFunc {

    public Zibenbot zibenbot;
    protected String appDirectory;
    protected CoolQ CQ;
    protected CQCode CC;


    public BaseFunc(Zibenbot zibenbot) {
        this.zibenbot = zibenbot;
        if (zibenbot == null) {
            appDirectory = "";
            CQ = null;
            CC = null;
        } else {
            CQ = zibenbot.getCoolQ();
            CC = zibenbot.getCQCode();
            appDirectory = zibenbot.appDirectory;
        }
    }

    /**
     * 根据传入的消息，回复消息
     *
     * @param fromMsg 从哪来的什么消息
     * @param msg 要回复的内容
     */
    public void replyMsg(CQMsg fromMsg, String msg) {
        if (zibenbot!= null) {
            zibenbot.replyMsg(fromMsg, msg);
        } else {
            System.out.println(msg);
        }

    }
}
