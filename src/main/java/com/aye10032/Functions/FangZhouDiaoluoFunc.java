package com.aye10032.Functions;

import com.aye10032.Utils.HttpUtils;
import com.aye10032.Zibenbot;
import com.dazo66.test.DiaoluoType;
import com.dazo66.test.Module;
import com.google.gson.Gson;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.http.HttpHost;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static com.dazo66.test.Module.getModules;
import static com.dazo66.test.Module.getVers;

/**
 * @author Dazo66
 */
public class FangZhouDiaoluoFunc extends BaseFunc {

    private DiaoluoType type;
    private Module module;
    private List<DiaoluoType.HeChenType> name_idList;
    private String arkonegraphFile = zibenbot.appDirectory+"/Arkonegraph.png";

    public FangZhouDiaoluoFunc(Zibenbot zibenbot) {
        super(zibenbot);
    }

    @Override
    public void setUp() {
        update();
    }

    public void update(){
        System.out.println(arkonegraphFile);
        Zibenbot.logger.info("fangzhoudiaoluo update start");
        Gson gson = new Gson();
        CloseableHttpClient client = HttpClients.createDefault();
        DiaoluoType diaoluoType = null;
        try {
            for (int i = 1; i <= 5; i++) {
                InputStream stream = getInputStreamFromNet(
                        "https://arkonegraph.herokuapp.com/materials/tier/" + String.valueOf(i), client);
                if (diaoluoType == null) {
                    diaoluoType = gson.fromJson(new InputStreamReader(stream), DiaoluoType.class);
                } else {
                    diaoluoType.material = ArrayUtils.addAll(diaoluoType.material, gson.fromJson(
                            new InputStreamReader(stream), DiaoluoType.class).material);
                }
                stream.close();

            }
            this.type = diaoluoType;
            InputStream stream = getInputStreamFromNet(
                    "https://github.com/Aye10032/Zibenbot/raw/master/res/%E6%96%B9%E8%88%9F%E6%8E%89%E8%90%BD/name-id.txt"
                    , client);
            List<String> strings = IOUtils.readLines(new InputStreamReader(stream));
            List<DiaoluoType.HeChenType> list = new ArrayList<>();
            for (String s : strings) {
                if ("".equals(s.trim()) || s.startsWith("//")) {
                    //忽略注释和空行
                } else {
                    List<String> modules = getModules(s);
                    String s1 = modules.get(0);
                    list.add(new DiaoluoType.HeChenType(s1.trim(), getVers(modules.get(1)).toArray(new String[]{}), getVers(modules.get(2)).toArray(new String[]{})));
                }
            }
            name_idList = list;
            stream.close();
            client.close();
            Module.update();
            module = Module.module;

            //更新图片
            HttpUtils.download("https://github.com/Aye10032/Zibenbot/raw/master/res/%E6%96%B9%E8%88%9F%E6%8E%89%E8%90%BD/Arkonegraph.png"
                    , arkonegraphFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Zibenbot.logger.info("fangzhoudiaoluo update end");
    }

    public static InputStream getInputStreamFromNet(String web, HttpClient client) throws IOException {
        //设置代理IP、端口、协议（请分别替换）
        HttpHost proxy = new HttpHost("127.0.0.1", 1080, "http");

        RequestConfig config = RequestConfig.custom()
                .setProxy(proxy)
                .build();
        HttpGet get = new HttpGet(web);
        get.setConfig(config);
        return client.execute(get).getEntity().getContent();

    }

    @Override
    public void run(CQMsg CQmsg) {
        String msg = CQmsg.msg.trim();
        if (msg.startsWith(".方舟素材")) {
            String[] strings = msg.split(" ");
            int len = strings.length;
            if (len >= 2) {
                loop:
                for (int i = 1; i < len; i++) {
                    for (DiaoluoType.HeChenType type : name_idList) {
                        if (type.isThis(strings[i])) {
                            for (String id : type.calls) {
                                zibenbot.replyGroupMsg(CQmsg, module.getString(this.type.getMaterialFromID(id)));
                            }
                            break loop;
                        }
                    }
                }
            } else {
                zibenbot.replyGroupMsg(CQmsg, zibenbot.getCoolQ().getImage(arkonegraphFile));
            }
        }
    }

}
