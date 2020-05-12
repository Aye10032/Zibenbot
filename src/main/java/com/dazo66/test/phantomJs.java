package com.dazo66.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class phantomJs {

    public static void main(String[] args) throws IOException {
        String BLANK = "  ";
        Process process = Runtime.getRuntime().exec(
                "res/phantomjs/bin/phantomjs.exe" + BLANK //你的phantomjs.exe路径
                        + "res/phantomjs/screenshot.js" + BLANK //就是上文中那段javascript脚本的存放路径
                        + "https://ngabbs.com/thread.php?fid=623&rand=51" + BLANK //你的目标url地址
                        + "res/test/nga623.jpg" + BLANK
                        + 5000
        );//你的图片输出路径
        InputStream inputStream = process.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String tmp = "";
        while ((tmp = reader.readLine()) != null) {
            reader.close();
            break;
        }
        System.out.println("渲染成功...");
    }


}
