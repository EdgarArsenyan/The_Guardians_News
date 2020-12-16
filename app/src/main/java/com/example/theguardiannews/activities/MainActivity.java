package com.example.theguardiannews.activities;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.theguardiannews.R;
import com.example.theguardiannews.fragments.FavListFragment;
import com.example.theguardiannews.fragments.NewsListFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.news_item:{
                        Fragment fragment;
                        fragment = new NewsListFragment();
                        FragmentManager fm = getSupportFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
//                        ft.setCustomAnimations(R.anim.next_in, R.anim.next_out);
                        ft.replace(R.id.frag_container, fragment);
                        ft.commit();
                        break;
                    }
                    case R.id.fav_item:{
                        Fragment fragment;
                        fragment = new FavListFragment();
                        FragmentManager fm = getSupportFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
//                        ft.setCustomAnimations(R.anim.next_in, R.anim.next_out);
                        ft.replace(R.id.frag_container, fragment);
                        ft.commit();
                        break;
                    }
                }
                return false;
            }
        });
    }

    private void init() {
        navigationView = findViewById(R.id.main_bottom_navigation);
    }
}