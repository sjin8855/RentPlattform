package com.example.sangjin_lee.rentplattform;

/**
 * Created by Sangjin-Lee on 2017-12-13.
 */



public class rentInfo {
    private String itemName;    // 빌릴 아이템
    private String rentUser;    // 빌린 User
    private String itemKey;
    private String startDate;   // 대여일
    private String endDate;     // 반납일
    private String itemFee;   // 대여료
    private boolean returnCheck;    // 반납여부
    private String userKey;

    public rentInfo(){

    }
    public rentInfo(String itemName, String rentUser, String itemKey, String startDate, String endDate, String itemFee, boolean returnCheck, String userKey) {
        this.itemName = itemName;
        this.rentUser= rentUser;
        this.itemKey = itemKey;
        this.startDate = startDate;
        this.endDate = endDate;
        this.itemFee = itemFee;
        this.returnCheck = returnCheck;
        this.userKey = userKey;

    }

    public String getitemName(){return itemName;}
    public String getrentUser(){return rentUser;}
    public String getitemKey(){return itemKey;}
    public String getstartRent() {return startDate;}
    public String getendRent() {return endDate;}
    public String getItemFee() {return itemFee;}
    public boolean getreturnCheck() {return returnCheck;}
    public String getuserKey(){return userKey;}

    public void setitemName(String itemName) {this.itemName= itemName;}
    public void setrentUser(String rentUser) {this.rentUser= rentUser;}
    public void setitemKey(String itemKey) {this.itemKey = itemKey;}
    public void setstartRent(String startDate) {this.startDate= startDate;}
    public void setendRent(String endDate) {this.endDate= endDate;}
    public void setItemFee(String itemFee) {this.itemFee = itemFee;}
    public void setreturnCheck(boolean returnCheck) {this.returnCheck = returnCheck;}
    public void setuserKey(String userKey){this.userKey = userKey;}
}
