package com.aye10032.Functions;

import com.aye10032.Utils.MHWUtil;
import com.aye10032.Zibenbot;

/**
 * @author Dazo66
 */
public class DianGuaiFunc extends BaseFunc {

    MHWUtil mhwUtil;

    public DianGuaiFunc(Zibenbot zibenbot) {
        super(zibenbot);
    }

    public void setUp() {
        mhwUtil = new MHWUtil();
    }

    public void run(CQMsg cqmsg) {
        if (cqmsg.msg.startsWith("点怪")) {
            if(cqmsg.msg.contains("冰原")){
                String aim = mhwUtil.getIceAim();
                zibenbot.replyMsg(cqmsg, aim);
            }else {
                String aim = mhwUtil.getAim();
                zibenbot.replyMsg(cqmsg, aim);
            }
        }
    }
}
