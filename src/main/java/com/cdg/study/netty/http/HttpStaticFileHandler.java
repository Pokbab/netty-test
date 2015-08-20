package com.cdg.study.netty.http;

import static io.netty.handler.codec.http.HttpHeaders.Names.*;
import static io.netty.handler.codec.http.HttpResponseStatus.*;
import static io.netty.handler.codec.http.HttpVersion.*;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.DefaultFileRegion;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.LastHttpContent;

import java.io.IOException;
import java.io.RandomAccessFile;

public class HttpStaticFileHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
    private String path;
    private String filename;

    public HttpStaticFileHandler(String path, String filename) {
        super(false); // set auto-release to false
        this.path = path;
        this.filename = filename;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext context, FullHttpRequest request) throws Exception {
        // TODO: [실습2-1] sendStaticFile메소드를 써서 구현합니다. "/" 요청이 아닌 경우에는 어떻게 할까요?
        // 이 Handler는 SimpleChannelInboundHandler<I>를 확장했지만 "auto-release: false"임에 주의합니다.
        // 상황에 따라 "필요시"에는 retain()을 부르도록 합니다.
    	
    	if ("/".equals(request.getUri())) {
    		sendStaticFile(context, request);
		} else {
			context.fireChannelRead(request);
		}
    }

    private void sendStaticFile(ChannelHandlerContext context, FullHttpRequest request) throws IOException {
        try {
            RandomAccessFile raf = new RandomAccessFile(filename, "r");
            long fileLength = raf.length();

            HttpResponse res = new DefaultHttpResponse(HTTP_1_1, OK);
            HttpHeaders.setContentLength(res, fileLength);
            res.headers().set(CONTENT_TYPE, "text/html; charset=utf-8");
            if (HttpHeaders.isKeepAlive(request)) {
                res.headers().set(CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
            }
            context.write(res); // 응답 헤더 전송
            context.write(new DefaultFileRegion(raf.getChannel(), 0, fileLength));
            ChannelFuture f = context.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);
            if (!HttpHeaders.isKeepAlive(request)) {
                f.addListener(ChannelFutureListener.CLOSE);
            }
        } finally {
            request.release();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }
}
