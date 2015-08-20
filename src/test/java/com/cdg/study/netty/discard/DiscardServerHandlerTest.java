package com.cdg.study.netty.discard;

import static org.junit.Assert.*;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;

import org.junit.Test;

public class DiscardServerHandlerTest {

	@Test
	public void discard() {
		String msg = "discard test\n";
		EmbeddedChannel channel = new EmbeddedChannel(new DiscardServerHandler());
		ByteBuf in = Unpooled.wrappedBuffer(msg.getBytes());
		channel.writeInbound(in);
		
		ByteBuf buf = (ByteBuf)channel.readOutbound();
		
		assertNull(buf);
	}
}
