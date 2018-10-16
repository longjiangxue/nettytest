package com.xuelongiang.netty.http.httphelloword;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;

/**
 * @author xuelongjiang
 */
public class HttpServerHelloWordHanlder extends SimpleChannelInboundHandler<HttpObject> {


    private String content = "<html>" +
            "<header></header>" +
            "<body>" +
            "<h1>hello word</h1>" +
            "</body>" +
            "</html>";
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String CONTENT_LENGTH = "Content-Length";
    private static final String CONNECTION = "Connection";
    private static final String KEEP_ALIVE = "keep-alive";


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

       if(msg instanceof HttpRequest){
           HttpRequest req = (HttpRequest)msg;

           FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                   HttpResponseStatus.OK, Unpooled.wrappedBuffer(content.getBytes()));

           response.headers().set(CONTENT_TYPE, "text/html");
           response.headers().setInt(CONTENT_LENGTH, response.content().readableBytes());

           boolean keppAlive = HttpHeaderUtil.isKeepAlive(req);
           System.out.println("http 请求");
           if(!keppAlive){

               ctx.write(response).addListener(ChannelFutureListener.CLOSE);

           }else {
                response.headers().set(CONNECTION, KEEP_ALIVE);
                ctx.write(response);
           }
       }
    }

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        System.out.println("messageReceived.....................");
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }



    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
            ctx.flush();
    }
}
