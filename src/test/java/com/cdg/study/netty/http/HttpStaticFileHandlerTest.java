package com.cdg.study.netty.http;

import static org.junit.Assert.*;

import io.netty.channel.FileRegion;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpVersion;

import org.junit.Test;

public class HttpStaticFileHandlerTest {

	@Test
	public void index() throws Exception {
		String index = HttpStaticServer.index;
		EmbeddedChannel ch = new EmbeddedChannel(new HttpStaticFileHandler("/", index));
		
		ch.writeInbound(new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET, "/"));
		assertTrue(ch.readOutbound() instanceof HttpResponse);
		
		FileRegion content = (FileRegion)ch.readOutbound();
		assertTrue(content.count() > 0);
	}
}
