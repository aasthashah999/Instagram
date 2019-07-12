package com.example.instagram;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.instagram.fragments.PostsFragment;
import com.example.instagram.fragments.composeFragemnt;
import com.example.instagram.fragments.profileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        final FragmentManager fragmentManager = getSupportFragmentManager();
        bottomNavigationView = findViewById(R.id.bottom_navigation);



        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment2;
                fragment2 = new composeFragemnt();
                switch (item.getItemId()) {
                    case R.id.home:
                        Fragment fragment1;
                        fragment1 = new PostsFragment();
                        fragmentManager.beginTransaction().replace(R.id.fragmentArea,fragment1).commit();
                        return true;
                    case R.id.compose:
                        fragmentManager.beginTransaction().replace(R.id.fragmentArea,fragment2).commit();
                        return true;
                    case R.id.profile:
                        Fragment fragment3;
                        fragment3 = new profileFragment();
                        fragmentManager.beginTransaction().replace(R.id.fragmentArea,fragment3).commit();
                        return true;
                    default: return true;

                }

            }
        });
    }

}
