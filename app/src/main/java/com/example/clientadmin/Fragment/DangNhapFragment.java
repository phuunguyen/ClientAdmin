package com.example.clientadmin.Fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.clientadmin.Fragment.Admin.DangKyCuaHangFragment;
import com.example.clientadmin.MainActivity;
import com.example.clientadmin.Object.Admin;
import com.example.clientadmin.Object.Store;
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
public class DangNhapFragment extends Fragment {

    EditText edtTaiKhoan, edtMatKhau;
    Button btnDangNhap;
    RadioButton rbquanly, rbcuahang;
    ImageView imgStore;
    View root;
    int REQUEST_CHOOSE_PHOTO = 1;

    DatabaseReference mData = FirebaseDatabase.getInstance().getReference();
    ArrayList<Store> arrStore = new ArrayList<>();
    ArrayList<Admin> arrAdmin = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_dang_nhap, container, false);


        setConTrol();


        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        setEvent();

    }
    public void choosePhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CHOOSE_PHOTO);

//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }
    private void setEvent() {
        imgStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choosePhoto();
            }
        });

        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {


                String email = edtTaiKhoan.getText().toString().trim();
                String pass = edtMatKhau.getText().toString().trim();
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(pass)) {
                    Toast.makeText(getContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }
//                if (pass.length() < 6) {
//                    Toast.makeText(getContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
//                    return;
//                }

                if (rbquanly.isChecked()) {

                    mData.child("Admin").addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                            Admin admin = dataSnapshot.getValue(Admin.class);
                            arrAdmin.add(admin);
//                        Admin admin = dataSnapshot.getValue(Admin.class);
//                        arrAdmin.add(admin);
                            //for(int i = 0; i < arrStore.size(); i++) {
                            for (int i = 0; i < arrAdmin.size(); i++) {
                                Log.d("admin", arrAdmin.get(i).getName());
                                if (arrAdmin.get(i).getName().equals(edtTaiKhoan.getText().toString())
                                        && arrAdmin.get(i).getPassword().equals(edtMatKhau.getText().toString())) {
                                    Toast.makeText(getContext(), "dang nhap thanh cong", Toast.LENGTH_LONG).show();
                                    Navigation.findNavController(view).navigate(R.id.action_dangNhapFragment_to_nav_tt_cuahang);
                                } else {
                                    Toast.makeText(getContext(), "Dang nhap khong thanh cong", Toast.LENGTH_SHORT).show();
                                }
                                break;
                            }
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
                } else if (rbcuahang.isChecked()) {
                    mData.child("Store_Account").addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                            Store store = dataSnapshot.getValue(Store.class);
                            arrStore.add(store);

                            for (int i = 0; i < arrStore.size(); i++) {
                                if (arrStore.get(i).getBossName().equals(edtTaiKhoan.getText().toString())
                                        && arrStore.get(i).getPassword().equals(edtMatKhau.getText().toString())) {
                                   // Log.d("store", arrStore.get(i).getBossName());
                                    Toast.makeText(getContext(), "dang nhap thanh cong", Toast.LENGTH_LONG).show();
                                    //Navigation.findNavController(view).navigate(R.id.action_dangNhapFragment_to_nav_tt_cuahang);
                                } else {
                                    Toast.makeText(getContext(), "Dang nhap khong thanh cong", Toast.LENGTH_SHORT).show();
                                }
                                break;

                            }
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

        });
    }

    private void setConTrol() {
        imgStore = (ImageView) root.findViewById(R.id.imgStore);
        rbcuahang = (RadioButton) root.findViewById(R.id.rbcuahang);
        rbquanly = (RadioButton) root.findViewById(R.id.rbquanly);
        edtTaiKhoan = (EditText) root.findViewById(R.id.edtName);
        edtMatKhau = (EditText) root.findViewById(R.id.edtPassword);
        btnDangNhap = (Button) root.findViewById(R.id.btnDangNhap);

    }

}
