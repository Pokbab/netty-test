/*
 * @(#)EchoServerHandler.java $version 2015. 8. 19.
 *
 * Copyright 2015 NHN Ent. All rights Reserved. 
 * NHN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.cdg.study.netty.echo;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;


/**
 * 수신한 데이터를 클라이언트에게 응답하는 프로토콜
 * 
 * @author Kanghoon Choi
 */
public class EchoServerHandler extends ChannelInboundHandlerAdapter {
	@Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        // TODO: [실습1-2] 받은대로 응답하는 코드를 한 줄 작성합니다. release는 필요하지 않습니다.
		ctx.write(msg); // (1)
        ctx.flush(); // (2)
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
