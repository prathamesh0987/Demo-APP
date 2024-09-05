package com.svrinfotech;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.makeramen.roundedimageview.RoundedImageView;
import com.orm.SugarRecord;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.svrinfotech.pojo.EndUser;
import com.svrinfotech.pojo.ProfilePic;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener {
    private final int GALLERY_REQUEST=1;
    private Uri imageUri=null,finalImageUri=null;
    Button cad,sw,catia,ansys,java,python,androidBtn;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference usersReference, profilePic;
    private String userStatus,user,username,course;
    //String sharedPreferenceName="SHARED_PREFERENCE";
    private SharedPreferences sharedPreferences;
    private NavigationView navigationView;
    private Fragment fragment;
    private View headerLayout;
    private RoundedImageView dp;
    EndUser loggedUser;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);


        user=firebaseAuth.getCurrentUser().getEmail();

        List<EndUser> loggedUserList=EndUser.find(EndUser.class,"email=?",user);
        Log.e("Size In MA : ",loggedUserList.size()+" ");

        loggedUser=loggedUserList.get(0);

        userStatus=loggedUser.getStatus();

        TextView email=headerLayout.findViewById(R.id.email);
        email.setText(user);

        dp.setImageResource(R.mipmap.ic_account_circle_black_48dp);
        username=loggedUser.getName();
        if(username!=null) {
            TextView name=headerLayout.findViewById(R.id.profile_name);
            name.setText(username);
            profilePic=FirebaseDatabase.getInstance().getReference().child("profileDP").child(username);
            profilePic.keepSynced(true);

            profilePic.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Iterator<DataSnapshot> iterator=dataSnapshot.getChildren().iterator();
                    while (iterator.hasNext()) {
                        DataSnapshot profileData=iterator.next();
                        final ProfilePic profile=profileData.getValue(ProfilePic.class);
                        if(profile.getUsername().equals(user)) {
                            Picasso.with(getBaseContext()).load(profile.getPic()).networkPolicy(NetworkPolicy.OFFLINE).into(dp, new Callback() {
                                @Override
                                public void onSuccess() {

                                }

                                @Override
                                public void onError() {
                                    Picasso.with(getBaseContext()).load(profile.getPic()).into(dp);
                                }
                            });
                            break;
                        } else {
                            Toast.makeText(getApplicationContext(),
                                    "Something Went Wrong...",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        if(userStatus!=null) {
            switch (userStatus) {
                case "student":
                case "employee":
                    if(!username.startsWith("Jaywant") && !username.startsWith("Durgesh") && !username.startsWith("sandeep") ) {
                        hideMenuItem(R.id.newAdmission);
                    }
                    hideMenuItem(R.id.totalAdmission);
                    hideMenuItem(R.id.totalCollection);
                    break;
                case "owner":
                    hideMenuItem(R.id.newAdmission);
                    hideMenuItem(R.id.groupChat);
                    break;
                case "admin":
                    hideMenuItem(R.id.groupChat);
                    break;
                case "none":
                    hideMenuItem(R.id.newAdmission);
                    hideMenuItem(R.id.totalAdmission);
                    hideMenuItem(R.id.totalCollection);
                    hideMenuItem(R.id.groupChat);
                    hideMenuItem(R.id.syllabus);
                    break;
            }
        }


        dp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,GALLERY_REQUEST);
            }
        });
    }

    @Override
    public void invalidateOptionsMenu() {
        super.invalidateOptionsMenu();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.admin_task,menu);
        switch (userStatus) {
            case "none":
            case "student":
                hideMenuContent(R.id.setStatus,menu);
                return true;
            case "employee":
                hideMenuContent(R.id.setStatus,menu);
                return true;
            default:return super.onCreateOptionsMenu(menu);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.setStatus:
                fragment=new AdminTask();
                setTitle("Administrator");
                openFragment();
                return true;
            default:return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK) {
            imageUri = data.getData();
            CropImage.activity(imageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1, 1)
                    .start(this);

        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                finalImageUri = result.getUri();
                dp.setImageURI(finalImageUri);
                final StorageReference storageReference= FirebaseStorage.getInstance().getReference().child("profile").child(username);
                storageReference.putFile(finalImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        storageReference.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                ProfilePic profile=new ProfilePic();
                                Uri downloadUri=task.getResult();
                                profile.setPic(downloadUri.toString());
                                profile.setUsername(user);
                                profilePic.child("pic").setValue(profile).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(getApplicationContext(),
                                                "Profile Pic Changed",
                                                Toast.LENGTH_LONG)
                                                .show();
                                    }
                                });

                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(
                                getApplicationContext(),
                                "Something went wrong",
                                Toast.LENGTH_LONG
                        ).show();
                    }
                });
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Log.e("Error", error.getMessage());
            }
        }
    }


        @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }

    private void init() {
        navigationView=findViewById(R.id.nav_view);
        cad=findViewById(R.id.cadButton);
        sw=findViewById(R.id.swButton);
        catia=findViewById(R.id.catiaButton);
        ansys=findViewById(R.id.ansysButton);
        java=findViewById(R.id.javaButton);
        python=findViewById(R.id.pythonButton);
        androidBtn=findViewById(R.id.androidButton);

        cad.setOnClickListener(this);
        sw.setOnClickListener(this);
        catia.setOnClickListener(this);
        ansys.setOnClickListener(this);
        java.setOnClickListener(this);
        python.setOnClickListener(this);
        androidBtn.setOnClickListener(this);

        firebaseAuth=FirebaseAuth.getInstance();
        usersReference= FirebaseDatabase.getInstance().getReference().child("users");
        usersReference.keepSynced(true);

        headerLayout=navigationView.getHeaderView(0);
        dp=headerLayout.findViewById(R.id.profilePic);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        switch (item.getItemId()) {
            case R.id.home:startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
                break;
            case R.id.newAdmission:fragment=new Admissions();
                setTitle("NEW ADMISSION");
                setBackNavigation();
                openFragment();
                break;
            case R.id.totalAdmission:fragment=new TotalAdmissions();
                setTitle("TOTAL ADMISSION");
                setBackNavigation();
                openFragment();
                break;
            case R.id.totalCollection:fragment=new TotalCollection();
                setTitle("TOTAL COLLECTION");
                setBackNavigation();
                openFragment();
                break;
            case R.id.syllabus:
                fragment=new Syllabus();
                setTitle("SYLLABUS");
                setBackNavigation();
                openFragment();

                break;
            case R.id.event:fragment=new Event();
                setTitle("EVENTS");
                openFragment();
                setBackNavigation();
                break;
            case R.id.groupChat:
                setTitle("GROUP CHAT");
                fragment=new GroupChat();
                openFragment();
                setBackNavigation();
                break;
            case R.id.formula:
                setTitle(R.string.formula);
                fragment=new Formula();
                openFragment();
                setBackNavigation();
                break;
            case R.id.logout:
                int no= SugarRecord.deleteAll(EndUser.class);
                Log.e("Deleted Records : ",no+"");
                firebaseAuth.signOut();
                startActivity(new Intent(getBaseContext(),LoginActivity.class));
                finish();
                break;
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
        Fragment fragment=new ShowCourseContent();
        Bundle bundle=null;
        switch (v.getId()) {
            case R.id.cadButton:
                bundle=new Bundle();
                bundle.putString("course","AUTOCAD");
                setBackNavigation();
                break;
            case R.id.swButton:
                bundle=new Bundle();
                bundle.putString("course","SOLIDWORKS");
                setBackNavigation();
                break;
            case R.id.catiaButton:
                getSupportFragmentManager().beginTransaction().replace(R.id.svrinfotechLayout,new CatiaCourse()).commit();
                hideFrontView();
                setBackNavigation();
                break;
            case R.id.ansysButton:
                getSupportFragmentManager().beginTransaction().replace(R.id.svrinfotechLayout,new AnsysCourse()).commit();
                hideFrontView();
                setBackNavigation();
                break;
            case R.id.javaButton:
                bundle=new Bundle();
                bundle.putString("course","JAVA");
                setBackNavigation();
                break;
            case R.id.pythonButton:
                bundle=new Bundle();
                bundle.putString("course","PYTHON");
                setBackNavigation();
                break;
            case R.id.androidButton:
                bundle=new Bundle();
                bundle.putString("course","ANDROID");
                setBackNavigation();
                break;
        }

        if (bundle!=null) {
            fragment.setArguments(bundle);
            hideFrontView();
            getSupportFragmentManager().beginTransaction().replace(R.id.svrinfotechLayout,fragment).commit();
        }
    }

    public void hideFrontView() {
        findViewById(R.id.engineeringTitle).setVisibility(View.GONE);
        cad.setVisibility(View.GONE);
        sw.setVisibility(View.GONE);
        catia.setVisibility(View.GONE);
        ansys.setVisibility(View.GONE);
        findViewById(R.id.programmingTitle).setVisibility(View.GONE);
        java.setVisibility(View.GONE);
        androidBtn.setVisibility(View.GONE);
        python.setVisibility(View.GONE);
    }

    private void hideMenuItem(int id) {
        Menu menu=navigationView.getMenu();
        menu.findItem(id).setVisible(false);
    }

    private void openFragment() {
        if(fragment!=null) {
            hideFrontView();
            getSupportFragmentManager().beginTransaction().replace(R.id.svrinfotechLayout, fragment).commit();
        }
    }

    private void setBackNavigation() {
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
            }
        });
    }

    private void hideMenuContent(int id,Menu menu) {
        MenuItem menuItem=menu.findItem(id);
        menuItem.setVisible(false);
    }
}