package com.pc;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Description
 * ������Ϣ
 * @Author zhouzixiang
 * @Date 2019/8/8 20:03
 **/
public class Receive extends AbstractReceiver{

    public static void main(String[] args) throws IOException, TimeoutException {
//        ������1 ����error��Ϣ
        receiveMsg("topic-test1",Send.exchangeName,"topic",new String[]{"category.update"});

    }
}
