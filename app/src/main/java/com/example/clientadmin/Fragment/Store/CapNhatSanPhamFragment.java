package com.example.clientadmin.Fragment.Store;


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

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.clientadmin.Model.Product;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class CapNhatSanPhamFragment extends Fragment {

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_cap_nhat_san_pham, container, false);
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
        btnUpdate = (MaterialButton)root.findViewById(R.id.btnUpdate);
        mStorageRef = FirebaseStorage.getInstance().getReference();
    }

    private void setEvent() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("SHARED_PREFERENCES_PRODUCT", Context.MODE_PRIVATE);
        final String idProduct = sharedPreferences.getString("IDPRODUCT", "");
        loadProduct(idProduct);

        imgProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePhoto();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mData.child("Product").child("Product" + idProduct).child("product_name").setValue(edtTenSP.getText().toString());
                mData.child("Product").child("Product" + idProduct).child("price").setValue(edtGiaSP.getText().toString());
                if(spinTypeProduct.getSelectedItemPosition() == 0){
                    mData.child("Product").child("Product" + idProduct).child("id_menu").setValue("001");
                }else if(spinTypeProduct.getSelectedItemPosition() == 1){
                    mData.child("Product").child("Product" + idProduct).child("id_menu").setValue("002");
                }else {
                    mData.child("Product").child("Product" + idProduct).child("id_menu").setValue("003");
                }
                uploadImage(idProduct);
            }
        });
    }

    private void loadProduct(final String idProduct) {
        mData.child("Product").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Product product = dataSnapshot.getValue(Product.class);
                products.add(product);
                for (Product product1 : products) {
                    if (product1.getId_product().equals(idProduct)) {
                        Picasso.get().load(product1.getProduct_image()).into(imgProduct);
                        edtTenSP.setText(product1.getProduct_name());
                        edtGiaSP.setText(product1.getPrice() + "");

                        if (product1.getId_menu().equals("001")) {
                            spinTypeProduct.setSelection(0);
                        } else if (product.getId_menu().equals("002")) {
                            spinTypeProduct.setSelection(1);
                        } else {
                            spinTypeProduct.setSelection(2);
                        }
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

    }

    private void choosePhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CHOOSE_PHOTO);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == getActivity().RESULT_OK) {
            if (requestCode == REQUEST_CHOOSE_PHOTO) {
                try {
                    Uri imageUri = data.getData();
                    InputStream is = getActivity().getContentResolver().openInputStream(imageUri);
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                    imgProduct.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void uploadImage(final String idProduct) {

        Calendar calendar = Calendar.getInstance();
        StorageReference mountainsRef = mStorageRef.child("image" + calendar.getTimeInMillis() + ".png");
        // Get the data from an ImageView as bytes
        imgProduct.setDrawingCacheEnabled(true);
        imgProduct.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) imgProduct.getDrawable()).getBitmap();
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
                Task<Uri> task = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                task.addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String photoLink = uri.toString();
                        mData.child("Product").child("Product" + idProduct).child("product_image").setValue(photoLink);
                        Log.d("---", photoLink);
                    }
                });
            }
        });
    }
}
