package com.dazo66.test;

import com.aye10032.Utils.ConfigLoader;
import org.apache.commons.lang3.ObjectUtils;

import java.util.HashMap;

public class ConfigTest {
    public static void main(String[] args) {
        //直接加载 如果不存在则加载json:"{}" 会被序列化成空的对象
        user user = ConfigLoader.load("res/test.txt", user.class);
        System.out.println(user);
        System.out.println(user.getId());
        //文件不存在会在保存的时候创建
        ConfigLoader.save("res/test.txt", user.class, user);
    }
}

class user{

    //以私有形式 防止获得null
    private String name;
    private Integer id;
    private HashMap map;

    user(String s, int i) {
        name = s;
        id = i;
    }

    //在获取的同时进行设置默认值
    //如果字段没有调用则始终为null调用一次则设置为默认值
    public String getName() {
        return name = ObjectUtils.defaultIfNull(name, "123");
    }

    //在获取的同时进行设置默认值
    public Integer getId() {
        return id = ObjectUtils.defaultIfNull(id, 123);
    }
}
