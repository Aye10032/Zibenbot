package com.aye10032.Functions;

import com.aye10032.Zibenbot;

/**
 * @author Dazo66
 */
public abstract class BaseFunc implements IFunc {

    protected boolean isEnable = true;
    protected Zibenbot zibenbot;

    public BaseFunc(Zibenbot zibenbot) {
        this.zibenbot = zibenbot;
    }

    public void setEnable() {
        this.isEnable = true;
    }

    public void setdisable() {
        this.isEnable = false;
    }

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
