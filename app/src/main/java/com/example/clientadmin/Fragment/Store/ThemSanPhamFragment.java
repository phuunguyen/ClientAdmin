package com.example.clientadmin.Fragment.Store;


import android.app.Activity;
import android.content.Context;
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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import java.util.ArrayList;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class ThemSanPhamFragment extends Fragment {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference mData = database.getReference();
    private StorageReference mStorageRef;
    private View root;

    private ImageView imgProduct;
    private TextInputEditText edtTenSP, edtGiaSP;
    private Spinner spinTypeProduct;
    private MaterialButton btnUpdate;
    private final int REQUEST_CHOOSE_PHOTO = 312;

    private ArrayList<Product> products = new ArrayList<Product>();

    int idProduct;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_them_san_pham, container, false);
        setControl();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setEvent();
    }

    private void setControl() {
        imgProduct = (ImageView) root.findViewById(R.id.imgProduct);
        edtGiaSP = (TextInputEditText) root.findViewById(R.id.edtGiaSP);
        edtTenSP = (TextInputEditText) root.findViewById(R.id.edtTenSanPham);
        spinTypeProduct = (Spinner) root.findViewById(R.id.spinTypeProduct);
        btnUpdate = (MaterialButton) root.findViewById(R.id.btnUpdate);
        mStorageRef = FirebaseStorage.getInstance().getReference();
    }

    private void setEvent() {

        mData.child("MaxID").child("MaxID_Product").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue() == null){
                    idProduct = 0;
                }else {
                    idProduct = Integer.parseInt(dataSnapshot.getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        imgProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePhoto();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                idProduct++;
                addToDatabase(idProduct);
                uploadImage(idProduct);
            }
        });
    }

    private void choosePhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CHOOSE_PHOTO);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CHOOSE_PHOTO) {
                try {
                    Uri imageUri = data.getData();
                    InputStream is = getContext().getContentResolver().openInputStream(imageUri);
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                    imgProduct.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void uploadImage(final int idProduct) {

        Calendar calendar = Calendar.getInstance();
        StorageReference mountainsRef = mStorageRef.child("Product" + calendar.getTimeInMillis() + ".png");
        // Get the data from an ImageView as bytes
        imgProduct.setDrawingCacheEnabled(true);
        imgProduct.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) imgProduct.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 50, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = mountainsRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                Toast.makeText(getContext(), "Thêm sản phẩm thành công thất bại", Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                // ...
                Task<Uri> task = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                task.addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String photoLink = uri.toString();
                        mData.child("Product").child("Product" + idProduct).child("product_image").setValue(photoLink);
                        mData.child("MaxID").child("MaxID_Product").setValue(idProduct);
                    }
                });
                Toast.makeText(getContext(), "Thêm sản phẩm thành công", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addToDatabase(int idProduct){
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("SHARED_PREFERENCES_LOGIN",
                Context.MODE_PRIVATE);
        final String idStore = sharedPreferences.getString("ID_Login", null);
        mData.child("Product").child("Product" + idProduct).child("id_product").setValue(String.valueOf(idProduct));
        mData.child("Product").child("Product" + idProduct).child("product_name").setValue(edtTenSP.getText().toString());
        mData.child("Product").child("Product" + idProduct).child("price").setValue(Double.parseDouble(edtGiaSP.getText().toString()));
        if (spinTypeProduct.getSelectedItemPosition() == 0) {
            mData.child("Product").child("Product" + idProduct).child("id_menu").setValue("001");
        } else if (spinTypeProduct.getSelectedItemPosition() == 1) {
            mData.child("Product").child("Product" + idProduct).child("id_menu").setValue("002");
        } else {
            mData.child("Product").child("Product" + idProduct).child("id_menu").setValue("003");
        }
        mData.child("Product").child("Product" + idProduct).child("id_store").setValue(idStore);
    }
}
