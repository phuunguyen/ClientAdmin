package com.example.clientadmin.Fragment.Admin;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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

import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.clientadmin.DrawerLocker;
import com.example.clientadmin.Model.Product;
import com.example.clientadmin.Object.Store;
import com.example.clientadmin.R;
import com.example.clientadmin.adapter.StoreAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

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
public class ThongTinChiTietCuaHangFragment extends Fragment {
    View root;
    TextView txtTenCH, txtDiachi, txtTenChuSH, txtTenDN, txtSoDT, txtNgayDK, txtTongDH, txtTienHoaHong;
    EditText edtTenCH, edtDiaChi, edtTenChuSH, edtTenDN, edtSoDT, edtNgayDK, edtTongDH;
    ImageView imgStore;
    ImageButton imgVEdit, imgVSave, imgDelete, imgchoosephoto;
    DatabaseReference mData = FirebaseDatabase.getInstance().getReference();
    DatabaseReference Table_Store = mData.child("Store");
    DatabaseReference Table_Product = mData.child("Product");
    DatabaseReference Table_Cart = mData.child("Cart");

    Store store = new Store();
    Product product = new Product();
    int REQUEST_CHOOSE_PHOTO = 1;
    private StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();
    SharedPreferences sharedPreferences;
    String idStore = "";
    StoreAdapter storeAdapter ;

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

       // ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
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
        txtTongDH = (TextView)root.findViewById(R.id.txttongdonhang);
        txtTienHoaHong = (TextView)root.findViewById(R.id.txttienhoahong);

        edtTenCH = (EditText) root.findViewById(R.id.edttencuahang);
        edtDiaChi = (EditText) root.findViewById(R.id.edtdiachi);
        edtTenChuSH = (EditText) root.findViewById(R.id.edttenchuho);
        edtTenDN = (EditText) root.findViewById(R.id.edtTenDN);
        edtSoDT = (EditText) root.findViewById(R.id.edtsdt);
        edtNgayDK = (EditText) root.findViewById(R.id.edtngaydk);

        imgStore = (ImageView) root.findViewById(R.id.imgStore1);
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

                edtNgayDK.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        chonngay();
                    }
                });

                Table_Store.child(idStore).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        edtTenCH.setText(dataSnapshot.child("store_Name").getValue().toString());
                        edtDiaChi.setText(dataSnapshot.child("address").getValue().toString());
                        edtSoDT.setText(dataSnapshot.child("phone").getValue().toString());
                        edtTenChuSH.setText(dataSnapshot.child("bossName").getValue().toString());
                        edtTenDN.setText(dataSnapshot.child("userName").getValue().toString());
                        edtNgayDK.setText(dataSnapshot.child("registerDay").getValue().toString());
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


                String tench = edtTenCH.getText().toString().trim();
                String tendangnhap = edtTenDN.getText().toString().trim();
                String diachi = edtDiaChi.getText().toString().trim();
                String sodt = edtSoDT.getText().toString().trim();
                String tenchuho = edtTenChuSH.getText().toString().trim();
                String ngaydk = edtNgayDK.getText().toString().trim();


                if (!isEmptyOrNull(tench)) {
                    Toast.makeText(getActivity(), "Vui lòng nhập tên cửa hàng!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!isEmptyOrNull(tenchuho)) {
                    Toast.makeText(getActivity(), "Vui lòng nhập tên chủ hộ!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!isValidPhoneNumber(sodt)) {
                    Toast.makeText(getActivity(), "Vui lòng nhập số điện thoai!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!isEmptyOrNull(tendangnhap)) {
                    Toast.makeText(getActivity(), "Vui lòng nhập tên đăng nhập!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!isEmptyOrNull(ngaydk)) {
                    Toast.makeText(getActivity(), "Vui lòng nhập ngày đăng ký!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!isEmptyOrNull(diachi)) {
                    Toast.makeText(getActivity(), "Vui lòng nhập địa chỉ!", Toast.LENGTH_SHORT).show();
                    return;
                }

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

                mData.child("Store").child(idStore).child("store_Name").setValue(edtTenCH.getText().toString());
                mData.child("Store").child(idStore).child("address").setValue(edtDiaChi.getText().toString());
                mData.child("Store").child(idStore).child("phone").setValue(edtSoDT.getText().toString());
                mData.child("Store").child(idStore).child("bossName").setValue(edtTenChuSH.getText().toString());
                mData.child("Store").child(idStore).child("userName").setValue(edtTenDN.getText().toString());
                mData.child("Store").child(idStore).child("registerDay").setValue(edtNgayDK.getText().toString());

                if (imgStore.getDrawable() == null) {
                    Toast.makeText(getActivity(), "Vui lòng chọn ảnh!", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    Calendar calendar = Calendar.getInstance();
                    StorageReference mountainsRef = mStorageRef.child("User " + calendar.getTimeInMillis() + ".png");
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
                                    mData.child("Store").child(idStore).child("image").setValue(photoLink);
                                }
                            });

                        }
                    });
                }
            }
        });

        imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xoaStoreAlertDialog();
            }
        });


    }

    public void showStore() {
        sharedPreferences = getContext().getSharedPreferences("SHARED_PREFERENCES_ID_STORE",
                Context.MODE_PRIVATE);
        idStore = sharedPreferences.getString("ID_STORE", "");
        Table_Store.child(idStore).addValueEventListener(new ValueEventListener() {
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
                        Picasso.get().load(dataSnapshot.child("image").getValue().toString()).into(imgStore);

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        final int[] count = {0};
        Table_Cart.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if(dataSnapshot.child("id_store").getValue().toString().equals(idStore)){
                    count[0]++;
                }
                txtTongDH.setText(count[0] + "");
                txtTienHoaHong.setText(count[0] * 1000 + "");
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

    private boolean isValidPhoneNumber(String phone) {
        if (phone != null && (phone.length() >= 6 || phone.length() < 13)) {
            return PhoneNumberUtils.isGlobalPhoneNumber(phone);
        }
        return false;
    }

    public void xoaStoreAlertDialog() {
        new MaterialAlertDialogBuilder(getContext())
                .setTitle("Xóa cửa hàng")
                .setMessage("Bạn có muốn xóa không?")
                .setCancelable(false)
                .setPositiveButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setNegativeButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        deleteStore();
                        imgStore.setImageResource(0);
                        txtTenCH.setText("");
                        txtDiachi.setText("");
                        txtTenChuSH.setText("");
                        txtSoDT.setText("");
                        txtTenDN.setText("");
                        txtNgayDK.setText("");
                        Toast.makeText(getContext(), "Xóa thành công", Toast.LENGTH_SHORT).show();
                        Navigation.findNavController(root).navigate(R.id.action_thongTinChiTietCuaHangFragment_to_adminFeaturesFragment);
                    }
                }).show();
    }
    public void deleteStore() {
        sharedPreferences = getContext().getSharedPreferences("SHARED_PREFERENCES_ID_STORE",
                Context.MODE_PRIVATE);
        idStore = sharedPreferences.getString("ID_STORE", "");

        Table_Store.child(idStore).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                 Table_Store.child(idStore).removeValue();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        Table_Product.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if(dataSnapshot.child("id_store").getValue().toString().equals(idStore)){
                    Table_Product.child("Product" + dataSnapshot.child("id_product").getValue().toString()).removeValue();
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
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ((DrawerLocker) getActivity()).setDrawerLocked(false);
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
                edtNgayDK.setText(simpleDateFormat.format(calendar.getTime()));
            }
        }, nam, thang, ngay);
        datePickerDialog.show();
    }
}
