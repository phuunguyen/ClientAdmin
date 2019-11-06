package com.example.clientadmin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.clientadmin.Model.Store;
import com.example.clientadmin.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
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
        View view = inflater.inflate(R.layout.recyclerview_liststore_admin, parent, false);
        return new StoreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StoreViewHolder holder, int position) {
        Picasso.get().load(data.get(position).getImage()).into(holder.img);
        holder.txtNameAdminStore.setText(data.get(position).getNameStore());
        holder.txtAdminAddress.setText(data.get(position).getAddress());
        holder.txtAdminRating.setText(data.get(position).getRating() + "");
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class StoreViewHolder extends RecyclerView.ViewHolder{
        ImageView img;
        TextView txtNameAdminStore;
        TextView txtAdminAddress;
        TextView txtAdminRating;

        public StoreViewHolder(View itemView) {
            super(itemView);
            img = (ImageView)itemView.findViewById(R.id.imgStore);
            txtAdminAddress = (TextView)itemView.findViewById(R.id.edtAddressAdmin);
            txtNameAdminStore = (TextView)itemView.findViewById(R.id.edtNameStore);
            txtAdminRating = (TextView)itemView.findViewById(R.id.ratingAdmin);
        }
    }

}
