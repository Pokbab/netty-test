package com.cdg.study.netty.discard;

import static org.junit.Assert.*;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;

import org.junit.Test;

public class DiscardServerHandlerTest {

	@Test
	public void discard() {
		String m = "discard test\n";
		EmbeddedChannel ch = new EmbeddedChannel(new DiscardServerHandler());
		ByteBuf in = Unpooled.wrappedBuffer(m.getBytes());
		ch.writeInbound(in);
		ByteBuf r = (ByteBuf)ch.readOutbound();
		assertNull(r);
	}
}
