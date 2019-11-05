package com.example.clientadmin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.clientadmin.Model.History;
import com.example.clientadmin.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {
    private Context context;
    private List<History> data = new ArrayList<>();

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    public HistoryAdapter(List<History> data, Context context) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recyclerview_paymenthistory_admin, parent, false);
        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        Picasso.get().load(data.get(position).getProduct_image()).into(holder.img);
        holder.txtNameAdminStore.setText(data.get(position).getProduct_name());
        holder.txtAdminAddress.setText(data.get(position).getProduct_address());
        holder.txtQuantity.setText(data.get(position).getProduct_quantity() + "");
        holder.txtTotal.setText(data.get(position).getTotal_product_price() + "");
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class HistoryViewHolder extends RecyclerView.ViewHolder{
        ImageView img;
        TextView txtNameAdminStore;
        TextView txtAdminAddress;
        TextView txtQuantity;
        TextView txtTotal;

        public HistoryViewHolder(View itemView) {
            super(itemView);
            img = (ImageView)itemView.findViewById(R.id.imgHistoryStore);
            txtAdminAddress = (TextView)itemView.findViewById(R.id.HistoryAddressAdmin);
            txtNameAdminStore = (TextView)itemView.findViewById(R.id.HistoryNameStore);
            txtQuantity = (TextView)itemView.findViewById(R.id.quantity);
            txtTotal = (TextView)itemView.findViewById(R.id.totalprice);
        }
    }
}
