package com.dazo66.test;

import com.aye10032.Utils.TimeUtil.TimeCycle;
import com.aye10032.Utils.TimeUtil.TimeConstant;

public class ClassTest {



    public static void main(String[] args) {
        TimeCycle cycle = date -> null;
        TimeCycle cycle2 = date -> null;
        TimeCycle cycle3 = date -> null;
        System.out.println(cycle.getClass().getSimpleName());
        System.out.println(cycle2.getClass().getSimpleName());
        System.out.println(cycle3.getClass().getSimpleName());
        System.out.println(TimeConstant.PER_DAY.getClass().getSimpleName());
    }


}
