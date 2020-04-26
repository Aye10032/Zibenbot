package com.aye10032.Functions;

import com.aye10032.Utils.ConfigListener;
import com.aye10032.Utils.HttpUtils;
import com.aye10032.Utils.TimeUtil.TimedTask;
import com.aye10032.Utils.fangzhoudiaoluo.DiaoluoType;
import com.aye10032.Utils.fangzhoudiaoluo.DiaoluoTypeDeserializer;
import com.aye10032.Utils.fangzhoudiaoluo.Module;
import com.aye10032.Zibenbot;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.*;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.aye10032.Utils.TimeUtil.TimeConstant.PER_DAY;
import static com.aye10032.Utils.fangzhoudiaoluo.Module.getModules;
import static com.aye10032.Utils.fangzhoudiaoluo.Module.getVers;

/**
 * @author Dazo66
 */
public class FangZhouDiaoluoFunc extends BaseFunc {

    private DiaoluoType type;
    private Module module;
    private List<DiaoluoType.HeChenType> name_idList;
    private String arkonegraphFile;
    private String cacheFile;
    private Pair<Long, DiaoluoType.HeChenType> last = null;

    public FangZhouDiaoluoFunc(Zibenbot zibenbot) {
        super(zibenbot);
        if (zibenbot != null) {
            arkonegraphFile = zibenbot.appDirectory + "/fangzhoudiaoluo/Arkonegraph.png";
            cacheFile = zibenbot.appDirectory + "/cacheFile.json";
        } else {
            arkonegraphFile = "res/Arkonegraph.png";
            cacheFile = "cacheFile.json";
        }
    }

    public FangZhouDiaoluoFunc() {
        this(null);
    }

    @Override
    public void setUp() {
        update();
        TimedTask task = new TimedTask();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 20);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date date = calendar.getTime();
        task.setTiggerTime(date).setCycle(PER_DAY).setRunnable(this::update);
        zibenbot.pool.add(task);
    }

    @Override
    public void run(CQMsg cqmsg) {
        String msg = cqmsg.msg.trim();
        if (last !=null) {
            if (cqmsg.fromClient == last.getKey()) {
                if (("是".equals(msg) || "yes".equals(msg) || "Yes".equals(msg) || "Y".equals(msg) || "y".equals(msg) || "确实".equals(msg) || "对".equals(msg))) {
                    retMsg(last.getValue(), cqmsg);
                } else {
                    last = null;
                }
            }
        }
        if (msg.startsWith(".方舟素材") || msg.startsWith(".方舟掉落")) {
            last = null;
            String[] strings = msg.split(" ");
            int len = strings.length;
            if (len >= 2) {
                for (int i = 1; i < len; i++) {
                    boolean flag = true;
                    if (name_idList == null) {
                        if (zibenbot == null) {
                            System.out.println("方舟掉落：初始化异常");
                        } else {
                            zibenbot.replyMsg(cqmsg, "方舟掉落：初始化异常");
                        }
                        return;
                    }
                    for (DiaoluoType.HeChenType type : name_idList) {
                        if (type.isThis(strings[i])) {
                            retMsg(type, cqmsg);
                            flag = false;
                            break;
                        }
                    }
                    if (flag) {
                        Pair<DiaoluoType.HeChenType, Float> max = Pair.of(null, 0f);
                        for (DiaoluoType.HeChenType type : name_idList) {
                            float f = type.maxSimilarity(strings[i]);
                            max = f > max.getValue() ? Pair.of(type, f) : max;
                        }
                        if (max.getValue() < 0.5f) {
                            if (zibenbot != null) {
                                zibenbot.replyMsg(cqmsg, "找不到素材：【" + strings[i] + "】");
                            } else if (zibenbot == null) {
                                System.out.println("找不到素材：【" + strings[i] + "】");
                            }
                        } else {
                            last = Pair.of(cqmsg.fromClient, max.getKey());
                            if (zibenbot != null) {
                                zibenbot.replyMsg(cqmsg, "你要找的是不是：【" + max.getKey().names[0] + "】");
                            } else if (zibenbot == null) {
                                System.out.println("你要找的是不是：【" + max.getKey().names[0] + "】");
                            }
                        }
                    }
                }
            } else {
                try {
                    if (zibenbot != null && !cqmsg.isTeamspealMsg()) {
                        zibenbot.replyMsg(cqmsg, zibenbot.getCQCode().image(new File(arkonegraphFile)));
                    } else if (cqmsg.isTeamspealMsg()){
                        zibenbot.replyMsg(cqmsg, "ts频道无法发图片，请从群聊或者私聊查询");
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void retMsg(DiaoluoType.HeChenType type, CQMsg msg){
        if (type.calls.length == 0) {
            if (zibenbot != null) {
                zibenbot.replyMsg(msg, module.getString(this.type.getMaterialFromID(type.id)));
            } else {
                System.out.println(module.getString(this.type.getMaterialFromID(type.id)));
            }
        } else {
            StringBuilder s = new StringBuilder();
            List<String> strings1 = getCalls(name_idList, type);
            for (int i1 = 0; i1 < strings1.size(); i1++) {
                s.append(module.getString(this.type.getMaterialFromID(strings1.get(i1))));
                if (i1 != strings1.size() - 1) {
                    s.append("\n\n");
                }
            }
            if (zibenbot != null) {
                zibenbot.replyMsg(msg, s.toString());
            } else {
                System.out.println(s.toString());
            }
        }
    }

    public void update() {
        System.out.println(arkonegraphFile);
        Zibenbot.logger.info("fangzhoudiaoluo update start");
        File file = new File(cacheFile);
        Gson gson = new GsonBuilder().registerTypeAdapter(DiaoluoType.class, new DiaoluoTypeDeserializer()).create();
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        DiaoluoType diaoluoType = null;
        try {
            for (int i = 1; i <= 5; i++) {
                InputStream stream = HttpUtils.getInputStreamFromNet("https://arkonegraph.herokuapp.com/materials/tier/" + String.valueOf(i), client);
                if (diaoluoType == null) {
                    diaoluoType = gson.fromJson(new InputStreamReader(stream), DiaoluoType.class);
                } else {
                    diaoluoType.material = ArrayUtils.addAll(diaoluoType.material, gson.fromJson(new InputStreamReader(stream), DiaoluoType.class).material);
                }
                stream.close();

            }
            this.type = diaoluoType;
            InputStream stream = HttpUtils.getInputStreamFromNet("https://gitee.com/aye10032/Zibenbot/raw/master/res/fangzhoudiaoluo/name-id.txt", client);
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
            Module.update();
            module = Module.module;

            String img_url = "https://img.nga.178.com/attachments/mon_202004/05/-klbw3Q5-2mbmXeZ3rT3cS2io-1bf.png";
            if (zibenbot != null) {
                img_url = zibenbot.config.getConfig("fzdl_img", img_url);
                String finalimgUrl = img_url;
                zibenbot.config.addListener(new ConfigListener("fzdl_img", () -> {
                    String url = zibenbot.config.getConfig("fzdl_img", finalimgUrl);
                    try {
                        update_img(url);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }));
            }
            if (!new File(arkonegraphFile).exists()) {
                update_img(img_url);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Zibenbot.logger.info("fangzhoudiaoluo update end");
    }

    public void update_img(String img_url) throws IOException {
        //更新图片
        File file = new File(arkonegraphFile);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
        }
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder().url(img_url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //Handle the error
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    InputStream is = response.body().byteStream();
                    OutputStream output = new FileOutputStream(arkonegraphFile);

                    byte[] data = new byte[1024];
                    int count;
                    while ((count = is.read(data)) != -1) {
                        output.write(data, 0, count);
                    }
                    output.flush();
                    output.close();
                    is.close();
                } else {
                    //Handle the error
                }
            }
        });

    }

    public List<String> getCalls(List<DiaoluoType.HeChenType> all, DiaoluoType.HeChenType type) {
        List<String> strings = new ArrayList<>();
        if (type.calls.length == 0) {
            strings.add(type.id);
            return strings;
        } else {
            for (String c : type.calls) {
                for (DiaoluoType.HeChenType type1 : all) {
                    if (type1.id.equals(c)) {
                        for (String s : getCalls(all, type1)) {
                            if (!strings.contains(s)) {
                                strings.add(s);
                            }
                        }
                    }
                }
            }
        }
        return strings;
    }

}
