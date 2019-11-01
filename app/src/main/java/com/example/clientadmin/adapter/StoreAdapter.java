package com.example.clientadmin.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.clientadmin.Model.Store;
import com.example.clientadmin.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class StoreAdapter extends ArrayAdapter<Store> {
    Context context;
    int layoutResource;
    ArrayList<Store> data = null;

    public StoreAdapter(Context context, int layoutResource, ArrayList<Store> data) {
        super(context, layoutResource, data);
        this.context = context;
        this.layoutResource = layoutResource;
        this.data = data;
    }

    static class StoreHolder{
        ImageView imgV;
        TextView nameStore, addressStore, ratingStore;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
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

            row.setTag(holder);
        }else{
            holder = (StoreHolder)row.getTag();
        }

        Store item = data.get(position);
        Picasso.get().load(item.getImage()).into(holder.imgV);
        holder.nameStore.setText(item.getNameStore());
        holder.addressStore.setText(item.getAddress());
        holder.ratingStore.setText(String.valueOf(item.getRating()));

        return row;
    }
}
