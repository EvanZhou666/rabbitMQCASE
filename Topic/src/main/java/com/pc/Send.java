package com.pc;

import com.pc.utils.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Description
 * @Author zhouzixiang
 * @Date 2019/8/9 9:08
 **/
public class Send {

    static String exchangeName= "topic-exchange-test";

    public static void sendMessage (String exchangeName,String exchangeType,String routingKey,String message) throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(exchangeName,exchangeType) ;
        channel.basicPublish(exchangeName, routingKey, null, message.getBytes());
        channel.close();
        connection.close();

    }

    public static void main(String[] args) throws IOException, TimeoutException {
        sendMessage(exchangeName,"topic","category.update","category update");
        sendMessage(exchangeName,"topic","category.add","category add");
    }
}
