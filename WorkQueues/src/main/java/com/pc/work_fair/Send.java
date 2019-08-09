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
//       ÿ�������߷���ȷ����Ϣ֮ǰ��mq��������һ����Ϣ
        channel.basicQos(1);

        channel.queueDeclare(queue_name,false,false,false,null);
        String msg = "hello-worker-fair-queue����";
        for (int i=0;i<10;i++) {
            channel.basicPublish("",queue_name,null,(msg+i).getBytes("utf-8"));

        }

        channel.close();
        connection.close();
    }

}
