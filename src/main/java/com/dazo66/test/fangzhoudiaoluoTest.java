package com.dazo66.test;

import com.aye10032.Functions.CQMsg;
import com.aye10032.Functions.FangZhouDiaoluoFunc;

import java.io.IOException;

public class fangzhoudiaoluoTest {
    public static void main(String[] args) throws IOException, InterruptedException {
        FangZhouDiaoluoFunc func = new FangZhouDiaoluoFunc();
        func.update();
        func.run(new CQMsg(-1, -1, -1, -1, null, ".方舟素材 糖", -1));
        func.run(new CQMsg(-1, -1, -1, -1, null, ".方舟素材 双极纳米片", -1));

        func.run(new CQMsg(-1, -1, -1, -1, null, ".方舟素材 全新装置", -1));

    }






}
