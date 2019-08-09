package com.pc;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Description
 * @Author zhouzixiang
 * @Date 2019/8/9 7:47
 **/
public class Send {
    static String exchangeName= "routing-exchange-test";

    public static void SendMessage (String exchangeName,String exchangeType,String routingKey,String message) throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(exchangeName,exchangeType) ;
        channel.basicPublish(exchangeName, routingKey, null, message.getBytes());
        channel.close();
        connection.close();

    }
    public static void main(String[] args) throws IOException, TimeoutException {
//        发送info 和 error消息
        SendMessage(exchangeName,"direct","error","test error");
        SendMessage(exchangeName,"direct","info","test info");

    }
}
