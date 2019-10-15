package com.example.clientadmin.Fragment.Admin;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.clientadmin.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DangKyCuaHangFragment extends Fragment {


    public DangKyCuaHangFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dang_ky_cua_hang, container, false);
    }

}
