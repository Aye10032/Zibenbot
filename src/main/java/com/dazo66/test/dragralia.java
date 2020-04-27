package com.dazo66.test;

import com.aye10032.TimeTask.DragraliaTask;
import com.aye10032.Utils.TimeUtil.TimeConstant;
import com.aye10032.Zibenbot;

import java.io.IOException;
import java.util.Date;

public class dragralia {

    public static void main(String[] args) throws IOException {
        System.out.println("<><1><><2>1<><><>123<><".replaceAll("<[^<>]*>+?", ""));
        System.out.println(System.currentTimeMillis());
        Zibenbot zibenbot = new Zibenbot();
        DragraliaTask task = new DragraliaTask(zibenbot);
        task.setTiggerTime(new Date(System.currentTimeMillis() + 10)).setCycle(TimeConstant.PER_MIN);
        zibenbot.pool.add(task);


    }


}
