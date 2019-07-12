package com.example.instagram.fragments;

import android.Manifest;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.camerakit.CameraKitView;
import com.example.instagram.Post;
import com.example.instagram.R;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;
import java.io.FileOutputStream;

public class composeFragemnt extends Fragment {

    Button addImage;
    Button getPicture;
    EditText etdescription;
    ImageView postImage;
    File savedPhoto;
    CameraKitView cameraKitView;

    String [] appPermissions = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.compose_fragment, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etdescription = view.findViewById(R.id.etDescription);
        addImage = view.findViewById(R.id.button);
        getPicture = view.findViewById(R.id.takePicture);
        postImage = view.findViewById(R.id.postImage);
        cameraKitView = view.findViewById(R.id.camera);


        getPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cameraKitView.captureImage(new CameraKitView.ImageCallback() {
                    @Override
                    public void onImage(CameraKitView cameraKitView, final byte[] capturedImage) {
                        savedPhoto = new File(getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES), "photo.jpg");
                        try {
                            FileOutputStream outputStream = new FileOutputStream(savedPhoto.getPath());
                            outputStream.write(capturedImage);
                            outputStream.close();
                            cameraKitView.setVisibility(View.GONE);
                            postImage.setVisibility(View.VISIBLE);
                            String filePath = savedPhoto.getPath();
                            Bitmap bitmap = BitmapFactory.decodeFile(filePath);
                            postImage.setImageBitmap(bitmap);
                        } catch (java.io.IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });

        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String description = etdescription.getText().toString();
                ParseUser user = ParseUser.getCurrentUser();
                if(savedPhoto == null) {
                    Log.d("error", "no image");
                    return;
                }
                creatPost(user, description, savedPhoto);
            }
        });
    }

    public void creatPost(ParseUser user, String description, File parseFile) {
        Post post = new Post();
        post.setUser(user);
        post.setDescription(description);
        post.setImage(new ParseFile(parseFile));
        post.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e!=null){
                    e.printStackTrace();
                    return;
                }else{
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        cameraKitView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        cameraKitView.onResume();
    }

    @Override
    public void onPause() {
        cameraKitView.onPause();
        super.onPause();
    }

    @Override
    public void onStop() {
        cameraKitView.onStop();
        super.onStop();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        cameraKitView.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }



}
