package com.aye10032.Functions;

import com.aye10032.Functions.funcutil.BaseFunc;
import com.aye10032.Functions.funcutil.CQMsg;
import com.aye10032.Utils.AmnestyClass;
import com.aye10032.Utils.BanUtil.BanRecord;
import com.aye10032.Zibenbot;
import com.dazo66.command.Commander;
import com.dazo66.command.CommanderBuilder;
import org.apache.commons.lang3.math.NumberUtils;
import org.meowy.cqp.jcq.entity.CoolQ;
import org.meowy.cqp.jcq.message.CQCode;

import java.util.List;

public class BanFunc extends BaseFunc {
    Commander<CQMsg> commander;
    BanRecord banRecord;
    CoolQ CQ;
    CQCode CC;

    public BanFunc(Zibenbot zibenbot) {
        super(zibenbot);
        CQ = zibenbot.getCoolQ();
        CC = zibenbot.getCQCode();
        commander =  new CommanderBuilder<CQMsg>()
                .start()
                .or("肃静"::equals)
                .run((cqmsg) -> {
                    if (cqmsg.isGroupMsg()) {
                        new AmnestyClass(CQ, cqmsg.fromGroup, 1);
                    } else {
                        zibenbot.replyMsg(cqmsg, "对不起，此功能未对私聊或teamspeak开放。");
                    }
                })
                .or("大赦"::equals)
                .run((cqmsg) -> {
                    if (cqmsg.isGroupMsg()) {
                        new AmnestyClass(CQ, cqmsg.fromGroup, banRecord).done(banRecord.getGroupObject(cqmsg.fromGroup).getBanList());
                    } else {
                        zibenbot.replyMsg(cqmsg, "对不起，此功能未对私聊或teamspeak开放。");
                    }
                })
                .or("禁言"::equals)
                .next()
                    .or(s -> true)
                    .next()
                        .or(NumberUtils::isDigits)
                        .run((cqmsg) -> {
                            String[] strings = cqmsg.getCommandPieces();
                            try {
                                if (CC.getAt(cqmsg.msg) == 2375985957L) {
                                    zibenbot.replyMsg(cqmsg, "对不起，做不到。");
                                    if (cqmsg.fromClient != 2375985957L) {
                                        if (cqmsg.fromClient == 895981998L) {
                                            CQ.setGroupBan(cqmsg.fromGroup, cqmsg.fromClient, 100);
                                        } else {
                                            CQ.setGroupBan(cqmsg.fromGroup, cqmsg.fromClient,
                                                    Long.parseLong(strings[2]));
                                            zibenbot.groupBan(2, 00000001, cqmsg.fromGroup,
                                                    cqmsg.fromClient, cqmsg.fromClient,
                                                    Long.parseLong(strings[2]));
                                            banRecord.getGroupObject(cqmsg.fromGroup).addBan(cqmsg.fromClient);
                                            banRecord.getGroupObject(cqmsg.fromGroup).addMemebrBanedTime(cqmsg.fromClient, cqmsg.fromClient);
                                        }
                                    }
                                } else if (CC.getAt(cqmsg.msg) == 895981998L) {
                                    CQ.setGroupBan(cqmsg.fromGroup, CC.getAt(cqmsg.msg), 100);
                                    zibenbot.groupBan(2, 00000001, cqmsg.fromGroup,
                                            cqmsg.fromClient, CC.getAt(cqmsg.msg),
                                            Long.parseLong(strings[2]));
                                    banRecord.getGroupObject(cqmsg.fromGroup).addBan(CC.getAt(cqmsg.msg));
                                    banRecord.getGroupObject(cqmsg.fromGroup).addMemebrBanedTime(cqmsg.fromClient, CC.getAt(cqmsg.msg));


                                } else {
                                    CQ.setGroupBan(cqmsg.fromGroup, CC.getAt(cqmsg.msg),
                                            Long.parseLong(strings[2]));
                                    zibenbot.groupBan(2, 00000001, cqmsg.fromGroup,
                                            cqmsg.fromClient, CC.getAt(cqmsg.msg),
                                            Long.parseLong(strings[2]));
                                    banRecord.getGroupObject(cqmsg.fromGroup).addBan(CC.getAt(cqmsg.msg));
                                    banRecord.getGroupObject(cqmsg.fromGroup).addMemebrBanedTime(cqmsg.fromClient, CC.getAt(cqmsg.msg));
                                }
                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                            }
                        })
                        .pop()
                    .pop()
                .or("击杀榜"::equals)
                .run((cqmsg) -> {
                    if (!cqmsg.isGroupMsg()) {
                        zibenbot.replyMsg(cqmsg, "对不起，此功能未对私聊或teamspeak开放。");
                    } else {
                        List<String> list = banRecord.getKillRank(cqmsg.fromGroup);
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
                        zibenbot.replyMsg(cqmsg, msgs.toString());
                    }
                })
                .build();
    }

    @Override
    public void run(CQMsg CQmsg) {
        commander.execute(CQmsg);
    }

    @Override
    public void setUp() {
        this.banRecord = new BanRecord(CQ, CC);
    }
}
