package com.example.clientadmin.Fragment.Store;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.clientadmin.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ThongTinCuaHangFragment extends Fragment {


    public ThongTinCuaHangFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_thong_tin_cua_hang, container, false);
    }

}