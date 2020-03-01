package com.aye10032.Functions;

import com.aye10032.Zibenbot;
import com.dazo66.test.DiaoluoType;
import com.dazo66.test.Module;
import com.google.gson.Gson;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.http.client.HttpClient;
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

    public FangZhouDiaoluoFunc(Zibenbot zibenbot) {
        super(zibenbot);
    }

    @Override
    public void setUp() {
        update();
    }

    public void update(){
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
                    int integer = Integer.parseInt(s1.trim());
                    System.out.println(integer);
                    list.add(new DiaoluoType.HeChenType(integer, getVers(modules.get(1)).toArray(new String[]{}), getVers(modules.get(2)).toArray(new String[]{})));
                }
            }
            name_idList = list;
            stream.close();
            client.close();
            module.update();
        } catch (Exception e) {
            Zibenbot.logger.warning(e.getMessage());
        }
        Zibenbot.logger.info("fangzhoudiaoluo update end");
    }

    public static InputStream getInputStreamFromNet(String web, HttpClient client) throws IOException {
        return client.execute(new HttpGet(web)).getEntity().getContent();

    }

    @Override
    public void run(CQMsg CQmsg) {
        if (CQmsg.msg.startsWith(".方舟素材")) {

        }
    }

}
