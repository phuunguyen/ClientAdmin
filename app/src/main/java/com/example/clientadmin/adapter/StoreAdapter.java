package com.example.clientadmin.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.clientadmin.Object.Store;
import com.example.clientadmin.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.StoreViewHolder> {
    private Context context;
    private List<Store> data = new ArrayList<>();



    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    public StoreAdapter(List<Store> data, Context context) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public StoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_view_item_admin, parent, false);

        return new StoreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StoreViewHolder holder, final int position) {
        Picasso.get().load(data.get(position).getImage()).into(holder.img);
        holder.txtNameAdminStore.setText(data.get(position).getStore_Name());
        holder.txtAdminAddress.setText(data.get(position).getAddress());
        holder.txtAdminRating.setText(data.get(position).getRating() + "");


//
        final String idStore = data.get(position).getId_Store();
        final String storeName = data.get(position).getStore_Name();
//        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                xoaStoreAlertDialog(idStore,storeName,position);
//            }
//        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xoaStoreAlertDialog(storeName,idStore,position);
            }
        });




    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class StoreViewHolder extends RecyclerView.ViewHolder{
        ImageView img;
        Button btnDelete;
        ImageView imgDelete;
        TextView txtNameAdminStore;
        TextView txtAdminAddress;
        TextView txtAdminRating;

        public StoreViewHolder(final View itemView) {
            super(itemView);
            btnDelete = (Button)itemView.findViewById(R.id.btnDelete);
            imgDelete = (ImageView)itemView.findViewById(R.id.imgdelete);
            img = (ImageView)itemView.findViewById(R.id.imgStore);
            txtAdminAddress = (TextView)itemView.findViewById(R.id.edtAddressAdmin);
            txtNameAdminStore = (TextView)itemView.findViewById(R.id.edtNameStore);
            txtAdminRating = (TextView)itemView.findViewById(R.id.ratingAdmin);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null)
                        listener.onItemClick(itemView, getLayoutPosition());
                }
            });



        }
    }

    private static OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void xoaStoreAlertDialog(String storeName, final String idStore, final int position) {
        new MaterialAlertDialogBuilder(context)
                .setTitle("Xóa cửa hàng")
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
                        myRef.child("Store").child(idStore).removeValue();
                        remove(position);
                        Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                    }
                }).show();
    }

    private void remove(int postion) {
        data.remove(postion);
        notifyItemRemoved(postion);
    }




}
