package com.example.clientadmin.Fragment.Store.product;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.clientadmin.R;
import com.example.clientadmin.adapter.ProductAdapter;
import com.example.clientadmin.database.Product;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllProductFragment extends Fragment {
    RecyclerView mRecyclerView;
    ProductAdapter productAdapter;
    List<Product> data;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference mData = database.getReference();

    View root;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_all_product, container, false);
        setControl();
        return root;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setEvent();
    }

    private void setControl() {
        mRecyclerView = (RecyclerView) root.findViewById(R.id.recyclerView_AllProduct);
    }

    private void setEvent() {
        data = new ArrayList<>();
        mData.child("Product").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                addProduct(dataSnapshot);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                data.clear();
                addProduct(dataSnapshot);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                data.clear();
                addProduct(dataSnapshot);
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
//        khoiTao();
        productAdapter = new ProductAdapter(data, getContext());
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(productAdapter);
    }

    private void addProduct(DataSnapshot dataSnapshot){
        Product product = new Product();
        product.setId_product(dataSnapshot.child("id_product").getValue().toString());
        product.setProduct_image(dataSnapshot.child("product_image").getValue().toString());
        product.setProduct_name(dataSnapshot.child("product_name").getValue().toString());
        product.setPrice(Double.parseDouble(dataSnapshot.child("price").getValue().toString()));
        data.add(product);
        productAdapter.notifyDataSetChanged();
    }
}
