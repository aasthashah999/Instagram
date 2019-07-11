package com.example.instagram;

import android.content.Context;
import android.content.Intent;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {
    public ArrayList<Post> posts;
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

        Glide.with(context).load(post.getImage().getUrl()).into(holder.postImage);

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
        TextView username;
        TextView username2;
        TextView description;
        CardView cardView;

        public ViewHolder(View itemView){
            super(itemView);
            postImage = itemView.findViewById(R.id.postImageView);
            like = itemView.findViewById(R.id.imageButtonheart);
            comment = itemView.findViewById(R.id.imageButtonComment);
            message = itemView.findViewById(R.id.imageButtonDirectMessage);
            save = itemView.findViewById(R.id.imageButtonSaved);
            profile = itemView.findViewById(R.id.imageViewUserPost);
            username = itemView.findViewById(R.id.tvPostUsername);
            username2 = itemView.findViewById(R.id.textViewUsername);
            description = itemView.findViewById(R.id.descriptiontv);
            cardView = itemView.findViewById(R.id.cardView);
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
        }
    }
}
