package com.example.clientadmin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.clientadmin.Fragment.Admin.DangKyCuaHangFragment;
import com.example.clientadmin.Fragment.Admin.ThongTinChiTietCuaHangFragment;
import com.example.clientadmin.Object.Admin;
import com.example.clientadmin.Object.Store;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class DangNhapActivity extends AppCompatActivity {

    EditText edtTaiKhoan, edtMatKhau;
    Button btnDangNhap;
    RadioButton rbquanly, rbcuahang;
    ImageView imgStore;
    View root;
    int REQUEST_CHOOSE_PHOTO = 1;

    // Write a message to the database
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference mData = database.getReference();

    //DatabaseReference mData = FirebaseDatabase.getInstance().getReference();
    ArrayList<Store> arrStore = new ArrayList<>();
    ArrayList<Admin> arrAdmin = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);
        setControl();
        setEvent();
    }

    private void setControl() {
        imgStore = (ImageView) findViewById(R.id.img);
        rbcuahang = (RadioButton) findViewById(R.id.rbcuahang);
        rbquanly = (RadioButton) findViewById(R.id.rbquanly);
        edtTaiKhoan = (EditText) findViewById(R.id.edtName);
        edtMatKhau = (EditText) findViewById(R.id.edtPassword);
        btnDangNhap = (Button) findViewById(R.id.btnDangNhap);

    }

    private void setEvent() {
            LoadDataStore();

            LoadDataAdmin();



        imgStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // choosePhoto();
            }
        });

        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                String email = edtTaiKhoan.getText().toString().trim();
                String pass = edtMatKhau.getText().toString().trim();
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(pass)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
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
                            for (int i = 0; i < arrAdmin.size(); i++) {
                                Log.d("admin", arrAdmin.get(i).getName());
                                if (arrAdmin.get(i).getName().equals(edtTaiKhoan.getText().toString())
                                        && arrAdmin.get(i).getPassword().equals(edtMatKhau.getText().toString())) {

                                    Toast.makeText(getApplicationContext(), "dang nhap thanh cong", Toast.LENGTH_LONG).show();
                                    //Navigation.findNavController(view).navigate(R.id.action_dangNhapFragment_to_nav_tt_cuahang);
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(getApplicationContext(), "Dang nhap khong thanh cong", Toast.LENGTH_SHORT).show();
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
                            for (int i = 0; i < arrStore.size(); i++) {
                                if (arrStore.get(i).getBossName().equals(edtTaiKhoan.getText().toString()) && arrStore.get(i).getPassword().equals(edtMatKhau.getText().toString())) {
                                     Log.d("store", arrStore.get(i).getBossName());
                                    String idDN = arrStore.get(i).getId_Store();
                                    Intent intent = new Intent(DangNhapActivity.this, MainActivity.class);
                                    intent.putExtra("idStore", idDN);
                                    startActivity(intent);
                                    finish();
                                    break;
                                    //Navigation.findNavController(view).navigate(R.id.action_dangNhapFragment_to_nav_tt_cuahang);
                                } else {
                                    Toast.makeText(getApplicationContext(), "Dang nhap khong thanh cong", Toast.LENGTH_SHORT).show();
                                }

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
                }else {
                    Toast.makeText(getApplicationContext(), "Chon loai dang nhap", Toast.LENGTH_LONG).show();
                }
            }

        });
    }

    private void LoadDataAdmin() {
        mData.child("Admin").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Admin admin = dataSnapshot.getValue(Admin.class);
                arrAdmin.add(admin);
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

    private void LoadDataStore() {
        mData.child("Store_Account").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Store store = dataSnapshot.getValue(Store.class);
                arrStore.add(store);
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
