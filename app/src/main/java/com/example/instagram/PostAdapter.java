package com.example.instagram;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.instagram.fragments.postProfile;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {
    public ArrayList<Post> posts;
    ImageButton heartButton;
    Context context;

    public PostAdapter(ArrayList<Post> posts1){
        posts = posts1;
    }


    @NonNull
    @Override
    public PostAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View post = inflater.inflate(R.layout.post_item, parent, false);
        final ViewHolder viewHolder = new ViewHolder(post);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull PostAdapter.ViewHolder holder, int position) {
        final Post post = posts.get(position);
        //TODO images
        holder.username.setText(post.getUser().getUsername());
        //TODO post image
        holder.username2.setText(post.getUser().getUsername());
        holder.description.setText(post.getDescription());
        if(post.getImage() != null){
            Glide.with(context).load(post.getImage().getUrl()).into(holder.postImage);
        }


    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView postImage;
        ImageButton like;
        ImageButton comment;
        ImageButton message;
        ImageButton save;
        ImageView profile;
        Button username;
        TextView username2;
        TextView description;
        CardView cardView;
        TextView likestv;

        public ViewHolder(View itemView){
            super(itemView);
            postImage = itemView.findViewById(R.id.postImageView);
            like = itemView.findViewById(R.id.imageButtonheart);
            comment = itemView.findViewById(R.id.imageButtonComment);
            message = itemView.findViewById(R.id.imageButtonDirectMessage);
            save = itemView.findViewById(R.id.imageButtonSaved);
            profile = itemView.findViewById(R.id.imageViewUserPost);
            username = itemView.findViewById(R.id.buttonUsername);
            username2 = itemView.findViewById(R.id.textViewUsername);
            description = itemView.findViewById(R.id.descriptiontv);
            cardView = itemView.findViewById(R.id.cardView);
            likestv = itemView.findViewById(R.id.likes);
            final Boolean clicked;
            clicked = false;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show();
                    int position = getAdapterPosition();
                    Intent i = new Intent(context, detailView.class);
                    i.putExtra("username", posts.get(position).getUser().getUsername());
                    i.putExtra("description", posts.get(position).getDescription());
                    i.putExtra("id", posts.get(position).getId());
                    i.putExtra("date", posts.get(position).getCreatedAt());
                    long dateMillis = posts.get(position).getCreatedAt().getTime();
                    String relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                            System.currentTimeMillis(), DateUtils.FORMAT_ABBREV_RELATIVE).toString();
                    i.putExtra("date", relativeDate);
                    context.startActivity(i);
                }
            });

            username.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    Post post = posts.get(position);
                    postProfile fragment = new postProfile ();
                    FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragmentArea, fragment);
                    Bundle args = new Bundle();
                    //args.putString("user", post.getUser().getObjectId());
                    args.putParcelable("user", post.getUser());
                    fragment.setArguments(args);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
            });

            like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(clicked == false){
                        int position = getAdapterPosition();
                        Post post = posts.get(position);
                        Number likes = post.getNumber("likes");
                        int like1 = likes.intValue() + 1;
                        likestv.setText(Integer.toString(like1));
                        likes = (Number) like1;
                        post.put("likes", likes);
                        //like.setBackground(drawable/ic_heartfilled);
                        //like.setImageDrawable("@drawable/ic_heartfilled");
                    } else{

                    }


                }
            });
        }

        public void updateLikes(Post post){
            Number likes = post.getNumber("likes");
            int like1 = likes.intValue() + 1;
            likestv.setText(Integer.toString(like1));
            likes = (Number) like1;
            post.put("likes", likes);
        }
    }
}
