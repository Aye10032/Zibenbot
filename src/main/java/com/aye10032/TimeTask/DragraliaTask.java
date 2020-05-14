package com.aye10032.TimeTask;

import com.aye10032.Functions.CQMsg;
import com.aye10032.Functions.MsgType;
import com.aye10032.Functions.ScreenshotFunc;
import com.aye10032.Utils.ArticleUpateDate;
import com.aye10032.Utils.Config;
import com.aye10032.Utils.ConfigLoader;
import com.aye10032.Utils.HttpUtils;
import com.aye10032.Utils.TimeUtil.TimeConstant;
import com.aye10032.Utils.TimeUtil.TimedTask;
import com.aye10032.Zibenbot;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.OkHttpClient;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringEscapeUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Dazo66
 */
public class DragraliaTask extends TimedTask {

    String TIMEOUT = "TIMEOUT";
    Zibenbot zibenbot;
    Gson gson = new Gson();
    //<span class="local_date" data-local_date="1587708000">
    private static Pattern date_tag_pattern = Pattern.compile("<span class=\"local_date\" data-local_date=\"\\d{10}\">");
    private static Pattern date_src_pattern = Pattern.compile("\\d{10}");
    private static Pattern img_name_pattern = Pattern.compile("\\w+.(png|jpg)");
    private static Pattern img_tag_pattern = Pattern.compile("<img[^<>]*\">");
    private static Pattern img_url_pattern = Pattern.compile("http[^<>]*.(png|jpg)");

    private JsonParser jsonParser = new JsonParser();
    private ArticleUpateDate date = null;
    public Config config;
    public CQMsg cqMsg = new CQMsg(-1, -1, 814843368L, 2155231604L, null, "DragraliaTask Return Msg", -1, MsgType.GROUP_MSG);
    public ConfigLoader<Config> loader;
    private int exceptionCount = 0;

    OkHttpClient client = new OkHttpClient.Builder().callTimeout(30, TimeUnit.SECONDS).build();
    public Runnable runnable = () -> {
        try {
            try {
                date = getUpdateDate();
            } catch (Exception e) {
                e.printStackTrace();
                //zibenbot.replyMsg(cqMsg, "公告获取异常");
                exceptionCount++;
            }
            Set<Article> articles = getNewArticles();
            articles.forEach(a -> this.sendArticle(a, cqMsg));
        } catch (Exception e) {
            e.printStackTrace();
            exceptionCount++;
        }

    };

    public DragraliaTask(Zibenbot zibenbot) {
        this.zibenbot = zibenbot;
        loader = new ConfigLoader<>(zibenbot.appDirectory + "/dragralia_4.json", Config.class);
        config = loader.load();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 1);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 1);
        Date date = calendar.getTime();
        setRunnable(runnable).setTimes(-1).setCycle(TimeConstant.PER_HALF_HOUR).setTiggerTime(date);
        cleanImg();
    }

/*    public Article getArticle(ArticleInfo articleInfo) {
        try {
            InputStream stream = HttpUtils.getInputStreamFromNet("https://dragalialost.com/api/index.php?format=json&type=information&category_id=&priority_lower_than=&action=information_detail&article_id=" + articleInfo.article_id + "&lang=zh_cn&td=%2B08%3A00", client);
            JsonObject object = jsonParser.parse(IOUtils.toString(stream)).getAsJsonObject();
            JsonObject data = object.get("data").getAsJsonObject().get("information").getAsJsonObject();
            return gson.fromJson(data, Article.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }*/

    private Set<Article> getNewArticles() {
        Set<Article> set = new HashSet<>();
        ArticleUpateDate last = gson.fromJson(config.getWithDafault("last_update_date", "{}"), ArticleUpateDate.class);
        long last_data = Long.parseLong(config.getWithDafault("last_data", Long.toString(System.currentTimeMillis()/1000 - 86400)));
        long current = System.currentTimeMillis() / 1000;
        //判断是不是过了一天的14点
        if ((current - 21600) / 86400 > (last_data - 21600) / 86400) {
            last.clear();
            exceptionCount = 0;
        }
        date.new_article_list.forEach(i -> {
            if (!last.new_article_list.contains(i)) {
                try {
                    set.add(getArticleFromNet(i, false));
                    last.new_article_list.add(i);
                } catch (Exception e) {
                    Article a = new Article();
                    a.message = "读取公告异常，公告id：" + i;
                    a.article_id = -1;
                    set.add(a);
                }
            }
        });
        date.update_article_list.forEach(i -> {
            ArticleUpateDate.UpdateDate d = null;
            for (ArticleUpateDate.UpdateDate date : last.update_article_list) {
                if (date.id == i.id) {
                    d = date;
                    break;
                }
            }
            if (d == null || d.update_time < i.update_time) {
                try {
                    set.add(getArticleFromNet(i.id, true));
                    last.update_article_list.remove(d);
                    last.update_article_list.add(i);
                } catch (Exception e) {
                    Article a = new Article();
                    a.message = "更新公告异常，公告id：" + i.id;
                    a.article_id = -1;
                    set.add(a);
                    exceptionCount++;
                }
            }

        });
        config.set("last_update_date", gson.toJson(last));
        if (set.size() > 0) {
            config.set("last_data", gson.toJson(current));
        }
        loader.save(config);
        return set;

    }

    public void sendArticle(Article a, CQMsg cqMsg) {
        List<String> img_list = new ArrayList<>();
        List<String> img_tag_list = new ArrayList<>();
        List<Runnable> runs = new ArrayList<>();
        String msg = StringEscapeUtils.unescapeHtml4(a.message);
        Matcher matcher = img_tag_pattern.matcher(msg);
        List<String> matchStrs = new ArrayList<>();
        AtomicReference<File> screenshotFile = new AtomicReference<>();

        int len = Chinese_length(msg);
        while (matcher.find()) { //此处find（）每次被调用后，会偏移到下一个匹配
            matchStrs.add(matcher.group());//获取当前匹配的值
        }
        for (String s : matchStrs) {
            img_tag_list.add(s);
            Matcher matcher1 = img_url_pattern.matcher(s);
            if (matcher1.find()) {
                img_list.add(matcher1.group());
            }
        }
        if (len < 300) {
            img_list.forEach(img -> runs.add(() -> {
                if (!new File(getFileName(img)).exists()) {
                    downloadImg(img);
                }
            }));
        } else {
            runs.add(() -> {
                String dir;
                if ("test".equals(zibenbot.appDirectory)) {
                    dir = "res";
                } else {
                    dir = zibenbot.appDirectory;
                }
                File file = new File(dir + "/dragraliatemp/" + a.article_id + ".jpg");
                if (!file.exists() || a.isUpdate) {
                    screenshotFile.set(getScreenshot(a));
                } else {
                    screenshotFile.set(file);
                }
            });
        }
        if (!"".equals(a.image_path)) {
            runs.add(() -> downloadImg(a.image_path));
        }
        zibenbot.pool.asynchronousPool.execute(() -> {
            StringBuilder builder = new StringBuilder();
            if (a.article_id != -1) {
                builder.append("【").append(a.category_name).append("】 ").append(a.title_name).append("\n");
                if (!"".equals(a.image_path)) {
                    try {
                        builder.append(zibenbot.getCQCode().image(new File(getFileName(a.image_path))));
                        builder.append("\n");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (a.isUpdate) {
                    //builder.append("（Update）\n");
                }
                String ret = clearMsg(msg);
                if (len > 300 && screenshotFile.get() != null && screenshotFile.get().exists()) {
                    try {
                        builder.append("公告详情：").append("\n").append(zibenbot.getCQCode().image(screenshotFile.get()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    builder.append("\n\n").append(ret);
                }
            } else {
                builder.append(a.message);
            }
            //todo 测试完毕修改这里
            if (exceptionCount <= 3) {
                zibenbot.replyMsg(cqMsg, builder.toString());
            }
        }, runs.toArray(new Runnable[]{}));
    }

    private File getScreenshot(Article a){
        String dir;
        if ("test".equals(zibenbot.appDirectory)) {
             dir = "res";
        } else {
            dir = zibenbot.appDirectory;
        }
        String url = "https://dragalialost.com/chs/news/detail/" + a.article_id;
        String exe = dir + "/phantomjs/bin/phantomjs.exe";
        String js = dir + "/phantomjs/screenshot.js";
        String outputfile = dir + "/dragraliatemp/" + a.article_id + ".jpg";
        try {
            return ScreenshotFunc.getScreenshot(exe, js, url, outputfile, 1000);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String clearMsg(String msg) {
        Matcher matcher = img_tag_pattern.matcher(msg);
        while (matcher.find()) {
            String tag = matcher.group();
            Matcher matcher1 = img_url_pattern.matcher(tag);
            matcher1.find();
            File file = new File(getFileName(matcher1.group()));
            if (file.exists()) {
                try {
                    msg = msg.replace(tag, zibenbot.getCQCode().image(file));
                } catch (Exception e) {
                    msg = msg.replace(tag, "[图片加载错误]");
                }
            } else {
                msg = msg.replace(tag, "[图片加载错误]");
            }
            /*} else {
                msg = msg.replace(img, "[图片]");
            }*/
        }
        msg = replaceDate(msg);
        msg = msg.replace("<br>", "\n");
        msg = msg.replace("</div>", "\n");
        msg = msg.replaceAll("<[^<>]*?>", "");
        msg = msg.replaceAll("[\\t]+", "");
        msg = msg.replaceAll(" +", " ");
        msg = msg.replaceAll("[\n]{3,}", "\n");
        return msg;
    }

    private String replaceDate(String msg) {
        Matcher matcher = date_tag_pattern.matcher(msg);
        List<String> matchStrs = new ArrayList<>();
        DateFormat format1 = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        while (matcher.find()) { //此处find（）每次被调用后，会偏移到下一个匹配
            matchStrs.add(matcher.group());//获取当前匹配的值
        }
        for (String s : matchStrs) {
            Matcher matcher1 = date_src_pattern.matcher(s);
            matcher1.find();
            Date date = new Date(Long.valueOf(matcher1.group()) * 1000);
            String s1 = format1.format(date);
            msg = msg.replace(s, s1);
        }
        return msg;
    }

    private String getFileName(String url) {
        Matcher matcher = img_name_pattern.matcher(url);
        matcher.find();
        return zibenbot.appDirectory + "\\dragraliatemp\\" + matcher.group();
    }

    private File downloadImg(String url) {
        File tmpFile = new File(getFileName(url));
        try {
            if (!tmpFile.exists()) {
                tmpFile.getParentFile().mkdirs();
                tmpFile.createNewFile();
                HttpUtils.download(url, tmpFile.getAbsolutePath(), client);
            }
        } catch (Exception e) {
            tmpFile.delete();
            return null;
        }
        return tmpFile;
    }

    public Article getArticleFromNet(int id, boolean isUpdate) throws IOException {
        InputStream stream = HttpUtils.getInputStreamFromNet("https://dragalialost.com/api/index.php?format=json&type=information&category_id=&priority_lower_than=&action=information_detail&article_id=" + id + "&lang=zh_cn&td=%2B08%3A00", client);
        JsonParser jsonParser = new JsonParser();
        JsonObject object = jsonParser.parse(IOUtils.toString(stream)).getAsJsonObject();
        JsonElement e = object.get("data").getAsJsonObject().get("information");
        Article a = gson.fromJson(e, Article.class);
        if (isUpdate) {
            a.isUpdate = true;
        }
        return a;
    }

    public ArticleUpateDate getUpdateDate() throws IOException {
        ArticleUpateDate date = new ArticleUpateDate();
        InputStream stream = HttpUtils.getInputStreamFromNet("https://dragalialost.com/api/index.php?format=json&type=information&category_id=0&priority_lower_than=&action=information_list&article_id=&lang=zh_cn&td=%2B08%3A00", client);
        JsonParser jsonParser = new JsonParser();
        JsonObject object = jsonParser.parse(IOUtils.toString(stream)).getAsJsonObject();
        object.get("data").getAsJsonObject().get("new_article_list").getAsJsonArray().forEach(
                jsonElement -> date.new_article_list.add(jsonElement.getAsInt())
        );
        object.get("data").getAsJsonObject().get("update_article_list").getAsJsonArray().forEach(
                e -> {
                    int id = e.getAsJsonObject().get("id").getAsInt();
                    long update_time = e.getAsJsonObject().get("update_time").getAsLong();
                    date.update_article_list.add(new ArticleUpateDate.UpdateDate(id, update_time));
                }
        );
        return date;
    }

    private void cleanImg(){
        File dir = new File(zibenbot.appDirectory + "\\dragraliatemp");
        long current = System.currentTimeMillis();
        int i = 0;
        for (File f: dir.listFiles()) {
            if (f.isFile() && current - f.lastModified() > 86400 * 3 * 1000) {
                f.delete();
                i++;
            }
        }
        Zibenbot.logger.info("清理了三天前的缓存 " + i + " 张。");
    }

    public static class Article {
        //文章id
        public int article_id;
        //类型名字
        public String category_name;
        //日期
        public String image_path;
        public String message;
        public String next_article_id;
        public String pr_category_id;
        public String prev_article_id;
        public long start_time;
        public String title_name;
        public boolean isUpdate = false;
        public long update_time;

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Article) {
                return ((Article) obj).article_id + ((Article) obj).update_time== article_id + update_time;
            }
            return false;
        }

        @Override
        public int hashCode() {
            return article_id * 11451 + (int) update_time;
        }
    }

    public static int Chinese_length(String s) {
        int length = 0;
        for (int i = 0; i < s.length(); i++) {
            int ascii = Character.codePointAt(s, i);
            if (!(ascii >= 0 && ascii <= 255)) {
                length += 1;
            }
        }
        return length;
    }

}
