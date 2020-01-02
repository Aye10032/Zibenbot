package com.aye10032.BanUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BanRecord {

    private Map<Long, AyeGroup> groupMap = new HashMap<Long, AyeGroup>();

    public BanRecord() {

    }

    public boolean GroupExist(long fromGroup) {
        return groupMap.containsKey(fromGroup);
    }

    public AyeGroup getGroupObject(long fromGroup) {
        return groupMap.get(fromGroup);
    }

}
