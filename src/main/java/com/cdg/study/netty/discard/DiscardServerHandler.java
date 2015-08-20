/*
 * @(#)DiscardServerHandler.java $version 2015. 8. 19.
 *
 * Copyright 2015 NHN Ent. All rights Reserved. 
 * NHN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.cdg.study.netty.discard;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Netty 기본서버
 * 
 * @author Kanghoon Choi
 */
public class DiscardServerHandler extends ChannelInboundHandlerAdapter { // 채널로 들어왔을때 이벤트를 실행

	@Override
	public void channelRead(ChannelHandlerContext context, Object msg) { // Netty의 이벤트루프에서 실행시켜줌
		ByteBuf buf = (ByteBuf) msg; // 네트워크에서 최초로 받으면 바이트버퍼로 받음
	    try {
	        while (buf.isReadable()) { // (1)
	            System.out.print((char) buf.readByte());
	            System.out.flush();
	        }
	    } finally {
	    	buf.release(); // 무조건 마지막에 호출한 메소드에서 해제해야함
	    }
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // 예외처리
		cause.printStackTrace();
		ctx.close();
	}
}
