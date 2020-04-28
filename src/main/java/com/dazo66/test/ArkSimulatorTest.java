package com.dazo66.test;

import com.aye10032.Utils.HttpUtils;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import okhttp3.OkHttpClient;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Dazo66
 */
public class ArkSimulatorTest {

    final  static Pattern pattern1 = Pattern.compile("\\w+:");
    final  static Pattern pattern2 = Pattern.compile("\\[\\{name:\"能天使[\\w\\W]{1,}(]}]){1}?");
    public static void main(String[] args) throws IOException {
        String s = "[{\n" + "\t\ttype: 'limit',\n" + "\t\tname: '【遗愿焰火】限定寻访',\n" + "\t\textra: '(05.01 开启)',\n" + "\t\t6: {\n" + "\t\t\tp: 70,\n" + "\t\t\tlist: ['W', '温蒂']\n" + "\t\t},\n" + "\t\t5: {\n" + "\t\t\tp: 50,\n" + "\t\t\tlist: ['极境']\n" + "\t\t}\n" + "\t},\n" + "\t{\n" + "\t\ttype: 'default',\n" + "\t\tname: '【联合行动】定向寻访',\n" + "\t\textra: '(05.15 开启)',\n" + "\t\t6: {\n" + "\t\t\tp: 50,\n" + "\t\t\tlist: ['煌', '塞雷娅', '莫斯提马', '赫拉格'],\n" + "\t\t\tonly: true\n" + "\t\t},\n" + "\t\t5: {\n" + "\t\t\tp: 50,\n" + "\t\t\tlist: ['临光', '守林人', '灰喉', '吽', '送葬人', '格劳克斯'],\n" + "\t\t\tonly: true\n" + "\t\t}\n" + "\t},\n" + "\t{\n" + "\t\ttype: 'default',\n" + "\t\tname: '【往日幻象】限时寻访',\n" + "\t\t6: {\n" + "\t\t\tp: 50,\n" + "\t\t\tlist: ['傀影'],\n" + "\t\t\tremove: ['W', '温蒂']\n" + "\t\t},\n" + "\t\t5: {\n" + "\t\t\tp: 50,\n" + "\t\t\tlist: ['巫恋', '白面鸮'],\n" + "\t\t\tremove: ['极境']\n" + "\t\t},\n" + "\t\t4: {\n" + "\t\t\tp: 20,\n" + "\t\t\tlist: ['刻刀']\n" + "\t\t}\n" + "\t},\n" + "\t{\n" + "\t\ttype: 'default',\n" + "\t\tname: '【标准寻访】',\n" + "\t\t6: {\n" + "\t\t\tp: 50,\n" + "\t\t\tlist: ['陈', '闪灵'],\n" + "\t\t\tremove: ['傀影', 'W', '温蒂']\n" + "\t\t},\n" + "\t\t5: {\n" + "\t\t\tp: 50,\n" + "\t\t\tlist: ['可颂', '凛冬', '陨星'],\n" + "\t\t\tremove: ['巫恋', '极境']\n" + "\t\t},\n" + "\t\t4: {\n" + "\t\t\tremove: ['刻刀']\n" + "\t\t}\n" + "\t}\n" + "\t// {\n" + "\t//   type: 'limit',\n" + "\t//   name: '限定寻访 -【地生五金】',\n" + "\t//   6: {\n" + "\t//     p: 70,\n" + "\t//     list: ['年', '阿'],\n" + "\t//   },\n" + "\t//   5: {\n" + "\t//     p: 50,\n" + "\t//     list: ['吽']\n" + "\t//   }\n" + "\t// }\n" + "]";
        Matcher matcher = pattern1.matcher(s);
        List<String> list = new ArrayList<>();
        while (matcher.find()) {
            list.add(matcher.group());
        }
        for (String s1 : list) {
            //s = s.replace(s1, "'" + s1.substring(0, s1.length() - 1) + "':");
            s = s.replace("'", "\"");
        }
        JsonParser parser = new JsonParser();
        JsonElement e = parser.parse(s);
        System.out.println(e);
        //寻访模拟
        //https://amiya.xyz/tool/js/simulator.f5459b0e.js
        //app.js
        //https://amiya.xyz/tool/js/app.a327f22e.js
        OkHttpClient client = new OkHttpClient.Builder().build();
        s = IOUtils.toString(HttpUtils.getInputStreamFromNet("https://amiya.xyz/tool/js/app.a327f22e.js", client));
        List<String> list1 = new ArrayList<>();
        Matcher matcher2 = pattern2.matcher(s);
        while (matcher2.find()) {
            list1.add(matcher2.group());
        }
        list1.forEach(s1 -> {
            System.out.println(s1);
            JsonElement e1 = parser.parse(s1);
            System.out.println(e1);
        });

    }

}
