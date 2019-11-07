package com.example.clientadmin.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.clientadmin.Object.Cart;
import com.example.clientadmin.R;

import java.util.ArrayList;

public class DonHangAdapter extends ArrayAdapter<Cart> {
    Context context;
    int layoutID;
    ArrayList<Cart> data = null;

    public DonHangAdapter(Context context, int layoutID, ArrayList<Cart> data) {
        super(context, layoutID, data);
        this.context = context;
        this.layoutID = layoutID;
        this.data = data;
    }

    static  class DonHangHolder{
        TextView txtMaDonHang, txtNgayTao;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = convertView;
        DonHangHolder donHangHolder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutID, parent, false);

            donHangHolder = new DonHangHolder();
            donHangHolder.txtMaDonHang = row.findViewById(R.id.txtMaDonHang);
            donHangHolder.txtNgayTao = row.findViewById(R.id.txtNgayTao);

            row.setTag(donHangHolder);
        }
        else
        {
            donHangHolder = (DonHangHolder)row.getTag();
        }

        Cart cart = data.get(position);
        donHangHolder.txtMaDonHang.setText(cart.getId_DonHang());
        donHangHolder.txtNgayTao.setText(cart.getDate());
        return row;
    }
}
