package com.example.clientadmin.model;

import android.widget.ImageView;

public class Product {
    private String imgProduct;
    private String txtProductName;
    private String txtProductPrice;

    public Product() {
    }

    public Product(String imgProduct, String txtProductName, String txtProductPrice) {
        this.imgProduct = imgProduct;
        this.txtProductName = txtProductName;
        this.txtProductPrice = txtProductPrice;
    }

    public String getImgProduct() {
        return imgProduct;
    }

    public void setImgProduct(String imgProduct) {
        this.imgProduct = imgProduct;
    }

    public String getTxtProductName() {
        return txtProductName;
    }

    public void setTxtProductName(String txtProductName) {
        this.txtProductName = txtProductName;
    }

    public String getTxtProductPrice() {
        return txtProductPrice;
    }

    public void setTxtProductPrice(String txtProductPrice) {
        this.txtProductPrice = txtProductPrice;
    }
}
