package com.aye10032.Functions;

import com.aye10032.Functions.funcutil.BaseFunc;
import com.aye10032.Functions.funcutil.CQMsg;
import com.aye10032.Functions.funcutil.FuncCommander;
import com.aye10032.Functions.funcutil.FuncCommanderFactory;
import com.aye10032.Utils.AmnestyClass;
import com.aye10032.Utils.BanUtil.BanRecord;
import com.aye10032.Zibenbot;
import com.dazo66.command.CommanderBuilder;
import org.apache.commons.lang3.math.NumberUtils;
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

    @Override
    public void setUp() {
        this.banRecord = new BanRecord(CQ, CC);
    }

    @Override
    public void run(CQMsg CQmsg) {
        FuncCommander commander = (FuncCommander) (new CommanderBuilder()
                .start()
                .or("肃静"::equals)
                .run((strings) -> {
                    if (CQmsg.isGroupMsg()) {
                        new AmnestyClass(CQ, CQmsg.fromGroup, 1);
                    } else {
                        zibenbot.replyMsg(CQmsg, "对不起，此功能未对私聊或teamspeak开放。");
                    }
                })
                .or("大赦"::equals)
                .run((strings) -> {
                    if (CQmsg.isGroupMsg()) {
                        new AmnestyClass(CQ, CQmsg.fromGroup, banRecord).done(banRecord.getGroupObject(CQmsg.fromGroup).getBanList());
                    } else {
                        zibenbot.replyMsg(CQmsg, "对不起，此功能未对私聊或teamspeak开放。");
                    }
                })
                .or("禁言"::equals)
                .next()
                .or(s -> true)
                .next()
                .or(NumberUtils::isDigits)
                .run((strings) -> {
                    try {
                        if (CC.getAt(CQmsg.msg) == 2375985957L) {
                            zibenbot.replyMsg(CQmsg, "对不起，做不到。");
                            if (CQmsg.fromClient != 2375985957L) {
                                if (CQmsg.fromClient == 895981998L) {
                                    CQ.setGroupBan(CQmsg.fromGroup, CQmsg.fromClient, 100);
                                }else {
                                    CQ.setGroupBan(CQmsg.fromGroup, CQmsg.fromClient, Long.parseLong(strings[2]));
                                    zibenbot.groupBan(2, 00000001, CQmsg.fromGroup, CQmsg.fromClient, CQmsg.fromClient, Long.parseLong(strings[2]));
                                    banRecord.getGroupObject(CQmsg.fromGroup).addBan(CQmsg.fromClient);
                                    banRecord.getGroupObject(CQmsg.fromGroup).addMemebrBanedTime(CQmsg.fromClient, CQmsg.fromClient);
                                }
                            }
                        } else if (CC.getAt(CQmsg.msg) == 895981998L) {
                            CQ.setGroupBan(CQmsg.fromGroup, CC.getAt(CQmsg.msg),
                                    100);
                            zibenbot.groupBan(2, 00000001, CQmsg.fromGroup,
                                    CQmsg.fromClient, CC.getAt(CQmsg.msg),
                                    Long.parseLong(strings[2]));
                            banRecord.getGroupObject(CQmsg.fromGroup).addBan(CC.getAt(CQmsg.msg));
                            banRecord.getGroupObject(CQmsg.fromGroup).addMemebrBanedTime(CQmsg.fromClient, CC.getAt(CQmsg.msg));


                        } else {
                            CQ.setGroupBan(CQmsg.fromGroup, CC.getAt(CQmsg.msg),
                                    Long.parseLong(strings[2]));
                            zibenbot.groupBan(2, 00000001, CQmsg.fromGroup,
                                    CQmsg.fromClient, CC.getAt(CQmsg.msg),
                                    Long.parseLong(strings[2]));
                            banRecord.getGroupObject(CQmsg.fromGroup).addBan(CC.getAt(CQmsg.msg));
                            banRecord.getGroupObject(CQmsg.fromGroup).addMemebrBanedTime(CQmsg.fromClient, CC.getAt(CQmsg.msg));
                        }
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                })
                .pop()
                .pop()
                .or("击杀榜"::equals)
                .run((strings) -> {
                    if (!CQmsg.isGroupMsg()) {
                        zibenbot.replyMsg(CQmsg, "对不起，此功能未对私聊或teamspeak开放。");
                    } else {
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
                        zibenbot.replyMsg(CQmsg, msgs.toString());
                    }
                })
                .build(new FuncCommanderFactory()));
        commander.execute(CQmsg);

        /*if (CQmsg.msg.equals("肃静")) {
            if (!CQmsg.isGroupMsg()) {
                zibenbot.replyMsg(CQmsg, "对不起，此功能未对私聊或teamspeak开放。");
            }
            new AmnestyClass(CQ, CQmsg.fromGroup, 1);
        } else if (CQmsg.msg.equals("大赦")) {
            if (!CQmsg.isGroupMsg()) {
                zibenbot.replyMsg(CQmsg, "对不起，此功能未对私聊或teamspeak开放。");
            }
            new AmnestyClass(CQ, CQmsg.fromGroup, banRecord).done(banRecord.getGroupObject(CQmsg.fromGroup).getBanList());
        } else if (CQmsg.msg.startsWith(".")) {
            if (CQmsg.msg.contains("禁言")) {
                if (!CQmsg.isGroupMsg()) {
                    zibenbot.replyMsg(CQmsg, "对不起，此功能未对私聊或teamspeak开放。");
                }
                String[] strlist = CQmsg.msg.split(" ");
                if (strlist.length == 3) {
                    try {
                        if (CC.getAt(CQmsg.msg) == 2375985957L) {
                            zibenbot.replyMsg(CQmsg, "对不起，做不到。");
                            if (CQmsg.fromClient != 2375985957L) {
                                CQ.setGroupBan(CQmsg.fromGroup, CQmsg.fromClient, Long.parseLong(strlist[2]));
                                zibenbot.groupBan(2, 00000001, CQmsg.fromGroup, CQmsg.fromClient, CQmsg.fromClient, Long.parseLong(strlist[2]));
                                banRecord.getGroupObject(CQmsg.fromGroup).addBan(CQmsg.fromClient);
                                banRecord.getGroupObject(CQmsg.fromGroup).addMemebrBanedTime(CQmsg.fromClient, CQmsg.fromClient);
                            }
                        } else {
                            CQ.setGroupBan(CQmsg.fromGroup, CC.getAt(CQmsg.msg), Long.parseLong(strlist[2]));
                            zibenbot.groupBan(2, 00000001, CQmsg.fromGroup, CQmsg.fromClient, CC.getAt(CQmsg.msg), Long.parseLong(strlist[2]));
                            banRecord.getGroupObject(CQmsg.fromGroup).addBan(CC.getAt(CQmsg.msg));
                            banRecord.getGroupObject(CQmsg.fromGroup).addMemebrBanedTime(CQmsg.fromClient, CC.getAt(CQmsg.msg));
                        }
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
            } else if (CQmsg.msg.equals(".击杀榜")) {
                if (!CQmsg.isGroupMsg()) {
                    zibenbot.replyMsg(CQmsg, "对不起，此功能未对私聊或teamspeak开放。");
                }
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
                zibenbot.replyMsg(CQmsg, msgs.toString());
            }
        }*/
    }
}
