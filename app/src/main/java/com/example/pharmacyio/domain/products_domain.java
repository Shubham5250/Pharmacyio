package com.example.pharmacyio.domain;

public class products_domain {

    private  String product_name;
    private String product_pic;
    private String product_fee;

    public products_domain(String product_name, String product_pic, String product_fee){
        this.product_name = product_name;
        this.product_fee = product_fee;
        this.product_pic = product_pic;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_pic() {
        return product_pic;
    }

    public void setProduct_pic(String product_pic) {
        this.product_pic = product_pic;
    }

    public String getProduct_fee() {
        return product_fee;
    }

    public void setProduct_fee(String product_fee) {
        this.product_fee = product_fee;
    }
}
