package com.aye10032.Functions;

import com.aye10032.Zibenbot;
import org.meowy.cqp.jcq.entity.CoolQ;
import org.meowy.cqp.jcq.message.CQCode;

/**
 * @author Dazo66
 */
public abstract class BaseFunc implements IFunc {

    protected boolean isEnable = true;
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

    @Override
    public void setEnable() {
        this.isEnable = true;
    }

    @Override
    public void setdisable() {
        this.isEnable = false;
    }

    @Override
    public boolean isEnable() {
        return isEnable;
    }

    public void replyMsg(CQMsg fromMsg, String msg) {
        if (zibenbot!= null) {
            zibenbot.replyMsg(fromMsg, msg);
        } else {
            System.out.println(msg);
        }

    }
}
