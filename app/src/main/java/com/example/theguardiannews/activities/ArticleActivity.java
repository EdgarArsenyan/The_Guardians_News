package com.example.theguardiannews.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.theguardiannews.R;
import com.google.android.material.appbar.MaterialToolbar;

public class ArticleActivity extends AppCompatActivity {

    private ImageView articleImage;
    private TextView articleTitle, articleText;
    private TextView categoryToolbar;
    private TextView articleDate;
    private String text;
    private String title;
    private String category;
    private String imageUrl;
    private String date;
    private MaterialToolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        init();
        getInfo();
        showInfo();
        backSpace(toolbar);
    }

    public void backSpace(MaterialToolbar toolbar) {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void init() {
        articleImage = findViewById(R.id.article_image);
        articleText = findViewById(R.id.article_text);
        articleTitle = findViewById(R.id.article_title);
        categoryToolbar = findViewById(R.id.category);
        articleDate = findViewById(R.id.article_date);
        toolbar = findViewById(R.id.title);
        toolbar.setTitleTextAppearance(this, R.style.RobotoBoldTextAppearance);
        toolbar.setElevation(0);
    }

    public void getInfo() {
        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        text = intent.getStringExtra("text");
        category = intent.getStringExtra("category");
        imageUrl = intent.getStringExtra("imageUrl");
        date = intent.getStringExtra("date");
    }

    public void showInfo() {
        articleTitle.setText(title);
        articleText.setText(text);
        categoryToolbar.setText(category);
        articleDate.setText(date);
        Glide.with(ArticleActivity.this)
                .load(imageUrl)
                .into(articleImage);
    }
}