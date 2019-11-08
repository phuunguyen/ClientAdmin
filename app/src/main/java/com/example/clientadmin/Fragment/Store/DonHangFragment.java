package com.example.clientadmin.Fragment.Store;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.clientadmin.Object.Cart;
import com.example.clientadmin.adapter.DonHangAdapter;
import com.example.clientadmin.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class DonHangFragment extends Fragment {
    private ListView lvDonHang;
    ArrayList<Cart> data = new ArrayList<>();
    DonHangAdapter adapter = null;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference mData = database.getReference("Cart");

    View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_don_hang, container, false);
        setControl();
        return view;
    }

    private void setControl() {
        lvDonHang = view.findViewById(R.id.lvDonHang);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setEvent();
    }

    private void setEvent() {
        lvDonHang.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                showDH(data.get(i).getId_DonHang(), data.get(i).getDate(), data.get(i).getId_User(), data.get(i).getTotal_price());
            }
        });
        adapter = new DonHangAdapter(getContext(), R.layout.list_donhang, data);
        lvDonHang.setAdapter(adapter);
        loadData();

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void loadData (){
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("SHARED_PREFERENCES_LOGIN",
                Context.MODE_PRIVATE);
        final String id_Store = sharedPreferences.getString("ID_Login", "");
        mData.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if (id_Store.equals(dataSnapshot.child("id_store").getValue())) {
                    Cart cart = new Cart();
                   // try {
                        if (dataSnapshot.child("finish").getValue().toString().equals("yes")) {
                            cart.setId_DonHang("Mã đơn hàng: " + dataSnapshot.child("id_donhang").getValue().toString());
                            cart.setDate(dataSnapshot.child("ngaytao").getValue().toString());
                            cart.setId_User(dataSnapshot.child("id_user").getValue().toString());
                            cart.setTotal_price(dataSnapshot.child("total").getValue().toString());
                            data.add(cart);
                            adapter.notifyDataSetChanged();
                        }
                  //  }catch (Exception e){}
                }
                //t nho t commit r ma` ta
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

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
    }

    public void showDH(String maDH, String ngayTao, String idUser, String total) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("***********  Thông Tin Đơn Hàng  ***********");
        builder.setMessage(maDH + "\n" + "Người đặt: " + idUser + "\n" + "Thời gian: " + ngayTao + "\n" + "Giá: " + total);

        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


}
