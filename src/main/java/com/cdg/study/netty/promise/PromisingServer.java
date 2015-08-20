package com.cdg.study.netty.promise;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import com.cdg.study.netty.util.NettyStartupUtil;

public final class PromisingServer {
    public static void main(String[] args) throws Exception {
        NettyStartupUtil.runServer(8031,  new ChannelInitializer<SocketChannel>() {
            @Override
            public void initChannel(SocketChannel ch) {
                ch.pipeline()
                    .addLast(new LineBasedFrameDecoder(1024))
                    .addLast(new StringDecoder(), new StringEncoder())
                    .addLast(new PromisingServerHandler());
            }
        });
    }
}
