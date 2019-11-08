package com.example.clientadmin.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clientadmin.R;

import com.example.clientadmin.Model.Product;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private List<Product> data = new ArrayList<>();
    private Context context;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    public ProductAdapter(List<Product> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recyclerview_product_item, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, final int position) {
        Picasso.get().load(data.get(position).getProduct_image()).into(holder.imgProduct);
        holder.txtProductName.setText(data.get(position).getProduct_name());
        holder.txtProductPrice.setText(data.get(position).getPrice() + " VND");

        final String idProduct = data.get(position).getId_product();
        final String productName = data.get(position).getProduct_name();
        holder.btnDeleteProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xoaSanPhamAlertDialog(productName, idProduct, position);
            }
        });

        holder.btnEditProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = context.getSharedPreferences("SHARED_PREFERENCES_PRODUCT", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("IDPRODUCT", idProduct).apply();
                Navigation.findNavController(v).navigate(R.id.action_nav_tt_sanpham_to_capNhatSanPhamFragment);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProduct;
        TextView txtProductName;
        TextView txtProductPrice;
        MaterialButton btnDeleteProduct;
        MaterialButton btnEditProduct;

        public ProductViewHolder(View itemView) {
            super(itemView);
            imgProduct = (ImageView) itemView.findViewById(R.id.imgProduct);
            txtProductName = (TextView) itemView.findViewById(R.id.txtProductName);
            txtProductPrice = (TextView) itemView.findViewById(R.id.txtProductPrice);
            btnDeleteProduct = (MaterialButton) itemView.findViewById(R.id.btnDelete);
            btnEditProduct = (MaterialButton) itemView.findViewById(R.id.btnEdit);
        }
    }

    public void xoaSanPhamAlertDialog(String productName, final String idProduct, final int position) {
        new MaterialAlertDialogBuilder(context)
                .setTitle("Xóa sản phẩm")
                .setMessage("Bạn có muốn xóa " + productName + " không?")
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
                        myRef.child("Product").child("Product" + idProduct).removeValue();
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
