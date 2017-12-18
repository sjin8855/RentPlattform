package com.example.sangjin_lee.rentplattform;

import java.io.Serializable;

/**
 * Created by Sangjin-Lee on 2017-12-13.
 */

public class enItem implements Serializable {
    private String itemName;    //  아이템 이름
    private String itemDescriptor;
    private boolean checkApplication;   // 신청서 체크
    private String userId;
    private String Date;

    public enItem(){

    }

    public enItem (String itemName, String itemDescriptor, boolean checkApplication, String userId, String Date) {
        this.itemName= itemName;
        this.itemDescriptor = itemDescriptor;
        this.checkApplication = checkApplication;
        this.userId = userId;
        this.Date = Date;
    }

    public String getitemName() {return itemName;}
    public String getItemDS() {return itemDescriptor;}
    public boolean getcheckApplication() {return checkApplication;}
    public String getuserId() {return userId;}
    public String getDate() {return Date;}

    public void setitemName(String itemName) {this.itemName = itemName;}
    public void setItemDS(String itemDescriptor) {this.itemDescriptor = itemDescriptor;}
    public void setcheckapp(boolean checkApplication) {this.checkApplication= checkApplication;}
    public void setuserId(String currentName) {this.userId = userId;}
    public void setDate(String Date) {this.Date = Date;}
}
