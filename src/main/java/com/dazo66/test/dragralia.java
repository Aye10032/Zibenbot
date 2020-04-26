package com.dazo66.test;

import com.aye10032.TimeTask.DragraliaTask;
import com.aye10032.Zibenbot;

import java.io.IOException;

public class dragralia {

    public static void main(String[] args) throws IOException {
        System.out.println("<><1><><2>1<><><>123<><".replaceAll("<[^<>]*>+?", ""));
        System.out.println(System.currentTimeMillis());
        Zibenbot zibenbot = new Zibenbot();
        DragraliaTask task = new DragraliaTask(zibenbot);
        zibenbot.pool.add(task);


    }


}
