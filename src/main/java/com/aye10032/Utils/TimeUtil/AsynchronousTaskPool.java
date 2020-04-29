package com.aye10032.Utils.TimeUtil;


import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author Dazo66
 */
public class AsynchronousTaskPool extends TimedTask {

    ExecutorService pool;
    Map<Runnable, List<Future<?>>> runnableMap = new HashMap<>();
    Runnable thisRun = () -> {
        if (runnableMap.size() != 0) {
            boolean allExecut = true;
            List<Runnable> list = new ArrayList<>();
            for (Runnable runnable : runnableMap.keySet()) {
                for (Future<?> future : runnableMap.get(runnable)) {
                    if (!future.isDone() && !future.isCancelled()) {
                        allExecut = false;
                    }
                }
                if (allExecut) {
                    list.add(runnable);
                    runnable.run();
                }
            }
            for (Runnable r : list) {
                runnableMap.remove(r);
            }

        }
    };

    public AsynchronousTaskPool(){
        pool = Executors.newCachedThreadPool();
        setRunnable(thisRun).setTimes(-1).setCycle(TimeConstant.PER_SEC)
                .setTiggerTime(new Date(System.currentTimeMillis() + 1000));
    }

    public void execute(Runnable callback, Runnable... runnables){
        List<Future<?>> list = new ArrayList<>();
        for (Runnable run : runnables) {
            list.add(pool.submit(run));
        }
        runnableMap.put(callback, list);
    }




}
