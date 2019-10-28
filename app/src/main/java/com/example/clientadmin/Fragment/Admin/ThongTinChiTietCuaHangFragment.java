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
public class ThongTinChiTietCuaHangFragment extends Fragment {


    public ThongTinChiTietCuaHangFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        return inflater.inflate(R.layout.fragment_thong_tin_chi_tiet_cua_hang, container, false);
    }

}
