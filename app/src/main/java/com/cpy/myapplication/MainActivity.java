package com.cpy.myapplication;

import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.dashscope.aigc.generation.GenerationResult;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.cpy.myapplication.adapter.MessageAdapter;
import com.cpy.myapplication.model.Message;
import com.cpy.myapplication.network.BaiLianApi;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    public List<Message> messageList = new ArrayList<>();
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

        // 监听回车键发送消息
        messageInput.setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_ENTER
                    && event.getAction() == KeyEvent.ACTION_DOWN) {
                sendButton.performClick(); // 模拟点击“发送”按钮
                return true; // 防止换行
            }
            return false;
        });

        sendButton.setOnClickListener(v -> {
            String text = messageInput.getText().toString().trim();
            if (text.isEmpty()) return;

            // 添加用户消息
            messageList.add(new Message(text, Message.TYPE_USER));
            adapter.notifyItemInserted(messageList.size() - 1);
            recyclerView.scrollToPosition(messageList.size() - 1);
            messageInput.setText("");

            // 异步调用百炼API
            Executors.newSingleThreadExecutor().execute(() -> {
                try {
                    GenerationResult result = BaiLianApi.sendMessage(text);
                    String reply = result.getOutput().getChoices().get(0).getMessage().getContent();

                    mainHandler.post(() -> {
                        messageList.add(new Message(reply, Message.TYPE_BOT));
                        adapter.notifyItemInserted(messageList.size() - 1);
                        recyclerView.scrollToPosition(messageList.size() - 1);
                    });
                } catch (NoApiKeyException | InputRequiredException e) {
                    e.printStackTrace();
                }
            });
        });
    }
}
