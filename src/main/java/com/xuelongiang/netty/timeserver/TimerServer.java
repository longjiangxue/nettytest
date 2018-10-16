package com.xuelongiang.netty.timeserver;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author xuelongjiang
 */
public class TimerServer {

   public void bind(int port){

       EventLoopGroup boss = new NioEventLoopGroup();
       EventLoopGroup word = new NioEventLoopGroup();

       try {

           ServerBootstrap b =  new ServerBootstrap();
           b.group(boss,word)
                   .channel(NioServerSocketChannel.class)
                   .option(ChannelOption.SO_BACKLOG,1024)
                   .childHandler(new ChildHalder());

           ChannelFuture future = b.bind(port).sync();

           future.channel().closeFuture().sync();

       }catch (Exception e){
           System.out.println("服务端异常:" + e);
       }finally {
            boss.shutdownGracefully();
            word.shutdownGracefully();
       }
   }


    public static void main(String[] args) {

       TimerServer timerServer = new TimerServer();
       timerServer.bind(8080);

    }



}


class ChildHalder extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        socketChannel.pipeline().addLast(new TimerServerHalder());
    }



}