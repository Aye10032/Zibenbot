package com.dazo66.test;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

/**
 * @author Dazo66
 */
public class BilibiliTest {

    public static void main(String[] args) {
        JsonParser jsonParser = new JsonParser();
        JsonElement element = jsonParser.parse("{\"code\":\"-404\",\"message\":\"啥都木有\",\"ttl\":1}");
        System.out.println(element.getAsJsonObject().get("code").getAsString());
        System.out.println(1 + "a");

    }

}
