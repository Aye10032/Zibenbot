package com.dazo66.test;

import com.aye10032.Functions.CQMsg;
import com.aye10032.Functions.FangZhouDiaoluoFunc;
import org.apache.http.Header;
import org.apache.http.client.CookieStore;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicHeader;

import java.io.IOException;

public class fangzhoudiaoluoTest {
    public static void main(String[] args) throws IOException, InterruptedException {
        FangZhouDiaoluoFunc func = new FangZhouDiaoluoFunc();
        func.update();
        func.run(new CQMsg(-1,-1, -1, -1, null, ".方舟掉落 糖", -1, null));
    }

    public static Header[] getHeaders() {
        return new Header[]{
                buildHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8"), buildHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2272.118 Safari/537.36"),
                buildHeader("Accept-Encoding", "gzip, deflate, sdch"),
                buildHeader("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6"),
                buildHeader("Cache-Control", "no-cache"),
                buildHeader("Connection", "keep-alive"),
                buildHeader("Host", ".ngabbs.com"),
                buildHeader("Cookie", "ngaPassportUid=guest05e8990f89e41f; bbsmisccookies=%7B%22uisetting%22%3A%7B0%3A%22a%22%2C1%3A1586074151%7D%2C%22pv_count_for_insad%22%3A%7B0%3A1%2C1%3A1586106022%7D%2C%22insad_views%22%3A%7B0%3A0%2C1%3A1586106022%7D%7D; taihe_bi_sdk_uid=4dedbe988780ba591166662922100910; taihe_bi_sdk_session=3e995a9d14a7e0f30b71e53ba46114ea; lastvisit=1586074837; guestJs=1586074837"),
                buildHeader("Referer", "https://ngabbs.com/read.php?tid=19069337&rand=111")};
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
