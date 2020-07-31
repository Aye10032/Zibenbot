package com.dazo66.test;

import com.aye10032.Functions.funcutil.CQMsg;
import com.aye10032.Zibenbot;

import java.util.Scanner;

public class test {

    public static void main(String[] args) {

        Zibenbot zibenbot = new Zibenbot();
        zibenbot.startup();
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入测试内容:");
        while (true) {
            String s = sc.nextLine();
            zibenbot.test(CQMsg.getTempMsg(s));
        }

    }
}
