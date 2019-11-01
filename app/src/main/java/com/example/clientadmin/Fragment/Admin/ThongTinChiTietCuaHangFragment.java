package com.example.clientadmin.Fragment.Admin;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.clientadmin.DrawerLocker;
import com.example.clientadmin.Model.Product;
import com.example.clientadmin.Object.Store;
import com.example.clientadmin.R;
import com.example.clientadmin.adapter.CuaHangAdapter;
import com.example.clientadmin.adapter.ProductAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class ThongTinChiTietCuaHangFragment extends Fragment {
    View root;
    TextView txtTenCH, txtDiachi, txtTenChuSH, txtTenDN, txtSoDT, txtNgayDK, txtTongDH;
    EditText edtTenCH, edtDiaChi, edtTenChuSH, edtTenDN, edtSoDT, edtNgayDK, edtTongDH;
    ImageView imgStore;
    ImageButton imgVEdit, imgVSave, imgDelete, imgchoosephoto;
    DatabaseReference mData = FirebaseDatabase.getInstance().getReference();
    DatabaseReference Table_Store = mData.child("Store");

    Store store = new Store();
    int REQUEST_CHOOSE_PHOTO = 1;
    private StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();
    SharedPreferences sharedPreferences;
    String idLogin = "";

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Activity a = getActivity();
        if (a != null) {
            a.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        showStore();
        setEvent();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_thong_tin_chi_tiet_cua_hang, container, false);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        ((DrawerLocker) getActivity()).setDrawerLocked(true);

        setControl();

        return root;
    }

    private void setControl() {

        txtTenCH = (TextView) root.findViewById(R.id.txttencuahang);
        txtDiachi = (TextView) root.findViewById(R.id.txtdiachi);
        txtTenChuSH = (TextView) root.findViewById(R.id.txttenchuho);
        txtTenDN = (TextView) root.findViewById(R.id.txttendn);
        txtSoDT = (TextView) root.findViewById(R.id.txtsdt);
        txtNgayDK = (TextView) root.findViewById(R.id.txtngaydk);

        edtTenCH = (EditText) root.findViewById(R.id.edttencuahang);
        edtDiaChi = (EditText) root.findViewById(R.id.edtdiachi);
        edtTenChuSH = (EditText) root.findViewById(R.id.edttenchuho);
        edtTenDN = (EditText) root.findViewById(R.id.edtTenDN);
        edtSoDT = (EditText) root.findViewById(R.id.edtsdt);
        edtNgayDK = (EditText) root.findViewById(R.id.edtngaydk);

        imgStore = (ImageView) root.findViewById(R.id.imgStore);
        imgVEdit = (ImageButton) root.findViewById(R.id.imgedit);
        imgVSave = (ImageButton) root.findViewById(R.id.imgsave);
        imgDelete = (ImageButton) root.findViewById(R.id.imgdelete);
        imgchoosephoto = (ImageButton) root.findViewById(R.id.imgaddphoto);

    }

    private void setEvent() {

        imgVEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgVEdit.setVisibility(View.GONE);
                imgVSave.setVisibility(View.VISIBLE);
                imgchoosephoto.setVisibility(View.VISIBLE);


                txtTenCH.setVisibility(View.GONE);
                txtDiachi.setVisibility(View.GONE);
                txtTenChuSH.setVisibility(View.GONE);
                txtTenDN.setVisibility(View.GONE);
                txtSoDT.setVisibility(View.GONE);
                txtNgayDK.setVisibility(View.GONE);
//                txtTongDH.setVisibility(View.GONE);

                edtTenCH.setVisibility(View.VISIBLE);
                edtDiaChi.setVisibility(View.VISIBLE);
                edtTenChuSH.setVisibility(View.VISIBLE);
                edtTenDN.setVisibility(View.VISIBLE);
                edtSoDT.setVisibility(View.VISIBLE);
                edtNgayDK.setVisibility(View.VISIBLE);
                //    edtTongDH.setVisibility(View.VISIBLE);


            imgchoosephoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    choosePhoto();
                }
            });

                Table_Store.child(idLogin).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        edtTenCH.setText(dataSnapshot.child("store_Name").getValue().toString());
                        edtDiaChi.setText(dataSnapshot.child("address").getValue().toString());
                        edtSoDT.setText(dataSnapshot.child("phone").getValue().toString());
                        edtTenChuSH.setText(dataSnapshot.child("bossName").getValue().toString());
                        edtTenDN.setText(dataSnapshot.child("userName").getValue().toString());
                        txtNgayDK.setText(dataSnapshot.child("registerDay").getValue().toString());


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });



            }
        });

        imgVSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgVEdit.setVisibility(View.VISIBLE);
                imgVSave.setVisibility(View.GONE);
                imgchoosephoto.setVisibility(View.GONE);

                txtTenCH.setVisibility(View.VISIBLE);
                txtDiachi.setVisibility(View.VISIBLE);
                txtTenChuSH.setVisibility(View.VISIBLE);
                txtTenDN.setVisibility(View.VISIBLE);
                txtSoDT.setVisibility(View.VISIBLE);
                txtNgayDK.setVisibility(View.VISIBLE);
//                txtTongDH.setVisibility(View.GONE);

                edtTenCH.setVisibility(View.GONE);
                edtDiaChi.setVisibility(View.GONE);
                edtTenChuSH.setVisibility(View.GONE);
                edtTenDN.setVisibility(View.GONE);
                edtSoDT.setVisibility(View.GONE);
                edtNgayDK.setVisibility(View.GONE);
                //    edtTongDH.setVisibility(View.VISIBLE);

                mData.child("Store").child(idLogin).child("store_Name").setValue(edtTenCH.getText().toString());
                mData.child("Store").child(idLogin).child("address").setValue(edtDiaChi.getText().toString());
                mData.child("Store").child(idLogin).child("phone").setValue(edtSoDT.getText().toString());
                mData.child("Store").child(idLogin).child("bossName").setValue(edtTenChuSH.getText().toString());
                mData.child("Store").child(idLogin).child("userName").setValue(edtTenDN.getText().toString());
                mData.child("Store").child(idLogin).child("registerDay").setValue(edtNgayDK.getText().toString());
            }
        });


    }

    public void showStore() {
        sharedPreferences = getContext().getSharedPreferences("SHARED_PREFERENCES_LOGIN",
                Context.MODE_PRIVATE);
        idLogin = sharedPreferences.getString("ID_Login", "");
        Table_Store.child(idLogin).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    txtTenCH.setText(dataSnapshot.child("store_Name").getValue().toString());
                    txtDiachi.setText(dataSnapshot.child("address").getValue().toString());
                    txtSoDT.setText(dataSnapshot.child("phone").getValue().toString());
                    txtTenChuSH.setText(dataSnapshot.child("bossName").getValue().toString());
                    txtTenDN.setText(dataSnapshot.child("userName").getValue().toString());
                    txtNgayDK.setText(dataSnapshot.child("registerDay").getValue().toString());
                    if (dataSnapshot.child("image").getValue() == null) {
                        //Toast.makeText(ThongTinUserActivity.this, "Khong co du lieu", Toast.LENGTH_SHORT).show();
                    } else {
//                        Picasso.get().load(dataSnapshot.child("image").getValue().toString()).into(imgStore);

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    public void choosePhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CHOOSE_PHOTO);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            try {
                Uri imgeUri = data.getData();
                InputStream is = getActivity().getContentResolver().openInputStream(imgeUri);
                Bitmap bitmap = BitmapFactory.decodeStream(is);
                imgStore.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(getActivity(), "error", Toast.LENGTH_LONG).show();
            }


        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ((DrawerLocker) getActivity()).setDrawerLocked(false);
    }

}
