package com.aye10032.Functions;

import com.aye10032.Utils.AyeCompile;
import com.aye10032.Utils.BiliInfo;
import com.aye10032.Zibenbot;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

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
                if (compile.hasBV()) {
                    biliInfo = new BiliInfo(compile.getBVString(), zibenbot.appDirectory);
                } else {
                    biliInfo = new BiliInfo(compile.getAVString(), zibenbot.appDirectory);
                }
                String send = "";
                if (CQmsg.isPrivateMsg() || CQmsg.isGroupMsg()) {
                    String pvideo = "\n预览：" + "视频太短，不提供预览。";
                    if (biliInfo.hasPvdeo && biliInfo.getDuration() >= 12) {
                        pvideo = "\n预览："+ CC.image(new File(zibenbot.appDirectory + "\\image\\pvideo.gif"));
                    } else if (!biliInfo.hasPvdeo){
                        pvideo = "";
                    }
                    send = biliInfo.getTitle() + "\n"
                            + biliInfo.getVideourl() + "\n"
                            +"封面："+ CC.image(new File(zibenbot.appDirectory + "\\image\\img.jpg"))
                            + pvideo
                            + "\nup主：" + biliInfo.getUp() + CC.image(new File(zibenbot.appDirectory + "\\image\\head.jpg"))
                            + "\n播放：" + formatToW(biliInfo.getView())
                            + " 弹幕：" + formatToW(biliInfo.getDanmaku())
                            + "\n点赞：" + formatToW(biliInfo.getLike())
                            + " 投币：" + formatToW(biliInfo.getCoin())
                            + " 收藏：" + formatToW(biliInfo.getFavorite())
                            + " 评论：" + formatToW(biliInfo.getReply());
                } else if(CQmsg.isTeamspealMsg()) {
                    send = biliInfo.getTitle() + "\n"
                            + biliInfo.getVideourl() + "\n"
                            + "\nup主：" + biliInfo.getUp()
                            + "\n播放：" + formatToW(biliInfo.getView())
                            + " 弹幕：" + formatToW(biliInfo.getDanmaku())
                            + "\n点赞：" + formatToW(biliInfo.getLike())
                            + " 投币：" + formatToW(biliInfo.getCoin())
                            + " 收藏：" + formatToW(biliInfo.getFavorite())
                            + " 评论：" + formatToW(biliInfo.getReply());
                }
                zibenbot.replyMsg(CQmsg, send);
            } catch (IOException e) {
                zibenbot.replyMsg(CQmsg, e.toString());
            }
        }
    }

    public static String formatToW(int i) {
        if (i >= 10000) {
            return new DecimalFormat("######.#万").format((double) i/10000d);
        } else {
            return String.valueOf(i);
        }
    }

}
