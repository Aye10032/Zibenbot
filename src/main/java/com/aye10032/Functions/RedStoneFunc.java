package com.aye10032.Functions;

import com.aye10032.Utils.ConfigLoader;
import com.aye10032.Utils.Video.VideoClass;
import com.aye10032.Utils.Video.VideoData;
import com.aye10032.Zibenbot;

import java.util.Map;

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
        String[] strlist = cqMsg.msg.split(" ");
        if (cqMsg.msg.equals("搬运")) {
            zibenbot.replyMsg(cqMsg, "关键词列表:\n" +
                    "搬运 <油管链接> [描述]-----添加搬运需求\n" +
                    "烤 <油管链接|B站链接> [描述]-----添加翻译需求\n" +
                    "搬运列表-----获取当前任务列表\n" +
                    "以下命令仅组群内可用:\n" +
                    "已搬 <序列号|油管链接>-----从搬运列表中去除\n" +
                    "接 <序列号|油管链接|B站链接> [时间段]-----承接翻译\n" +
                    "接坑-----查看当前翻译需求队列");
        } else if (cqMsg.msg.startsWith("搬运") && cqMsg.msg.contains(" ")) {
            if (strlist.length == 3) {
                videoClass.addVideoSum();
                videoClass.addVideo(new VideoData(videoClass.getVideoSum(), strlist[1], strlist[2], cqMsg.fromQQ));
            } else if (strlist.length == 2) {
                videoClass.addVideoSum();
                videoClass.addVideo(new VideoData(videoClass.getVideoSum(), strlist[1], "", cqMsg.fromQQ));
            }
        } else if (cqMsg.msg.startsWith("烤 ")) {
            if (strlist.length == 3) {
                videoClass.addVideoSum();
                videoClass.addVideo(new VideoData(videoClass.getVideoSum(), strlist[1], strlist[2], true, cqMsg.fromQQ));
            } else if (strlist.length == 2) {
                videoClass.addVideoSum();
                videoClass.addVideo(new VideoData(videoClass.getVideoSum(), strlist[1], "", true, cqMsg.fromQQ));
            }
        } else if (cqMsg.msg.equals("搬运列表")) {
            videoClass.updateList();
            returnMSG = new StringBuilder("当前列表中");
            if (videoClass.getVideoNum() == 0) {
                returnMSG.append("无视频");
            } else {
                returnMSG.append("有").append(videoClass.getVideoNum()).append("个视频\n------");

                for (VideoData data : videoClass.getDataList()) {
                    returnMSG.append("\nNo.").append(data.getNO())
                            .append("\n  链接: ").append(data.getVideoLink())
                            .append("\n  描述: ").append(data.getDescription())
                            .append("\n  状态: ");
                    if (!data.getIsTrans()) {
                        if (!data.isHasDone()) {
                            returnMSG.append("未搬运");
                        } else {
                            returnMSG.append("已搬运");
                        }
                        if (data.getNeedTrans()) {
                            returnMSG.append(" 要翻译");
                        }
                    } else {
                        returnMSG.append("翻译中");
                    }
                }
            }
            zibenbot.replyMsg(cqMsg, new String(returnMSG));
        } else if (cqMsg.msg.equals("接坑")) {
            returnMSG = new StringBuilder("");
            if (videoClass.getVideoNum() == 0) {
                returnMSG.append("当前列表中无视频");
            } else {
                returnMSG.append("待翻译列表:\n------");

                for (VideoData data : videoClass.getDataList()) {
                    if (data.getNeedTrans()) {
                        returnMSG.append("\nNo.").append(data.getNO())
                                .append("\n  链接: ").append(data.getVideoLink())
                                .append("\n  描述: ").append(data.getDescription())
                                .append("\n  状态: ");
                        if (!data.getIsTrans()) {
                            returnMSG.append("待翻译");
                        } else {
                            returnMSG.append("翻译中:");

                            for (Map.Entry<Long, String> entry : data.getTransList().entrySet()) {
                                returnMSG.append("\n    ").append(zibenbot.getCQCode().at(entry.getKey())).append(": ").append(entry.getValue());
                            }
                        }
                    }
                }
            }
            zibenbot.replyMsg(cqMsg, new String(returnMSG));
        } else if (cqMsg.msg.startsWith("已搬 ")) {
            videoClass.VideoDone(strlist[1]);
        } else if (cqMsg.msg.startsWith("接 ")) {
            returnMSG = new StringBuilder("");
            if (videoClass.getVideoNum() == 0) {
                returnMSG.append("当前列表中无视频");
            } else {
                for (VideoData data : videoClass.getDataList()) {
                    if (data.getVideoLink().equals(strlist[1]) || (data.getNO() + "").equals(strlist[1])) {
                        if (strlist.length == 3) {
                            data.addTrans(cqMsg.fromQQ, strlist[2]);
                        } else if (strlist.length == 2) {
                            data.addTrans(cqMsg.fromQQ, "");
                        }
                    }
                }
            }
        }

        ConfigLoader.save(zibenbot.appDirectory + "/videoData.json", VideoClass.class, videoClass);
    }
}
