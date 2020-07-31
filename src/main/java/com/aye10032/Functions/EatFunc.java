package com.aye10032.Functions;

import com.aye10032.Functions.funcutil.BaseFunc;
import com.aye10032.Functions.funcutil.CQMsg;
import com.aye10032.Utils.ConfigLoader;
import com.aye10032.Utils.FoodUtil;
import com.aye10032.Utils.food.FoodClaass;
import com.aye10032.Zibenbot;

/**
 * @author Dazo66
 */
public class EatFunc extends BaseFunc {

    FoodUtil foodUtil;
    FoodClaass foodClaass = ConfigLoader.load(zibenbot.appDirectory + "/foodData.json", FoodClaass.class);

    public EatFunc(Zibenbot zibenbot) {
        super(zibenbot);
    }

    public void setUp() {
        this.foodUtil = new FoodUtil();
    }

    public void run(CQMsg CQmsg) {
        if (CQmsg.msg.equals("晚饭")) {
            if (CQmsg.fromGroup == 792666782L) {
                if (foodClaass.getTimes(CQmsg.fromClient) == 99) {
                    foodClaass.resetTimes(CQmsg.fromClient);
                    String[] food = foodUtil.eatGuaranteed(3);
                    zibenbot.replyMsg(CQmsg, food[0]);
                } else {
                    String[] food = foodUtil.eatWhatWithSSR();
                    zibenbot.replyMsg(CQmsg, food[0]);
                    if (food[1].equals("3")) {
                        foodClaass.resetTimes(CQmsg.fromClient);
                    } else {
                        foodClaass.addOne(CQmsg.fromClient);
                    }
                }
            } else {
                String food = foodUtil.eatWhat();
                zibenbot.replyMsg(CQmsg, food);
            }
            ConfigLoader.save(zibenbot.appDirectory + "/foodData.json", FoodClaass.class, foodClaass);
        } else if (CQmsg.fromGroup == 792666782L && CQmsg.msg.equals("晚饭十连")) {
            StringBuilder foodBuilder = new StringBuilder();
            boolean hasSSR = false;
            for (int i = 0; i < 9; i++) {
                if (foodClaass.getTimes(CQmsg.fromClient) == 99) {
                    foodClaass.resetTimes(CQmsg.fromClient);
                    String[] food = foodUtil.eatGuaranteed(3);
                    zibenbot.replyMsg(CQmsg, food[0]);
                } else {
                    String[] food = foodUtil.eatWhatWithSSR();
                    foodBuilder.append(food[0]).append("\n");
                    switch (food[1]) {
                        case "1":
                            foodClaass.addOne(CQmsg.fromClient);
                            break;
                        case "2":
                            hasSSR = true;
                            foodClaass.addOne(CQmsg.fromClient);
                            break;
                        case "3":
                            hasSSR = true;
                            foodClaass.resetTimes(CQmsg.fromClient);
                            break;
                    }
                }
            }

            if (foodClaass.getTimes(CQmsg.fromClient) == 99) {
                foodClaass.resetTimes(CQmsg.fromClient);
                String[] food = foodUtil.eatGuaranteed(3);
                foodBuilder.append(food[0]);
            } else {
                if (!hasSSR) {
                    String[] food = foodUtil.eatGuaranteed(2);
                    foodBuilder.append(food[0]);

                    if (food[1].equals("3")) {
                        foodClaass.resetTimes(CQmsg.fromClient);
                    } else {
                        foodClaass.addOne(CQmsg.fromClient);
                    }

                } else {
                    String[] food = foodUtil.eatWhatWithSSR();
                    foodBuilder.append(food[0]);
                    switch (food[1]) {
                        case "1":
                            foodClaass.addOne(CQmsg.fromClient);
                            break;
                        case "2":
                            hasSSR = true;
                            foodClaass.addOne(CQmsg.fromClient);
                            break;
                        case "3":
                            hasSSR = true;
                            foodClaass.resetTimes(CQmsg.fromClient);
                            break;
                    }
                }
            }
            zibenbot.replyMsg(CQmsg, foodBuilder.toString());
            ConfigLoader.save(zibenbot.appDirectory + "/foodData.json", FoodClaass.class, foodClaass);
        }
    }
}
