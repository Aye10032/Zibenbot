package com.aye10032.Utils.TimeUtil;

import com.aye10032.Zibenbot;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;

/**
 * @author Dazo66
 */
public class TimeFlow implements Runnable {

    TimeTaskPool pool;
    Thread thread = new Thread(this);
    ExecutorService service = Executors.newCachedThreadPool();

    public TimeFlow(TimeTaskPool pool) {
        this.pool = pool;
    }

    public void flush() {
        //中断线程的while循环 结束线程
        thread.interrupt();
        //创建新的线程 由于只有一个线程 这里就不使用线程池了
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        Zibenbot.logger.log(Level.INFO, "Time Thread Start");
        while (!Thread.currentThread().isInterrupted()) {
            //如果没有任务 就使线程长时间休眠
            if (pool.getNextTasks().size() == 0) {
                try {
                    Thread.sleep(1111114514);
                } catch (InterruptedException e) {
                    System.out.println("线程刷新");
                    break;//捕获到异常之后，执行break跳出循环。
                }
            }
            long timeInterval = pool.nextTasks.get(0).getTiggerTime().getTime() - System.currentTimeMillis();
            try {
                if (timeInterval > 0) {
                    Thread.sleep(timeInterval);
                }
            } catch (InterruptedException e) {
                Zibenbot.logger.log(Level.INFO, "Time Thread Flush");
                break;//捕获到异常之后，执行break跳出循环。
            }

            for (TimedTask task : pool.nextTasks) {
                try {
                    if (!(task instanceof AsynchronousTaskPool)) {
                        Zibenbot.logger.log(Level.INFO, String.format("触发任务", task.runnable.getClass().getSimpleName()));
                    }

                    if (task.runnable != null && (task.times > 0 || task.times == -1)) {
                        if (task.times > 0) {
                            task.times--;
                        }
                        service.execute(task);
                        task.tiggerTime = task.getNextTiggerTime();
                    }
                    if (task.times == 0) {
                        pool.remove(task);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Zibenbot.logger.log(Level.WARNING, String.format("运行任务：[%s]时出现异常[%s]", task.getClass().getName(), e.getMessage()));
                }
            }
        }
    }

}
