package com.svrinfotech;


import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.svrinfotech.adapter.ChatPagerAdapter;
import com.svrinfotech.pojo.EndUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class GroupChat extends Fragment {
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private ArrayList<String> course;

    public GroupChat() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_group_chat, container, false);
        init(rootView);
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        String user=firebaseAuth.getCurrentUser().getEmail();
        List<EndUser> loggedUserList=EndUser.find(EndUser.class,"email=?",user);
        Log.e("Size In MA : ",loggedUserList.size()+" ");

        EndUser loggedUser=loggedUserList.get(0);
        String status=loggedUser.getStatus();
        course= new ArrayList<>();

        if(!status.equals("admin") && !status.equals("owner") ) {
            try {
                JSONObject jsonObject=new JSONObject(loggedUser.getHashSet());
                String json=jsonObject.getString("hashSet");
                JSONArray jsonArray=new JSONArray(json);
                for(int i=0;i<jsonArray.length();i++) {
                    course.add(jsonArray.getString(i));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        if(course!=null) {
            setupViewPager();
            tabLayout.setupWithViewPager(viewPager);
            tabLayout.setSelectedTabIndicatorHeight((int) (5 * getResources().getDisplayMetrics().density));
            tabLayout.setTabTextColors(Color.parseColor("#ffffff"),Color.parseColor("#000000"));
        }
        return rootView;
    }

    private void init(View rootView) {
        tabLayout=rootView.findViewById(R.id.tabView);
        viewPager=rootView.findViewById(R.id.chatViewPager);
    }

    private void setupViewPager() {
        ChatPagerAdapter chatPagerAdapter=new ChatPagerAdapter(getFragmentManager());
        for(String subject:course) {
            Fragment fragment=new Chat();
            Bundle bundle=new Bundle();
            bundle.putString("subject",subject);
            fragment.setArguments(bundle);
            chatPagerAdapter.addFragment(fragment,subject);
        }
        viewPager.setAdapter(chatPagerAdapter);
    }
}