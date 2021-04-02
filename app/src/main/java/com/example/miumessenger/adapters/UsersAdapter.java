package com.example.miumessenger.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miumessenger.R;
import com.example.miumessenger.activities.ChatDetailsActivity;
import com.example.miumessenger.databinding.SampleShowUserBinding;
import com.example.miumessenger.models.Users;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class UsersAdapter extends  RecyclerView.Adapter<UsersAdapter.ViewHolder>{
    Context context;
    ArrayList<Users> usersArrayList;
    public UsersAdapter( ArrayList<Users> usersArrayList, Context context){
        this.context= context;
        this.usersArrayList = usersArrayList;
    }

    @NonNull
    @Override
    public UsersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sample_show_user,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsersAdapter.ViewHolder holder, int position) {
        Users users = usersArrayList.get(position);
        Picasso.get().load(users.getProfilePic()).placeholder(R.drawable.avatar)
                .into(holder.binding.inboxProfilePic);
        holder.binding.userName.setText(users.getUserName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context , ChatDetailsActivity.class);
                intent.putExtra("userName",users.getUserName());
                intent.putExtra("userId",users.getUserId());
                intent.putExtra("profilePic",users.getProfilePic());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return usersArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
//        ImageView image;
//        TextView userName, lastMsg, lastMsgTime;
        SampleShowUserBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = SampleShowUserBinding.bind(itemView);
//            image = itemView.findViewById(R.id.inboxProfilePic);
//            userName = itemView.findViewById(R.id.userName);
//            lastMsg = itemView.findViewById(R.id.lastMsg);
//            lastMsgTime = itemView.findViewById(R.id.lastMsgTime);
        }
    }
}
