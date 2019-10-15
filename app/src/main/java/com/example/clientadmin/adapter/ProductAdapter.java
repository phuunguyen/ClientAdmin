package com.example.clientadmin.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clientadmin.R;
import com.example.clientadmin.model.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private List<Product> data = new ArrayList<>();

    public ProductAdapter(List<Product> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recyclerview_product_item, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Picasso.get().load(data.get(position).getImgProduct()).into(holder.imgProduct);
        holder.txtProductName.setText(data.get(position).getTxtProductName());
        holder.txtProductPrice.setText(data.get(position).getTxtProductPrice() + " VND");
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProduct;
        TextView txtProductName;
        TextView txtProductPrice;

        public ProductViewHolder(View itemView) {
            super(itemView);
            imgProduct = (ImageView) itemView.findViewById(R.id.imgProduct);
            txtProductName = (TextView) itemView.findViewById(R.id.txtProductName);
            txtProductPrice = (TextView) itemView.findViewById(R.id.txtProductPrice);
        }
    }
}
