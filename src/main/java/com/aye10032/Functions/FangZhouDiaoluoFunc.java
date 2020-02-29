package com.aye10032.Functions;

import com.aye10032.Zibenbot;
import com.dazo66.test.DiaoluoType;
import com.google.gson.Gson;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;

import java.io.File;
import java.io.FileInputStream;
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

    public FangZhouDiaoluoFunc(Zibenbot zibenbot) {
        super(zibenbot);
    }

    public void setUp() {

        Gson gson = new Gson();
        HttpClient client = HttpClients.createDefault();
        DiaoluoType diaoluoType = null;
        try {
            for (int i = 1; i <= 5; i++) {
                HttpGet httpGet = new HttpGet("https://arkonegraph.herokuapp.com/materials/tier/" + String.valueOf(i));
                CloseableHttpResponse response1 = (CloseableHttpResponse) client.execute(httpGet);
                if (diaoluoType == null) {
                    diaoluoType = gson.fromJson(new InputStreamReader(response1.getEntity().getContent()), DiaoluoType.class);
                } else {
                    diaoluoType.material = ArrayUtils.addAll(diaoluoType.material, gson.fromJson(new InputStreamReader(response1.getEntity().getContent()), DiaoluoType.class).material);
                }

            }
            this.type = diaoluoType;

            File file = new File("res/name-id.txt");
            List<String> strings = IOUtils.readLines(new FileInputStream(file));
            List<DiaoluoType.HeChenType> list = new ArrayList<>();
            for (String s : strings) {
                if ("".equals(s.trim()) || s.startsWith("//")) {
                    //忽略注释和空行
                    continue;
                } else {
                    List<String> modules = getModules(s);
                    String s1 = modules.get(0);
                    Integer integer = Integer.parseInt(s1.trim());
                    System.out.println(integer);
                    list.add(new DiaoluoType.HeChenType(integer, getVers(modules.get(1)).toArray(new String[]{}), getVers(modules.get(2)).toArray(new String[]{})));
                }
            }
        } catch (Exception e) {
            Zibenbot.logger.warning(e.getMessage());
        }

    }

    @Override
    public void run(CQMsg CQmsg) {
        if (CQmsg.msg.startsWith(".方舟素材")) {

        }
    }

}
