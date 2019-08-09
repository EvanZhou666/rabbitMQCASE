package com.pc.wor_robbin;

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
    public static final String  queue_name  = "hello-worker-queue";
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(queue_name,false,false,false,null);
        String msg = "hello worker queue";
        for (int i=0;i<10;i++) {
            channel.basicPublish("",queue_name,null,(msg+i).getBytes("utf-8"));

        }

        channel.close();
        connection.close();
    }

}
