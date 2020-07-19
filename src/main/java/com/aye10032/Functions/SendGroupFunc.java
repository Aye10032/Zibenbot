package com.aye10032.Functions;

import com.aye10032.Zibenbot;

import java.util.ArrayList;
import java.util.List;

public class SendGroupFunc extends BaseFunc {

    private List<Long> oplist = new ArrayList<>();

    public SendGroupFunc(Zibenbot zibenbot) {
        super(zibenbot);
    }


    @Override
    public void setUp() {
        oplist.add(2375985957L);
    }

    @Override
    public void run(CQMsg CQmsg) {
        if (CQmsg.isPrivateMsg() && oplist.contains(CQmsg.fromClient) && CQmsg.msg.startsWith("send")) {
            try {
                String msg = CQmsg.msg;
                long group = Long.parseLong(msg.split(" ")[1]);
                int flag = msg.indexOf(" ", msg.indexOf(" ") + 1);
                msg = msg.substring(flag + 1);
                zibenbot.getCoolQ().sendGroupMsg(group,msg);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }
}
