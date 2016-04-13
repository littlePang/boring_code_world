package com.little.pang.boring.code.netty.protocal.netty.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Created by jaky on 4/11/16.
 */
public class NettyMessage {

    private NettyMessageHeader header;

    private Object body;

    public NettyMessageHeader getHeader() {
        return header;
    }

    public void setHeader(NettyMessageHeader header) {
        this.header = header;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
