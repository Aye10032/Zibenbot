package com.dazo66.test;

import com.aye10032.Functions.BiliFunc;
import com.aye10032.Utils.AyeCompile;
import com.aye10032.Utils.BiliInfo;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class test {

    public static void main(String[] args) {

        System.out.println(BiliFunc.formatToW(12312312));
        System.out.println((int)(3 % 2.5f));

        try {
            BufferedImage image = ImageIO.read(new File("res\\Arkonegraph.png"));
            ImageIO.write(image.getSubimage(0, 0 ,100, 200), "PNG", new File("res/test.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }


        AyeCompile compile = new AyeCompile("https://b23.tv/BV124411p7CJ");
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
