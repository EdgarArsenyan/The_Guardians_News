package com.example.theguardiannews.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.theguardiannews.R;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        init();
        getInfo();
        showInfo();
    }

    private void init() {
        articleImage = findViewById(R.id.article_image);
        articleText = findViewById(R.id.article_text);
        articleTitle = findViewById(R.id.article_title);
        categoryToolbar = findViewById(R.id.category);
        articleDate = findViewById(R.id.article_date);
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
                .centerCrop()
                .into(articleImage);
    }
}