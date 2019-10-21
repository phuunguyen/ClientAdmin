package com.example.clientadmin.Fragment.Store;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.clientadmin.database.Product;
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
import java.io.InputStream;
import java.util.Calendar;

public class ThemSanPhamFragment extends Fragment {

    private ImageView imgSP;
    private EditText edtNameSP, edtGiaSP, edtLoaiSP;
    private RadioGroup radioGroup;
    private RadioButton radTraSua, radCoffee;
    private Button btnThem;
    private Spinner spinner;

    DatabaseReference mData = FirebaseDatabase.getInstance().getReference();
    DatabaseReference Table_Product = mData.child("Product");
    Product product = new Product();
    View root;
    final int REQUEST_CHOOSE_PHOTO = 123;
    private StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();
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
                String nameSP = edtNameSP.getText().toString().trim();
                String giaSP = edtGiaSP.getText().toString().trim();

                if(!isEmptyOrNull(nameSP)){
                    Toast.makeText(getContext(), "Vui lòng nhập tên món", Toast.LENGTH_SHORT).show();
                }
                if(!isEmptyOrNull(giaSP)){
                    Toast.makeText(getContext(), "Vui lòng nhập giá sản phẩm", Toast.LENGTH_SHORT).show();
                }

                if (spinner.getSelectedItemPosition() == 0){
                    product.setTypeProduct(getString(R.string.idCoffee));
                }else if (spinner.getSelectedItemPosition() == 1){
                    product.setTypeProduct(getString(R.string.idTrasua));
                }else{
                    product.setTypeProduct(getString(R.string.idTopping));
                }

                i++;
                product.setNameProduct(edtNameSP.getText().toString());
                product.setTypeProduct(edtLoaiSP.getText().toString());
                product.setPriceProduct(Double.parseDouble(edtGiaSP.getText().toString()));

                Table_Product.child("Product" + i).setValue(product);
                Toast.makeText(getContext(), "Thêm Thành Công", Toast.LENGTH_SHORT).show();

                mData.child("MaxID").child("MaxID_Product").setValue(i);

                Calendar calendar = Calendar.getInstance();
                StorageReference mountainsRef = mStorageRef.child("Store " + calendar.getTimeInMillis() + ".png");
                imgSP.setDrawingCacheEnabled(true);
                imgSP.buildDrawingCache();

                Bitmap bitmap = ((BitmapDrawable) imgSP.getDrawable()).getBitmap();
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
                                mData.child("Product").child("Product" + i).child("product_image").setValue(photoLink);
                            }
                        });

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
        if (resultCode == Activity.RESULT_OK){
            if (requestCode == REQUEST_CHOOSE_PHOTO){
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
}
