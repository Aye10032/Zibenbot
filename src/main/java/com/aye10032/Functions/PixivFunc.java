package com.aye10032.Functions;

import com.aye10032.Utils.SetuUtil;
import com.aye10032.Zibenbot;

import java.io.IOException;

public class PixivFunc extends BaseFunc {

    public PixivFunc(Zibenbot zibenbot) {
        super(zibenbot);
    }

    public void setUp() {

    }

    public void run(CQMsg CQmsg) {
        if (CQmsg.msg.equals(".p站") || CQmsg.msg.equals(".pixiv") || CQmsg.msg.equals(".P站")) {
            try {
                if (CQmsg.isTeamspealMsg()){
                    zibenbot.replyMsg(CQmsg, "ts频道无法发图片，请从群聊或者私聊获取");
                    return;
                }
                zibenbot.replyMsg(CQmsg, zibenbot.getCQCode().image(new SetuUtil(zibenbot.appDirectory).getImage()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
