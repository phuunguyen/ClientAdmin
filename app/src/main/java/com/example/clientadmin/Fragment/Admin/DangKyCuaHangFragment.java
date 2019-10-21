package com.example.clientadmin.Fragment.Admin;


import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.clientadmin.Object.Store;
import com.example.clientadmin.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class DangKyCuaHangFragment extends Fragment {

    private static final int RESULT_OK = 0;
    View root;
    EditText edtTenCH, edtDiaChi, edtEmail, edtSDT, edtTenChuSoHuu, edtNgDK, edtMatkhau, edtNhapLaiMK;
    Button btnDK;
    ImageView imgStore;
    DatabaseReference mData = FirebaseDatabase.getInstance().getReference();
    DatabaseReference Table_Store = mData.child("Store_Account");
    //Store1 store1 = new Store1();
    Store store = new Store();


    private StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();
    private final int REQUEST_CHOOSE_PHOTO = 1;
    int i;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_dang_ky_cua_hang, container, false);

        setControl();
        return root;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setEvent();
    }

    private void setControl() {
        imgStore = (ImageView) root.findViewById(R.id.imgStore);
        edtTenCH = (EditText) root.findViewById(R.id.edttencuahang);
        edtDiaChi = (EditText) root.findViewById(R.id.edtdiachi);
        edtEmail = (EditText) root.findViewById(R.id.edtemail);
        edtTenChuSoHuu = (EditText) root.findViewById(R.id.edttenchuho);
        edtSDT = (EditText) root.findViewById(R.id.edtsdt);
        edtNgDK = (EditText) root.findViewById(R.id.edtngaydk);
        btnDK = (Button) root.findViewById(R.id.btndangky);
        edtMatkhau = (EditText) root.findViewById(R.id.edtmatkhau);
        edtNhapLaiMK = (EditText) root.findViewById(R.id.edtmatkhau1);
    }

    private void setEvent() {
        imgStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choosePhoto();
            }
        });


        edtNgDK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chonngay();
            }
        });

        btnDK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mData.child("MaxID").child("MaxID_Store").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getValue() == null) {
                            i = 0;
                        } else {
                            i = Integer.parseInt(dataSnapshot.getValue().toString());
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                String tench = edtTenCH.getText().toString().trim();
                String email = edtEmail.getText().toString().trim();
                String diachi = edtDiaChi.getText().toString().trim();
                String sodt = edtSDT.getText().toString().trim();
                String tenchuho = edtTenChuSoHuu.getText().toString().trim();
                String ngaydk = edtNgDK.getText().toString().trim();
                String mk = edtMatkhau.getText().toString().trim();
                String nhaplaimk = edtNhapLaiMK.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getActivity(), "Nhap email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(diachi)) {
                    Toast.makeText(getActivity(), "Nhap dia chi", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(tench)) {
                    Toast.makeText(getActivity(), "Nhap ten cua hang", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(tenchuho)) {
                    Toast.makeText(getActivity(), "Nhap ten chu cua hang", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (sodt.length() == 0) {
                    Toast.makeText(getActivity(), "Nhap so dien thoai", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (ngaydk.length() == 0) {
                    Toast.makeText(getActivity(), "Chon ngay dang ky", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (mk.length() < 6) {
                    Toast.makeText(getActivity(), "Nhap mat khau", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (nhaplaimk.length() < 6) {
                    Toast.makeText(getActivity(), "Nhap lai mat khau", Toast.LENGTH_SHORT).show();
                    return;
                }

                i++;
                store.setId_Store("Store_Account" + i);
                store.setAddress(edtDiaChi.getText().toString());
                store.setPhone(Integer.parseInt(edtSDT.getText().toString()));
                store.setEmail(edtEmail.getText().toString());

                Calendar calendar = Calendar.getInstance();
                StorageReference mountainsRef = mStorageRef.child("Store_Account " + calendar.getTimeInMillis() + ".png");
                imgStore.setDrawingCacheEnabled(true);
                imgStore.buildDrawingCache();
                Bitmap bitmap = ((BitmapDrawable) imgStore.getDrawable()).getBitmap();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] data = baos.toByteArray();

                UploadTask uploadTask = mountainsRef.putBytes(data);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                        // ...
                        //String uri = taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();
                        Task<Uri> task = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                        task.addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String photoLink = uri.toString();
                                mData.child("Store_Account").child("Store_Account" + i).child("image").setValue(photoLink);

                            }
                        });

                    }
                });
                store.setStore_Name(edtTenCH.getText().toString());
                store.setBossName(edtTenChuSoHuu.getText().toString());
                store.setRegisterDay(edtNgDK.getText().toString());
                store.setPassword(edtMatkhau.getText().toString());


                if (edtMatkhau.getText().toString().equals(edtNhapLaiMK.getText().toString())) {
                    Table_Store.child("Store_Account" + i).setValue(store);
                    Toast.makeText(getContext(), "Dang ki thanh cong", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getContext(), "Sai mat khau vui long nhap lai", Toast.LENGTH_SHORT).show();
                }
                mData.child("MaxID").child("MaxID_Store").setValue(i);
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
            }
        }
    }

    public void chonngay() {
        final Calendar calendar = Calendar.getInstance();
        int ngay = calendar.get(Calendar.DATE);
        int thang = calendar.get(Calendar.MONTH);
        int nam = calendar.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calendar.set(i, i1, i2);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                edtNgDK.setText(simpleDateFormat.format(calendar.getTime()));
            }
        }, ngay, thang, nam);
        datePickerDialog.show();
    }
}
