package com.example.instagram.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instagram.Post;
import com.example.instagram.R;
import com.example.instagram.postAdapterProfile;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class postProfile extends Fragment {
    ArrayList<Post> posts;
    ParseUser user1;
    RecyclerView profileRV;
    postAdapterProfile adapter;
    TextView tv;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        user1 = bundle.getParcelable("user");
        profileRV = view.findViewById(R.id.profilerv1);
        profileRV.setLayoutManager(new GridLayoutManager(getContext(), 3));
        posts = new ArrayList<>();
        adapter = new postAdapterProfile(posts);
        tv = view.findViewById(R.id.tvprofileUsername2);
        tv.setText(user1.getUsername());
        profileRV.setAdapter(adapter);
        getPosts();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.profile, container, false);
    }

    public void getPosts(){
        Toast.makeText(getContext(),user1.getUsername(), Toast.LENGTH_LONG ).show();
        final Post.Query postsQuery = new Post.Query();
        postsQuery.getTop().withUser();
        postsQuery.whereEqualTo("user", user1);
        Toast.makeText(getContext(),user1.getObjectId(), Toast.LENGTH_LONG ).show();
        postsQuery.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> objects, ParseException e) {
                if (e == null){
                    for(int i = 0; i < objects.size(); i++){
                        Toast.makeText(getContext(),"In function", Toast.LENGTH_LONG ).show();
                        Post post;
                        post = objects.get(i);
                        posts.add(0, post);
                        String size = Integer.toString(posts.size());
                        Log.d("size", size);
                        adapter.notifyDataSetChanged();
                    }
                }else{
                    e.printStackTrace();
                }
            }
        });
    }

}
