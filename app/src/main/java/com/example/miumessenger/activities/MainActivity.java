package com.example.miumessenger.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.miumessenger.R;
import com.example.miumessenger.databinding.ActivityMainBinding;
import com.example.miumessenger.fragments.AcademicCalenderFragment;
import com.example.miumessenger.fragments.ChatsFragment;
import com.example.miumessenger.fragments.EventFragment;
import com.example.miumessenger.fragments.NewsFragment;
import com.example.miumessenger.fragments.NoticeFragment;
import com.example.miumessenger.fragments.PortalFragment;
import com.example.miumessenger.fragments.SettingsFragment;
import com.example.miumessenger.models.Users;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawer;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private ActionBarDrawerToggle toggle;
    FirebaseAuth auth;
    FirebaseDatabase database;
    ActivityMainBinding binding;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    TextView navUserName, navEmail, navProfilePic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        auth = FirebaseAuth.getInstance();
        navEmail = findViewById(R.id.nav_userEmail);
        navUserName = findViewById(R.id.nav_userName);


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();
        //load layout
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_container,new ChatsFragment());
        fragmentTransaction.commit();

    navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            navEmail.setText(auth.getCurrentUser().getUid());

            drawer.closeDrawer(GravityCompat.START);
            switch (item.getItemId()){
                case R.id.nav_logout:
                    auth.signOut();
                    Toast.makeText(MainActivity.this, "Logged Out",Toast.LENGTH_SHORT).show();
                    Intent intentSignIn = new Intent(MainActivity.this,SignInActivity.class);
                    startActivity(intentSignIn);
                    finish();
                    break;
                case R.id.nav_settings:
                    fragmentManager = getSupportFragmentManager();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, new SettingsFragment());
                    fragmentTransaction.commit();


                     break;
                case R.id.nav_chats:
                    fragmentManager = getSupportFragmentManager();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, new ChatsFragment());
                    fragmentTransaction.commit();
                    break;
                case R.id.nav_portal:

                    fragmentManager = getSupportFragmentManager();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, new PortalFragment());
                    fragmentTransaction.commit();
                    break;
                case R.id.nav_news:

                    fragmentManager = getSupportFragmentManager();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, new NewsFragment());
                    fragmentTransaction.commit();
                    break;
                case R.id.nav_academicCalender:

                    fragmentManager = getSupportFragmentManager();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, new AcademicCalenderFragment());
                    fragmentTransaction.commit();
                    break;
                case R.id.nav_notice:

                    fragmentManager = getSupportFragmentManager();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, new NoticeFragment());
                    fragmentTransaction.commit();
                    break;
                case R.id.nav_events:

                    fragmentManager = getSupportFragmentManager();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, new EventFragment());
                    fragmentTransaction.commit();
                    break;
            }

            return true;
        }
    });

    }


}