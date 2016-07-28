package com.jikexueyuan.server;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

import java.util.ArrayList;
import java.util.List;

public class MySocketHandler extends IoHandlerAdapter {

    private List<IoSession> list = new ArrayList<>();

    @Override
    public void sessionCreated(IoSession ioSession) throws Exception {
        //该服务器只支持连接2个客户端 多余客户端将会被关闭
        if (list.size() == 2) {
            ioSession.closeNow();
        } else {
            list.add(ioSession);
            System.out.println("create");
        }
    }

    @Override
    public void sessionClosed(IoSession ioSession) throws Exception {
        list.remove(ioSession);
    }

    @Override
    public void messageReceived(IoSession ioSession, Object o) throws Exception {
        for (IoSession i : list) {
            if (i != ioSession) {
                i.write(o);
            }
        }

    }

}
