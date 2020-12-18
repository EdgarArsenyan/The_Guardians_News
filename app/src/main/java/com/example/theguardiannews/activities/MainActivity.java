package com.example.theguardiannews.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.theguardiannews.R;
import com.example.theguardiannews.fragments.FavListFragment;
import com.example.theguardiannews.fragments.NewsListFragment;
import com.example.theguardiannews.utils.Dialogs;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView navigationView;
    @SuppressLint("StaticFieldLeak")
    public static ProgressBar progress;
    private boolean checkClose = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        clickNavigationTab();
    }

    private void clickNavigationTab() {
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                boolean checked = false;
                switch (item.getItemId()) {
                    case R.id.news_item: {
                        Fragment fragment;
                        fragment = new FavListFragment();
                        FragmentManager fm = getSupportFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                        ft.remove(fragment);
                        ft.commit();
                        fm.popBackStack();
                        checked = true;
                        break;
                    }
                    case R.id.fav_item: {
                        Fragment fragment;
                        fragment = new FavListFragment();
                        FragmentManager fm = getSupportFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                        ft.addToBackStack(null);
                        ft.replace(R.id.frag_container, fragment);
                        ft.commit();
                        checked = true;
                        break;
                    }
                }
                return checked;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        if(checkClose){
            finish();
            moveTaskToBack(true);
        }else {
            Dialogs.openEmailDialog(this, getString(R.string.dialog_close_app));
            checkClose = true;
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    checkClose = false;
                }
            },4000);
        }
    }
    private void init() {
        navigationView = findViewById(R.id.main_bottom_navigation);
        MaterialToolbar toolbar = findViewById(R.id.main_title);
        toolbar.setTitleTextAppearance(this, R.style.RobotoBoldTextAppearance);
        toolbar.setElevation(3);
        progress = findViewById(R.id.progress);
        Fragment fragment;
        fragment = new NewsListFragment();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frag_container, fragment);
        ft.commit();
    }
}