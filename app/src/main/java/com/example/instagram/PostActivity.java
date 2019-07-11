package com.example.instagram;

import android.Manifest;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.camerakit.CameraKitView;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;
import java.io.FileOutputStream;


public class PostActivity extends AppCompatActivity {
    Button addImage;
    Button getPicture;
    EditText etdescription;
    ImageView postImage;
    File savedPhoto;
    private CameraKitView cameraKitView;
    String [] appPermissions = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        etdescription = findViewById(R.id.etDescription);
        addImage = findViewById(R.id.button);
        getPicture = findViewById(R.id.takePicture);
        postImage = findViewById(R.id.postImage);
        cameraKitView = findViewById(R.id.camera);

        ActivityCompat.requestPermissions(PostActivity.this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                1);

        getPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cameraKitView.captureImage(new CameraKitView.ImageCallback() {
                    @Override
                    public void onImage(CameraKitView cameraKitView, final byte[] capturedImage) {
                        savedPhoto = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "photo.jpg");
                        try {
                            Toast.makeText(getApplicationContext(), "Image Taken", Toast.LENGTH_SHORT).show();
                            FileOutputStream outputStream = new FileOutputStream(savedPhoto.getPath());
                            outputStream.write(capturedImage);
                            outputStream.close();
                            cameraKitView.setVisibility(View.GONE);
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
                    Toast.makeText(getApplicationContext(), "error!!!", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                    return;
                }else{
                    Toast.makeText(getApplicationContext(), "success!!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        cameraKitView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        cameraKitView.onResume();
    }

    @Override
    protected void onPause() {
        cameraKitView.onPause();
        super.onPause();
    }

    @Override
    protected void onStop() {
        cameraKitView.onStop();
        super.onStop();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        cameraKitView.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }




}