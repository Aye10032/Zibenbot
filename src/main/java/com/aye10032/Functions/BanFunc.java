package com.aye10032.Functions;

import com.aye10032.Utils.AmnestyClass;
import com.aye10032.Utils.BanUtil.BanRecord;
import com.aye10032.Zibenbot;
import org.meowy.cqp.jcq.entity.CoolQ;
import org.meowy.cqp.jcq.message.CQCode;

import java.util.List;

public class BanFunc extends BaseFunc {

    BanRecord banRecord;
    CoolQ CQ;
    CQCode CC;

    public BanFunc(Zibenbot zibenbot) {
        super(zibenbot);
        CQ = zibenbot.getCoolQ();
        CC = zibenbot.getCQCode();
    }

    public void setUp() {
        this.banRecord = new BanRecord(CQ, CC);
    }

    public void run(CQMsg CQmsg) {
        if (CQmsg.msg.equals("肃静")) {
            new AmnestyClass(CQ, CQmsg.fromGroup, 1);
        } else if (CQmsg.msg.equals("大赦")) {
            new AmnestyClass(CQ, CQmsg.fromGroup, banRecord).done(banRecord.getGroupObject(CQmsg.fromGroup).getBanList());
        } else if (CQmsg.msg.startsWith(".")) {
            if (CQmsg.msg.contains("禁言")) {
                String[] strlist = CQmsg.msg.split(" ");
                if (strlist.length == 3) {
                    try {
                        if (CC.getAt(CQmsg.msg) == 2375985957L) {
                            zibenbot.replyGroupMsg(CQmsg, "对不起，做不到。");
                            if (CQmsg.fromQQ != 2375985957L) {
                                CQ.setGroupBan(CQmsg.fromGroup, CQmsg.fromQQ, Long.parseLong(strlist[2]));
                                zibenbot.groupBan(2, 00000001, CQmsg.fromGroup, CQmsg.fromQQ, CQmsg.fromQQ, Long.parseLong(strlist[2]));
                                banRecord.getGroupObject(CQmsg.fromGroup).addBan(CQmsg.fromQQ);
                                banRecord.getGroupObject(CQmsg.fromGroup).addMemebrBanedTime(CQmsg.fromQQ, CQmsg.fromQQ);
                            }
                        } else {
                            CQ.setGroupBan(CQmsg.fromGroup, CC.getAt(CQmsg.msg), Long.parseLong(strlist[2]));
                            zibenbot.groupBan(2, 00000001, CQmsg.fromGroup, CQmsg.fromQQ, CC.getAt(CQmsg.msg), Long.parseLong(strlist[2]));
                            banRecord.getGroupObject(CQmsg.fromGroup).addBan(CC.getAt(CQmsg.msg));
                            banRecord.getGroupObject(CQmsg.fromGroup).addMemebrBanedTime(CQmsg.fromQQ, CC.getAt(CQmsg.msg));
                        }
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
            } else if (CQmsg.msg.equals(".击杀榜")) {
                List<String> list = banRecord.getKillRank(CQmsg.fromGroup);
                StringBuilder msgs = new StringBuilder();
                if (list.size() >= 10) {
                    for (int i = 0; i < 10; i++) {
                        msgs.append(list.get(i));
                    }
                } else {
                    for (String temp : list) {
                        msgs.append(temp);
                    }
                }
                zibenbot.replyGroupMsg(CQmsg, msgs.toString());
            }
        }
    }
}
