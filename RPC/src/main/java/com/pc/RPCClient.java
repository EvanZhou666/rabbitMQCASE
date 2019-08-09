package com.pc;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.AMQP.BasicProperties;

//RPC���ÿͻ���
public class RPCClient {
    private Connection connection;
    private Channel channel;
    private String requestQueueName = "rpc_queue";
    private String replyQueueName;
    private QueueingConsumer consumer;

    public RPCClient() throws Exception {
        //?	�Ƚ���һ�����Ӻ�һ��ͨ������Ϊ�ص�����һ��Ψһ��'�ص�'����
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.30.40");
        factory.setUsername("admin");
        factory.setPassword("admin");
        factory.setPort(AMQP.PROTOCOL.PORT);
        connection = factory.newConnection();
        channel = connection.createChannel();
        //?	ע��'�ص�'���У������Ϳ����յ�RPC��Ӧ
        replyQueueName = channel.queueDeclare().getQueue();
        consumer = new QueueingConsumer(channel);
        channel.basicConsume(replyQueueName, true, consumer);
    }

    //����RPC����
    public String call(String message) throws Exception {
        String response = null;
        String corrId = java.util.UUID.randomUUID().toString();
        //����������Ϣ����Ϣʹ�����������ԣ�replyto��correlationId
        BasicProperties props = new BasicProperties.Builder()
                .correlationId(corrId).replyTo(replyQueueName).build();
        channel.basicPublish("", requestQueueName, props, message.getBytes());
        //�ȴ����ս��
        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            //�������correlationId�Ƿ���������Ҫ�ҵ��Ǹ�
            if (delivery.getProperties().getCorrelationId().equals(corrId)) {
                response = new String(delivery.getBody());
                break;
            }
        }
        return response;
    }
    public void close() throws Exception {
        connection.close();
    }

    public static void main(String[] args) throws Exception {
        RPCClient rpcClient = new RPCClient();
        System.out.println(" [x] Requesting getMd5String(abc)");
        String response = rpcClient.call("abc");
        System.out.println(" [.] Got '" + response + "'");
        rpcClient.close();
    }
}
