package com.aye10032;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import static com.aye10032.Utils.IDNameUtil.getGroupNameByID;

public class SendGroupMSGTask implements Runnable {

    private Zibenbot zibenbot;
    private List<Long> groupList = new ArrayList<Long>();
    private String msg;

    public SendGroupMSGTask(Zibenbot zibenbot, List<Long> list, String msg) {
        this.zibenbot = zibenbot;
        this.msg = msg;
        for (long group : list) {
            final boolean add = this.groupList.add(group);
        }
    }

    public void run() {
        try {
            // 在这里写你要执行的内容
            for (long fromGroup : groupList) {
                Zibenbot.logger.log(Level.INFO, String.format("向群[%s]发送消息:%s", getGroupNameByID(fromGroup, zibenbot.getCoolQ().getGroupList()) ,msg));
                zibenbot.getCoolQ().sendGroupMsg(fromGroup, zibenbot.getCQCode().at(-1) + msg);
            }
        } catch (Exception e) {
            System.out.println("-------------解析信息发生异常--------------");
        }
    }
}
