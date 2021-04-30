package com.codingproject.nationalparktool.moreinfodata;

import java.util.List;

public class Hours {

    private List<StandardHours> operatingHours;
    private ContactInfo contacts;
    private List<AddressItem> addresses;

    public List<StandardHours> getOperatingHours() { return operatingHours; }

    public ContactInfo getContacts() {
        return contacts;
    }

    public List<AddressItem> getAddresses() {
        return addresses;
    }
}





