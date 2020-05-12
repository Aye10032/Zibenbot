package com.dazo66.test;

import com.aye10032.Functions.CQMsg;
import com.aye10032.Functions.FangZhouDiaoluoFunc;
import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicHeader;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

public class fangzhoudiaoluoTest {
    public static void main(String[] args) throws IOException, InterruptedException {
        FangZhouDiaoluoFunc func = new FangZhouDiaoluoFunc();
        func.update();
        func.run(new CQMsg(-1,-1, -1, -1, null, ".方舟掉落 zs", -1, null));
        HttpClient client = HttpClientBuilder.create().setDefaultHeaders(Arrays.asList(getHeaders())).build();
        HttpResponse httpResponse =client.execute(new HttpGet("https://api.aog.wiki/materials//tier/1"));
        System.out.println(IOUtils.toString(httpResponse.getEntity().getContent()));

        System.out.println();
        //func.run(new CQMsg(-1,-1, -1, -1, null, ".方舟掉落 双击纳米片", -1, null));
        //func.run(new CQMsg(-1,-1, -1, -1, null, "是", -1, null));
        /*BotConfigFunc configFunc = new BotConfigFunc(null);
        configFunc.setUp();
        String img_url = "https://img.nga.178.com/attachments/mon_202004/05/-klbw3Q5-2mbmXeZ3rT3cS2io-1bf.png";
        img_url = configFunc.getConfig("fzdl_img", img_url);
        String finalimgUrl = img_url;
        configFunc.addListener(new ConfigListener("fzdl_img", () -> {
            String url = configFunc.getConfig("fzdl_img", finalimgUrl);
            try {
                func.update_img(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));

        configFunc.run(new CQMsg(-1,-1, -1, -1, null, ".setconfig fzdl_img https://dragalialost.akamaized.net/attached/cartoon/images/15ab5fb800afb870a44f17f09c4455e2.png", -1, null));
    */}

    public static Header[] getHeaders() {
        return new Header[]{
                buildHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8"), buildHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2272.118 Safari/537.36"),
                buildHeader("Accept-Encoding", "gzip, deflate, sdch"),
                buildHeader("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6"),
                buildHeader("pragma", "no-cache"),
                buildHeader("origin", "https://aog.wiki"),
                buildHeader("referer", "https://aog.wiki/"),
                buildHeader("Connection", "keep-alive"),
                buildHeader("sec-fetch-site", "same-site"),
                buildHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.129 Safari/537.36")
        };
    }

    public static Header buildHeader(String name, String value) {
        return new BasicHeader(name, value);
    }

    public static CookieStore getCookie() {
        String now = Long.toString(System.currentTimeMillis());
        //Creating the CookieStore object
        CookieStore cookiestore = new BasicCookieStore();


        //Adding the created cookies to cookie store
        cookiestore.addCookie(buildCookie("bbsmisccookies", "%7B%22uisetting%22%3A%7B0%3A%22a%22%2C1%3A1586074151%7D%2C%22pv_count_for_insad%22%3A%7B0%3A1%2C1%3A1586106022%7D%2C%22insad_views%22%3A%7B0%3A0%2C1%3A1586106022%7D%7D", ".ngabbs.com"));
        cookiestore.addCookie(buildCookie("lastpath", "/read.php?tid=19069337&rand=651", ".ngabbs.com"));
        cookiestore.addCookie(buildCookie("guestJs", now, ".ngabbs.com"));
        cookiestore.addCookie(buildCookie("lastvisit", now, ".ngabbs.com"));
        cookiestore.addCookie(buildCookie("ngaPassportUid", "guest05e8990f89e41f", ".ngabbs.com"));
        cookiestore.addCookie(buildCookie("taihe_bi_sdk_session", "3e995a9d14a7e0f30b71e53ba46114ea", ".ngabbs.com"));
        cookiestore.addCookie(buildCookie("taihe_bi_sdk_uid", "4dedbe988780ba591166662922100910", ".ngabbs.com"));

        return cookiestore;
    }

    public static Cookie buildCookie(String name, String value, String domain){
        BasicClientCookie clientcookie = new BasicClientCookie(name, value);
        clientcookie.setDomain(domain);
        clientcookie.setPath("/");
        return clientcookie;
    }
}
