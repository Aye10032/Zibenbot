package com.dazo66.test;

import com.aye10032.Functions.BiliFunc;
import com.aye10032.Functions.CQMsg;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;

/**
 * @author Dazo66
 */
public class BilibiliTest {

    public static void main(String[] args) throws IOException {
        /*String url = "https://b23.tv/BV1j64y1M7qb/";
        RequestConfig config = RequestConfig.custom().setConnectTimeout(50000).setConnectionRequestTimeout(10000).setSocketTimeout(50000).setRedirectsEnabled(false).build();
        //2.3调用HttpURLconn方法，用于发送或者接收
        CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(config).build();
        CloseableHttpResponse conn = httpClient.execute(new HttpGet(url));
        //2.4设置发送get请求

        System.out.println(conn.getHeaders("Location")[0].getValue());
        CloseableHttpResponse conn1 = httpClient.execute(new HttpGet(conn.getHeaders("Location")[0].getValue()));
*/
        BiliFunc func = new BiliFunc(null);
        func.run(CQMsg.getTempMsg("https://b23.tv/BV1j64y1M7qb/"));


    }

}
