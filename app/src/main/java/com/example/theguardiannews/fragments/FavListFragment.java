package com.example.theguardiannews.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.theguardiannews.R;
import com.example.theguardiannews.adapters.NewsListAdapter;


public class FavListFragment extends Fragment {

    private NewsListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fav_list, container, false);
        init();
        initRecycler();
        return view;
    }

    private void init() {
    }

    private void initRecycler() {
    }
}