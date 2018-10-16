package com.xuelongiang.netty.timeserver;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author xuelongjiang
 */
public class TimerServerClientHanlder extends ChannelHandlerAdapter {


    private final ByteBuf buf ;

    public TimerServerClientHanlder() {

        byte [] req = "Query time order".getBytes();
        buf = Unpooled.buffer(req.length);
        buf.writeBytes(req);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(buf);
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

        System.out.println("客户端异常");
        ctx.close();
    }
}
