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
        //�������ӹ���
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername("guest");
        factory.setPassword("guest");
        //���� RabbitMQ ��ַ
        factory.setHost("localhost");
//        Ĭ�϶˿�
        factory.setPort(5672);
        //���������������������

        Connection conn = factory.newConnection();
        //����ŵ�
        Channel channel = conn.createChannel();
        //����������
        String exchangeName = "hello-exchange";
//        String queue, String exchange, String routingKey
//        channel.queueBind()

        /**
         * Actively declare a non-autodelete exchange with no extra arguments
         * @param exchange the name of the exchange  ����������
         * @param type the exchange type ����������
         * @param durable true if we are declaring a durable exchange (the exchange will survive a server restart) �������־���
         */
        channel.exchangeDeclare(exchangeName, "direct", true);

        String routingKey = "hola";
        //������Ϣ
        byte[] messageBodyBytes = "���ͷ��������".getBytes("UTF-8");
        channel.queueBind("hola",exchangeName,routingKey);


        /**
         * ������Ϣ
         *
         * ������Ϣ�������ڵĽ�������,���ᵼ��ͨ�������Э���쳣,�Ӷ�����ͨ���ر�.
         * ����Դ����ʱ��,������ϢҲ�ǻᱻ������
         *
         * @param exchange  ������exchange���֣���Ϊ����ʹ��Ĭ�ϵ�exchange[]
         * @param routingKey the routing key
         * @param props other properties for the message - routing headers etc
         * @param body the message body
         */

        channel.basicPublish(exchangeName, routingKey, null, messageBodyBytes);

        channel.close();
        conn.close();
    }

}
