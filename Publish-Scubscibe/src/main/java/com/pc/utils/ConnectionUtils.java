package com.pc.utils;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Description
 * ��ȡ���ӹ�����
 * @Author zhouzixiang
 * @Date 2019/8/8 20:07
 **/
public class ConnectionUtils {
    private static final String  host= "localhost";
    private static final int port = 5672;
    private static final String username = "admin";
    private static final String password = "admin";
    private static final String visualHost = "/";

    public static Connection getConnection () throws IOException, TimeoutException {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername(username);
        factory.setPassword(password);
        //���� RabbitMQ ��ַ
        factory.setHost(host);
//        Ĭ�϶˿�
        factory.setPort(port);

        factory.setVirtualHost(visualHost);
        //���������������������
        Connection conn = factory.newConnection();
        return conn;
    }
}
