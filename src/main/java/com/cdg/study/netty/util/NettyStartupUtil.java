/*
 * @(#)NettyStartupUtil.java $version 2015. 8. 19.
 *
 * Copyright 2015 NHN Ent. All rights Reserved. 
 * NHN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.cdg.study.netty.util;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.util.function.Consumer;


/**
 * @author Kanghoon Choi
 */
public class NettyStartupUtil {
	 public static void runServer(int port, ChannelHandler childHandler, Consumer<ServerBootstrap> block) throws Exception {
	        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
	        EventLoopGroup workerGroup = new NioEventLoopGroup();
	        try {
	            ServerBootstrap b = new ServerBootstrap();
	            b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class);
	            b.handler(new LoggingHandler(LogLevel.INFO));
	            b.childHandler(childHandler);
	            block.accept(b);
	            Channel ch = b.bind(port).sync().channel();
	            System.err.println("Ready for 0.0.0.0:" + port);
	            ch.closeFuture().sync();
	        } finally {
	            bossGroup.shutdownGracefully();
	            workerGroup.shutdownGracefully();
	        }
	    }

	    public static void runServer(int port, ChannelHandler childHandler) throws Exception {
	        runServer(port, childHandler, b -> {
	        });
	    }
}
