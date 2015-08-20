package com.cdg.study.netty.webchat;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

import com.cdg.study.netty.http.HttpNotFoundHandler;
import com.cdg.study.netty.http.HttpStaticFileHandler;
import com.cdg.study.netty.util.NettyStartupUtil;

public class WebChatServer {
    static String index = System.getProperty("user.dir") + "/res/h4/index.html";

    public static void main(String[] args) throws Exception {
        NettyStartupUtil.runServer(8040, new ChannelInitializer<SocketChannel>() {
            @Override
            public void initChannel(SocketChannel ch) {
                ChannelPipeline p = ch.pipeline();
                p.addLast(new HttpServerCodec());
                p.addLast(new HttpObjectAggregator(65536));
                p.addLast(new WebSocketHandshakeHandler("/chat", new WebChatHandler()));	// http요청이 연결된 이후는 tcp커넥션으로 업그레이드!
                p.addLast(new HttpStaticFileHandler("/", index));
                p.addLast(new HttpNotFoundHandler());
            }
        });
    }
}
