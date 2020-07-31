package com.dazo66.test;

import com.aye10032.Functions.funcutil.CQMsg;
import com.aye10032.Functions.DraSummonSimulatorFunc;
import com.aye10032.Zibenbot;

import java.util.Arrays;
import java.util.Scanner;

/**
 * @author Dazo66
 */
public class DraSummonSimulatorTest {

    public static void main(String[] args) {
        DraSummonSimulatorFunc func = new DraSummonSimulatorFunc(new Zibenbot());

        System.out.println(Arrays.toString(func._split_1(" ", ".龙约 i18n [T em pl ar Hope]]]] [ 霍 " +
                "普（骑士 ver ）]")));
        System.out.println(Arrays.toString(func._split_1(" ", ".龙约 i18n Hope 霍普")));
        System.out.println(Arrays.toString(func._split_1(" ", ".龙约 i18n [Xiao Lei] [小蕾]")));
        System.out.println(Arrays.toString(func._split_1(" ", ".龙约 i18n [Hope] [霍普]")));
        func.setUp();

        /*func.run(CQMsg.getTempMsg(".龙约 十连"));
        System.out.println();
        func.run(CQMsg.getTempMsg(".龙约 单抽"));
        System.out.println();
        func.run(CQMsg.getTempMsg(".龙约 卡池列表"));
        System.out.println();
        func.run(CQMsg.getTempMsg(".龙约 卡池切换 2"));
        System.out.println();
        func.run(CQMsg.getTempMsg(".龙约 十连"));
        System.out.println();
        func.run(CQMsg.getTempMsg(".龙约 十连"));
        System.out.println();
        func.run(CQMsg.getTempMsg(".龙约 i18n [Templar Hope] [霍普（骑士ver）]"));*/

        Scanner sc = new Scanner(System.in);
        while (true) {
            String s = sc.nextLine();
            func.run(CQMsg.getTempMsg(s));
        }

        //DraSummonSimulatorFunc.UserDate o = func.getTempUser();

        /*StringBuilder builder = new StringBuilder();
        builder.append("total:" + o.total).append("\n");
        builder.append("total_char:").append(o.currentEvent.getPoolCount()).append("\n");
        builder.append("map_count:").append(o.summerRes.size()).append("\n");
        java.text.DecimalFormat df = new java.text.DecimalFormat("##0.0000");
        o.summerRes.forEach((k , v) -> {
            builder.append(df.format( (double) 100*v/o.total));
            builder.append("%").append(", ").append(k.rarity_num).append(", ").append(k.title)
            .append("\n");
        });

        System.out.println(builder.toString());*/

    }

}
