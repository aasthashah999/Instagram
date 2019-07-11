package com.example.instagram;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.parse.FindCallback;
import com.parse.ParseException;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    Button addImage;
    RecyclerView rvPosts;
    ArrayList<Post> posts;
    PostAdapter adapter;
    SwipeRefreshLayout swipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        addImage = findViewById(R.id.addImageButton);
        rvPosts = findViewById(R.id.rvPosts);
        posts = new ArrayList<>();
        swipe = findViewById(R.id.swipeContainer);
        adapter = new PostAdapter(posts);
        rvPosts.setLayoutManager(new LinearLayoutManager(this));
        rvPosts.setAdapter(adapter);
        getPosts();
        ActivityCompat.requestPermissions(HomeActivity.this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                1);
        ActivityCompat.requestPermissions(HomeActivity.this,
                new String[]{Manifest.permission.CAMERA},
                1);

        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeActivity.this, PostActivity.class);
                startActivity(i);
            }
        });

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getPosts();
            }
        });
        swipe.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    public void getPosts(){
        final Post.Query postsQuery = new Post.Query();
        postsQuery.getTop().withUser();

        postsQuery.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> objects, ParseException e) {
                if (e == null){
                    for(int i = 0; i < objects.size(); i++){
                        Post post;
                        post = objects.get(i);
                        posts.add(0, post);
                        adapter.notifyDataSetChanged();
                        swipe.setRefreshing(false);
                    }
                }else{
                    e.printStackTrace();
                }
            }
        });
    }
}
