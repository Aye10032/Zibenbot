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
                String[] food = foodUtil.eatWhatWithSSR();
                zibenbot.replyMsg(CQmsg, food[0]);
            }else {
                String food = foodUtil.eatWhat();
                zibenbot.replyMsg(CQmsg, food);
            }
        }else if (CQmsg.fromGroup == 792666782L&&CQmsg.msg.equals("晚饭十连")){
            StringBuilder foodBuilder = new StringBuilder();
            boolean hasSSR = false;
            for (int i = 0; i < 9; i++) {
                String[] food = foodUtil.eatWhatWithSSR();
                foodBuilder.append(food).append("\n");
                if (!food[1].equals("1")){
                    hasSSR = true;
                }
            }
            if (!hasSSR){
                foodBuilder.append(foodUtil.eatGuaranteed(2)[0]);
            }
            zibenbot.replyMsg(CQmsg, foodBuilder.toString());
        }
    }
}
