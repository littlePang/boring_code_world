package com.little.pang.boring.code.netty.protocal.netty.enums;

/**
 * Created by jaky on 4/11/16.
 */
public enum MessageType {
    BUSINESS_REQUEST(0, "业务请求消息"),
    BUSINESS_RESPONSE(1, "业务应答消息"),
    BUSINESS_ONE_WAY(2, "业务one_way消息,既是请求又是应答"),
    HANDSHAKE_REQUEST(3, "握手请求消息"),
    HANDSHAKE_RESPONSE(4, "握手应答消息"),
    HEART_REQUEST(5, "心跳请求消息"),
    HEART_RESPONSE(6, "心跳应答消息"),
    UNKNOWN(-1, "未知");

    private byte code;

    private String desc;

    MessageType(int code, String desc) {
        this.code = (byte) code;
        this.desc = desc;
    }

    public static MessageType codeOf(int code) {
        for (MessageType messageType : values()) {
            if (messageType.getCode() == code) {
                return messageType;
            }
        }
        return UNKNOWN;
    }

    public byte getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
