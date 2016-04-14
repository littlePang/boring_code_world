package com.little.pang.boring.code.netty.protocal.netty.common;

import com.google.common.collect.Sets;

import java.util.Set;

/**
 * Created by jaky on 4/11/16.
 */
public class NettyProtocolConstant {

    public static int NETTY_MESSAGE_MAX_LENGTH = 1024 * 1024;
    public static int NETTY_MESSAGE_LENGTH_FIELD_OFFSET = 4;
    public static int NETTY_MESSAGE_LENGTH_FIELD_LENGTH = 4;

    public static final int NETTY_PROTOCOL_IDENTIFICATION = 0xABEF;

    public static final Set<String> ALLOW_LOGIN_IPS = Sets.newHashSet("127.0.0.1", "10.86.41.79");

}
