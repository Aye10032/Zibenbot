package com.aye10032.Functions;

import com.aye10032.Utils.ConfigLoader;
import com.aye10032.Utils.StringUtil;
import com.aye10032.Utils.Video.VideoClass;
import com.aye10032.Utils.Video.VideoData;
import com.aye10032.Zibenbot;

public class RedStoneFunc extends BaseFunc {

    public RedStoneFunc(Zibenbot zibenbot) {
        super(zibenbot);
    }

    @Override
    public void setUp() {

    }

    @Override
    public void run(CQMsg cqMsg) {
        VideoClass videoClass = ConfigLoader.load(zibenbot.appDirectory + "/videoData.json", VideoClass.class);

        StringBuilder returnMSG = new StringBuilder();
        String[] strlist = new StringUtil().split(cqMsg.msg);
        if (cqMsg.msg.equals("搬运")) {
            zibenbot.replyMsg(cqMsg, "关键词列表:\n" +
                    "搬运 <油管链接> [描述]-----添加搬运需求\n" +
                    "烤 <油管链接|B站链接> [描述]-----添加翻译需求\n" +
                    "搬运列表-----获取当前任务列表\n" +
                    "以下命令仅组群内可用:\n" +
                    "已搬 <序列号|油管链接>-----从搬运列表中去除\n" +
                    "接 <序列号|油管链接|B站链接> [时间段]-----承接翻译\n" +
                    "接坑-----查看当前翻译需求队列");
        } else if ((cqMsg.msg.startsWith("搬运")||cqMsg.msg.startsWith("反向")) && cqMsg.msg.contains(" ")) {
            if (strlist.length == 3) {
                videoClass.addVideoSum();
                videoClass.addVideo(new VideoData(videoClass.getVideoSum(), strlist[1], strlist[2], cqMsg.fromClient));
            } else if (strlist.length == 2) {
                videoClass.addVideoSum();
                videoClass.addVideo(new VideoData(videoClass.getVideoSum(), strlist[1], "", cqMsg.fromClient));
            }
            videoClass.updateList();
            zibenbot.replyMsg(cqMsg, "已添加" + strlist[1] + " " + strlist[2]);
            if (cqMsg.fromGroup != 456919710L){
                zibenbot.getCoolQ().sendGroupMsg(456919710L, "已添加" + strlist[1] + " " + strlist[2]);
            }
            ConfigLoader.save(zibenbot.appDirectory + "/videoData.json", VideoClass.class, videoClass);
        } else if (cqMsg.msg.startsWith("烤 ")) {
            if (strlist.length == 3) {
                videoClass.addVideoSum();
                videoClass.addVideo(new VideoData(videoClass.getVideoSum(), strlist[1], strlist[2], true, cqMsg.fromClient));
            } else if (strlist.length == 2) {
                videoClass.addVideoSum();
                videoClass.addVideo(new VideoData(videoClass.getVideoSum(), strlist[1], "", true, cqMsg.fromClient));
            }
            videoClass.updateList();
            zibenbot.replyMsg(cqMsg, "已添加" + strlist[1] + " " + strlist[2]);
            ConfigLoader.save(zibenbot.appDirectory + "/videoData.json", VideoClass.class, videoClass);
        } else if (cqMsg.msg.equals("搬运列表")) {
            videoClass.updateList();
            zibenbot.replyMsg(cqMsg, videoClass.getFullList());
        }
        if (cqMsg.fromGroup==456919710L || cqMsg.fromClient ==2375985957L) {
            if (cqMsg.msg.equals("接坑")) {
                videoClass.updateList();
                zibenbot.replyMsg(cqMsg, videoClass.getTranslateList());
                ConfigLoader.save(zibenbot.appDirectory + "/videoData.json", VideoClass.class, videoClass);
            } else if (cqMsg.msg.startsWith("已搬 ")) {
                videoClass.VideoDone(strlist[1]);
                videoClass.updateList();
                zibenbot.replyMsg(cqMsg, videoClass.getFullList());
                ConfigLoader.save(zibenbot.appDirectory + "/videoData.json", VideoClass.class, videoClass);
            } else if (cqMsg.msg.startsWith("接 ")) {
                if (videoClass.getVideoNum() == 0) {
                    zibenbot.replyMsg(cqMsg, "当前列表中无视频");
                } else {
                    for (VideoData data : videoClass.getDataList()) {
                        if (data.getVideoLink().equals(strlist[1]) || (data.getNO() + "").equals(strlist[1])) {
                            if (strlist.length == 3) {
                                data.addTrans(cqMsg.fromClient, strlist[2]);
                            } else if (strlist.length == 2) {
                                data.addTrans(cqMsg.fromClient, "");
                            }
                        }
                    }
                }
                videoClass.updateList();
                zibenbot.replyMsg(cqMsg, videoClass.getTranslateList());
                ConfigLoader.save(zibenbot.appDirectory + "/videoData.json", VideoClass.class, videoClass);
            }
        }
    }
}
