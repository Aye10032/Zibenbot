package com.dazo66.test;

import com.aye10032.Utils.HttpUtils;
import com.aye10032.Zibenbot;
import okhttp3.OkHttpClient;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;

public class xmltest {

    public static void main(String[] args) {
        OkHttpClient client = new OkHttpClient.Builder().proxy(Zibenbot.getProxy()).build();
        try {
            InputStream stream = HttpUtils.getInputStreamFromNet("http://rsshub.app/weibo/user/6279793937", client);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(stream);
            NodeList nl = doc.getElementsByTagName("item");
            for (int i = 0; i < nl.getLength(); i++) {
                System.out.println(findNode(nl.item(i).getChildNodes(), "title").getTextContent());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }

    public static Node findNode(NodeList nl, String name){
        for (int i = 0; i < nl.getLength(); i++) {
            if (nl.item(i).getNodeName().equals(name)) {
                return nl.item(i);
            }
        }
        return null;
    }

    public static Header[] getHeaders() {
        return new Header[]{
                buildHeader("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9"),
                buildHeader("accept-encoding", "gzip, deflate, br"),
                buildHeader("accept-language", "zh-CN,zh;q=0.9,en;q=0.8"),
                buildHeader("pragma", "no-cache"),
                buildHeader("cache-control", "no-cache"),
                buildHeader("origin", "https://rsshub.app"),
                buildHeader("referer", "https://rsshub.app/"),
                buildHeader("connection", "keep-alive"),
                buildHeader("cookie", "__cfduid=d1f677655c856c119d43c2ae05ff745c31591450728"),
                buildHeader("sec-fetch-site", "none"),
                buildHeader("sec-fetch-dest", "document"),
                buildHeader("sec-fetch-mode", "navigate"),
                buildHeader("sec-fetch-user", "?1"),
                buildHeader("upgrade-insecure-requests", "1"),
                buildHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.61 Safari/537.36")
        };
    }

    public static Header buildHeader(String name, String value) {
        return new BasicHeader(name, value);
    }


}
