package com.example.theguardiannews.adapters;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.theguardiannews.R;
import com.example.theguardiannews.activities.ArticleActivity;
import com.example.theguardiannews.database.UploadModel;
import com.example.theguardiannews.models.Result;
import com.example.theguardiannews.viewModel_repo.UploadViewModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.NewsVH> {

    private final Context context;
    private List<Result> results;
    private UploadModel model = new UploadModel();

    public NewsListAdapter(Context context, List<Result> results) {
        this.context = context;
        this.results = results;
    }

    @NonNull
    @Override
    public NewsVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.news_item, parent, false);
        return new NewsVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsVH holder, int position) {
        holder.postCategory.setText(results.get(position).getmCategory());
        holder.postTitle.setText(results.get(position).getmTitle());
        holder.text = results.get(position).getmFields().getBodyText();
        holder.imageUrl = results.get(position).getmFields().getThumbnail();
        holder.date = results.get(position).getDate();
        holder.id = results.get(position).getId();

        Glide.with(context)
                .load(holder.imageUrl)
                .into(holder.postImage);
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class NewsVH extends RecyclerView.ViewHolder {

        TextView postCategory;
        ImageView postImage;
        TextView postTitle;
        ImageView saveFavBtn;
        String text;
        String title;
        String category;
        String imageUrl;
        String id;
        Date date;

        public NewsVH(@NonNull View itemView) {
            super(itemView);
            postCategory = itemView.findViewById(R.id.post_item_category);
            postImage = itemView.findViewById(R.id.post_item_img);
            postTitle = itemView.findViewById(R.id.post_item_title);
            saveFavBtn = itemView.findViewById(R.id.fav_icon);
            category = postCategory.getText().toString();
            title = postTitle.getText().toString();

            itemView.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ArticleActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("title", postTitle.getText().toString());
                    intent.putExtra("category", postCategory.getText().toString());
                    intent.putExtra("text", text);
                    intent.putExtra("imageUrl", imageUrl);
                    @SuppressLint("SimpleDateFormat")
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    String dateText = dateFormat.format(date);
                    intent.putExtra("date", dateText);
                    context.startActivity(intent);
                }
            });

            saveFavBtn.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onClick(View v) {
                    model.setTitleModel(postTitle.getText().toString());
                    model.setCategoryModel(postCategory.getText().toString());
                    model.setTextModel(text);
                    @SuppressLint("SimpleDateFormat")
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    String dateText = dateFormat.format(date);
                    model.setDate(dateText);
                    if(verifyPermissions()){
                        saveFavBtn.setBackgroundResource(R.drawable.ic_fav_checked_24);
                        downloadImage(imageUrl);
                    }
                }
            });
        }
    }

    public Boolean verifyPermissions() {
        int permissionExternalMemory = ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionExternalMemory != PackageManager.PERMISSION_GRANTED) {
            String[] STORAGE_PERMISSIONS = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
            ActivityCompat.requestPermissions((Activity) context, STORAGE_PERMISSIONS, 1);
            return false;
        }
        return true;
    }

    void downloadImage(String imageURL) {
//        if (verifyPermissions()) {
            Glide.with(context)
                    .load(imageURL)
                    .into(new CustomTarget<Drawable>() {
                        @Override
                        public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                            Bitmap bitmap = ((BitmapDrawable) resource).getBitmap();
                            saveImage(bitmap);
                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {
                        }

                        @Override
                        public void onLoadFailed(@Nullable Drawable errorDrawable) {
                            super.onLoadFailed(errorDrawable);
                            Log.e("saving  ", errorDrawable.toString());
                        }
                    });
//        }
    }

    private void saveImage(Bitmap image) {
        String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + "News" + "/";
        File dir = new File(dirPath);

        if (!dir.isDirectory()) {
            dir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File imageFile = new File(dir, fileName);
        try {
            OutputStream fOut = new FileOutputStream(imageFile);
            image.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
            model.setImageUrlModel(imageFile.getAbsolutePath());
            UploadViewModel viewModel = ViewModelProviders.of((FragmentActivity) context).get(UploadViewModel.class);
            viewModel.insert(model);
            fOut.close();
        } catch (Exception e) {
            Log.e("saving  ", Objects.requireNonNull(e.getMessage()));
            e.printStackTrace();
        }
    }

    public void addNewItem(List<Result> results) {
        this.results = results;
        notifyDataSetChanged();
    }
}
