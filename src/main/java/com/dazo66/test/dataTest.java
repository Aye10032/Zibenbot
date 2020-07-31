package com.dazo66.test;

import com.aye10032.Functions.funcutil.CQMsg;
import com.aye10032.NLP.DataCollect;
import com.aye10032.Zibenbot;

public class dataTest {
    public static void main(String[] args) {
        DataCollect func = new DataCollect(new Zibenbot());
        func.run(CQMsg.getTempMsg("aye在嘛"));
    }

}
