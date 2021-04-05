package com.example.miumessenger.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.miumessenger.R;
import com.example.miumessenger.adapters.GroupShowingAdapter;
import com.example.miumessenger.adapters.UsersAdapter;
import com.example.miumessenger.databinding.FragmentGroupBinding;
import com.example.miumessenger.models.Groups;
import com.example.miumessenger.models.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class GroupFragment extends Fragment {


    public GroupFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    FragmentGroupBinding binding;
    ArrayList<Groups> groupsArrayList = new ArrayList<>();
    FirebaseAuth auth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentGroupBinding.inflate(inflater, container, false);
        getActivity().setTitle("Groups");

        GroupShowingAdapter adapter = new GroupShowingAdapter(groupsArrayList , getContext());
        binding.chatsRecyclerView.setAdapter(adapter);


        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.chatsRecyclerView.setLayoutManager(layoutManager);
        binding.chatsRecyclerView.showShimmerAdapter();

        database.getReference().child("groups").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                groupsArrayList.clear();
                for(DataSnapshot  datasnapshot : snapshot.getChildren()){
                    Groups group = datasnapshot.getValue(Groups.class);
                    group.setGroupName(datasnapshot.getKey());

                    //group division implement
                }
                binding.chatsRecyclerView.hideShimmerAdapter();
                adapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return  binding.getRoot();
    }
}