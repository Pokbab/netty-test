package com.cdg.study.netty.webchat;

import static org.junit.Assert.*;

import io.netty.channel.ChannelPipeline;
import io.netty.channel.embedded.EmbeddedChannel;

import java.util.stream.StreamSupport;

import org.junit.Test;

import com.cdg.study.netty.chat.ChatServerHandler;

public class WebChatHandlerTest {

	@Test
	public void pipilineUpdate() throws Exception {
		EmbeddedChannel ch = new EmbeddedChannel(new WebChatHandler());
		ChannelPipeline p = ch.pipeline();
		assertEquals("WebChatHandler는 유지돼야 합니다", p.first().getClass(), WebChatHandler.class);
		assertNotNull("WebSocketChatCodec이 추가돼야 해요", p.get(WebSocketChatCodec.class));
		assertNotNull("ChatServerHandler도 추가돼야 해요", p.get(ChatServerHandler.class));
		Object[] klasses = StreamSupport.stream(p.spliterator(), false).limit(3).map(e -> e.getValue().getClass()).toArray();
		Object[] expected = new Object[]{WebChatHandler.class, WebSocketChatCodec.class, ChatServerHandler.class};
		assertArrayEquals("파이프라인 순서가 맞나요?", klasses, expected);
		System.out.println(ch.readOutbound());
	}
}
