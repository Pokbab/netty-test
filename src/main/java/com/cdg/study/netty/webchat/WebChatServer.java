package com.cdg.study.netty.webchat;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

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
                p.addLast(new WebSocketHandshakeHandler("/chat", new WebChatHandler()));
                p.addLast(new HttpStaticFileHandler("/", index));
                // TODO: [실습4-1] 실습2-2와 마찬가지로 404 응답을 처리하게 합니다.

            }
        });
    }
}
