package com.aye10032;

import org.meowy.cqp.jcq.entity.CoolQ;
import org.meowy.cqp.jcq.entity.Member;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AmnestyClass {

    private CoolQ CQ;
    private long fromGroup;

    public AmnestyClass(CoolQ CQ, long fromGroup, int mode) {
        this.CQ = CQ;
        this.fromGroup = fromGroup;
//        System.out.println("start");
        if (mode == 0) {
            done();
        }else if (mode == 1){
            shutup();
        }
    }

    private void done() {
        List<Member> memberList = CQ.getGroupMemberList(fromGroup);
        for (Member persion : memberList) {
            long qq = persion.getQQId();
            int flag = CQ.setGroupBan(fromGroup, qq, 0);

        }

        Date date = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        String msg = ft.format(date) + " 群臣奏请大赦天下，王曰：“善。” ，乃大赦天下\n";
        if (fromGroup == 995497677L){
             msg += "------《史记 奥创本纪》";
        }else if (fromGroup == 792666782L){
            msg += "------《史记 卞高祖本纪》";
        }
        CQ.sendGroupMsg(fromGroup, msg);

    }

    private void shutup() {
        List<Member> memberList = CQ.getGroupMemberList(fromGroup);
        for (Member persion : memberList) {
            long qq = persion.getQQId();
            if (qq != 2375985957L) {
                int flag = CQ.setGroupBan(fromGroup, qq, 114514);
            }

        }

    }

}
