package com.aye10032;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MHWUtil {

    private List<String> monsterList = new ArrayList<String>();
    private List<String> armsList = new ArrayList<String>();

    public MHWUtil(){
        monsterList.add("蛮鄂");
        monsterList.add("丝瓜");
        monsterList.add("尸套");
        monsterList.add("烤鱼");
        monsterList.add("爆锤");
        monsterList.add("鹿首精");
        monsterList.add("惨爪");
        monsterList.add("麒麟");
        monsterList.add("钢龙");
        monsterList.add("骚鸟");
        monsterList.add("泥鱼龙");
        monsterList.add("冥灯龙");
        monsterList.add("熔山龙");
        monsterList.add("记者");
        monsterList.add("角龙");
        monsterList.add("黑角");
        monsterList.add("炎喵");
        monsterList.add("大凶鄂龙");
        monsterList.add("中分哥");
        monsterList.add("肥宅");
        monsterList.add("飞雷龙");
        monsterList.add("娜娜子");
        monsterList.add("咩咩子");
        monsterList.add("浮空龙");
        monsterList.add("阿爆");
        monsterList.add("毒妖鸟");
        monsterList.add("贝爷");
        monsterList.add("土砂龙");
        monsterList.add("渣渣辉");
        monsterList.add("骨锤龙");
        monsterList.add("雌火龙");
        monsterList.add("樱火龙");
        monsterList.add("雄火龙");
        monsterList.add("苍火龙");
        monsterList.add("风飘龙");
        monsterList.add("古代鹿首精");

        armsList.add("狩猎笛");
        armsList.add("大锤");
        armsList.add("太刀");
        armsList.add("片手");
        armsList.add("双刀");
        armsList.add("大剑");
        armsList.add("轻弩");
        armsList.add("重弩");
        armsList.add("操虫棍");
        armsList.add("弓");
        armsList.add("铳枪");
        armsList.add("长枪");
        armsList.add("盾斧");
        armsList.add("斩斧");
    }

    public String getAim(){
        Random random = new Random();
        int m = random.nextInt(monsterList.size());
        int n = random.nextInt(armsList.size());

        String aim = "用" + armsList.get(n) + "打" + monsterList.get(m);
        return aim;
    }

}
