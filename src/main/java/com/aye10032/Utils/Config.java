package com.aye10032.Utils;

import java.util.HashMap;

public class Config {

    HashMap<String, String> map = new HashMap<>();

    public String getWithDafault(String key, String defualt) {
        if (map.containsKey(key)) {
            return map.get(key);
        } else {
            map.put(key, defualt);
            return defualt;
        }
    }

    public String get(String key) {
        return map.get(key);
    }

    public String set(String key, String value) {
        return map.get(key);
    }


}
