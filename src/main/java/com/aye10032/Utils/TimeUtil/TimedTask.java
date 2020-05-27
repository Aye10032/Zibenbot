package com.aye10032.Utils.TimeUtil;

import java.util.Date;

/**
 * @author Dazo66
 */
public class TimedTask implements Runnable {

    int times = -1;

    Date tiggerTime = new Date();

    TaskCycle cycle = TimeConstant.PER_DAY;

    Runnable runnable = null;

    public TimedTask() {
    }

    public TimedTask setCycle(TaskCycle cycle) {
        this.cycle = cycle;
        return this;
    }

    public Date getTiggerTime() {
        return tiggerTime;
    }

    public TimedTask setTiggerTime(Date tiggerTime) {
        this.tiggerTime = tiggerTime;
        return this;
    }

    public Date getNextTiggerTime() {
        return cycle.getNextTime(tiggerTime);
    }

    public TimedTask setTimes(int times) {
        this.times = times;
        return this;
    }

    public TimedTask setRunnable(Runnable runnable) {
        this.runnable = runnable;
        return this;
    }

    @Override
    public void run() {
        runnable.run();
    }
}
