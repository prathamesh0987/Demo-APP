package com.svrinfotech;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.orm.SugarRecord;
import com.svrinfotech.pojo.EndUser;
import com.svrinfotech.pojo.UserPojo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText username,password;
    private Button login,signup;
    private TextView forgotPassword;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FragmentManager fragmentManager;
    private Fragment fragment;
    //private final String sharedPreferenceName="SHARED_PREFERENCE";
    private Window window;
    private boolean flag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        int REQUEST_CODE=101;
        String[] permissions={
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.INTERNET
        };

        if(!hasPermission(this,permissions)) {
            ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE);
        }
        init();
        window=getWindow();
        login.setOnClickListener(this);
        signup.setOnClickListener(this);
        forgotPassword.setOnClickListener(this);

        authStateListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()!=null) {
                    //final SharedPreferences.Editor sharedPreferences=getApplicationContext().getSharedPreferences(sharedPreferenceName,MODE_PRIVATE).edit();
                    final String email=firebaseAuth.getCurrentUser().getEmail();
                    DatabaseReference userReference= FirebaseDatabase.getInstance().getReference().child("users");
                    userReference.keepSynced(true);
                    fragmentManager.beginTransaction().replace(R.id.frameLayout,fragment).commitAllowingStateLoss();
                    window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    userReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            /*Iterator<DataSnapshot> iterator=dataSnapshot.getChildren().iterator();*/
                            for(DataSnapshot ds:dataSnapshot.getChildren()) {
                                UserPojo user=ds.getValue(UserPojo.class);
                                if(user.getUsername().equals(email)) {
                                    fragmentManager.beginTransaction().remove(fragment).commitAllowingStateLoss();
                                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                    startActivity(new Intent(getBaseContext(),MainActivity.class));
                                    finish();
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        List<EndUser> userList=EndUser.listAll(EndUser.class);
        if(userList!=null && userList.size()>0) {
            firebaseAuth.addAuthStateListener(authStateListener);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        flag=false;
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void init() {
        username=findViewById(R.id.username);
        password=findViewById(R.id.password);
        login=findViewById(R.id.login);
        signup=findViewById(R.id.signup);
        firebaseAuth=FirebaseAuth.getInstance();
        forgotPassword=findViewById(R.id.forgotPassword);
        fragmentManager=getSupportFragmentManager();
        fragment=new Progressbar();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:startLogin();
                break;
            case R.id.signup:
                hideFronView();
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new Signup()).commit();
                break;
            case R.id.forgotPassword:
                String email_id=username.getText().toString().trim();
                if(TextUtils.isEmpty(email_id)) {
                    username.setError("Please Enter Email-ID Then Click Forgot Password");
                } else {
                    firebaseAuth.sendPasswordResetEmail(email_id)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()) {
                                        Toast.makeText(getApplicationContext(),"Password Reset Link Has Been Sent To You Registred Email-ID",Toast.LENGTH_LONG)
                                                .show();
                                    } else {
                                        Toast.makeText(getApplicationContext(),"USER NOT FOUND",Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
                break;
        }
    }

    private void startLogin() {

        final String emailValue=username.getText().toString();
        String passwordValue=password.getText().toString();
        fragmentManager.beginTransaction().replace(R.id.frameLayout,fragment).commitAllowingStateLoss();
        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        if(TextUtils.isEmpty(emailValue) || TextUtils.isEmpty(passwordValue)) {
            Toast.makeText(getApplicationContext(),"Please fill all details", Toast.LENGTH_LONG).show();
        } else {
            firebaseAuth.signInWithEmailAndPassword(emailValue,passwordValue).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()) {
                        Log.e("MSG","completing Login Task");
                        fragmentManager.beginTransaction().remove(fragment).commit();
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                        DatabaseReference userReference= FirebaseDatabase.getInstance().getReference().child("users");
                        userReference.keepSynced(true);


                        userReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for(DataSnapshot ds:dataSnapshot.getChildren()) {
                                    UserPojo user=ds.getValue(UserPojo.class);
                                    if(user.getUsername().equals(emailValue)) {
                                        int no= SugarRecord.deleteAll(EndUser.class);
                                        Log.e("Deleted Records : ",no+"");
                                        EndUser currentUser=new EndUser();
                                        String name=user.getName();
                                        currentUser.setName(name);
                                        String status=user.getStatus();
                                        currentUser.setStatus(status);
                                        currentUser.setEmail(emailValue);
                                        if(user.getCourse()!=null) {
                                            HashSet<String> courses = new HashSet<>(user.getCourse());
                                            Log.e("Course Size : ",courses.size()+"");
                                            currentUser.setHashSet(new ArrayList<>(courses));
                                        }
                                        long id=EndUser.save(currentUser);
                                        Log.e("Saved ",String.valueOf(id));
                                        startActivity(new Intent(getBaseContext(),MainActivity.class));
                                        finish();
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    fragmentManager.beginTransaction().remove(fragment).commit();
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    Toast.makeText(getApplicationContext(), "Login Issue : "+e.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private void hideFronView() {
        username.setVisibility(View.GONE);
        password.setVisibility(View.GONE);
        login.setVisibility(View.GONE);
        signup.setVisibility(View.GONE);
        forgotPassword.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        if(flag) {
            finish();
        } else {
            Toast.makeText(getApplicationContext(),"PRESS AGAIN TO EXIT",Toast.LENGTH_LONG).show();
            flag=true;
        }
    }

    boolean hasPermission(Context context, String... permissions) {
        if(context !=null && permissions!=null) {
            for(String permission:permissions) {
                if(ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
}