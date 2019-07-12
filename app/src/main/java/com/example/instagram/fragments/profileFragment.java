package com.example.instagram.fragments;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instagram.MainActivity;
import com.example.instagram.Post;
import com.example.instagram.R;
import com.example.instagram.postAdapterProfile;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class profileFragment extends Fragment {

    ArrayList<Post> posts;
    Button logoutButton;
    ParseUser user;
    RecyclerView profileRV;
    postAdapterProfile adapter;
    TextView tv;
    ImageButton profileImage;
    public final int RESULT_LOAD_IMAGE = 20;
    File file;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        profileRV = view.findViewById(R.id.profilerv);
        profileRV.setLayoutManager(new GridLayoutManager(getContext(), 3));
        posts = new ArrayList<>();
        adapter = new postAdapterProfile(posts);
        logoutButton = view.findViewById(R.id.logoutButton);
        user = ParseUser.getCurrentUser();
        tv = view.findViewById(R.id.tvprofileUsername);
        tv.setText(user.getUsername());
        profileRV.setAdapter(adapter);
        getPosts();
        profileImage = view.findViewById(R.id.imageButtonProfile);
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();

            }
        });
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

    public void getPosts(){
        Toast.makeText(getContext(),user.getUsername(), Toast.LENGTH_LONG ).show();
        final Post.Query postsQuery = new Post.Query();
        postsQuery.getTop().withUser();
        postsQuery.whereEqualTo("user", user);
        Toast.makeText(getContext(),user.getObjectId(), Toast.LENGTH_LONG ).show();
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

    public void openGallery(){
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Uri selectedImage = data.getData();
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContext().getContentResolver().query(selectedImage,filePathColumn,null, null, null);
        cursor.moveToFirst();

        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String imgDecodableString = cursor.getString(columnIndex);
        cursor.close();
        file = new File(selectedImage.getPath());
        profileImage.setImageBitmap(BitmapFactory.decodeFile(imgDecodableString));
        saveProfileImage(user, file);
    }

   public void saveProfileImage(ParseUser user, File file ){
        user.put("profileImage", new ParseFile(file));
        user.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e == null){
                    Log.d("jnwe", "wdiqd");
                }else{
                    e.printStackTrace();
                }

            }
        });

    }
}
