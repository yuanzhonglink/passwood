package com.pass.util.task.scheduled;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * scheduled
 *
 * @author yuanzhonglin
 * @date 2019/5/20
 */
public class ScheduledTask {

    private ScheduledExecutorService scheduledExecutor = Executors.newSingleThreadScheduledExecutor();

    private SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");

    // 延迟（时间段）执行
    public void timingTask() {
       scheduledExecutor.schedule(new Runnable() {
           @Override
           public void run() {
               System.out.println("Hello Pass!");
           }
       }, 2000, TimeUnit.SECONDS);
    }

    /**
     * 1.延迟initialDelay时间，任务开始周期执行
     * 2.如果任务执行时间超出延迟时间，等待上一个任务执行完毕，开始执行下一个任务
     */
    public void delayOne() {
        scheduledExecutor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println(sdf.format(new Date()) + "--Hello Pass!");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(sdf.format(new Date()) + "--end");
            }
        }, 0, 2,TimeUnit.SECONDS);
    }

    /**
     * 1.延迟initialDelay时间，任务开始周期执行
     * 2.效果：上一次任务执行完毕后，延迟时间段，开始下一次执行
     */
    public void delayTwo() {
        scheduledExecutor.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                System.out.println(sdf.format(new Date()) + "--Hello Pass!");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(sdf.format(new Date()) + "--end");
            }
        }, 0, 2,TimeUnit.SECONDS);
    }

    public static void main(String[] args) {
        ScheduledTask scheduledTask = new ScheduledTask();
        scheduledTask.delayTwo();
    }
}
