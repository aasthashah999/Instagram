package com.example.instagram;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.io.File;

public class signUpActivity extends AppCompatActivity {

    ImageButton profileImage;
    EditText name;
    EditText username;
    EditText password;
    EditText confirmPassword;
    EditText email;
    Button signUp;
    public final int RESULT_LOAD_IMAGE = 20;
    File file;
    String [] appPermissions = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        profileImage = findViewById(R.id.profileButton);
        name = findViewById(R.id.tvNameInput);
        username = findViewById(R.id.tvUsernameInput1);
        password = findViewById(R.id.tvpassInput1);
        confirmPassword = findViewById(R.id.tvpassInput1);
        signUp = findViewById(R.id.addUserButton);
        email = findViewById(R.id.tvemail);


        ActivityCompat.requestPermissions(signUpActivity.this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                1);
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseUser user = new ParseUser();
                setUser(user);
                //saveProfileImage(user);

            }
        });

    }

    public void setUser(ParseUser user){
        user.setUsername(username.getText().toString());
        if (password.getText().toString().equals(confirmPassword.getText().toString())){
            user.setPassword(password.getText().toString());
        }else{
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_LONG).show();
        }
        user.setEmail(email.getText().toString());
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if(e==null){
                    Log.d("success", "success");
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data!= null){
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(selectedImage,filePathColumn,null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String imgDecodableString = cursor.getString(columnIndex);
            cursor.close();
            profileImage.setImageBitmap(BitmapFactory.decodeFile(imgDecodableString));
        }else{
            Toast.makeText(this, "Please select an image", Toast.LENGTH_LONG).show();
        }
    }

//    public void saveProfileImage(ParseUser user){
//        user.put("profileImage", new ParseFile(file));
//        user.saveInBackground(new SaveCallback() {
//            @Override
//            public void done(ParseException e) {
//                if(e == null){
//                    Toast.makeText(getApplicationContext(), "Signed Up!", Toast.LENGTH_LONG).show();
//                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
//                    startActivity(i);
//                }else{
//                    e.printStackTrace();
//                }
//            }
//        });
//    }
}
