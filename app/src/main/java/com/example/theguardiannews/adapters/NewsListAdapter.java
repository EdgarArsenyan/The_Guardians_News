package com.example.theguardiannews.adapters;

import android.content.Context;
import android.content.Intent;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.theguardiannews.R;
import com.example.theguardiannews.activities.ArticleActivity;
import com.example.theguardiannews.database.DatabaseHelper;
import com.example.theguardiannews.database.UploadModel;
import com.example.theguardiannews.models.Result;
import com.example.theguardiannews.utils.App;

import java.util.Date;
import java.util.List;

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.NewsVH> {

    private Context context;
    private List<Result> results;

    public NewsListAdapter(Context context, List<Result> results) {
        this.context = context;
        this.results = results;
    }

    @NonNull
    @Override
    public NewsVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.news_item, parent, false );
        return new NewsVH(view);

    }

    @Override
    public void onBindViewHolder(@NonNull NewsVH holder, int position) {
        holder.postCategory.setText(results.get(position).getmCategory());
        holder.postTitle.setText(results.get(position).getmTitle());
        holder.text = results.get(position).getmFields().getBodyText();
        holder.imageUrl =results.get(position).getmFields().getThumbnail();
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
                    DatabaseHelper databaseHelper = App.getInstance().getDatabaseInstance();
                    UploadModel model = new UploadModel();
                    model.setTitleModel(postTitle.getText().toString());
                    model.setCategoryModel(postCategory.getText().toString());
                    model.setTextModel(text);
//                    model.setImageUrlModel(getImagePath(imageUrl));
                    databaseHelper.getDataDao().insert(model);
                    saveFavBtn.setBackgroundResource(R.drawable.ic_fav_checked_24);
                }
            });

        }
    }

    public void  addNewItem(List<Result> results){
        this.results = results;
        notifyDataSetChanged();
    }
}
