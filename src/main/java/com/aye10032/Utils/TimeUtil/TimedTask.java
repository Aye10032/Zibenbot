package com.aye10032.Utils.TimeUtil;

import com.aye10032.Utils.ExceptionUtils;
import com.aye10032.Zibenbot;

import java.util.Date;
import java.util.logging.Level;

/**
 * @author Dazo66
 */
public class TimedTask implements Runnable {

    private int times = -1;

    private Long begin;

    private Long tiggerTime = System.currentTimeMillis();

    private TimeCycle cycle = TimeConstant.PER_DAY;

    private Runnable runnable = null;

    public TimedTask() {
    }

    public int getTimes() {
        return times;
    }

    public TimeCycle getCycle() {
        return cycle;
    }

    public Runnable getRunnable() {
        return runnable;
    }

    public TimedTask setCycle(TimeCycle cycle) {
        this.cycle = cycle;
        return this;
    }

    public Date getTiggerTime() {
        return new Date(tiggerTime);
    }

    public TimedTask setTiggerTime(Date tiggerTime) {
        if (begin == null) {
            this.begin = tiggerTime.getTime();
        }
        this.tiggerTime = tiggerTime.getTime();
        return this;
    }

    public Date getBegin() {
        return new Date(begin);
    }

    public Date getNextTiggerTime() {
        return TimeConstant.getNextTimeFromNow(getTiggerTime(), getCycle());
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
        try {
            getRunnable().run();
        } catch (Exception e) {
            e.printStackTrace();
            Zibenbot.logger.log(Level.WARNING, String.format("运行任务：[%s]时出现异常[%s]\n%s", this.getClass().getName(), e.getMessage(), ExceptionUtils.printStack(e)));
        }
    }
}
