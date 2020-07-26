package com.aye10032.Utils.TimeUtil;

import com.aye10032.Zibenbot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;

/**
 * @author Dazo66
 */
public class TimeTaskPool {

    //创建线程安全的列表
    List<TimedTask> nextTasks = Collections.synchronizedList(new ArrayList<>());
    public List<TimedTask> tasks = Collections.synchronizedList(new ArrayList<>());

    //时间流对象 主要是包装了时间任务的线程
    TimeFlow flow;

    //异步线程池
    //使用方法 asynchronousPool(callback, runnables);
    //当后面所有的任务异步运行完毕后， 执行callback
    public AsynchronousTaskPool asynchronousPool;

    public TimeTaskPool() {
        flow = new TimeFlow(this);
        asynchronousPool = new AsynchronousTaskPool();
        add(asynchronousPool);
    }

    public void add(TimedTask task) {
        if (task.getRunnable() != null) {
            tasks.add(task);
            flow.flush();
            Zibenbot.logger.log(Level.INFO, String.format("添加时间任务 触发时间：%s 当前时间%s", task.getTiggerTime().toString(), new Date().toString()));
        }
    }

    public void flush(){
        flow.flush();
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
     * @return
     */
    public List<TimedTask> getNextTasks() {
        nextTasks.clear();
        for (TimedTask task : tasks) {
            if (task.getTiggerTime() == null) {
                continue;
            }
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

    public void timeoutEvent(int millisTimeout, Runnable runnable) {
        add(new TimedTask()
                .setTimes(1)
                .setRunnable(runnable)
                .setTiggerTime(new Date(System.currentTimeMillis() + millisTimeout))
        );
    }
}
