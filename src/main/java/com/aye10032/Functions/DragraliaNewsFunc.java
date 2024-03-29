package com.aye10032.Functions;

import com.aye10032.Functions.funcutil.BaseFunc;
import com.aye10032.Functions.funcutil.CQMsg;
import com.aye10032.TimeTask.DragraliaTask;
import com.aye10032.Utils.ArticleUpateDate;
import com.aye10032.Utils.Config;
import com.aye10032.Utils.ConfigLoader;
import com.aye10032.Zibenbot;

import java.io.IOException;
import java.util.*;

/**
 * @author Dazo66
 */
public class DragraliaNewsFunc extends BaseFunc {

    private DragraliaTask task;
    private ArticleUpateDate date = null;

    public DragraliaNewsFunc(Zibenbot zibenbot) {
        super(zibenbot);
    }

    @Override
    public void setUp() {
        this.task = new DragraliaTask(zibenbot) {
            @Override
            public String getName() {
                //不会用到 直接为null
                return null;
            }
        };
        this.task.loader = new ConfigLoader<>(zibenbot.appDirectory + "/dragraliaFunc.json", Config.class);
        this.task.config = this.task.loader.load();
    }

    @Override
    public void run(CQMsg CQmsg) {
        String s = CQmsg.msg;
        if (s.startsWith(".龙约公告")) {
            s = s.replaceAll(" +", " ");
            String[] strings = s.trim().split(" ");
            if (strings.length == 1) {
                ArrayList<Runnable> rs = new ArrayList<>();
                Set<DragraliaTask.Article> articles = Collections.synchronizedSet(new HashSet<>());
                date = null;
                try {
                    date = task.getUpdateDate();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (date != null) {
                    date.new_article_list.forEach(integer -> rs.add(() -> {
                        try {
                            articles.add(Objects.requireNonNull(task.getArticleFromNet(integer, false)));
                        } catch (Exception e) {
                            DragraliaTask.Article a = new DragraliaTask.Article();
                            a.title_name = "获取公告异常";
                            a.article_id = integer;
                            articles.add(a);
                        }
                    }));
                    date.update_article_list.forEach(date1 -> rs.add(() -> {
                        try {
                            articles.add(Objects.requireNonNull(task.getArticleFromNet(date1.id, false)));
                        } catch (Exception e) {
                            DragraliaTask.Article a = new DragraliaTask.Article();
                            a.title_name = "获取公告异常";
                            a.article_id = date1.id;
                            articles.add(a);
                        }
                    }));
                    zibenbot.pool.getAsynchronousPool().execute(() -> {
                        TreeSet<DragraliaTask.Article> set = new TreeSet<>(Comparator.reverseOrder());
                        set.addAll(articles);
                        StringBuilder builder = new StringBuilder();
                        if (set.size() != 0) {
                            builder.append("今天的公告如下：\n");
                            set.forEach(a -> {
                                builder.append("\t").append("【").append(a.article_id).append("】").append(a.title_name).append("\n");
                            });
                            builder.append("如果需要查询具体公告 请回复：.龙约公告 [公告id] [公告id]...");
                        } else {
                            builder.append("今天无事发生。");
                        }
                        replyMsg(CQmsg, builder.toString());
                    }, rs.toArray(new Runnable[]{}));
                }
            } else if (strings.length > 1) {
                for (int i = 1; i < strings.length; i++) {
                    int id;
                    try {
                        id = Integer.parseInt(strings[i]);
                    } catch (Exception e) {
                        replyMsg(CQmsg, "id格式错误：" + strings[i] + " 应该全是数字。");
                        continue;
                    }
                    DragraliaTask.Article a;
                    try {
                        a = task.getArticleFromNet(id, false);
                    } catch (Exception e) {
                        replyMsg(CQmsg, "获取公告异常，公告id：" + id);
                        e.printStackTrace();
                        continue;
                    }
                    ArrayList<CQMsg> list = new ArrayList<>();
                    list.add(CQmsg);
                    task.sendArticle(a, list);
                }
            }
        }
    }
}
