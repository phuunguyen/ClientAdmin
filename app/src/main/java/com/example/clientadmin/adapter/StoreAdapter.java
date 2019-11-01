package com.example.clientadmin.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.clientadmin.Model.Store;
import com.example.clientadmin.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class StoreAdapter extends ArrayAdapter<Store> {
    Context context;
    int layoutResource;
    ArrayList<Store> data = null;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    public StoreAdapter(Context context, int layoutResource, ArrayList<Store> data) {
        super(context, layoutResource, data);
        this.context = context;
        this.layoutResource = layoutResource;
        this.data = data;
    }

    static class StoreHolder{
        ImageView imgV;
        TextView nameStore, addressStore, ratingStore;
        ImageButton imgDelete;
    }


    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = convertView;
        StoreHolder holder = null;
        if (row == null){
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResource, parent, false);

            holder = new StoreHolder();
            holder.imgV = (ImageView)row.findViewById(R.id.imgStore);
            holder.nameStore = (TextView)row.findViewById(R.id.edtNameStore);
            holder.addressStore = (TextView)row.findViewById(R.id.edtAddressAdmin);
            holder.ratingStore = (TextView)row.findViewById(R.id.ratingAdmin);

            holder.imgDelete = (ImageButton) row.findViewById(R.id.imgdelete);

            row.setTag(holder);
        }else{
            holder = (StoreHolder)row.getTag();

        }

        Store item = data.get(position);
        Picasso.get().load(item.getImage()).into(holder.imgV);
        holder.nameStore.setText(item.getNameStore());
        holder.addressStore.setText(item.getAddress());
        holder.ratingStore.setText(String.valueOf(item.getRating()));


//        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                final String idStore = data.get(position).getIdStore();
//                final String nameStore = data.get(position).getNameStore();
//                xoaStoreAlertDialog(idStore,nameStore,position);
//            }
//        });
        return row;
    }





    public void xoaStoreAlertDialog(String storeName, final String idStore, final int position) {
        new MaterialAlertDialogBuilder(context)
                .setTitle("Xóa Cửa Hàng")
                .setMessage("Bạn có muốn xóa " + storeName + " không?")
                .setCancelable(false)
                .setPositiveButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setNegativeButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        myRef.child("Store").child("Store" + idStore).removeValue();
                        remove(position);
                        Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                    }
                }).show();
    }

    private void remove(int postion) {
        data.remove(postion);
    }
}