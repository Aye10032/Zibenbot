package com.aye10032.Utils.TimeUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Dazo66
 */
public class TimeTaskPool {

    List<TimedTask> nextTasks = Collections.synchronizedList(new ArrayList<TimedTask>());
    List<TimedTask> tasks = Collections.synchronizedList(new ArrayList<TimedTask>());
    TimeFlow flow;

    public TimeTaskPool(){
        flow = new TimeFlow(this);
    }

    public void add(TimedTask task) {
        tasks.add(task);

        flow.flush();
    }

    public void remove(TimedTask task) {
        tasks.remove(task);
        flow.flush();
    }


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
