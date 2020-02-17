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

    public void setUp(Zibenbot zibenbot) {
        mhwUtil = new MHWUtil();
    }

    public void run(CQMsg CQmsg) {
        if (CQmsg.msg.contains("点怪")) {
            String aim = mhwUtil.getAim();
            CQ.sendGroupMsg(CQmsg.fromGroup, aim);
        }
    }
}
