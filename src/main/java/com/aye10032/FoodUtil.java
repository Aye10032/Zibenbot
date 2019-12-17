package com.aye10032;

import java.util.Random;

public class FoodUtil {

    private String[] mainlist;
    private String[] snackList;

    public FoodUtil() {
        mainlist = new String[]{"炒米 炒面 炒河粉", "金陵鸭血粉丝 酸辣粉", "徐州牛肉汤", "淮南牛肉汤", "铁板肉夹馍", "鸡肉卷饼",
                "老北京脆皮烤鸭卤菜", "传说中的鸡蛋灌饼", "东北味烤冷面 手抓饼", "广东肠粉", "麻辣串卷饼",
                "水饺 汤团", "捞面", "炒方便面", "鸭血粉丝汤", "山东特色馅饼",  "鸡蛋肉汉堡",
                "寿司","一夜蚝情 花甲米线","烤冷面手抓饼"};
        snackList = new String[]{"满口香铁板烤肠", "烤肠 烤面筋 一元一串", "铁板煎正宗长沙臭豆腐", "正宗鸡锁骨",
                "冰糖烤梨","章鱼小丸子", "铁板煎豆腐","回味铁板鱿鱼","鹌鹑蛋"};
    }

    public String eatWhat() {
        Random random = new Random();
        int m = random.nextInt(mainlist.length);
        int n = random.nextInt(snackList.length);

        String food = mainlist[m] + " + " + snackList[n];

        return food;
    }

    public String slist(){
        String str = "";

        for (String temp:snackList) {
            str = str + temp + "\n";
        }

        return str;
    }

    public String mlist(){
        String str = "";

        for (String temp:mainlist) {
            str = str + temp + "\n";
        }

        return str;
    }

}
