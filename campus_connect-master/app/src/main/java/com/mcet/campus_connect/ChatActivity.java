package com.mcet.campus_connect;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {

    private RecyclerView chatRecyclerView;
    private EditText messageBox;
    private ImageView sendButton;
    private MessageAdapter messageAdapter;
    private ArrayList<Message> messageList;
    private DatabaseReference mDbRef;

    private String receiverRoom = null;
    private String senderRoom = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        String name = getIntent().getStringExtra("name");
        String receiverUid = getIntent().getStringExtra("uid");
        String senderUid = FirebaseAuth.getInstance().getCurrentUser() != null
                ? FirebaseAuth.getInstance().getCurrentUser().getUid()
                : null;

        mDbRef = FirebaseDatabase.getInstance().getReference();

        senderRoom = receiverUid + senderUid;
        receiverRoom = senderUid + receiverUid;

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(name);
        }

        chatRecyclerView = findViewById(R.id.chatRecyclerView);
        messageBox = findViewById(R.id.messageBox);
        sendButton = findViewById(R.id.sentButton);

        messageList = new ArrayList<>();
        messageAdapter = new MessageAdapter(this, messageList);

        chatRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        chatRecyclerView.setAdapter(messageAdapter);

        // listen for messages
        mDbRef.child("chats").child(senderRoom).child("messages")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        messageList.clear();
                        for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                            Message message = postSnapshot.getValue(Message.class);
                            if (message != null) {
                                messageList.add(message);
                            }
                        }
                        messageAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // handle error if needed
                    }
                });

        sendButton.setOnClickListener(v -> {
            String message = messageBox.getText().toString();
            Message messageObject = new Message(message, senderUid);
            mDbRef.child("chats").child(senderRoom).child("messages").push()
                    .setValue(messageObject)
                    .addOnSuccessListener(aVoid ->
                            mDbRef.child("chats").child(receiverRoom).child("messages").push()
                                    .setValue(messageObject)
                    );
            messageBox.setText("");
        });
    }
}
