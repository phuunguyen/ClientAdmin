package com.example.clientadmin.Fragment.Store;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.clientadmin.Model.Product;
import com.example.clientadmin.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ThemSanPham extends AppCompatActivity {

    private ImageView imgSP;
    private EditText edtNameSP, edtGiaSP, edtLoaiSP;
    private RadioGroup radioGroup;
    private RadioButton radTraSua, radCoffee;
    private Button btnThem;

    DatabaseReference mData = FirebaseDatabase.getInstance().getReference();
    DatabaseReference Table_Product = mData.child("Product");
    Product product = new Product();
    int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_san_pham);

        setControl();


        setEvent();
    }

    public void setControl(){
        imgSP = (ImageView)findViewById(R.id.imgAddSP);
        edtNameSP = (EditText)findViewById(R.id.edtTenSanPham);
        edtGiaSP = (EditText)findViewById(R.id.edtGia);
        edtLoaiSP = (EditText)findViewById(R.id.edtTypeSP);
//        radioGroup = (RadioGroup)findViewById(R.id.radGroup);
//        radCoffee = (RadioButton)findViewById(R.id.radCoffee);
//        radTraSua = (RadioButton)findViewById(R.id.radTraSua);
        btnThem = (Button)findViewById(R.id.btnThem);
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
                String loaiSP = edtLoaiSP.getText().toString().trim();

//                if (radTraSua.isChecked()){
//                    String loaiSP = getText(R.string.TraSua).toString().trim();
//                }
//                else if (radCoffee.isChecked()){
//                    String loaiSP = getText(R.string.coffee).toString().trim();
//                }
                if(!isEmptyOrNull(nameSP)){
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập tên món", Toast.LENGTH_SHORT).show();
                }
                if(!isEmptyOrNull(giaSP)){
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập giá sản phẩm", Toast.LENGTH_SHORT).show();
                }
                if(!isEmptyOrNull(loaiSP)){
                    Toast.makeText(getApplicationContext(),"Vui lòng nhập loại sản phẩm", Toast.LENGTH_SHORT).show();
                }

                i++;
//                product.setNameProduct(edtNameSP.getText().toString());
//                product.setTypeProduct(edtLoaiSP.getText().toString());
//                product.setPriceProduct(Integer.parseInt(edtGiaSP.getText().toString()));

                Table_Product.child("Product" + i).setValue(product);
                Toast.makeText(getApplicationContext(), "Thêm Thành Công", Toast.LENGTH_SHORT).show();

                mData.child("MaxID").child("MaxID_Product").setValue(i);
            }

        });



    }

    private boolean isEmptyOrNull(String text) {
        if (text != null && !TextUtils.isEmpty(text)) {
            return true;
        }
        return false;
    }
}
