package com.example.clientadmin.Fragment.Store;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.clientadmin.R;
import com.example.clientadmin.object.Rating;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ThongTinCuaHangFragment extends Fragment {

    TextView txtTenCH, txtDC, txtSDT, txtTenChu, txtEmail, txtNDK;
    RatingBar ratingBar;
    ImageView imgA;
    View view;
    DatabaseReference mData = FirebaseDatabase.getInstance().getReference();
    DatabaseReference Table_Store = mData.child("Store");
    DatabaseReference Table_Comment = mData.child("Comment");
    ArrayList<Rating> arrRating = new ArrayList();
    SharedPreferences sharedPreferences;
    String idLogin = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_thong_tin_cua_hang, container, false);
        setControl();
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        showStore();
        ratingStore();
    }

    public void setControl() {
        imgA = (ImageView) view.findViewById(R.id.imvAvt);
        txtTenCH = (TextView) view.findViewById(R.id.txtTenCH);
        txtDC = (TextView) view.findViewById(R.id.txtDiaChi);
        txtSDT = (TextView) view.findViewById(R.id.txtSDT);
        txtTenChu = (TextView) view.findViewById(R.id.txtTenChu);
        txtEmail = (TextView) view.findViewById(R.id.txtEmail);
        txtNDK = (TextView) view.findViewById(R.id.txtNDK);
        ratingBar = (RatingBar) view.findViewById(R.id.rtB);
        sharedPreferences = getContext().getSharedPreferences("SHARED_PREFERENCES_LOGIN",
                Context.MODE_PRIVATE);
        idLogin = sharedPreferences.getString("ID_Login", "");
    }

    public void showStore() {

        Table_Store.child(idLogin).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    txtTenCH.setText(dataSnapshot.child("store_Name").getValue().toString());
                    txtDC.setText(dataSnapshot.child("address").getValue().toString());
                    txtSDT.setText(dataSnapshot.child("phone").getValue().toString());
                    txtTenChu.setText(dataSnapshot.child("bossName").getValue().toString());
                    txtEmail.setText(dataSnapshot.child("username").getValue().toString());
                    txtNDK.setText(dataSnapshot.child("registerDay").getValue().toString());
                    float rating = Float.parseFloat(dataSnapshot.child("rating").getValue().toString());
                    ratingBar.setRating(rating);
                    Picasso.get().load(dataSnapshot.child("image").getValue().toString()).into(imgA);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void ratingStore() {
        //loadRating();
//        final String idLogin = sharedPreferences.getString("ID_Login", "");
        Table_Comment.child(idLogin).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                double total = 0.0;
                double count = 0.0;
                double average = 0.0;
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    double rating = Double.parseDouble(ds.child("rating").getValue().toString());
                    total = total + rating;
                    count = count + 1;
                    average = total / count;
                }
                String s = String.valueOf(average);
                Log.d("---", s);
                //ratingBar.setRating(Float.parseFloat(String.valueOf(average[0])));
                Table_Store.child(idLogin).child("rating").setValue(average);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void loadRating() {
        Table_Comment.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Rating rating = dataSnapshot.getValue(Rating.class);
                arrRating.add(rating);
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
