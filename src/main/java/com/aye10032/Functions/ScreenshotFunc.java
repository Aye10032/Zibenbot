package com.aye10032.Functions;

import com.aye10032.Zibenbot;

import java.io.File;
import java.io.IOException;

/**
 * @author Dazo66
 */
public class ScreenshotFunc extends BaseFunc {

    static String BLANK = " ";

    public ScreenshotFunc(Zibenbot zibenbot) {
        super(zibenbot);
    }

    @Override
    public void setUp() {
        File dir = new File(zibenbot.appDirectory + "/screenshot");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        //清理缓存
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

    @Override
    public void run(CQMsg CQmsg) {
        String msg = CQmsg.msg;
        if (msg.startsWith("网页快照") || msg.startsWith(".网页快照")){
            msg = msg.replaceAll(" +", " ");
            String[] args = msg.split(" ");
            zibenbot.pool.timeoutEvent(1, () -> {
                try {
                    if (args.length == 3) {
                        replyMsg(CQmsg, zibenbot.getCQCode().image(getScreenshot(addhttp(args[1]), Integer.parseInt(args[2]))));
                    } else if (args.length == 2) {
                        replyMsg(CQmsg, zibenbot.getCQCode().image(getScreenshot(addhttp(args[1]), 4000)));
                    } else {
                        replyMsg(CQmsg, "参数异常，Example：网页快照 [url] [timeout]");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    replyMsg(CQmsg, "获取网页快照失败，可能是网页不支持" + e);
                }
            });

        }

    }

    public File getScreenshot(String url, int timeOut) throws IOException {
        String outFileName = getOutFileName(url);
        return getScreenshot(appDirectory + "\\phantomjs\\bin\\phantomjs.exe", appDirectory + "\\phantomjs\\screenshot.js", url, outFileName, timeOut);
    }

    public static File getScreenshot(String phantomjs, String script, String url, String outFileName,int timeOut) throws IOException {
        Process process = Runtime.getRuntime().exec(
                "\"" + phantomjs+ "\"" + BLANK //你的phantomjs.exe路径
                        + "\"" + script + "\"" + BLANK //就是上文中那段javascript脚本的存放路径
                        + url + BLANK //你的目标url地址
                        +"\""+ outFileName +"\""+ BLANK
                        + timeOut
        );//你的图片输出路径
/*        System.out.println(
                "\"" + phantomjs + BLANK //你的phantomjs.exe路径
                        + "\"" + script + "\"" + BLANK //就是上文中那段javascript脚本的存放路径
                        + url + BLANK //你的目标url地址
                        +"\""+ outFileName +"\""+ BLANK
                        + timeOut);*/
        try {
            int exit = process.waitFor();

            process.destroy();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new File(outFileName);
    }

    private String addhttp(String url){
        if (url.startsWith("http")) {
            return url;
        } else {
            return "https://" + url;
        }
    }

    public String getOutFileName(String url){
        return zibenbot.appDirectory + "\\screenshot\\" + url.hashCode() + ".jpg";
    }
}
