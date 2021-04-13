package com.example.miumessenger.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.miumessenger.R;
import com.example.miumessenger.databinding.ActivitySettingsBinding;
import com.example.miumessenger.databinding.FragmentChatsBinding;
import com.example.miumessenger.databinding.FragmentSettingsBinding;
import com.example.miumessenger.models.Users;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;


public class SettingsFragment extends Fragment {


    public SettingsFragment() {}

//    FragmentSettingsBinding binding;
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        getActivity().setTitle("Settings");
//        binding = FragmentSettingsBinding.inflate(getLayoutInflater());
//
//    }

ActivitySettingsBinding binding;
    FirebaseDatabase database;
    FirebaseAuth auth;
    FirebaseStorage storage;
    String profilePic;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = ActivitySettingsBinding.inflate(inflater, container, false);
        getActivity().setTitle(R.string.menu_Settings);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();

        binding.plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,33);
            }
        });
        database.getReference().child("users").child(FirebaseAuth.getInstance().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Users user = snapshot.getValue(Users.class);
                        Picasso.get().load(user.getProfilePic()).placeholder(R.drawable.avatar)
                                .into(binding.userProfilePic);
                        binding.etUserName.setText(user.getUserName());
                        binding.etStatus.setText(user.getAbout());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        binding.saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String status = binding.etStatus.getText().toString();
                String userName = binding.etUserName.getText().toString();
                HashMap<String, Object> obj = new HashMap<>();
                obj.put("userName",userName);
                obj.put("about",status);
                database.getReference().child("users").child(FirebaseAuth.getInstance().getUid())
                        .updateChildren(obj);
            }
        });


        return binding.getRoot();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 33) {
            if (data.getData() != null) {

                Uri uri = data.getData();
                binding.userProfilePic.setImageURI(uri);

                final StorageReference reference = storage.getReference()
                        .child("profile_pictures")
                        .child(FirebaseAuth.getInstance().getUid());

                reference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                database.getReference().child("users").child(FirebaseAuth.getInstance().getUid())
                                        .child("profilePic").setValue(uri.toString());

                                profilePic = uri.toString();
                                Users user = new Users(profilePic);



                            }
                        });

                    }
                });
            }
        }

    }
}