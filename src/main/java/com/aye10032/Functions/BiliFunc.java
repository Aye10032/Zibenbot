package com.aye10032.Functions;

import com.aye10032.Functions.funcutil.BaseFunc;
import com.aye10032.Functions.funcutil.CQMsg;
import com.aye10032.Utils.AyeCompile;
import com.aye10032.Utils.BiliInfo;
import com.aye10032.Zibenbot;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class BiliFunc extends BaseFunc {
    Map<Integer, String> code_msg = new HashMap<>();

    public BiliFunc(Zibenbot zibenbot) {
        super(zibenbot);
        code_msg.put(-200, "视频撞车了，请访问源视频。");
        code_msg.put(-400, "视频找不到。");
        code_msg.put(62002, "视频找不到。");
        code_msg.put(62003, "视频审核已通过，正在发布中。");
        code_msg.put(62004, "视频正在审核中，请耐心等待。");
        code_msg.put(62005, "视频需要登陆后查看。");
        code_msg.put(-403, "视频需要登陆后查看。");
    }



    @Override
    public void setUp() {

    }

    @Override
    public void run(CQMsg CQmsg) {
        AyeCompile compile = new AyeCompile(CQmsg.msg);
        if (compile.hasAV() | compile.hasBV()) {
            try {
                BiliInfo biliInfo;
                if (compile.hasBV()) {
                    biliInfo = new BiliInfo(compile.getBVString(), appDirectory);
                } else {
                    biliInfo = new BiliInfo(compile.getAVString(), appDirectory);
                }
                String send = "";
                if (CQmsg.isPrivateMsg() || CQmsg.isGroupMsg()) {
                    if (!biliInfo.hasVideo) {
                        send += "错误代码：";
                        send += biliInfo.code;
                        send += " ";
                        send += code_msg.get(biliInfo.code);
                        replyMsg(CQmsg, send);
                        return;
                    }
                    String pvideo = "\n预览：" + "视频太短，不提供预览。";
                    if (biliInfo.hasPvdeo && biliInfo.getDuration() >= 12) {
                        pvideo = "\n预览："+ CC.image(new File(appDirectory + "\\image\\pvideo.gif"));
                    } else if (!biliInfo.hasPvdeo){
                        pvideo = "";
                    }
                    send = biliInfo.getTitle() + "\n"
                            + biliInfo.getVideourl() + "\n"
                            +"封面："+ CC.image(new File(appDirectory + "\\image\\img.jpg"))
                            + pvideo
                            + "\nup主：" + biliInfo.getUp() + CC.image(new File(appDirectory + "\\image\\head.jpg"))
                            + "\n播放：" + formatToW(biliInfo.getView())
                            + " 弹幕：" + formatToW(biliInfo.getDanmaku())
                            + "\n点赞：" + formatToW(biliInfo.getLike())
                            + " 投币：" + formatToW(biliInfo.getCoin())
                            + " 收藏：" + formatToW(biliInfo.getFavorite())
                            + " 评论：" + formatToW(biliInfo.getReply())
                            + "\n简介：" + biliInfo.getDescription();
                } else if(CQmsg.isTeamspealMsg()) {
                    send = biliInfo.getTitle() + "\n"
                            + biliInfo.getVideourl() + "\n"
                            + "\nup主：" + biliInfo.getUp()
                            + "\n播放：" + formatToW(biliInfo.getView())
                            + " 弹幕：" + formatToW(biliInfo.getDanmaku())
                            + "\n点赞：" + formatToW(biliInfo.getLike())
                            + " 投币：" + formatToW(biliInfo.getCoin())
                            + " 收藏：" + formatToW(biliInfo.getFavorite())
                            + " 评论：" + formatToW(biliInfo.getReply())
                            + "\n简介：" + biliInfo.getDescription();;
                }
                replyMsg(CQmsg, send);
            } catch (IOException e) {
                replyMsg(CQmsg, e.toString());
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
