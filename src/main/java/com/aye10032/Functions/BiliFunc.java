package com.aye10032.Functions;

import com.aye10032.Utils.AyeCompile;
import com.aye10032.Utils.BiliInfo;
import com.aye10032.Zibenbot;

import java.io.File;
import java.io.IOException;

public class BiliFunc extends BanFunc{
    public BiliFunc(Zibenbot zibenbot) {
        super(zibenbot);
    }

    public void setUp() {

    }

    public void run(CQMsg CQmsg) {
        AyeCompile compile = new AyeCompile(CQmsg.msg);
        if (compile.hasAV() | compile.hasBV()) {
            try {
                BiliInfo biliInfo;
                if (compile.hasAV()) {
                    biliInfo = new BiliInfo(compile.getAVString(), zibenbot.appDirectory);
                } else {
                    biliInfo = new BiliInfo(compile.getBVString(), zibenbot.appDirectory);
                }
                String send = "";
                if (CQmsg.isPrivateMsg() || CQmsg.isGroupMsg()) {
                    send = biliInfo.getTitle() + "\n"
                            + biliInfo.getVideourl() + "\n"
                            + CC.image(new File(zibenbot.appDirectory + "\\image\\img.jpg"))
                            + "\nup主：" + biliInfo.getUp() + CC.image(new File(zibenbot.appDirectory + "\\image\\head.jpg"))
                            + "\n播放：" + biliInfo.getView()
                            + " 弹幕" + biliInfo.getDanmaku()
                            + "\n点赞" + biliInfo.getLike()
                            + " 投币" + biliInfo.getCoin()
                            + " 收藏" + biliInfo.getFavorite()
                            + " 评论：" + biliInfo.getReply();
                } else if(CQmsg.isTeamspealMsg()) {
                    send = biliInfo.getTitle() + "\n"
                            + biliInfo.getVideourl() + "\n"
                            + "\nup主：" + biliInfo.getUp()
                            + "\n播放：" + biliInfo.getView()
                            + " 弹幕" + biliInfo.getDanmaku()
                            + "\n点赞" + biliInfo.getLike()
                            + " 投币" + biliInfo.getCoin()
                            + " 收藏" + biliInfo.getFavorite()
                            + " 评论：" + biliInfo.getReply();
                }
                zibenbot.replyMsg(CQmsg, send);
            } catch (IOException e) {
                zibenbot.replyMsg(CQmsg, e.toString());
            }
        }
    }

}
