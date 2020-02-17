package com.aye10032.Functions;

import com.aye10032.Utils.SetuUtil;
import com.aye10032.Zibenbot;

import java.io.IOException;

public class PixivFunc extends BaseFunc {

    public PixivFunc(Zibenbot zibenbot) {
        super(zibenbot);
    }

    public void setUp(Zibenbot zibenbot) {

    }

    public void run(CQMsg CQmsg) {
        if (CQmsg.msg.equals(".p站") || CQmsg.msg.equals(".pixiv") || CQmsg.msg.equals(".P站")) {
            try {
                CQ.sendGroupMsg(CQmsg.fromGroup, CC.image(new SetuUtil(zibenbot.appDirectory).getImage()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
