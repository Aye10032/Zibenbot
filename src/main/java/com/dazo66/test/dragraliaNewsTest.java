package com.dazo66.test;

import com.aye10032.Functions.CQMsg;
import com.aye10032.Functions.DragraliaNewsFunc;
import com.aye10032.Zibenbot;

import java.util.Scanner;

public class dragraliaNewsTest {

    public static void main(String[] args) {
        DragraliaNewsFunc func = new DragraliaNewsFunc(new Zibenbot());
        func.zibenbot.appDirectory = "res";
        func.setUp();
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入测试内容:");
        while (true) {
            String s = sc.nextLine();
            func.run(CQMsg.getTempMsg(s));
        }

    }


}
