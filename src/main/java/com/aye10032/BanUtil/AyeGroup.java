package com.aye10032.BanUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AyeGroup {

    private long Group;
    private Map<Long, AyeMember> memberMap = new HashMap<Long, AyeMember>();
    private List<Long> banList = new ArrayList<Long>();

    public AyeGroup(long group) {
        this.Group = group;
    }

    private boolean MemebrExist(long QQ) {
        return memberMap.containsKey(QQ);
    }

    public void addBan(long QQ) {
        banList.add(QQ);
    }

    public List<Long> getBanList(){
        return this.banList;
    }

    public AyeMember getMemberObject(long QQ) {
        return memberMap.get(QQ);
    }

    public void addMemebrBanedTime(long fromQQ, long baned) {
        if (!MemebrExist(fromQQ)) {
            AyeMember member = new AyeMember(fromQQ);
            memberMap.put(fromQQ, member);
        }
        if (!MemebrExist(baned)) {
            AyeMember member = new AyeMember(baned);
            memberMap.put(baned, member);
        }
        getMemberObject(fromQQ).addBanOtherTime();
        getMemberObject(baned).addBanedTime();
    }

    public int getMemberBandeTime(long QQ) {
        if (!MemebrExist(QQ)) {
            return 0;
        } else {
            AyeMember member = memberMap.get(QQ);
            return member.getBanedTime();
        }
    }

    public int getMemberBanOtherTime(long QQ) {
        if (!MemebrExist(QQ)) {
            return 0;
        } else {
            AyeMember member = memberMap.get(QQ);
            return member.getBanOtherTime();
        }
    }

}
