package com.example.sangjin_lee.rentplattform;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Sangjin-Lee on 2017-12-12.
 */
//enum Condition{CAN_RENT, ING_RENT, IMPOSSIBLE_RENT}

public class item implements Serializable {
    private String itemName;    // 아이템 이름
    private String itemOwner;  // 등록한 유저(User로하면 계좌금액까지받아오는데, 그것은 필요가없음)
    private Long itemRentalAvailability;      // 대여정보
    private String itemType;    // 아이템 타입
    private String itemKey;
    private String itemFee;
    public item() {

    }

    public item(String itemName, String itemOwner, Long itemRentalAvailability, String itemType, String itemKey, String itemFee) {
        this.itemName= itemName;
        this.itemOwner= itemOwner;
        this.itemRentalAvailability = itemRentalAvailability;
        this.itemType = itemType;
        this.itemKey = itemKey;
        this.itemFee = itemFee;
    }

    public String getitemName(){return itemName;}
    public String getitemType(){return itemType;}
    public String getitemOwner(){return itemOwner;}
    public Long getitemRentalAvailability() {return itemRentalAvailability;}
    public String getitemKey(){return itemKey;}
    public String getitemFee(){return itemFee;}

    public void setitemName(String itemName) {this.itemName = itemName;}
    public void setitemType(String itemType) {this.itemType = itemType;}
    public void setitemOwner(String itemOwner) {this.itemOwner = itemOwner;}
    public void setitemRentalAvailability(Long itemRentalAvailability) {this.itemRentalAvailability = itemRentalAvailability;}
    public void setitemKey(String itemKey){this.itemKey = itemKey;}
    public void setitemFee(String itemFee) {this.itemFee = itemFee;}
}
