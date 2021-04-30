package com.codingproject.nationalparktool.adapterdata;

import android.os.Parcel;

import java.io.Serializable;

// class for each grid cell item in national park recycler view
public class NationalParkItem implements Serializable {

    // recycler view items in each cell
    private String parkCode;
    private String parkImageRes;
    private String parkName;
    private int bookmarkImageRes;

    // default constructor
    public NationalParkItem() {
        this.parkImageRes = "";
        this.parkName = "";
        this.bookmarkImageRes = 0;
    }

    // parameterized constructor
    public NationalParkItem(String code, String parkImage, String parkName, int bookImage) {
        this.parkCode = code;
        this.parkImageRes = parkImage;
        this.parkName = parkName;
        this.bookmarkImageRes = bookImage;
    }

    protected NationalParkItem(Parcel in) {
        parkCode = in.readString();
        parkImageRes = in.readString();
        parkName = in.readString();
        bookmarkImageRes = in.readInt();
    }

    // getter for park code string
    public String getParkCode() { return parkCode; }

    // getter for park image string
    public String getImageRes() { return parkImageRes; }

    // getter for park name string
    public String getName() { return parkName; }

    // getter for bookmark drawable
    public int getBookmarkImageRes() { return bookmarkImageRes; }

    // setter for park code string
    public void setParkCode(String parkCode) { this.parkCode = parkCode; }

    // setter for park image string
    public void setParkImageRes(String parkImageRes) { this.parkImageRes = parkImageRes; }

    // setter for park name string
    public void setParkName(String parkName) { this.parkName = parkName; }

    // setter for bookmark drawable
    public void setBookmarkImageRes(int bookmarkImageRes) { this.bookmarkImageRes = bookmarkImageRes; }

}
