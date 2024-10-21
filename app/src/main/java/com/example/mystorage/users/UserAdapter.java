package com.example.mystorage.users;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mystorage.activity.ChatWindow;
import com.example.mystorage.activity.MainActivity;
import com.example.mystorage.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    MainActivity mainActivity;
    ArrayList<Users> usersArrayList;


    //copy from main activity and paster upper
    public UserAdapter(MainActivity mainActivity, ArrayList<Users> usersArrayList) {
        this.mainActivity= mainActivity;
        this.usersArrayList=usersArrayList;
    }

    @NonNull
    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mainActivity).inflate(R.layout.user_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.ViewHolder holder, int position) {

        Users users= usersArrayList.get(position);

//        if(FirebaseAuth.getInstance().getCurrentUser().getUid().equals(users.getUserId())){
//            holder.itemView.setVisibility(View.GONE);
//        }

        holder.username.setText(users.userName);
        holder.userstatus.setText(users.status);
        Picasso.get().load(users.profilepic).into(holder.userimg);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(mainActivity, ChatWindow.class);

                intent.putExtra("nameeee",users.getUserName());
                intent.putExtra("receiverImg",users.getProfilepic());
                intent.putExtra("uid",users.getUserId());
                mainActivity.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return usersArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView userimg;
        TextView username,userstatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            userimg= itemView.findViewById(R.id.userimg);
            username= itemView.findViewById(R.id.username);
            userstatus= itemView.findViewById(R.id.userstatus);

        }
    }
}
