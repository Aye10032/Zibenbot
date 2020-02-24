package com.aye10032.Utils.TimeUtil;

/**
 * @author Dazo66
 */
public class TimeFlow implements Runnable {

    TimeTaskPool pool;
    Thread thread = new Thread(this);

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

    public void run() {
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
                System.out.println("线程刷新");
                break;//捕获到异常之后，执行break跳出循环。
            }

            for (TimedTask task : pool.nextTasks) {
                try {
                    //依次运行任务
                    task.run();
                    //当执行次数为0时从等待任务中删除
                    if (task.times == 0) {
                        pool.remove(task);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
