package com.mcet.campus_connect;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.auth.FirebaseAuth;
import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context context;
    private final ArrayList<Message> messageList;

    private final int ITEM_RECEIVE = 1;
    private final int ITEM_SENT = 2;

    public MessageAdapter(Context context, ArrayList<Message> messageList) {
        this.context = context;
        this.messageList = messageList;
    }

    @Override
    public int getItemViewType(int position) {
        Message currentMessage = messageList.get(position);
        if (FirebaseAuth.getInstance().getCurrentUser() != null
                && FirebaseAuth.getInstance().getCurrentUser().getUid()
                .equals(currentMessage.senderId)) {
            return ITEM_SENT;
        } else {
            return ITEM_RECEIVE;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == ITEM_RECEIVE) {
            View view = LayoutInflater.from(context).inflate(R.layout.receive, parent, false);
            return new ReceiveViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.sent, parent, false);
            return new SentViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Message currentMessage = messageList.get(position);
        if (holder.getClass() == SentViewHolder.class) {
            SentViewHolder viewHolder = (SentViewHolder) holder;
            viewHolder.sentMessage.setText(currentMessage.message);
        } else {
            ReceiveViewHolder viewHolder = (ReceiveViewHolder) holder;
            viewHolder.receiveMessage.setText(currentMessage.message);
        }
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    static class SentViewHolder extends RecyclerView.ViewHolder {
        TextView sentMessage;

        SentViewHolder(@NonNull View itemView) {
            super(itemView);
            sentMessage = itemView.findViewById(R.id.txt_sent_message);
        }
    }

    static class ReceiveViewHolder extends RecyclerView.ViewHolder {
        TextView receiveMessage;

        ReceiveViewHolder(@NonNull View itemView) {
            super(itemView);
            receiveMessage = itemView.findViewById(R.id.txt_receive_message);
        }
    }
}
