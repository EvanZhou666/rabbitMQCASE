package com.pc;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Description
 * @Author zhouzixiang
 * @Date 2019/8/9 11:02
 **/
public abstract class AbstractReceiver {

    public static void receiveMsg (String queueName,String exchangeName,String exchangeType,String[] routingKeys) throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.getConnection();
        final Channel channel = connection.createChannel();
        channel.exchangeDeclare(exchangeName,exchangeType);
        channel.queueDeclare(queueName,false,false,false,null);
        System.out.println("-----queueName: "+queueName);

//        每条队列可以绑定多个routingKey
        for (String routingKey : routingKeys) {
            channel.queueBind(queueName,exchangeName,routingKey);
        }

        DefaultConsumer defaultConsumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "utf-8");
                System.out.println("收到的消息是: "+message);
//                channel.basicAck(envelope.getDeliveryTag(),false);
            }
        };

        channel.basicConsume(queueName, true, defaultConsumer);

    }


}
