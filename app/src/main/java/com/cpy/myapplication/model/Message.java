package com.cpy.myapplication.model;
// 消息实体类
public class Message {
    public static final int TYPE_USER = 0;
    public static final int TYPE_BOT = 1;

    private String content;
    private int type;

    public Message(String content, int type) {
        this.content = content;
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public int getType() {
        return type;
    }
}
