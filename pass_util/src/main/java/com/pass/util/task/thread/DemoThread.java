package com.pass.util.task.thread;

/**
 * <p>
 * 线程使用例子<br>
 * 1.定义变量，可以是xxxService等等
 * 2.增加有参构造函数
 * 3.在业务类中创建该线程，再调用start()方法
 *
 * </p>
 *
 * @author yuanzhonglin
 * @date 2019/5/27
 */
public class DemoThread extends Thread{

    @Override
    public void run() {
        System.out.println("-----running-----");
    }


}
