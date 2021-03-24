package com.pass.util.task.thread;

import com.pass.util.task.thread.DemoThread;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * 当前项目启动，执行固定方法(可以是一个线程)
 *
 * @author yuanzhonglin
 * @date 2019/5/20
 */
@Component
public class ApplicationRunnerUtil implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("项目启动--执行");
        new DemoThread().start();
    }
}
