package com.codingproject.nationalparktool.parklistdata;

import java.util.List;

public class Park {

    private String fullName;
    private String parkCode;
    private List<ParkImage> images;


    public String getFullName() {
        return fullName;
    }

    public String getParkCode() {
        return parkCode;
    }

    public List<ParkImage> getImages() { return images; }

}
