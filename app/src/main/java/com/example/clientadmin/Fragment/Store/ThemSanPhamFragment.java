package com.example.clientadmin.Fragment.Store;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.clientadmin.database.Product;
import com.example.clientadmin.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
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
import java.util.ArrayList;
import java.util.Calendar;

import static android.app.Activity.RESULT_OK;

public class ThemSanPhamFragment extends Fragment {

    private ImageView imgSP;
    private EditText edtNameSP, edtGiaSP;
    private Button btnThem;
    private MaterialButton btnChonHinh;
    private Spinner spinner;

    DatabaseReference mData = FirebaseDatabase.getInstance().getReference();
    DatabaseReference Table_Product = mData.child("Product");
    Product product = new Product();
    View root;
    final int REQUEST_CHOOSE_PHOTO = 321;
    private StorageReference mStorageRef;
    int i;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_them_san_pham, container, false);
        setControl();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setEvent();
    }

    public void setControl(){
        imgSP = (ImageView)root.findViewById(R.id.imgAddSP);
        edtNameSP = (EditText)root.findViewById(R.id.edtTenSanPham);
        edtGiaSP = (EditText)root.findViewById(R.id.edtGia);
        btnThem = (Button)root.findViewById(R.id.btnThem);
        spinner = (Spinner)root.findViewById(R.id.spinner_type_product);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        btnChonHinh = (MaterialButton)root.findViewById(R.id.btnChonHinh);
    }

    public void setEvent(){
        mData.child("MaxID").child("MaxID_Product").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue()==null)
                {
                    i = 0;
                }
                else
                {
                    i = Integer.parseInt(dataSnapshot.getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (edtNameSP.getText().toString().isEmpty()){
                    Toast.makeText(getContext(), "Hãy nhập tên sản phẩm", Toast.LENGTH_SHORT).show();
                }
                if (edtGiaSP.getText().toString().isEmpty()){
                    Toast.makeText(getContext(), "Hãy nhập giá sản phẩm", Toast.LENGTH_SHORT).show();
                }
                if (imgSP.getDrawable() == null){
                    Toast.makeText(getContext(), "Hãy chọn hình ảnh", Toast.LENGTH_SHORT).show();
                }

                if (!edtNameSP.getText().toString().isEmpty() && !edtGiaSP.getText().toString().isEmpty() && imgSP.getDrawable() != null){
                    i++;
                    upLoadImg(i);
                    Toast.makeText(getContext(), "Thêm Thành Công", Toast.LENGTH_SHORT).show();

                }

            }
        });
        btnChonHinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePhoto();
            }
        });
    }

    private void upLoadImg(final int i){
        Calendar calendar = Calendar.getInstance();
        final StorageReference mountainsRef = mStorageRef.child("image" + calendar.getTimeInMillis() + ".png");
        // Get the data from an ImageView as bytes
        imgSP.setDrawingCacheEnabled(true);
        imgSP.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) imgSP.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();



        UploadTask uploadTask = mountainsRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Fail !!", Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> task = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                task.addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String photoLink = uri.toString();
                        SharedPreferences sharedPreferences = getContext().getSharedPreferences("SHARED_PREFERENCES_LOGIN",
                                Context.MODE_PRIVATE);
                        final String idStore = sharedPreferences.getString("ID_Login", null);
                        mData.child("MaxID").child("MaxID_Product").setValue(i);
                        mData.child("Product").child("Product"+i).child("product_image").setValue(photoLink);
                        mData.child("Product").child("Product" + i).child("id_product").setValue(String.valueOf(i));
                        mData.child("Product").child("Product" + i).child("product_name").setValue(edtNameSP.getText().toString());
                        mData.child("Product").child("Product" + i).child("id_store").setValue(String.valueOf(idStore));
                        mData.child("Product").child("Product" + i).child("price").setValue(Double.parseDouble(edtGiaSP.getText().toString()));
                        if (spinner.getSelectedItemPosition() == 0){
                            mData.child("Product").child("Product" + i).child("id_menu").setValue(getText(R.string.idMenuCoffee));
                        }else if (spinner.getSelectedItemPosition() == 1){
                            mData.child("Product").child("Product" + i).child("id_menu").setValue(getText(R.string.idMenuTraSua));
                        }else{
                            mData.child("Product").child("Product" + i).child("id_menu").setValue(getText(R.string.idMenuTopping));
                        }
                        getActivity().onBackPressed();
                    }
                });
            }
        });
    }

    private boolean isEmptyOrNull(String text) {
        if (text != null && !TextUtils.isEmpty(text)) {
            return true;
        }
        return false;
    }

    private void choosePhoto(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,REQUEST_CHOOSE_PHOTO);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_CHOOSE_PHOTO){
            try{
                Uri imageUri = data.getData();
                InputStream is = getActivity().getContentResolver().openInputStream(imageUri);
                Bitmap bitmap = BitmapFactory.decodeStream(is);
                imgSP.setImageBitmap(bitmap);
            }catch (Exception e){
                e.getMessage();
            }
        }
    }
}
