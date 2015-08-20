package com.cdg.study.netty.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

import com.cdg.study.netty.util.NettyStartupUtil;

public class HttpStaticServer {
    static String index = System.getProperty("user.dir") + "/res/h2/index.html";

    public static void main(String[] args) throws Exception {
        NettyStartupUtil.runServer(8020, new ChannelInitializer<SocketChannel>() {
            @Override
            public void initChannel(SocketChannel ch) {
                ChannelPipeline p = ch.pipeline();
                p.addLast(new HttpServerCodec());					// http 파싱
                p.addLast(new HttpObjectAggregator(65536));			// 본문 합쳐주는거 64k
                p.addLast(new HttpStaticFileHandler("/", index));	// 
                p.addLast(new HttpNotFoundHandler());
            }
        });
    }
}
