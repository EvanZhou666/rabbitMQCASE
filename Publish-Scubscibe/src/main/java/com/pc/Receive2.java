package com.pc;

import com.pc.utils.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Description
 * @Author zhouzixiang
 * @Date 2019/8/9 7:19
 **/
public class Receive2 {
    public static final String QUEUE_NAME = "hello-publish-subscript"+"-second";
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.getConnection();
        final Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        channel.queueBind(QUEUE_NAME,Send.Exchange_Name,"");
        channel.basicQos(1);

        DefaultConsumer defaultConsumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "utf-8");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println("收到的消息是: "+message);
                    channel.basicAck(envelope.getDeliveryTag(),false);

                }
            }
        };

//        关闭自动应答
        channel.basicConsume(QUEUE_NAME,false,defaultConsumer);
    }
}
