package com.dazo66.test;

import com.aye10032.Utils.TimeUtil.ITimeAdapter;
import com.aye10032.Utils.TimeUtil.TimeConstant;

public class ClassTest {



    public static void main(String[] args) {
        ITimeAdapter cycle = date -> null;
        ITimeAdapter cycle2 = date -> null;
        ITimeAdapter cycle3 = date -> null;
        System.out.println(cycle.getClass().getSimpleName());
        System.out.println(cycle2.getClass().getSimpleName());
        System.out.println(cycle3.getClass().getSimpleName());
        System.out.println(TimeConstant.NEXT_DAY.getClass().getSimpleName());
    }


}
