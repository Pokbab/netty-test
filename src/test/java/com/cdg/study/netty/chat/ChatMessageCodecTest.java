package com.cdg.study.netty.chat;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;



public class ChatMessageCodecTest {
	private ChatMessageCodec codec = new ChatMessageCodec();

    @Test
    public void encode() throws Exception {
        List<Object> out = new ArrayList<>();
        codec.encode(null, new ChatMessage("JOIN", "netty"), out);
        codec.encode(null, new ChatMessage("FROM", "netty", "안녕하세요, 반갑습니다."), out);
        assertEquals(out.size(), 2);
        assertEquals(out.get(0), "JOIN:netty\n");
        assertEquals(out.get(1), "FROM:netty 안녕하세요, 반갑습니다.\n");
    }

    @Test
    public void decode() throws Exception {
        List<Object> out = new ArrayList<>();
        codec.decode(null, "JOIN:netty", out);
        assertEquals(out.size(), 1);
        assertEquals(new ChatMessage("JOIN", "netty", null), out.get(0));
    }
}
