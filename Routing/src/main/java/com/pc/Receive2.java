package com.pc;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Description
 * @Author zhouzixiang
 * @Date 2019/8/9 10:28
 **/
public class Receive2 extends AbstractReceiver{

    public static void main(String[] args) throws IOException, TimeoutException {
        // ������2 ���Խ���error ��info��Ϣ
        receiveMsg("routing-error-info-test",Send.exchangeName,"direct",new String[]{"error","info"});
    }
}
