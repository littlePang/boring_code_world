package com.little.pang.boring.code.netty.protocal.netty.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Map;

/**
 * Created by jaky on 4/11/16.
 */
public class NettyMessageHeader {

    private int crcCode; // 三部分,1.固定值,标识是netty协议栈消息(0xABEF),2.主版本号(1B),3.次版本号(1B)

    private int length; // 消息长度

    private byte type; // 消息类型 MessageType

    private byte priority; // 消息优先级

    private long sessionId; // 会话Id

    private Map<String, Object> attachment; // 请求头扩展信息

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public int getCrcCode() {
        return crcCode;
    }

    public void setCrcCode(int crcCode) {
        this.crcCode = crcCode;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public byte getPriority() {
        return priority;
    }

    public void setPriority(byte priority) {
        this.priority = priority;
    }

    public long getSessionId() {
        return sessionId;
    }

    public void setSessionId(long sessionId) {
        this.sessionId = sessionId;
    }

    public Map<String, Object> getAttachment() {
        return attachment;
    }

    public void setAttachment(Map<String, Object> attachment) {
        this.attachment = attachment;
    }
}
