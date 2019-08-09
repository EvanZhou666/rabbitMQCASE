package com.pc;

import com.rabbitmq.client.*;

import java.security.MessageDigest;

/**
 * @Description
 * @Author zhouzixiang
 * @Date 2019/8/9 13:04
 **/
public class RPCServer {
    private static final String RPC_QUEUE_NAME = "rpc_queue";
    public static void main(String[] args) throws Exception {
        //?	�Ƚ������ӡ�ͨ��������������
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.30.40");
        factory.setUsername("admin");
        factory.setPassword("admin");
        factory.setPort(AMQP.PROTOCOL.PORT);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(RPC_QUEUE_NAME, false, false, false, null);
        //?�������ж�����������̡�ͨ��channel.basicQos����prefetchCount���Կɽ�����ƽ�����䵽��̨�������ϡ�
        channel.basicQos(1);
        QueueingConsumer consumer = new QueueingConsumer(channel);
        //��Ӧ�����autoAck=false
        channel.basicConsume(RPC_QUEUE_NAME, false, consumer);
        System.out.println(" [x] Awaiting RPC requests");
        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            BasicProperties props = delivery.getProperties();
            BasicProperties basicProperties = new AMQP.BasicProperties();
            BasicProperties replyProps = ((AMQP.BasicProperties) basicProperties).builder()
                    .correlationId(props.getCorrelationId()).build();
            String message = new String(delivery.getBody());
            System.out.println(" [.] getMd5String(" + message + ")");
            String response = getMd5String(message);
            //���ش���������
            channel.basicPublish("", props.getReplyTo(), (AMQP.BasicProperties)replyProps,
                    response.getBytes());
            //����Ӧ��
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        }
    }
    // ģ��RPC���� ��ȡMD5�ַ���
    public static String getMd5String(String str) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }
        char[] charArray = str.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++)
            byteArray[i] = (byte) charArray[i];
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16)
                hexValue.append("0");
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }

}
