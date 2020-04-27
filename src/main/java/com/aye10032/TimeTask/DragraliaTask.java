package com.aye10032.TimeTask;

import com.aye10032.Functions.CQMsg;
import com.aye10032.Functions.MsgType;
import com.aye10032.Utils.Config;
import com.aye10032.Utils.ConfigLoader;
import com.aye10032.Utils.HttpUtils;
import com.aye10032.Utils.TimeUtil.TimeConstant;
import com.aye10032.Utils.TimeUtil.TimedTask;
import com.aye10032.Zibenbot;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.OkHttpClient;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.meowy.cqp.jcq.entity.CQDebug;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
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
    private static Pattern img_file_pattern = Pattern.compile("\\w+.(png|jpg)");
    private static Pattern img_tag_pattern = Pattern.compile("<img[^<>]*\">");
    private static Pattern src_tag_pattern = Pattern.compile("http[^<>]*.(png|jpg)");
    private Config config;
    private ConfigLoader<Config> loader;
    OkHttpClient client = new OkHttpClient().newBuilder()
            .build();
    public Runnable runnable = new Runnable() {
        @Override
        public void run() {
            Set<ArticleInfo> articleInfos = getNewArticles(Integer.parseInt(config.getWithDafault("last_priority", "-1")));
            articleInfos.forEach(a -> sendArticle(getArticle(a)));
        }
    };

    public DragraliaTask(Zibenbot zibenbot){
        this.zibenbot = zibenbot;
        loader = new ConfigLoader<>(zibenbot.appDirectory + "/dragralia.json", Config.class);
        config = loader.load();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 1);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date date = calendar.getTime();
        setRunnable(runnable).setTimes(-1).setCycle(TimeConstant.PER_HOUR)
                .setTiggerTime(date);
    }

    public Article getArticle(ArticleInfo articleInfo){
        try {
            InputStream stream = HttpUtils.getInputStreamFromNet(
                    "https://dragalialost.com/api/index.php?format=json&type=information&category_id=&priority_lower_than=&action=information_detail&article_id="+ articleInfo.article_id +"&lang=zh_cn&td=%2B08%3A00", client);
            JsonParser jsonParser = new JsonParser();
            JsonObject object = jsonParser.parse(IOUtils.toString(stream)).getAsJsonObject();
            JsonObject data = object.get("data").getAsJsonObject().get("information").getAsJsonObject();
            return gson.fromJson(data, Article.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Set<ArticleInfo> getNewArticles(int index){
        Set<ArticleInfo> articleInfos = new HashSet<>();
        try {
            if (index == -1) {
                articleInfos = getArticleFromNet(-1);
            } else {
                articleInfos = getArticleFromNet(index + 9999);
            }
            while (true) {
                if (index == -1) {
                    break;
                }
                int min = 999999;
                int size = articleInfos.size();
                Iterator<ArticleInfo> iterator = articleInfos.iterator();
                while (iterator.hasNext()) {
                    ArticleInfo a = iterator.next();
                    if (a.priority < min) {
                        min = a.priority;
                    }
                    if (a.priority <= index) {
                        iterator.remove();
                    }
                }
                if (size == articleInfos.size()) {
                    articleInfos.addAll(getArticleFromNet(min));
                } else {
                    break;
                }
            }

            /*int size = articleInfos.size();
            if (index != -1) {
                int min = 999999;
                Iterator<ArticleInfo> iterator = articleInfos.iterator();
                while (iterator.hasNext()) {
                    ArticleInfo a = iterator.next();
                    if (a.priority < min) {
                        min = a.priority;
                    }
                    if (a.priority <= index) {
                        iterator.remove();
                    }
                }
                if (size == articleInfos.size()) {
                    articleInfos.addAll(getNewArticles(min));

                }
            }*/
        } catch (Exception e) {
            e.printStackTrace();
        }
        int max1 = index;
        Iterator<ArticleInfo> iterator = articleInfos.iterator();
        while (iterator.hasNext()) {
            ArticleInfo a = iterator.next();
            if (a.priority > max1) {
                max1 = a.priority;
            }
        }
        config.set("last_priority", String.valueOf(max1));
        loader.save(config);
        return articleInfos;
    }

    private void sendArticle(Article a){
        List<String> img_list = new ArrayList<>();
        List<String> img_tag_list = new ArrayList<>();
        List<Runnable> runs = new ArrayList<>();
        String msg = StringEscapeUtils.unescapeHtml4(a.message);
        Matcher matcher = img_tag_pattern.matcher(msg);
        List<String> matchStrs = new ArrayList<>();

        while (matcher.find()) { //此处find（）每次被调用后，会偏移到下一个匹配
            matchStrs.add(matcher.group());//获取当前匹配的值
        }
        for (String s : matchStrs) {
            img_tag_list.add(s);
            Matcher matcher1 = src_tag_pattern.matcher(s);
            if (matcher1.find()) {
                img_list.add(matcher1.group());
            }
        }
        img_list.forEach(img -> runs.add(() -> downloadImg(img)));
        if (!"".equals(a.image_path)) {
            runs.add(() -> downloadImg(a.image_path));
        }
        zibenbot.pool.asynchronousPool.execute(() -> {
            StringBuilder builder = new StringBuilder();
            if (!"".equals(a.image_path)) {
                builder.append(getFileName(a.image_path));
                builder.append("\n");
            }

            builder.append("【")
                    .append(a.category_name)
                    .append("】 ")
                    .append(a.title_name)
                    .append("\n\n")
                    .append(clearMsg(msg, img_list));
                    if (!(zibenbot.getCoolQ() instanceof CQDebug)) {
                //todo 测试完毕修改这里
                zibenbot.replyMsg(new CQMsg(-1, -1, 814843368L, 895981998L, null, "DragraliaTask Return Msg", -1, MsgType.PRIVATE_MSG), builder.toString());
            } else {
                System.out.println(builder.toString());
            }
            },
                runs.toArray(new Runnable[]{}));
    }

    private String clearMsg(String msg, List<String> imgs) {
        for (String img : imgs) {
            /*if (!(zibenbot.getCoolQ() instanceof CQDebug)) {*/
                Matcher matcher = src_tag_pattern.matcher(img);
                if (matcher.find()) {
                    String imgFileName = getFileName(matcher.group());
                    if (new File(imgFileName).exists()) {
                        msg = msg.replace(img, zibenbot.getCoolQ().getImage(imgFileName));
                    } else {
                        msg = msg.replace(img, "[图片加载错误]");
                    }
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
        msg = msg.replaceAll("[\n]{3,}", "\n\n");
        return msg;
    }

    private String replaceDate(String msg){
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

    private String getFileName(String url){
        return zibenbot.appDirectory+ "/dragraliatemp/" +  String.valueOf(url.hashCode()) + ".png";
    }

    private File downloadImg(String url){
        File tmpFile = new File(getFileName(url));

        OkHttpClient client = new OkHttpClient().newBuilder().callTimeout(20, TimeUnit.SECONDS)
                .build();
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

    private Set<ArticleInfo> getArticleFromNet(int index) throws IOException {

        Set<ArticleInfo> articleInfos = new HashSet<>();
        InputStream stream = HttpUtils.getInputStreamFromNet(
                "https://dragalialost.com/api/index.php?format=json&type=information&category_id=0&priority_lower_than="+ (index == -1 ? "" : index) +"&action=information_list&article_id=&lang=zh_cn&td=%2B08%3A00"
                , client);
        JsonParser jsonParser = new JsonParser();
        JsonObject object = jsonParser.parse(IOUtils.toString(stream)).getAsJsonObject();
        JsonArray array = object.get("data").getAsJsonObject().get("category").getAsJsonObject().get("contents").getAsJsonArray();
        array.forEach(jsonElement -> {
            articleInfos.add(gson.fromJson(jsonElement, ArticleInfo.class));
        });
        return articleInfos;
    }



    class ArticleInfo {
        //文章id
        String article_id;
        String caption_type;
        //类型名字
        String category_name;
        //日期
        long date;
        String image_path;
        boolean is_new;
        boolean is_update;
        String pr_category_id;
        String pr_thumb_type;
        int priority;
        String title_name;
        String update_time;

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof ArticleInfo) {
                return ((ArticleInfo) obj).article_id.equals(article_id);
            }
            return false;
        }

        @Override
        public int hashCode() {
            return priority;
        }
    }

    class Article{
        //文章id
        String article_id;
        //类型名字
        String category_name;
        //日期
        String image_path;
        String message;
        String next_article_id;
        String pr_category_id;
        String prev_article_id;
        long start_time;
        String title_name;
        long update_time;

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof ArticleInfo) {
                return ((ArticleInfo) obj).article_id.equals(article_id);
            }
            return false;
        }

        @Override
        public int hashCode() {
            return article_id.hashCode();
        }
    }

}
