package com.cpy.myapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cpy.myapplication.R;
import com.cpy.myapplication.model.Message;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<Message> messageList;

    public MessageAdapter(List<Message> messageList) {
        this.messageList = messageList;
    }

    @Override
    public int getItemViewType(int position) {
        return messageList.get(position).getType();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == Message.TYPE_USER) {
            View view = inflater.inflate(R.layout.user_message_layout, parent, false);
            return new UserMessageViewHolder(view);
        } else {
            View view = inflater.inflate(R.layout.bot_message_layout, parent, false);
            return new BotMessageViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Message msg = messageList.get(position);
        if (holder instanceof UserMessageViewHolder) {
            ((UserMessageViewHolder) holder).bind(msg);
        } else if (holder instanceof BotMessageViewHolder) {
            ((BotMessageViewHolder) holder).bind(msg);
        }
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    static class UserMessageViewHolder extends RecyclerView.ViewHolder {
        TextView userMessage;
        public UserMessageViewHolder(View itemView) {
            super(itemView);
            userMessage = itemView.findViewById(R.id.userMessage);
        }
        void bind(Message msg) {
            userMessage.setText(msg.getContent());
        }
    }

    static class BotMessageViewHolder extends RecyclerView.ViewHolder {
        TextView botMessage;
        public BotMessageViewHolder(View itemView) {
            super(itemView);
            botMessage = itemView.findViewById(R.id.botMessage);
        }
        void bind(Message msg) {
            botMessage.setText(msg.getContent());
        }
    }
}
