package com.aye10032.Utils.TimeUtil;

/**
 * @author Dazo66
 */
public class TimeFlow implements Runnable {

    TimeTaskPool pool;
    Thread thread = new Thread(this);
    int times = 0;

    public TimeFlow(TimeTaskPool pool) {
        this.pool = pool;
    }

    public void flush() {
        thread.interrupt();
        thread = new Thread(this);
        thread.start();
    }

    public void run() {
        System.out.println("Thread 1 run");
        while (!Thread.currentThread().isInterrupted()) {
            if (pool.getNextTasks().size() == 0) {
                continue;
            }
            long timeInterval = pool.nextTasks.get(0).getTiggerTime().getTime() - System.currentTimeMillis();
            try {
                if (timeInterval > 0) {
                    Thread.sleep(timeInterval);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;//捕获到异常之后，执行break跳出循环。
            }

            for (TimedTask task : pool.nextTasks) {
                try {
                    System.out.println("run");
                    task.run();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
