package com.dazo66.test;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class danmuTest {


    public static void main(String[] args) throws IOException {
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("https://api.bilibili.com/x/v1/dm/list.so?oid=171147896");
        CloseableHttpResponse httpResponse = closeableHttpClient.execute(httpGet);
        HttpEntity httpEntity = httpResponse.getEntity();
        String en = EntityUtils.toString(httpEntity, "utf-8");
        System.out.println(en);
    }

}
