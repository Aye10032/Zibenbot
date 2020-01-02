package com.aye10032.BanUtil;

import java.util.HashMap;
import java.util.Map;

public class AyeGroup {

    private long Group;
    private Map<Long, AyeMember> memberMap = new HashMap<Long, AyeMember>();

    public AyeGroup(long group) {
        this.Group = group;
    }

    public boolean MemebrExist(long QQ) {
        return memberMap.containsKey(QQ);
    }

    public void addMemebr(long QQ) {
        AyeMember member = new AyeMember(QQ);
        memberMap.put(QQ, member);
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
