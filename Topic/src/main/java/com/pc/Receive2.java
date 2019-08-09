package com.pc;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Description
 * @Author zhouzixiang
 * @Date 2019/8/9 11:46
 **/
public class Receive2 extends AbstractReceiver{

    public static void main(String[] args) throws IOException, TimeoutException {
//        消费者1 接受error消息
        receiveMsg("topic-test2",Send.exchangeName,"topic",new String[]{"category.*"});

    }
}

