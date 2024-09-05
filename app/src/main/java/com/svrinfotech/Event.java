package com.svrinfotech;


import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.svrinfotech.holder.EventHolder;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class Event extends Fragment {

    private RecyclerView displayEvent;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReferenceLikes;
    private DatabaseReference databaseReferenceEvent;
    private String userStatus;
    private final String sharedPreferenceName="SHARED_PREFERENCE";
    private SharedPreferences sharedPreferences;
    private boolean likeValue=false;

    public Event() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_event, container, false);
        init(rootView);
        userStatus=sharedPreferences.getString("account_type","NULL");
        displayEvent.setHasFixedSize(true);
        displayEvent.setLayoutManager(new LinearLayoutManager(getContext()));
        final FirebaseRecyclerOptions<com.svrinfotech.pojo.Event> firebaseRecyclerOptions=new FirebaseRecyclerOptions
                .Builder<com.svrinfotech.pojo.Event>()
                .setQuery(databaseReferenceEvent, com.svrinfotech.pojo.Event.class)
                .build();
        FirebaseRecyclerAdapter firebaseRecyclerAdapter=
                new FirebaseRecyclerAdapter<com.svrinfotech.pojo.Event, EventHolder>(firebaseRecyclerOptions) {
                    @Override
                    protected void onBindViewHolder(@NonNull final EventHolder holder, int position, @NonNull com.svrinfotech.pojo.Event model) {
                        final String post_key=getRef(position).getKey();
                        holder.setTitle(model.getTitle());
                        holder.setDescription(model.getDescription());
                        holder.setEventImage(model.getImage(),getActivity().getApplicationContext());

                        holder.like.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                likeValue=true;
                                databaseReferenceLikes.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        if(likeValue) {
                                            if(dataSnapshot.child(post_key).hasChild(firebaseAuth.getCurrentUser().getUid())) {
                                                databaseReferenceLikes.child(post_key).child(firebaseAuth.getCurrentUser().getUid()).removeValue();
                                                likeValue=false;
                                            } else {
                                                databaseReferenceLikes.child(post_key).child(firebaseAuth.getCurrentUser().getUid()).setValue("Liked");
                                                likeValue=false;
                                            }

                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                            }
                        });
                        holder.setLike(post_key);
                    }

                    @Override
                    public EventHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                        View itemView = LayoutInflater.from(parent.getContext()).
                                inflate(R.layout.event_row, parent, false);
                        return new EventHolder(itemView);
                    }
                };

        displayEvent.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();
        setHasOptionsMenu(true);
        // Inflate the layout for this fragment
        return rootView;
    }


    private void init(View view) {
        displayEvent=view.findViewById(R.id.displayEvent);
        firebaseAuth=FirebaseAuth.getInstance();
        databaseReferenceLikes= FirebaseDatabase.getInstance().getReference().child("likes");
        databaseReferenceEvent=FirebaseDatabase.getInstance().getReference().child("event");
        databaseReferenceLikes.keepSynced(true);
        databaseReferenceEvent.keepSynced(true);
        sharedPreferences=getActivity().getApplicationContext().getSharedPreferences(sharedPreferenceName,MODE_PRIVATE);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.event_menu,menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addPost:
                displayEvent.setVisibility(View.GONE);
                getFragmentManager().beginTransaction().replace(R.id.eventLayout,new EventPost()).commit();
                setMenuVisibility(false);
                return true;
            default:return super.onOptionsItemSelected(item);
        }
    }
}