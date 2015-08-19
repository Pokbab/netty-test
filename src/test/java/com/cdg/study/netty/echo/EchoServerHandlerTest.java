package com.cdg.study.netty.echo;

import static org.junit.Assert.*;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;

import org.junit.Test;

public class EchoServerHandlerTest {

	@Test
	public void echo() {
		String m = "echo test\n";
		EmbeddedChannel ch = new EmbeddedChannel(new EchoServerHandler());
		ByteBuf in = Unpooled.copiedBuffer(m, CharsetUtil.UTF_8);
		ch.writeInbound(in);
		ByteBuf r = (ByteBuf)ch.readOutbound();
		ReferenceCountUtil.releaseLater(r);
		assertNotNull("응답이 없습니다", r);
		assertEquals("참조수는 1이어야 합니다", r.refCnt(), 1);
		assertEquals("수신한 텍스트가 결과로 와야합니다", r.toString(CharsetUtil.UTF_8), m);
	}
}
