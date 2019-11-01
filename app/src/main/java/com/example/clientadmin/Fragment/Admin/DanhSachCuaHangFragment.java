package com.example.clientadmin.Fragment.Admin;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.clientadmin.Model.CuaHang;
import com.example.clientadmin.R;
import com.example.clientadmin.adapter.CuaHangAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import static com.example.clientadmin.R.*;

/**
 * A simple {@link Fragment} subclass.
 */
public class DanhSachCuaHangFragment extends Fragment {

    View root;
    ListView lvDSCH;

    ArrayList<CuaHang> data = new ArrayList<>();
    CuaHangAdapter adapter = null;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference mData;

    private ArrayList<String> listStoreName = new ArrayList<>();
    private ArrayAdapter adapterStoreName;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setControl();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(layout.fragment_danh_sach_cua_hang, container, false);

        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        mData = database.getReference();
        
        setEvent();

        return root;

    }

    private void setEvent() {
        adapter = new CuaHangAdapter(getContext(),R.layout.listview_item_cuahang,data);
       // lvDSCH.setAdapter(adapter);
        loadNameStore();
        loadData();

//        lvDSCH.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Navigation.findNavController(view).navigate(R.id.action_danhSachCuaHangFragment_to_thongTinChiTietCuaHangFragment);
//            }
//        });
    }

    private void setControl() {
        lvDSCH = root.findViewById(id.lvDSCH);
    }

    private void loadData() {
        mData.child("Store").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull final DataSnapshot dataSnapshot, @Nullable String s) {
                final CuaHang store = new CuaHang();
                store.setIdStore(dataSnapshot.child("id_Store").getValue().toString());
                store.setImageCuaHang(dataSnapshot.child("image").getValue().toString());
                store.setShopName(dataSnapshot.child("store_Name").getValue().toString());
                store.setShopAddress(dataSnapshot.child("address").getValue().toString());
                data.add(store);
                adapter.notifyDataSetChanged();
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

    private void loadNameStore() {
        mData.child("Store").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                listStoreName.add(dataSnapshot.child("store_Name").getValue().toString());
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


}
