package com.example.miumessenger.adapters;

import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miumessenger.R;
import com.example.miumessenger.activities.ChatDetailsActivity;
import com.example.miumessenger.activities.GroupChatActivity;
import com.example.miumessenger.databinding.SampleShowGroupBinding;
import com.example.miumessenger.databinding.SampleShowUserBinding;
import com.example.miumessenger.models.Groups;
import com.example.miumessenger.models.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class GroupShowingAdapter extends  RecyclerView.Adapter<GroupShowingAdapter.ViewHolder>{
    Context context;
    ArrayList<Groups> groupsArrayList;
    public GroupShowingAdapter(ArrayList<Groups> groupsArrayList, Context context){
        this.context= context;
        this.groupsArrayList = groupsArrayList;
    }

    @NonNull
    @Override
    public GroupShowingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sample_show_group,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupShowingAdapter.ViewHolder holder, int position) {
        Groups group = groupsArrayList.get(position);
        FirebaseDatabase.getInstance().getReference()
                .child("groups")
                .child("miu")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            String lastMsg = snapshot.child("lastMsg").getValue(String.class);
                            long time = snapshot.child("lastMsgTime").getValue(Long.class);

                            SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a");
                            holder.binding.lastMsgTime.setText(dateFormat.format(new Date(time)));
                            holder.binding.lastMsg.setText(lastMsg);
                        }else{
                            holder.binding.lastMsg.setText("Tap to chat");
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        holder.binding.groupName.setText(Groups.getGroupName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context , GroupChatActivity.class);
                intent.putExtra("groupName",group.getGroupName());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return groupsArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        SampleShowGroupBinding binding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = SampleShowGroupBinding.bind(itemView);
        }
    }
}
