package com.aye10032.Functions;

import com.aye10032.Utils.IDNameUtil;
import com.aye10032.Zibenbot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

public class SendGroupFunc extends BaseFunc {

    private List<Long> oplist = new ArrayList<>();
    private Map<Integer, Long> groupMap = new HashMap<>();

    public SendGroupFunc(Zibenbot zibenbot) {
        super(zibenbot);
    }


    @Override
    public void setUp() {
        oplist.add(2375985957L);

        groupMap.put(1, 995497677L);
        groupMap.put(2, 947657871L);
        groupMap.put(3, 792666782L);
        groupMap.put(4, 1098042439L);
    }

    @Override
    public void run(CQMsg CQmsg) {
        if (CQmsg.isPrivateMsg() && oplist.contains(CQmsg.fromClient)) {
            if (CQmsg.msg.startsWith("send ")) {
                sendGroupMSG(CQmsg);
            } else if (CQmsg.msg.equals("send")) {
                String desc = "send1 XP交流群\nsend2 TIS\nsend3 实验室\nsend4 公会";
                zibenbot.replyMsg(CQmsg, desc);
            } else if (CQmsg.msg.startsWith("send1")) {
                sendGroupMSG(CQmsg,groupMap.get(1));
            }else if (CQmsg.msg.startsWith("send2")) {
                sendGroupMSG(CQmsg,groupMap.get(2));
            }else if (CQmsg.msg.startsWith("send3")) {
                sendGroupMSG(CQmsg,groupMap.get(3));
            }else if (CQmsg.msg.startsWith("send4")) {
                sendGroupMSG(CQmsg,groupMap.get(4));
            }
        }
    }

    private void sendGroupMSG(CQMsg CQmsg) {
        try {
            String msg = CQmsg.msg;
            long group = Long.parseLong(msg.split(" ")[1]);
            int flag = msg.indexOf(" ", msg.indexOf(" ") + 1);
            msg = msg.substring(flag + 1);
            zibenbot.getCoolQ().sendGroupMsg(group, msg);
            Zibenbot.logger.log(Level.INFO,
                    String.format("回复群[%s]成员[%s]消息:%s",
                            IDNameUtil.getGroupNameByID(group, zibenbot.getCoolQ().getGroupList()),
                            IDNameUtil.getGroupMemberNameByID(group, CQmsg.fromClient, CQ),
                            msg));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    private void sendGroupMSG(CQMsg CQmsg, long group) {
        try {
            String msg = CQmsg.msg;
            int flag = msg.indexOf(" ");
            msg = msg.substring(flag + 1);
            zibenbot.getCoolQ().sendGroupMsg(group, msg);
            Zibenbot.logger.log(Level.INFO,
                    String.format("回复群[%s]成员[%s]消息:%s",
                            IDNameUtil.getGroupNameByID(group, zibenbot.getCoolQ().getGroupList()),
                            IDNameUtil.getGroupMemberNameByID(group, CQmsg.fromClient, CQ),
                            msg));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }
}
