package com.pc;

import com.pc.utils.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Description
 * 生产消息
 * @Author zhouzixiang
 * @Date 2019/8/8 20:06
 **/
public class Producer {
    public static final String queue_name = "queue-simple";
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(queue_name,true,false,false,null);
        String message = "hello world";
        channel.basicPublish("",queue_name,null,message.getBytes("utf-8"));
        channel.close();
        connection.close();

    }

}
