package com.cpy.myapplication;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.EditText;

import com.alibaba.dashscope.aigc.generation.GenerationResult;
import com.cpy.myapplication.model.Message;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.cpy.myapplication.adapter.MessageAdapter;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import com.cpy.myapplication.network.BaiLianApi;

public class MainActivity extends AppCompatActivity {

    private List<Message> messageList = new ArrayList<>();
    private MessageAdapter adapter;
    private EditText messageInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.messageContainer);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MessageAdapter(messageList);
        recyclerView.setAdapter(adapter);

        messageInput = findViewById(R.id.messageInput);
        Button sendButton = findViewById(R.id.sendButton);

        Handler mainHandler = new Handler();

        sendButton.setOnClickListener(v -> {
            String text = messageInput.getText().toString().trim();
            if (text.isEmpty()) return;

            messageList.add(new Message(text, Message.TYPE_USER));
            adapter.notifyItemInserted(messageList.size() - 1);
            recyclerView.scrollToPosition(messageList.size() - 1);
            messageInput.setText("");
            System.out.println("开始调用api"+text);
            // 异步请求阿里百炼
            Executors.newSingleThreadExecutor().execute(() -> {
                try {
                    GenerationResult result = BaiLianApi.sendMessage(text);
                    String reply = BaiLianApi.sendMessage(text).getOutput().getChoices().get(0).getMessage().getContent();
                    System.out.println("调用结果"+reply);
                } catch (NoApiKeyException | InputRequiredException e) {
                    System.out.println("调用失败");
                    throw new RuntimeException(e);
                }
            }
            );});

    }

}
