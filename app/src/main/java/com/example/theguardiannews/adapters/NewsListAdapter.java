package com.example.theguardiannews.adapters;

import android.Manifest;
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
import android.widget.Toast;

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
import com.example.theguardiannews.models.UploadViewModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.NewsVH> {

    private Context context;
    private List<Result> results;
    private UploadViewModel viewModel;
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
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    String dateText = dateFormat.format(date);
                    intent.putExtra("date", dateText);
                    context.startActivity(intent);
                }
            });

            saveFavBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    model.setTitleModel(postTitle.getText().toString());
                    model.setCategoryModel(postCategory.getText().toString());
                    model.setTextModel(text);
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    String dateText = dateFormat.format(date);
                    model.setDate(dateText);
                    Log.e("sdfsdf ", imageUrl);
                    downloadImage(imageUrl);
                    saveFavBtn.setBackgroundResource(R.drawable.ic_fav_checked_24);
//                    viewModel = ViewModelProviders.of((FragmentActivity) context).get(UploadViewModel.class);
//                    viewModel.insert(model);
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
        if (verifyPermissions()) {
//            String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + "News" + "/";
//            File dir = new File(dirPath);
            Glide.with(context)
                    .load(imageURL)
                    .into(new CustomTarget<Drawable>() {
                        @Override
                        public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                            Bitmap bitmap = ((BitmapDrawable) resource).getBitmap();
                            Toast.makeText(context, "Saving Image...", Toast.LENGTH_SHORT).show();
                            saveImage(bitmap);
                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {
                        }

                        @Override
                        public void onLoadFailed(@Nullable Drawable errorDrawable) {
                            super.onLoadFailed(errorDrawable);

                            Toast.makeText(context, "Failed to Download Image! Please try again later.", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private void saveImage(Bitmap image) {
        String savedImagePath = null;
        String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + "News" + "/";
        File dir = new File(dirPath);

        if (!dir.isDirectory()) {
            dir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File imageFile = new File(dir, fileName);
        savedImagePath = imageFile.getAbsolutePath();
        try {
            OutputStream fOut = new FileOutputStream(imageFile);
            image.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
            model.setImageUrlModel(savedImagePath);
            viewModel = ViewModelProviders.of((FragmentActivity) context).get(UploadViewModel.class);
            viewModel.insert(model);
            fOut.close();
            Toast.makeText(context, "Image Saved!", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e("sdfsdf ", e.getMessage());

            Toast.makeText(context, "Error while saving image!", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
}

    public void addNewItem(List<Result> results) {
        this.results = results;
        notifyDataSetChanged();
    }
}
