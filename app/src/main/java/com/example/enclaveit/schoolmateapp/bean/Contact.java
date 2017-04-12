package com.example.enclaveit.schoolmateapp.bean;

/**
 * Created by enclaveit on 12/04/2017.
 */

public class Contact {
    private String contactName;
    private String contactPhone;
    private int contactPhoto;

    public Contact(){
    }

    public Contact(String contactName, String contactPhone, int contactPhoto) {
        this.contactName = contactName;
        this.contactPhone = contactPhone;
        this.contactPhoto = contactPhoto;
    }

    public String getContactName() {
        return contactName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public int getContactPhoto() {
        return contactPhoto;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public void setContactPhoto(int contactPhoto) {
        this.contactPhoto = contactPhoto;
    }
}
