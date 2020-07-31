package com.aye10032.Functions.funcutil;

import com.aye10032.Functions.funcutil.CQMsg;
import com.aye10032.Functions.funcutil.IFunc;
import com.aye10032.Zibenbot;
import org.meowy.cqp.jcq.entity.CoolQ;
import org.meowy.cqp.jcq.message.CQCode;

/**
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

    public void replyMsg(CQMsg fromMsg, String msg) {
        if (zibenbot!= null) {
            zibenbot.replyMsg(fromMsg, msg);
        } else {
            System.out.println(msg);
        }

    }
}
