package com.example.emi_master;

public class InsuranceItem {


    public int getImageResId() {
        return imageResId;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return Detail;
    }

    public void setDetail(String detail) {
        Detail = detail;
    }

    public InsuranceItem(int imageResId, String title, String detail) {
            this.imageResId = imageResId;
            this.title = title;
            Detail = detail;
        }
        private int imageResId;
        private String title;
        private String Detail;

}
