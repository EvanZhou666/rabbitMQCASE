package com.pc;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Description
 * 消费消息
 * @Author zhouzixiang
 * @Date 2019/8/8 20:03
 **/
public class Receive extends AbstractReceiver{

    public static void main(String[] args) throws IOException, TimeoutException {
//        消费者1 接受error消息
        receiveMsg("topic-test1",Send.exchangeName,"topic",new String[]{"category.update"});

    }
}
