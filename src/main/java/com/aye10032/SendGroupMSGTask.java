package com.aye10032;

import org.meowy.cqp.jcq.entity.CoolQ;
import org.meowy.cqp.jcq.message.CQCode;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

public class SendGroupMSGTask extends TimerTask {

    private CoolQ CQ;
    private CQCode CC;
    private List<Long> groupList = new ArrayList<Long>();
    private String MSG;

    public SendGroupMSGTask(CoolQ cq, CQCode cc, List<Long> list, String msg) {
        this.CQ = cq;
        this.CC = cc;
        this.MSG = msg;
        for (long group : list) {
            final boolean add = this.groupList.add(group);
        }
    }

    @Override
    public void run() {
        try {
            // 在这里写你要执行的内容
            for (long fromGroup : groupList) {
                CQ.sendGroupMsg(fromGroup, CC.at(-1) + MSG);
            }
        } catch (Exception e) {
            System.out.println("-------------解析信息发生异常--------------");
        }
    }
}
