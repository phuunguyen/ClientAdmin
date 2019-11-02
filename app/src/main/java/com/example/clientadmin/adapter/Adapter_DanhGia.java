package com.example.clientadmin.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.clientadmin.R;
import com.example.clientadmin.Object.Rating;

import java.util.ArrayList;

public class Adapter_DanhGia extends ArrayAdapter<Rating> {
    Context context;
    int layoutID;
    ArrayList<Rating> data = null;

    public Adapter_DanhGia(@NonNull Context context, int layoutID, ArrayList<Rating> data) {
        super(context, layoutID, data);
        this.context = context;
        this.layoutID = layoutID;
        this.data = data;
    }

    static class Adapter_DanhGia_Holder{
        TextView txtNameUser, txtCMTUser;
        RatingBar rtbDanhGia;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = convertView;
        Adapter_DanhGia_Holder adapter_danhGia_holder = null;
        if(row == null){
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutID,parent, false);
            adapter_danhGia_holder = new Adapter_DanhGia_Holder();
            adapter_danhGia_holder.txtNameUser = row.findViewById(R.id.txtTenUserDG);
            adapter_danhGia_holder.txtCMTUser = row.findViewById(R.id.txtDanhGia);
            adapter_danhGia_holder.rtbDanhGia = row.findViewById(R.id.rtbDanhGia);
            row.setTag(adapter_danhGia_holder);
        }else{
            adapter_danhGia_holder = (Adapter_DanhGia_Holder) row.getTag();
        }

        Rating item = data.get(position);
        adapter_danhGia_holder.txtNameUser.setText(item.getId_User());
        adapter_danhGia_holder.txtCMTUser.setText(item.getComment());
        adapter_danhGia_holder.rtbDanhGia.setRating(item.getRating());
        return row;

    }
}
