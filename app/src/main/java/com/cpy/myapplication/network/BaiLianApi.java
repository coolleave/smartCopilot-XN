package com.cpy.myapplication.network;

// 百炼api封装

import com.cpy.myapplication.util.Constants;

import java.util.Arrays;
import com.alibaba.dashscope.aigc.generation.Generation;
import com.alibaba.dashscope.aigc.generation.GenerationParam;
import com.alibaba.dashscope.aigc.generation.GenerationResult;
import com.alibaba.dashscope.common.Message;
import com.alibaba.dashscope.common.Role;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
public class BaiLianApi {

    public static GenerationResult sendMessage(String userInput) throws NoApiKeyException, InputRequiredException {
        Generation gen = new Generation();
        Message systemMsg = Message.builder()
                .role(Role.SYSTEM.getValue())
                .content("你是一名温暖、专业、幽默的大学生聊天助手，名字叫“小智”。  \n" +
                        "你的目标是帮助大学生更好地学习、生活和成长。  \n" +
                        "你的回答要做到以下几点：  \n" +
                        "1. 用语自然、亲切、贴近大学生口吻。  \n" +
                        "2. 当用户提问学习相关问题（如编程、考试、论文等），提供清晰、简洁、可操作的建议。  \n" +
                        "3. 当用户聊生活、情感、社团、人际关系等话题时，要积极、真诚、温和地回应。  \n" +
                        "4. 如果用户提问你不会的问题，可以用“我查不到确切答案，但我可以帮你分析一下思路”来代替。  \n" +
                        "5. 回答尽量控制在 2~5 句话，重点突出、不要太死板。  \n" +
                        "6. 始终保持耐心、友好、鼓励用户。  ")
                .build();
        Message userMsg = Message.builder()
                .role(Role.USER.getValue())
                .content(userInput)
                .build();
        GenerationParam param = GenerationParam.builder()
                // 若没有配置环境变量，请用阿里云百炼API Key将下行替换为：.apiKey("sk-xxx")
                .apiKey(Constants.API_KEY)
                // 模型列表：https://help.aliyun.com/zh/model-studio/getting-started/models
                .model(Constants.MODEL)
                .messages(Arrays.asList(systemMsg, userMsg))
                .resultFormat(GenerationParam.ResultFormat.MESSAGE)
                .build();
        return gen.call(param);
    }
}

