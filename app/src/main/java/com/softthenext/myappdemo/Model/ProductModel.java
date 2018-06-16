package com.softthenext.myappdemo.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Dilip Ghawade
 * Organization Name SoftTheNext
 * on Date 13/06/2018.
 */
public class ProductModel {


    @SerializedName("productName")
    @Expose
    private String ProductName;
    @SerializedName("imageUrl")
    @Expose
    private String ImageUrl;
    @SerializedName("price")
    @Expose
    private String Price;
    @SerializedName("quantity")
    @Expose
    private String Quantity;
    @SerializedName("unit")
    @Expose
    private String unit;



    public String getImageUrl() {
        return ImageUrl;
    }

    public String getPrice() {
        return Price;
    }

    public String getQuantity() {
        return Quantity;
    }

    public String getUnit() {
        return unit;
    }


    public String getProductName() {
        return ProductName;
    }


}
