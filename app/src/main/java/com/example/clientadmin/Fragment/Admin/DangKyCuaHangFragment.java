package com.example.clientadmin.Fragment.Admin;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.clientadmin.DrawerLocker;
import com.example.clientadmin.Object.Store;
import com.example.clientadmin.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class DangKyCuaHangFragment extends Fragment {
    View root;
    TextInputEditText edtTenCH, edtDiaChi, edtTenDangNhap, edtSDT, edtTenChuSoHuu, edtNgDK, edtMatkhau, edtNhapLaiMK;
    Button btnDK;
    ImageView imgStore;
    DatabaseReference mData = FirebaseDatabase.getInstance().getReference();
    DatabaseReference Table_Store = mData.child("Store");
    Store store = new Store();


    private StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();
    int REQUEST_CHOOSE_PHOTO = 1;
    int i;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_dang_ky_cua_hang, container, false);
        setControl();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        ((DrawerLocker)getActivity()).setDrawerLocked(true);
        return root;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Activity a = getActivity();
        if(a != null) {
            a.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        setEvent();
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
                Toast.makeText(getActivity(),"error",Toast.LENGTH_LONG).show();
            }


        }
    }


    private void setControl() {
        edtTenCH = (TextInputEditText) root.findViewById(R.id.edttencuahang);
        edtDiaChi = (TextInputEditText) root.findViewById(R.id.edtdiachi);
        edtTenDangNhap = (TextInputEditText) root.findViewById(R.id.edtTenDangNhap);
        edtTenChuSoHuu = (TextInputEditText) root.findViewById(R.id.edttenchuho);
        edtSDT = (TextInputEditText) root.findViewById(R.id.edtsdt);
        edtNgDK = (TextInputEditText) root.findViewById(R.id.edtngaydk);
        btnDK = (Button) root.findViewById(R.id.btndangky);
        edtMatkhau = (TextInputEditText) root.findViewById(R.id.edtmatkhau);
        edtNhapLaiMK = (TextInputEditText) root.findViewById(R.id.edtmatkhau1);
        imgStore = (ImageView)root.findViewById(R.id.img);
    }

    private void setEvent() {
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


                String tench = edtTenCH.getText().toString().trim();
                String tendangnhap = edtTenDangNhap.getText().toString().trim();
                String diachi = edtDiaChi.getText().toString().trim();
                String sodt = edtSDT.getText().toString().trim();
                String tenchuho = edtTenChuSoHuu.getText().toString().trim();
                String ngaydk = edtNgDK.getText().toString().trim();
                String mk = edtMatkhau.getText().toString().trim();
                String nhaplaimk = edtNhapLaiMK.getText().toString().trim();

                if(!isEmptyOrNull(tench)){
                    Toast.makeText(getActivity(), "Vui lòng nhập tên cửa hàng!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!isEmptyOrNull(tenchuho)){
                    Toast.makeText(getActivity(), "Vui lòng nhập tên chủ hộ!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!isEmptyOrNull(tendangnhap)){
                    Toast.makeText(getActivity(), "Vui lòng nhập tên đăng nhập!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!isEmptyOrNull(ngaydk)){
                    Toast.makeText(getActivity(), "Vui lòng nhập ngày đang ký!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!isEmptyOrNull(diachi)){
                    Toast.makeText(getActivity(), "Vui lòng nhập địa chỉ!", Toast.LENGTH_SHORT).show();
                    return;
                }
//                if(!isValidEmailID(email)){
//                    Toast.makeText(getActivity(), "Email không đúng định dạng!", Toast.LENGTH_SHORT).show();
//                    return;
//                }
                if(!isValidPassword(mk)){
                    Toast.makeText(getActivity(), "Mật khẩu của bạn phải từ 6 kí tự trở lên!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!isValidPassword(nhaplaimk)){
                    Toast.makeText(getActivity(), "Mật khẩu nhập lại của bạn phải từ 6 kí tự trở lên!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!isValidPhoneNumber(sodt)) {
                    Toast.makeText(getActivity(), "Vui lòng kiểm tra lại số điện thoại của bạn!", Toast.LENGTH_SHORT).show();
                    return;
                }

                i++;
                store.setId_Store("Store" + i);
                store.setAddress(edtDiaChi.getText().toString());
                store.setPhone(edtSDT.getText().toString());
               // store.setEmail(edtEmail.getText().toString());
                store.setUserName(edtTenDangNhap.getText().toString());

                if (imgStore.getDrawable() == null){
                    Toast.makeText(getActivity(), "Vui lòng chọn ảnh!", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    Calendar calendar = Calendar.getInstance();
                    StorageReference mountainsRef = mStorageRef.child("Store" + calendar.getTimeInMillis() + ".png");
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
                            Task<Uri> task = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                            task.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String photoLink = uri.toString();
                                    mData.child("Store").child("Store" + i).child("image").setValue(photoLink);

                                }
                            });

                        }
                    });
                }
                store.setStore_Name(edtTenCH.getText().toString());
                store.setBossName(edtTenChuSoHuu.getText().toString());
                store.setRegisterDay(edtNgDK.getText().toString());
                store.setPassword(edtMatkhau.getText().toString());
                if (edtMatkhau.getText().toString().equals(edtNhapLaiMK.getText().toString())) {
                    Table_Store.child("Store" + i).setValue(store);
                    Toast.makeText(getContext(), "Dang ki thanh cong", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getContext(), "Sai mat khau vui long nhap lai", Toast.LENGTH_SHORT).show();
                }
                mData.child("MaxID").child("MaxID_Store").setValue(i);
            }
        });


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
    private boolean isEmptyOrNull(String text) {
        if (text != null && !TextUtils.isEmpty(text)) {
            return true;
        }
        return false;
    }

    private boolean isValidEmailID(String email) {
        String PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean isValidPassword(String password) {
        if (password != null && password.length() > 6) {
            return true;
        }
        return false;
    }

    private boolean isValidPhoneNumber(String phone) {
        if (phone != null && (phone.length() >= 6 || phone.length() < 13)) {
            return PhoneNumberUtils.isGlobalPhoneNumber(phone);
        }
        return false;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ((DrawerLocker)getActivity()).setDrawerLocked(false);
    }


}
