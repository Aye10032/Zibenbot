package com.aye10032.Utils;

import java.util.HashMap;

public class Config {

    public HashMap<String, String> map;

    public String getWithDafault(String key, String defualt) {
        if (map != null) {
            if (map.containsKey(key)) {
                return map.get(key);
            } else {
                map.put(key, defualt);
                return defualt;
            }
        } else {
            set(key, defualt);
            return defualt;
        }
    }

    public String get(String key) {
        return map == null ? null : map.get(key);
    }

    public void set(String key, String value) {
        if (map == null) {
            map = new HashMap<>();
        }
        map.put(key, value);
    }


}
