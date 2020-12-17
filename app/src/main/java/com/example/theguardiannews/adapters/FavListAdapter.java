package com.example.theguardiannews.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.theguardiannews.R;
import com.example.theguardiannews.activities.ArticleActivity;
import com.example.theguardiannews.database.UploadModel;
import com.example.theguardiannews.viewModel_repo.UploadViewModel;

import java.io.File;
import java.util.List;

public class FavListAdapter extends RecyclerView.Adapter<FavListAdapter.FavoriteVH> {

    Context context;
    private List<UploadModel> uploadModels;

    public FavListAdapter(Context context, List<UploadModel> uploadModels) {
        this.context = context;
        this.uploadModels = uploadModels;
    }

    @NonNull
    @Override
    public FavoriteVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fav_item, parent, false);
        return new FavoriteVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteVH holder, int position) {
        holder.postCategory.setText(uploadModels.get(position).getCategoryModel());
        holder.postTitle.setText(uploadModels.get(position).getTitleModel());
        holder.title = uploadModels.get(position).getTitleModel();
        holder.category = uploadModels.get(position).getCategoryModel();
        holder.imageUrl = uploadModels.get(position).getImageUrlModel();
        holder.date = uploadModels.get(position).getDate();
        holder.text = uploadModels.get(position).getTextModel();
        if (holder.imageUrl != null) {
            File imgFile = new File(holder.imageUrl);
            if (imgFile.exists()) {
                Glide.with(context)
                        .load(imgFile)
                        .into(holder.postImage);
            }
        }

        holder.saveFavBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UploadViewModel viewModel;
                viewModel = ViewModelProviders.of((FragmentActivity) context).get(UploadViewModel.class);
                viewModel.delete(uploadModels.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return uploadModels.size();
    }

    public class FavoriteVH extends RecyclerView.ViewHolder {

        TextView postCategory;
        ImageView postImage;
        TextView postTitle;
        ImageView saveFavBtn;
        String text;
        String title;
        String category;
        String imageUrl;
        String date;

        public FavoriteVH(@NonNull View itemView) {
            super(itemView);
            postCategory = itemView.findViewById(R.id.fav_item_category_text);
            postImage = itemView.findViewById(R.id.fav_item_image);
            postTitle = itemView.findViewById(R.id.fav_item_title);
            saveFavBtn = itemView.findViewById(R.id.fav_item_delete_icon);


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
                    intent.putExtra("date", date);
                    context.startActivity(intent);
                }
            });
        }
    }

    public void addNewItem(List<UploadModel> uploadModels) {
        this.uploadModels = uploadModels;
        notifyDataSetChanged();
    }
}
