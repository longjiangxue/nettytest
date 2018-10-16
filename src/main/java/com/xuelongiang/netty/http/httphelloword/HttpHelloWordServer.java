package com.xuelongiang.netty.http.httphelloword;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @author xuelongjiang
 */
public class HttpHelloWordServer {


    public static void main(String[] args) {


        // 服务端模版代码
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup work = new NioEventLoopGroup();

        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(boss, work)
             .channel(NioServerSocketChannel.class)
             .option(ChannelOption.SO_BACKLOG,1024)
             .childHandler(new ChannelInitializer<SocketChannel>() {

                 @Override
                 protected void initChannel(SocketChannel ch) throws Exception {
                     ChannelPipeline pipeline =  ch.pipeline();
                     pipeline.addLast(new HttpServerCodec());
                     // pipeline.addLast();
                     //添加 hello word 处理器
                     pipeline.addLast(new HttpServerHelloWordHanlder());
                 }
             });

            Channel ch = b.bind(8080).sync().channel();

            ch.closeFuture().sync();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
