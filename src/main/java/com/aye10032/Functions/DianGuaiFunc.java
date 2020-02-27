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
        if (cqmsg.msg.contains("点怪")) {
            String aim = mhwUtil.getAim();
            zibenbot.replyGroupMsg(cqmsg, aim);
        }
    }
}
