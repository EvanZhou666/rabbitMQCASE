package com.pc;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Description
 * @Author zhouzixiang
 * @Date 2019/8/8 20:00
 **/
public class Send {
    public static void main(String[] args) throws IOException, TimeoutException {
        //创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername("guest");
        factory.setPassword("guest");
        //设置 RabbitMQ 地址
        factory.setHost("localhost");
//        默认端口
        factory.setPort(5672);
        //建立到代理服务器到连接

        Connection conn = factory.newConnection();
        //获得信道
        Channel channel = conn.createChannel();
        //声明交换器
        String exchangeName = "hello-exchange";
//        String queue, String exchange, String routingKey
//        channel.queueBind()

        /**
         * Actively declare a non-autodelete exchange with no extra arguments
         * @param exchange the name of the exchange  交换机名称
         * @param type the exchange type 交换机类型
         * @param durable true if we are declaring a durable exchange (the exchange will survive a server restart) 交换机持久性
         */
        channel.exchangeDeclare(exchangeName, "direct", true);

        String routingKey = "hola";
        //发布消息
        byte[] messageBodyBytes = "你的头发还好吗".getBytes("UTF-8");
        channel.queueBind("hola",exchangeName,routingKey);


        /**
         * 发布消息
         *
         * 发布消息到不存在的交换机上,将会导致通道级别的协议异常,从而导致通道关闭.
         * 当资源紧张时候,发送消息也是会被阻塞的
         *
         * @param exchange  交换机exchange名字，若为空则使用默认的exchange[]
         * @param routingKey the routing key
         * @param props other properties for the message - routing headers etc
         * @param body the message body
         */

        channel.basicPublish(exchangeName, routingKey, null, messageBodyBytes);

        channel.close();
        conn.close();
    }

}
