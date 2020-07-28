package com.aye10032.Utils.TimeUtil;

import com.aye10032.Utils.ExceptionUtils;
import com.aye10032.Zibenbot;

import java.util.Date;
import java.util.logging.Level;

/**
 * 时间任务的子类
 *
 * @author Dazo66
 */
public abstract class TimedTaskBase implements Runnable {

    private int times = -1;

    private Long begin;

    private Long tiggerTime = System.currentTimeMillis();

    private ITimeAdapter cycle = TimeConstant.NEXT_DAY;

    private Runnable runnable = null;

    public TimedTaskBase() {
    }

    public int getTimes() {
        return times;
    }

    public ITimeAdapter getCycle() {
        return cycle;
    }

    public Runnable getRunnable() {
        return runnable;
    }

    public TimedTaskBase setCycle(ITimeAdapter cycle) {
        this.cycle = cycle;
        return this;
    }

    public Date getTiggerTime() {
        return new Date(tiggerTime);
    }

    public TimedTaskBase setTiggerTime(Date tiggerTime) {
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

    public TimedTaskBase setTimes(int times) {
        this.times = times;
        return this;
    }

    public TimedTaskBase setRunnable(Runnable runnable) {
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
