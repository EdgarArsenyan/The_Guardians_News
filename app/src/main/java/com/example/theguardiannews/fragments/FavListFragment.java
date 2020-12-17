package com.example.theguardiannews.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.theguardiannews.R;
import com.example.theguardiannews.adapters.FavListAdapter;
import com.example.theguardiannews.database.UploadModel;
import com.example.theguardiannews.viewModel_repo.UploadViewModel;

import java.util.ArrayList;
import java.util.List;


public class FavListFragment extends Fragment {

    private FavListAdapter adapter;
    private RecyclerView recycler;
    private LinearLayoutManager linearLayoutManager;
    private UploadViewModel viewModel;
    private List<UploadModel> list = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fav_list, container, false);
        initRecycler(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initViewModel();
    }

    private void initRecycler(View view) {
        recycler = view.findViewById(R.id.favorite_recycler);
        adapter = new FavListAdapter(getContext(), list);
        linearLayoutManager = new LinearLayoutManager(getContext());
        recycler.setLayoutManager(linearLayoutManager);
        recycler.setAdapter(adapter);
    }

    private void initViewModel() {
        viewModel = ViewModelProviders.of(this).get(UploadViewModel.class);
        viewModel.getUploadModel().observe(getViewLifecycleOwner(), new Observer<List<UploadModel>>() {
            @Override
            public void onChanged(List<UploadModel> uploadModels) {
                adapter.addNewItem(uploadModels);
            }
        });
    }
}