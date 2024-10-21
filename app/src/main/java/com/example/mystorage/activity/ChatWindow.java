package com.example.mystorage.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mystorage.R;
import com.example.mystorage.messages.messagesAdapter;
import com.example.mystorage.messages.msgModelclass;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatWindow extends AppCompatActivity {

    String receiverImg, receiverUid, receiverName, senderUid;
    CircleImageView profileImg;
    TextView receiverNameView;
    CardView sendBtn;
    EditText textMsg;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase database;
    public static String senderImg;
    public static String receiverIImg;
    String senderRoom, receiverRoom;
    RecyclerView messageAdapterView;
    ArrayList<msgModelclass> messagesArrayList;
    com.example.mystorage.messages.messagesAdapter messagesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);

        // Initialize FirebaseAuth and FirebaseDatabase
        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        // Initialize UI components
        receiverName = getIntent().getStringExtra("nameeee");
        receiverImg = getIntent().getStringExtra("receiverImg");
        receiverUid = getIntent().getStringExtra("uid");
        senderUid = firebaseAuth.getUid(); // Get current user UID

        profileImg = findViewById(R.id.profileimgg);
        receiverNameView = findViewById(R.id.recivername);
        sendBtn = findViewById(R.id.sendbtnn);
        textMsg = findViewById(R.id.textmsg);
        messageAdapterView = findViewById(R.id.msgadpter);

        // Load receiver's profile image and name
        Picasso.get().load(receiverImg).into(profileImg);
        receiverNameView.setText(receiverName);

        // Initialize message list and adapter
        messagesArrayList = new ArrayList<>();
        messagesAdapter = new messagesAdapter(this, messagesArrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        messageAdapterView.setLayoutManager(linearLayoutManager);
        messageAdapterView.setAdapter(messagesAdapter);

        // Generate senderRoom and receiverRoom
        senderRoom = senderUid + receiverUid;
        receiverRoom = receiverUid + senderUid;

        // Fetch messages from Firebase
        DatabaseReference chatReference = database.getReference().child("chats").child(senderRoom).child("messages");
        chatReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messagesArrayList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    msgModelclass message = dataSnapshot.getValue(msgModelclass.class);
                    messagesArrayList.add(message);
                }
                messagesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error
            }
        });

        // Fetch sender's profile picture
        DatabaseReference reference = database.getReference().child("user").child(senderUid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                senderImg = snapshot.child("profilepic").getValue().toString();
                receiverIImg = receiverImg;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error
            }
        });

        // Send message button click listener
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = textMsg.getText().toString();
                if (messageText.isEmpty()) {
                    Toast.makeText(ChatWindow.this, "Enter a message first", Toast.LENGTH_SHORT).show();
                    return;
                }

                textMsg.setText("");
                Date date = new Date();
                msgModelclass message = new msgModelclass(messageText, senderUid, date.getTime());

                // Save message to Firebase
                database.getReference().child("chats").child(senderRoom).child("messages").push()
                        .setValue(message).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                database.getReference().child("chats").child(receiverRoom).child("messages").push()
                                        .setValue(message).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                // Message saved in both rooms
                                            }
                                        });
                            }
                        });
            }
        });
    }
}
