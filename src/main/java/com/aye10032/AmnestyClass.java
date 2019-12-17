package com.aye10032;

import org.meowy.cqp.jcq.entity.CoolQ;
import org.meowy.cqp.jcq.entity.Member;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AmnestyClass {

    private CoolQ CQ;
    private long fromGroup;

    public AmnestyClass(CoolQ CQ, long fromGroup) {
        this.CQ = CQ;
        this.fromGroup = fromGroup;
        System.out.println("start");
        done();
    }

    private void done() {
        List<Member> memberList = CQ.getGroupMemberList(fromGroup);
        for (Member persion : memberList) {
            long qq = persion.getQQId();
            int flag = CQ.setGroupBan(fromGroup, qq, 0);

        }

        Date date = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        CQ.sendGroupMsg(fromGroup, ft.format(date) + " 王大赦天下\n------《史记 奥创本纪》");

    }

}
