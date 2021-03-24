package com.pass.util.fanshe.demo1;

/**
 * @author yuanzhonglin
 * @date 2021/3/23
 */
@MQConsumer
public class ServerDemo {

    @MQMethod(tableName = "one")
    public void method1(String str){
        System.out.println("method1");
    }
}
