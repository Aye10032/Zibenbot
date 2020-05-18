package com.dazo66.test;

import java.io.*;

public class phantomJs {

    public static void main(String[] args) throws IOException {
        String BLANK = "  ";
        String file = "res/test/" +System.currentTimeMillis()/1000 +".png";
        Process process = Runtime.getRuntime().exec(
                "res/phantomjs/bin/phantomjs.exe" + BLANK //你的phantomjs.exe路径
                        + "res/phantomjs/screenshot.js" + BLANK //就是上文中那段javascript脚本的存放路径
                        + "https://twitter.com/ainidekilukoto5" + BLANK //你的目标url地址
                        + file + BLANK
                        + 5000
        );//你的图片输出路径
        InputStream inputStream = process.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String tmp = "";
        while ((tmp = reader.readLine()) != null) {
            reader.close();
            break;
        }
        Runtime.getRuntime().exec("rundll32 url.dll FileProtocolHandler file://" + new File(file).getAbsolutePath());
        System.out.println("渲染成功...");
    }


}
