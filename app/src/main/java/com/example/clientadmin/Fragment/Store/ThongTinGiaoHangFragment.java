package com.example.clientadmin.Fragment.Store;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.clientadmin.Fragment.Object.Cart;
import com.example.clientadmin.Fragment.adapter.TrangThaiAdapter;

import com.example.clientadmin.R;

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
public class ThongTinGiaoHangFragment extends Fragment {
    RecyclerView recyclerView;
    TrangThaiAdapter adapter;

    List<Cart> data = new ArrayList<>();

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference mData = database.getReference("Cart");

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_thong_tin_giao_hang, container, false);
        setControl();
        return view;
    }

    private void setControl() {
        recyclerView = view.findViewById(R.id.rclTrangThai);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setEvent();
    }

    private void setEvent() {

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("SHARED_PREFERENCES_LOGIN",
                Context.MODE_PRIVATE);
        final String id_Store = sharedPreferences.getString("ID_Login", "");
        mData.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    if (id_Store.equals(dataSnapshot.child("id_store").getValue())) {
                        Cart cart = new Cart();
                        cart.setId_DonHang(dataSnapshot.child("id_donhang").getValue().toString());
                        cart.setDate(dataSnapshot.child("ngaytao").getValue().toString());
                        data.add(cart);
                        adapter.notifyDataSetChanged();
                    }

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                try {
                    if (id_Store.equals(dataSnapshot.child("id_store").getValue())) {
                        Cart cart = new Cart();
                        cart.setId_DonHang(dataSnapshot.child("id_donhang").getValue().toString());
                        cart.setDate(dataSnapshot.child("ngaytao").getValue().toString());
                        data.add(cart);
                        adapter.notifyDataSetChanged();
                    }

                }catch (Exception e){}

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        adapter = new TrangThaiAdapter(data, getContext());
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        //layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
