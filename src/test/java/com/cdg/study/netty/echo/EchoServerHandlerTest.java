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
		String msg = "echo test\n";
		EmbeddedChannel channel = new EmbeddedChannel(new EchoServerHandler());
		
		ByteBuf in = Unpooled.copiedBuffer(msg, CharsetUtil.UTF_8);
		channel.writeInbound(in);
		
		ByteBuf out = (ByteBuf)channel.readOutbound();
		ReferenceCountUtil.releaseLater(out);

		assertNotNull("응답이 없습니다", out);
		assertEquals("참조수는 1이어야 합니다", out.refCnt(), 1);
		assertEquals("수신한 텍스트가 결과로 와야합니다", out.toString(CharsetUtil.UTF_8), msg);
	}
}
