package com.example.instagram.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instagram.MainActivity;
import com.example.instagram.Post;
import com.example.instagram.R;
import com.parse.ParseUser;

import java.util.ArrayList;

public class profileFragment extends Fragment {

    ArrayList<Post> posts;
    Button logoutButton;
    RecyclerView profileRV;
    //postAdapterProfile adapter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        profileRV = view.findViewById(R.id.profilerv);
        profileRV.setLayoutManager(new GridLayoutManager(getContext(), 3));
        //adapter = new postAdapterProfile(posts);
        logoutButton = view.findViewById(R.id.logoutButton);
        posts = new ArrayList<>();
        //profileRV.setAdapter(adapter);
        //getPosts();
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseUser.logOut();
                ParseUser currentUser = ParseUser.getCurrentUser();
                Intent i = new Intent(getContext(), MainActivity.class);
                startActivity(i);
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.profile, container, false);
    }

//    public void getPosts(){
//        final Post.Query postsQuery = new Post.Query();
//        postsQuery.getTop().withUser();
//        final ParseUser user = ParseUser.getCurrentUser();
//
//        postsQuery.findInBackground(new FindCallback<Post>() {
//            @Override
//            public void done(List<Post> objects, ParseException e) {
//                if (e == null){
//                    for(int i = 0; i < objects.size(); i++){
//                        Post post = new Post();
//                        if(post.getUser().getObjectId().equals(user.getObjectId())){
//                            post = objects.get(i);
//                            posts.add(0, post);
//                        }
//                    }
//                }else{
//                    e.printStackTrace();
//                }
//            }
//        });
//    }
}
