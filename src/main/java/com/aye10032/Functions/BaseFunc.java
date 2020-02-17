package com.aye10032.Functions;

import com.aye10032.Zibenbot;
import org.meowy.cqp.jcq.entity.CoolQ;
import org.meowy.cqp.jcq.message.CQCode;

/**
 * @author Dazo66
 */
public abstract class BaseFunc implements IFunc {

    protected boolean isEnable = true;
    protected Zibenbot zibenbot;
    protected CoolQ CQ;
    protected CQCode CC;

    public BaseFunc(Zibenbot zibenbot){
        this.zibenbot = zibenbot;
        this.CQ = zibenbot.getCoolQ();
        this.CC = zibenbot.getCQCode();
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
}
