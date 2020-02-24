package com.aye10032.Utils.TimeUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Dazo66
 */
public class TimeTaskPool {

    //创建线程安全的列表
    List<TimedTask> nextTasks = Collections.synchronizedList(new ArrayList<TimedTask>());
    List<TimedTask> tasks = Collections.synchronizedList(new ArrayList<TimedTask>());
    //时间流对象 主要是包装了时间任务的线程
    TimeFlow flow;

    public TimeTaskPool(){
        flow = new TimeFlow(this);
    }

    public void add(TimedTask task) {
        if (task.runnable != null) {
            tasks.add(task);
            flow.flush();
        }
    }

    public void remove(TimedTask task) {
        tasks.remove(task);
        flow.flush();
    }

    /**
     * 得到下一个要运行的任务列表
     * 同时运行的会放在一起
     * 只会比较时间先后 总是把先运行的拿出来
     *
     *
     * @return
     */
    public List<TimedTask> getNextTasks() {
        nextTasks.clear();
        for (TimedTask task : tasks) {
            if (nextTasks.size() == 0) {
                nextTasks.add(task);
            } else {
                int flag = task.getTiggerTime().compareTo(nextTasks.get(0).getTiggerTime());
                if (flag == 0) {
                    nextTasks.add(task);
                } else if (flag < 0) {
                    nextTasks.clear();
                    nextTasks.add(task);
                }
            }
        }

        return nextTasks;
    }




}
