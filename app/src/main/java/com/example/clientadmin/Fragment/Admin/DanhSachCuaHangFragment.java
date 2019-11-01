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
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
    RecyclerView recyclerView;
    List<Store> data = new ArrayList<>();
    AutoCompleteTextView edtSearch;
    StoreAdapter storeAdapter;
    View view;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference mData = database.getReference();
    SharedPreferences sharedPreferences;

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
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_AllProduct);
        edtSearch = (AutoCompleteTextView)view.findViewById(R.id.searchBox);
        sharedPreferences = getContext().getSharedPreferences("SHARED_PREFERENCES_LOGIN",
                Context.MODE_PRIVATE);
    }


    public void setEvent(){
        storeAdapter = new StoreAdapter(data, getContext());
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(storeAdapter);

        loadData();
    }

    public void loadData(){
        //final String idStore = sharedPreferences.getString("ID_Login", null);
        mData.child("Store").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Store store = new Store();
                store.setIdStore(dataSnapshot.child("id_Store").getValue().toString());
                store.setImage(dataSnapshot.child("image").getValue().toString());
                store.setNameStore(dataSnapshot.child("store_Name").getValue().toString());
                store.setAddress(dataSnapshot.child("address").getValue().toString());
                store.setRating(Double.parseDouble(dataSnapshot.child("rating").getValue().toString()));

                data.add(store);
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

    public void loadDataSearch(final String storeName){


        mData.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                final Store store = new Store();
                if (dataSnapshot.child("store_Name").getValue().toString().equals(storeName)) {
                    store.setIdStore(dataSnapshot.child("id_Store").getValue().toString());
                    store.setImage(dataSnapshot.child("image").getValue().toString());
                    store.setNameStore(dataSnapshot.child("store_Name").getValue().toString());
                    store.setAddress(dataSnapshot.child("address").getValue().toString());
                    store.setRating((double) Math.round(Double.parseDouble(dataSnapshot.child("rating").getValue().toString()) * 10) / 10);
                    data.add(store);
                }
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

    boolean shouldAllowBack = false;
    public void onBackPressed(){
        if (!shouldAllowBack){

        }else {
            onBackPressed();
        }
    }
}
