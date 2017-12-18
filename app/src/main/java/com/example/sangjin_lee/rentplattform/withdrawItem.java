package com.example.sangjin_lee.rentplattform;

import java.io.Serializable;

/**
 * Created by Sangjin-Lee on 2017-12-13.
 */

public class withdrawItem implements Serializable {
    private String itemName;    //  아이템 이름
    private String itemType;    //  아이템 타입
    private boolean checkApplication;   // 신청서 체크
    private String userId;
    private String Date;

    public withdrawItem(){

    }

    public withdrawItem (String itemName, String itemType, boolean checkApplication, String userId, String Date) {
        this.itemName= itemName;
        this.itemType = itemType;
        this.checkApplication = checkApplication;
        this.userId = userId;
        this.Date = Date;
    }

    public String getitemName() {return itemName;}
    public String getitemType() {return itemType;}
    public boolean getcheckApplication() {return checkApplication;}
    public String getuserId() {return userId;}
    public String getDate() {return Date;}

    public void setitemName(String itemName) {this.itemName = itemName;}
    public void setitemType(String itemType) {this.itemType= itemType;}
    public void setcheckapp(boolean checkApplication) {this.checkApplication= checkApplication;}
    public void setuserId(String userId) {this.userId = userId;}
    public void setDate(String Date) {this.Date = Date;}
}
