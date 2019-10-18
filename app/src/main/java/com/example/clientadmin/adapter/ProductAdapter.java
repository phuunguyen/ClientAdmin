package com.example.clientadmin.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clientadmin.R;
import com.example.clientadmin.database.Product;
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
        holder.btnDeleteProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idProduct = data.get(position).getId_product();
                xoaSanPhamAlertDialog(data.get(position).getProduct_name(), idProduct);
                //myRef.child("Product").child("Product" + idProduct).removeValue();
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
        Button btnDeleteProduct;

        public ProductViewHolder(View itemView) {
            super(itemView);
            imgProduct = (ImageView) itemView.findViewById(R.id.imgProduct);
            txtProductName = (TextView) itemView.findViewById(R.id.txtProductName);
            txtProductPrice = (TextView) itemView.findViewById(R.id.txtProductPrice);
            btnDeleteProduct = (Button) itemView.findViewById(R.id.btnDelete);
        }
    }

    public void xoaSanPhamAlertDialog(String productName, final String idProduct){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Xóa sản phẩm");
        builder.setMessage("Bạn có muốn xóa " + productName + " không?");
        builder.setCancelable(false);
        builder.setPositiveButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.setNegativeButton("Xóa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                myRef.child("Product").child("Product" + idProduct).removeValue();
                Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
