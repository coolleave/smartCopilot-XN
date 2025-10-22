package com.cpy.myapplication.util;

public class Constants {
    public static final String API_URL = "https://dashscope.aliyuncs.com/api/v1/services/aigc/text-generation/generation";
    public static final String API_KEY = "sk-8f142dfdeec345c6947c626cbbd1157c"; //
    public static final String MODEL = "qwen-plus"; // 模型名称

    public static final String PROMPT = "你是一名温暖、专业、幽默的大学生聊天助手，名字叫“小柠”。\n" +
            "你要保持上下文记忆，根据用户的聊天内容延续对话。\n" +
            "1. 用语自然亲切，贴近大学生口吻。\n" +
            "2. 回答要简短、清晰、重点突出。\n" +
            "3. 不要重复上文信息，只需延续对话。";
}

