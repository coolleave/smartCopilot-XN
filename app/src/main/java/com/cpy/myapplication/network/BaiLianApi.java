package com.cpy.myapplication.network;

import com.alibaba.dashscope.aigc.generation.Generation;
import com.alibaba.dashscope.aigc.generation.GenerationParam;
import com.alibaba.dashscope.aigc.generation.GenerationResult;
import com.alibaba.dashscope.common.Message;
import com.alibaba.dashscope.common.Role;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.cpy.myapplication.util.Constants;

import java.util.ArrayList;
import java.util.List;

public class BaiLianApi {

    // 保存整个对话的消息历史
    private static final List<Message> history = new ArrayList<>();

    // 初始化 system 角色（只添加一次）
    static {
        Message systemMsg = Message.builder()
                .role(Role.SYSTEM.getValue())
                .content(Constants.PROMPT)
                .build();
        history.add(systemMsg);
    }

    // 发送消息方法（带上下文）
    public static GenerationResult sendMessage(String userInput)
            throws NoApiKeyException, InputRequiredException {

        // 添加用户消息到历史
        history.add(Message.builder()
                .role(Role.USER.getValue())
                .content(userInput)
                .build());

        // 构建请求参数
        Generation gen = new Generation();
        GenerationParam param = GenerationParam.builder()
                .apiKey(Constants.API_KEY)
                .model(Constants.MODEL)
                .messages(history) // 把整个历史发给API
                .resultFormat(GenerationParam.ResultFormat.MESSAGE)
                .build();

        // 调用百炼
        GenerationResult result = gen.call(param);

        // 取出回复
        Message botMsg = result.getOutput().getChoices().get(0).getMessage();

        // 保存阿里回复到历史
        history.add(botMsg);

        return result;
    }

    // 清空历史，todo
    public static void clearHistory() {
        if (history.size() > 1) {
            Message systemMsg = history.get(0);
            history.clear();
            history.add(systemMsg);
        }
    }
}
