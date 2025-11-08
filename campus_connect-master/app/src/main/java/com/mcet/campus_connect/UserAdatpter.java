package com.mcet.campus_connect;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class UserAdatpter extends RecyclerView.Adapter<UserAdatpter.UserViewHolder> {

    private final Context context;
    private final ArrayList<user> userList;

    public UserAdatpter(Context context, ArrayList<user> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_layot, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        user currentUser = userList.get(position);
        holder.textName.setText(currentUser.name);
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ChatActivity.class);
            intent.putExtra("name", currentUser.name);
            intent.putExtra("uid", currentUser.uid);
            context.startActivity(intent);
        });
    }

    static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView textName;

        UserViewHolder(@NonNull View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.txt_name);
        }
    }
}
