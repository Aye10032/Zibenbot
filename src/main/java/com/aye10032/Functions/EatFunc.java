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

    public void setUp() {
        this.foodUtil = new FoodUtil();
    }

    public void run(CQMsg CQmsg) {
        if (CQmsg.msg.equals("晚饭")) {
            if (CQmsg.fromGroup == 792666782L){
                String food = foodUtil.eatWhatWithSSR();
                zibenbot.replyMsg(CQmsg, food);
            }else {
                String food = foodUtil.eatWhat();
                zibenbot.replyMsg(CQmsg, food);
            }
        }
    }
}
