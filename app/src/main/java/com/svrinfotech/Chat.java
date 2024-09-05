package com.svrinfotech;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.svrinfotech.holder.MessageHolder;
import com.svrinfotech.pojo.ChatMessage;

import java.util.ArrayDeque;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class Chat extends Fragment {
    private RecyclerView chatRecyclerView;
    private EditText messageBody;
    private FloatingActionButton sendMessage;
    NotificationManager notificationManager;
    NotificationCompat.Builder builder;
    Context context;
    Intent intent;
    PendingIntent pendingIntent;
    private DatabaseReference chatReference;
    private ArrayDeque<ChatMessage> arrayDeque;

    public Chat() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_chat, container, false);
        String subject=getArguments().getString("subject");
        init(rootView);
        arrayDeque=new ArrayDeque<>();
        context=getContext();
        intent=new Intent(context,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        pendingIntent=PendingIntent.getActivity(context,0,intent,0);
        String CHANNEL_ID="CHAT_NOTIFICATIONS";

        builder=new NotificationCompat.Builder(context,CHANNEL_ID);
        builder.setContentTitle("SVR")
                .setContentText("Welcome To SVR Chat")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        notificationManager=(NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    "SVR NOTIFICATIONS",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        //notificationManager.notify(0,builder.build());

        chatReference= FirebaseDatabase.getInstance().getReference().child("chats").child(subject);
        chatReference.keepSynced(true);
        FirebaseRecyclerOptions<ChatMessage> chatMessageFirebaseRecyclerOptions=new FirebaseRecyclerOptions
                .Builder<ChatMessage>()
                .setQuery(chatReference,ChatMessage.class)
                .build();

        final FirebaseRecyclerAdapter<ChatMessage,MessageHolder> adapter=
                new FirebaseRecyclerAdapter<ChatMessage, MessageHolder>(chatMessageFirebaseRecyclerOptions) {
                    @Override
                    protected void onBindViewHolder(@NonNull MessageHolder holder, int position, @NonNull ChatMessage model) {
                        holder.setMessage(model.getMessage());
                        holder.setTime(DateFormat.format("dd-MM-yy\nHH:mm",model.getDateTime()));
                        holder.setUser(model.getUser());
                        arrayDeque.add(model);
                    }

                    @NonNull
                    @Override
                    public MessageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view=LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.message_row,parent,false);
                        return new MessageHolder(view);
                    }
                };

        adapter.startListening();
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getActivity().getApplicationContext());
        chatRecyclerView.setLayoutManager(layoutManager);
        chatRecyclerView.setAdapter(adapter);

        /*if(arrayDeque!=null) {
            Toast.makeText(context,
                    "MSG : "+arrayDeque.getLast().getMessage(),
                    Toast.LENGTH_LONG).show();
        }*/

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message=messageBody.getText().toString();
                String email= FirebaseAuth.getInstance().getCurrentUser().getEmail();
                ChatMessage chatMessage=new ChatMessage(message,email,new Date().getTime());
                chatReference.push().setValue(chatMessage);
                messageBody.setText("");
            }
        });
    }

    private void init(View rootView) {
        chatRecyclerView=rootView.findViewById(R.id.chatView);
        sendMessage=rootView.findViewById(R.id.sendMessage);
        messageBody=rootView.findViewById(R.id.typeMessage);
    }
}