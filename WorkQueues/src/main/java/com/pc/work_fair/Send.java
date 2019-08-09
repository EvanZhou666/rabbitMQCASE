package com.pc.work_fair;

import com.pc.utils.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Description
 * @Author zhouzixiang
 * @Date 2019/8/8 21:42
 **/
public class Send {
    public static final String  queue_name  = "hello-worker-fair-queue";
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();
//       每个消费者发送确认消息之前，mq不发送下一条消息
        channel.basicQos(1);

        channel.queueDeclare(queue_name,false,false,false,null);
        String msg = "hello-worker-fair-queue测试";
        for (int i=0;i<10;i++) {
            channel.basicPublish("",queue_name,null,(msg+i).getBytes("utf-8"));

        }

        channel.close();
        connection.close();
    }

}
