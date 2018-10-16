package com.xuelongiang.netty.timeserver;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author xuelongjiang
 */
public class TimerServerClient {


    public void connect(int port, String host){

        EventLoopGroup group = new NioEventLoopGroup();

        try {

            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group).channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY,true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new TimerServerClientHanlder());
                        }
                    });

            ChannelFuture f = bootstrap.connect(host, port).sync();

            f.channel().closeFuture().sync();

        }catch (Exception e){

        }finally {
            group.shutdownGracefully();
        }

    }


    public static void main(String[] args) {

        new TimerServerClient().connect(8080,"127.0.0.1");
    }


}
