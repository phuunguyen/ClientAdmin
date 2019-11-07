package com.example.clientadmin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clientadmin.Object.Cart;
import com.example.clientadmin.Model.Product;

import com.example.clientadmin.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
    public void onBindViewHolder(@NonNull final TrangThaiViewHolder holder, final int position) {
        holder.txtMaDH.setText(data.get(position).getId_DonHang());
        holder.txtNgay.setText(data.get(position).getDate());

        holder.cbXacNhanDH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.cbXacNhanDH.setBackgroundResource(R.drawable.hlcheck);
                mData.child(data.get(position).getId_DonHang()).child("check").setValue("yes");
            }
        });

        //load du lieu va set dieu kien de? click
        mData.child(data.get(position).getId_DonHang()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    if (dataSnapshot.child("check").getValue().toString().equals("yes")) {
                        holder.cbXacNhanDH.setBackgroundResource(R.drawable.hlcheck);
                        holder.cbGiaoHang.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                holder.cbGiaoHang.setBackgroundResource(R.drawable.hlcheck);
                                mData.child(data.get(position).getId_DonHang()).child("delivery").setValue("yes");
                            }
                        });
                    }

                    if (dataSnapshot.child("delivery").getValue().toString().equals("yes")) {
                        holder.cbGiaoHang.setBackgroundResource(R.drawable.hlcheck);
                        holder.cbHoanThanh.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                holder.cbHoanThanh.setBackgroundResource(R.drawable.hlcheck);
                                mData.child(data.get(position).getId_DonHang()).child("finish").setValue("yes");
                            }
                        });
                    }

                    if (dataSnapshot.child("finish").getValue().toString().equals("yes")) {
                        holder.cbHoanThanh.setBackgroundResource(R.drawable.hlcheck);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });









    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class TrangThaiViewHolder extends RecyclerView.ViewHolder {
        TextView txtMaDH, txtNgay;
        ImageButton cbXacNhanDH, cbGiaoHang, cbHoanThanh;
        ;

        public TrangThaiViewHolder(View itemView) {
            super(itemView);
            txtMaDH = itemView.findViewById(R.id.txtMaDH);
            txtNgay = itemView.findViewById(R.id.txtNgay);
            cbXacNhanDH = itemView.findViewById(R.id.cbXacNhanDH);
            cbGiaoHang = itemView.findViewById(R.id.cbGiaoHang);
            cbHoanThanh = itemView.findViewById(R.id.cbHoanThanh);
        }
    }


}
