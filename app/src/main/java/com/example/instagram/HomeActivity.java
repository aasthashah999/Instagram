package com.example.instagram;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.FindCallback;
import com.parse.ParseException;

import java.util.List;

public class HomeActivity extends AppCompatActivity {

    Button addImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        addImage = findViewById(R.id.addImageButton);
        final Post.Query postsQuery = new Post.Query();
        postsQuery.getTop().withUser();

        postsQuery.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> objects, ParseException e) {
                if (e == null){
                    for(int i = 0; i < objects.size(); i++){
                        Log.d("HomeActivity", objects.get(i).getDescription()+ objects.get(i).getUser().getUsername());
                    }
                }else{
                    e.printStackTrace();
                }
            }
        });

        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeActivity.this, PostActivity.class);
                startActivity(i);
            }
        });
    }




}
