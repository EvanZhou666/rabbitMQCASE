package com.pc;

import com.pc.utils.ConnectionUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Description
 * @Author zhouzixiang
 * @Date 2019/8/9 7:05
 **/
public class Send {

    public static final String  Exchange_Name  = "hello-publish-subscript-exchange";
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();

//      BuiltinExchangeType.FANOUT 不会路由
        channel.exchangeDeclare(Exchange_Name,BuiltinExchangeType.FANOUT,false);
        channel.queueDeclare(Receive1.QUEUE_NAME,false,false,false,null);
        channel.queueDeclare(Receive2.QUEUE_NAME,false,false,false,null);
        String msg = "hello-publish-subscript 测试";
        for (int i=0;i<10;i++) {
            channel.basicPublish(Exchange_Name,"",null,(msg+i).getBytes("utf-8"));
        }
        channel.close();
        connection.close();
    }
}
