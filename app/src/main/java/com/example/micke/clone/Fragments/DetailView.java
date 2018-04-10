package com.example.micke.clone.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.example.micke.clone.R;
import com.example.micke.clone.Utils.Data;

import java.util.ArrayList;

public class DetailView extends Fragment {
    private static final String TAG = "DetailV";

    public interface viewPostListener{
        void onViewPost(ArrayList data);

        //void onItemClick(AdapterView<?> parent, View view, int position, long id);
    }
    viewPostListener mViewPostListener;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detail_view, container, false);



        return view;
    }
}
