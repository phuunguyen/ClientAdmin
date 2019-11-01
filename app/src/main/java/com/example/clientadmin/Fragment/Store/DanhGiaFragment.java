package com.example.clientadmin.Fragment.Store;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.clientadmin.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class DanhGiaFragment extends Fragment {

    View view;
    TextView txtRating;
    RatingBar rtDG, rtDG1;
    DatabaseReference mData = FirebaseDatabase.getInstance().getReference();
    DatabaseReference Table_Cmt = mData.child("Comment");
    DatabaseReference Table_Store = mData.child("Store");
    ListView lv;
//    ArrayList<Rating> data = new ArrayList();
//    Adapter_DanhGia adapter = null;
    SharedPreferences sharedPreferences;
    String idLogin = "";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_danh_gia, container, false);
        setControl();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        showDanhGia();
//        adapter = new Adapter_DanhGia(getActivity(), R.layout.listview_danhgia, data);
//        lv.setAdapter(adapter);
//        loadData();

    }

    public void loadData() {
        Table_Cmt.child(idLogin).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                Rating rating = new Rating();
//                rating.setId_User(dataSnapshot.child("id_User").getValue().toString());
//                rating.setComment(dataSnapshot.child("comment").getValue().toString());
//                rating.setRating(Float.parseFloat(dataSnapshot.child("rating").getValue().toString()));
//                data.add(rating);
//                adapter.notifyDataSetChanged();

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

    public void showDanhGia() {
        Table_Store.child(idLogin).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {

                    txtRating.setText("" + (int) (double) Math.round(Double.parseDouble(dataSnapshot.child("rating").getValue().toString()) * 10) / 10);
                    rtDG.setRating(Float.parseFloat(dataSnapshot.child("rating").getValue().toString()));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void setControl() {
//        txtRating = (TextView) view.findViewById(R.id.txtRating);
//        rtDG = (RatingBar) view.findViewById(R.id.rtbDG);
//        rtDG1 = (RatingBar) view.findViewById(R.id.rtbDanhGia);
//        lv = (ListView) view.findViewById(R.id.lvDG);
        sharedPreferences = getContext().getSharedPreferences("SHARED_PREFERENCES_LOGIN",
                Context.MODE_PRIVATE);
        idLogin = sharedPreferences.getString("ID_Login", "");
    }
}
