package com.example.instagram;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class postAdapterProfile extends RecyclerView.Adapter<postAdapterProfile.ViewHolder> {
    public ArrayList<Post> posts;
    Context context;

    public postAdapterProfile(ArrayList<Post> posts1){
        posts = posts1;
    }

    @NonNull
    @Override
    public postAdapterProfile.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        posts = new ArrayList<>();
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View post = inflater.inflate(R.layout.profile_item_view, parent, false);
        final postAdapterProfile.ViewHolder viewHolder = new postAdapterProfile.ViewHolder(post);

        return viewHolder;
    }



    @Override
    public void onBindViewHolder(@NonNull postAdapterProfile.ViewHolder holder, int position) {
        final Post post = posts.get(position);
        //TODO images

        Glide.with(context).load(post.getImage().getUrl()).into(holder.postImage);

    }

    public int getItemCount() {
        return posts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView postImage;


        public ViewHolder(View itemView){
            super(itemView);
            postImage = itemView.findViewById(R.id.postImageView2);
        }
    }
}
