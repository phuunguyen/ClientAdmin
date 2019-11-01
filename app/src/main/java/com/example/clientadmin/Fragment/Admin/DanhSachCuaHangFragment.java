package com.example.clientadmin.Fragment.Admin;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.clientadmin.Model.Product;
import com.example.clientadmin.Model.Store;
import com.example.clientadmin.R;
import com.example.clientadmin.adapter.StoreAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DanhSachCuaHangFragment extends Fragment {

    TextView tvNameStore, tvAdress, rating;
    ImageView img;
    ListView listView;
    ArrayList<Store> data = new ArrayList<>();
    StoreAdapter storeAdapter;
    View view;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference mData = database.getReference();
    SharedPreferences sharedPreferences;
    String idLogin = "";



    public DanhSachCuaHangFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setEvent();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_danh_sach_cua_hang, container, false);
        setControl();
        return view;
    }

    public void setControl(){
//        tvNameStore = (TextView)view.findViewById(R.id.edtNameStore);
//        tvAdress = (TextView)view.findViewById(R.id.edtAddressAdmin);
//        rating = (TextView)view.findViewById(R.id.ratingAdmin);
//        img = (ImageView)view.findViewById(R.id.imgStore);
        listView = (ListView)view.findViewById(R.id.lvDSCH);
        sharedPreferences = getContext().getSharedPreferences("SHARED_PREFERENCES_LOGIN",
                Context.MODE_PRIVATE);
    }


    public void setEvent(){
        final String idStore = sharedPreferences.getString("ID_Store", null);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        storeAdapter = new StoreAdapter(getContext(),R.layout.list_view_item_admin, data);
        listView.setAdapter(storeAdapter);
        loadData();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Navigation.findNavController(view).navigate(R.id.action_danhSachCuaHangFragment_to_thongTinChiTietCuaHangFragment);
                
            }
        });
    }

    public void loadData(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("SHARED_PREFERENCES_IDSTORE", Context.MODE_PRIVATE);
        final String idStore = sharedPreferences.getString("IDSTORE", null);
        mData.child("Store").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Store store = new Store();
                store.setIdStore(dataSnapshot.child("id_Store").getValue().toString());
                store.setImage(dataSnapshot.child("image").getValue().toString());
                store.setNameStore(dataSnapshot.child("store_Name").getValue().toString());
                store.setAddress(dataSnapshot.child("address").getValue().toString());
                store.setRating((double) Math.round(Double.parseDouble(dataSnapshot.child("rating").getValue().toString()) * 10) / 10);
                data.add(store);
                storeAdapter.notifyDataSetChanged();


                storeAdapter.notifyDataSetChanged();
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
