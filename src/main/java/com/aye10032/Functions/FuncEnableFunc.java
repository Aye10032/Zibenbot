package com.aye10032.Functions;

import com.aye10032.Utils.ConfigLoader;
import com.aye10032.Zibenbot;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Dazo66
 */
public class FuncEnableFunc extends BaseFunc {

    Map<Long, List<String>> disableList;

    public FuncEnableFunc(Zibenbot zibenbot) {
        super(zibenbot);
    }

    @Override
    public void setUp() {
        disableList = ConfigLoader.load(new File(zibenbot.appDirectory + "/disable.json")
                , new TypeToken<Map<Long, List<String>>>(){}.getType());
    }

    @Override
    public void run(CQMsg CQmsg) {
        if (CQmsg.isGroupMsg()) {
            String[] msgs = CQmsg.msg.split(" ");
            Boolean flag = null;
            if (msgs[0].equals("disable") || msgs[0].equals("禁用")
                    || msgs[0].equals(".disable") || msgs[0].equals(".禁用")) {
                flag = false;
            } else if (msgs[0].equals("enable") || msgs[0].equals("启用")
                    || msgs[0].equals(".enable") || msgs[0].equals(".启用")) {
                flag = true;
            }
            if (flag != null) {
                if (msgs.length == 1) {
                    StringBuilder builder = new StringBuilder();
                    builder.append("本群当前启用的模块有：\n");
                    List<IFunc> list = zibenbot.getRegisterFunc();
                    IFunc func;
                    for (int i = 0; i < list.size(); i++) {
                        func = list.get(i);
                        if (func != this && isEnable(CQmsg.fromGroup, func)) {
                            builder.append("\t").append(func.getClass().getSimpleName());
                            builder.append("\n");
                        }
                    }
                    builder.append("本群当前禁用的模块有：\n");
                    for (int i = 0; i < list.size(); i++) {
                        func = list.get(i);
                        if (func != this && !isEnable(CQmsg.fromGroup, func)) {
                            builder.append("\t").append(func.getClass().getSimpleName());
                            if (i != list.size() - 1) {
                                builder.append("\n");
                            }
                        }
                    }
                    replyMsg(CQmsg, builder.toString());
                } else if (msgs.length == 2){
                    boolean a = false;
                    for (IFunc func : zibenbot.getRegisterFunc()) {
                        if (func == this) continue;
                        if (msgs[1].equals(func.getClass().getSimpleName())) {
                            a = true;
                            break;
                        }
                    }
                    if (a) {
                        if (flag) {
                            setEnable(CQmsg.fromGroup, msgs[1]);
                            replyMsg(CQmsg, "已启用：" + msgs[1]);
                        } else {
                            setDisable(CQmsg.fromGroup, msgs[1]);
                            replyMsg(CQmsg, "已禁用：" + msgs[1]);
                        }
                    } else {
                        replyMsg(CQmsg, "模块名称有误，或者不存在：" + msgs[1]);
                    }
                }
                save();
            }
        } else {
            replyMsg(CQmsg, "只可以对群启用/禁用功能");
        }
    }

    public void setEnable(Long groupId, String func){
        if (!isEnable(groupId, func)) {
            disableList.computeIfAbsent(groupId, k -> new ArrayList<>());
            disableList.get(groupId).remove(func);
        }
    }

    public void setEnable(Long groupId, IFunc func){
        if (!isEnable(groupId, func)) {
            disableList.computeIfAbsent(groupId, k -> new ArrayList<>());
            disableList.get(groupId).remove(func.getClass().getSimpleName());
        }
    }

    public void setDisable(Long groupId, String func){
        if (isEnable(groupId, func)) {
            disableList.computeIfAbsent(groupId, k -> new ArrayList<>());
            disableList.get(groupId).add(func);
        }
    }

    public void setDisable(Long groupId, IFunc func){
        if (isEnable(groupId, func)) {
            disableList.computeIfAbsent(groupId, k -> new ArrayList<>());
            disableList.get(groupId).add(func.getClass().getSimpleName());
        }
    }

    public boolean isEnable(Long groupId, String func) {
        disableList.computeIfAbsent(groupId, k -> new ArrayList<>());
        return disableList.get(groupId).indexOf(func) == -1;
    }

    public boolean isEnable(Long groupId, IFunc func) {
        disableList.computeIfAbsent(groupId, k -> new ArrayList<>());
        return disableList.get(groupId).indexOf(func.getClass().getSimpleName()) == -1;
    }

    public void save(){
        ConfigLoader.save(new File(zibenbot.appDirectory + "/disable.json")
                , new TypeToken<Map<Long, List<String>>>(){}.getType(), disableList);
    }
}
