/*
 * @(#)TimeServer.java $version 2015. 8. 19.
 *
 * Copyright 2015 NHN Ent. All rights Reserved. 
 * NHN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.cdg.study.netty.time;

import com.cdg.study.netty.util.NettyStartupUtil;



/**
 * @author Kanghoon Choi
 */
public class TimeServer {
	public static void main(String[] args) throws Exception {
        NettyStartupUtil.runServer(8021, new TimeServerHandler());
    }
}
