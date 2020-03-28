package com.dazo66.test;

import com.aye10032.Utils.AyeCompile;
import com.aye10032.Utils.BiliInfo;

public class test {

    public static void main(String[] args) {
        AyeCompile compile = new AyeCompile("https://b23.tv/BV19t4y1m7zL");
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
