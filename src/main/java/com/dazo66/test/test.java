package com.dazo66.test;

import com.aye10032.Utils.AyeCompile;
import com.aye10032.Utils.BiliInfo;

public class test {

    public static void main(String[] args) {
        AyeCompile compile = new AyeCompile("av114514");
        if (compile.hasAV() | compile.hasBV()) {
                BiliInfo biliInfo;
                if (compile.hasAV()) {
                    biliInfo = new BiliInfo(compile.getAVString(), "");
                } else {
                    biliInfo = new BiliInfo(compile.getBVString(), "");
                }

            System.out.println(biliInfo.getTitle());
        }
    }
}
