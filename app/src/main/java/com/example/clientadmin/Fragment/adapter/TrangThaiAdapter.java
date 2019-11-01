package com.example.clientadmin.Fragment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clientadmin.Fragment.Object.Cart;
import com.example.clientadmin.Model.Product;

import com.example.clientadmin.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class TrangThaiAdapter extends RecyclerView.Adapter<TrangThaiAdapter.TrangThaiViewHolder> {
    private List<Cart> data = new ArrayList<>();
    private Context context;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference mData = database.getReference("Cart");


    public TrangThaiAdapter(List<Cart> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public TrangThaiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_trangthai, parent, false);
        return new TrangThaiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrangThaiViewHolder holder, int position) {
        holder.txtMaDH.setText(data.get(position).getId_DonHang());
        holder.txtNgay.setText(data.get(position).getDate());
        if (holder.cbXacNhanDH.isChecked()){
            mData.child("check").setValue("Ok");
        }

        if (holder.cbGiaoHang.isChecked()){
            mData.child("delivery").setValue("Ok");
        }

        if (holder.cbXacNhanDH.isChecked()){
            mData.child("finish").setValue("Ok");
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class TrangThaiViewHolder extends RecyclerView.ViewHolder {
        TextView txtMaDH, txtNgay;
        CheckBox cbXacNhanDH, cbGiaoHang, cbHoanThanh;

        public TrangThaiViewHolder (View itemView) {
            super(itemView);
            txtMaDH = itemView.findViewById(R.id.txtMaDH);
            txtNgay = itemView.findViewById(R.id.txtNgay);
            cbXacNhanDH = itemView.findViewById(R.id.cbXacNhanDH);
            cbGiaoHang = itemView.findViewById(R.id.cbGiaoHang);
            cbHoanThanh = itemView.findViewById(R.id.cbHoanThanh);
        }
    }


}
