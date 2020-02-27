package com.dazo66.test;

import com.google.gson.Gson;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class fangzhoudiaoluoTest {
    public static void main(String[] args) throws IOException {
        Gson gson = new Gson();
        HttpClient client = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("https://arkonegraph.herokuapp.com/materials/tier/1");
        CloseableHttpResponse response1 = (CloseableHttpResponse) client.execute(httpGet);
        DiaoluoType diaoluoType = gson.fromJson(new InputStreamReader(response1.getEntity().getContent()), DiaoluoType.class);


        for (DiaoluoType.Material material : diaoluoType.material) {
            System.out.println(Module.module.getString(material));
            System.out.println("\n");
        }
    }



}
