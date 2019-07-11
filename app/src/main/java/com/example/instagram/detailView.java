package com.example.instagram;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

public class detailView extends AppCompatActivity {

    ImageView profileIv;
    TextView imgUsername;
    ImageView post1;
    TextView username;
    TextView description;
    TextView createdAt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view);

        profileIv = findViewById(R.id.imageViewUserPost1);
        imgUsername = findViewById(R.id.tvPostUsername1);
        post1 = findViewById(R.id.DetailPostIv);
        username = findViewById(R.id.textViewUsername1);
        description = findViewById(R.id.descriptiontv1);
        createdAt = findViewById(R.id.createdAtv);
        String relativeDate = "";
        createdAt.setText(getIntent().getStringExtra("date"));
        imgUsername.setText(getIntent().getStringExtra("username"));
        username.setText(getIntent().getStringExtra("username"));
        description.setText(getIntent().getStringExtra("description"));
        String id = getIntent().getStringExtra("id");
        Toast.makeText(this, id, Toast.LENGTH_LONG).show();
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.getInBackground(id, new GetCallback<Post>(){
            public void done(Post post, ParseException e){
                if (e == null){
                    Glide.with(getApplicationContext()).load(post.getImage().getUrl()).into(post1);
                }else {
                    e.printStackTrace();
                }
            }

        });
    }
}
