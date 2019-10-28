package com.example.clientadmin.Fragment.Admin;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.clientadmin.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LichSuGiaoDichFragment extends Fragment {


    public LichSuGiaoDichFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        return inflater.inflate(R.layout.fragment_lich_su_giao_dich, container, false);
    }

}
