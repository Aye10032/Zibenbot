package com.aye10032.Functions;

import com.aye10032.Utils.FoodUtil;
import com.aye10032.Zibenbot;

/**
 * @author Dazo66
 */
public class EatFunc extends BaseFunc {

    FoodUtil foodUtil;

    public EatFunc(Zibenbot zibenbot) {
        super(zibenbot);
    }

    public void setUp(Zibenbot zibenbot) {
        this.foodUtil = new FoodUtil();
    }

    public void run(CQMsg CQmsg) {
        if (CQmsg.msg.contains("晚饭")) {
            String food = foodUtil.eatWhat();
            CQ.sendGroupMsg(CQmsg.fromGroup, food);
        } else if (CQmsg.msg.contains("主食列表")) {
            String food = foodUtil.mlist();
            CQ.sendGroupMsg(CQmsg.fromGroup, food);
        } else if (CQmsg.msg.contains("小吃列表")) {
            String food = foodUtil.slist();
            CQ.sendGroupMsg(CQmsg.fromGroup, food);
        }
    }
}
