package com.aye10032.BanUtil;

import org.meowy.cqp.jcq.entity.CoolQ;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BanRecord {

    private Map<Long, AyeGroup> groupMap = new HashMap<Long, AyeGroup>();
    private CoolQ CQ;

    public BanRecord(CoolQ CQ) {
        this.CQ = CQ;
    }

    private boolean GroupExist(long fromGroup) {
        return groupMap.containsKey(fromGroup);
    }

    public AyeGroup getGroupObject(long fromGroup) {
        if (!GroupExist(fromGroup)) {
            AyeGroup group = new AyeGroup(fromGroup);
            groupMap.put(fromGroup, group);
        }
        return groupMap.get(fromGroup);
    }

    public List<String> getKillRank(long fromGroup){
        List<String> list = new ArrayList<String>();
        if (!GroupExist(fromGroup)){
            list.add("本群往前的历史是一片空白，没有记载");
        }else {
            AyeGroup group = getGroupObject(fromGroup);

        }
        return list;
    }

}
