package com.example.theguardiannews.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.theguardiannews.R;
import com.example.theguardiannews.activities.MainActivity;
import com.example.theguardiannews.adapters.NewsListAdapter;
import com.example.theguardiannews.viewModel_repo.NewsViewModel;
import com.example.theguardiannews.models.Result;
import com.example.theguardiannews.utils.Dialogs;
import com.example.theguardiannews.utils.NetworkUtil;

import java.util.ArrayList;
import java.util.List;


public class NewsListFragment extends Fragment {

    private final List<Result> list = new ArrayList<>();
    private RecyclerView recyclerView;
    private NewsListAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private int currentItems;
    private int totalItems;
    private int scrollOutItems;
    private int position = 1;
    private boolean isScrolling = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_list, container, false);

        init(view);
        scrollRecycler();
        initViewModel(1);
        return view;
    }

    private void init(View view) {
        adapter = new NewsListAdapter(getContext(), list);
        recyclerView = view.findViewById(R.id.news_recycler);
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void scrollRecycler() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true;
                }

            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                currentItems = linearLayoutManager.getChildCount();
                totalItems = linearLayoutManager.getItemCount();
                scrollOutItems = linearLayoutManager.findFirstVisibleItemPosition();
                if (isScrolling && (currentItems + scrollOutItems == totalItems)) {
                    ++position;
                    initViewModel(position);
                    isScrolling = false;
                }
            }
        });
    }

    private void initViewModel(int offset) {
        if (NetworkUtil.isNetworkAvailable(getContext())) {
            Log.e("view Model ", "Call");
            NewsViewModel viewModel = ViewModelProviders.of(this).get(NewsViewModel.class);
            viewModel.getFromRepo();
            viewModel.getNews(offset).observe(getViewLifecycleOwner(), new Observer<List<Result>>() {
                @Override
                public void onChanged(List<Result> results) {
                    list.addAll(results);
                    adapter.addNewItem(list);
                    MainActivity.progress.setVisibility(View.GONE);
                    adapter.notifyDataSetChanged();
                }
            });
        }else{
            MainActivity.progress.setVisibility(View.GONE);
            Dialogs.openEmailDialog(getContext(), getString(R.string.dialog_not_internet));
        }
    }
}