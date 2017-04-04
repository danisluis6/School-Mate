package com.example.enclaveit.schoolmateapp.bean;

public class Announcement {

    private int announcementID;
    private String announcementTitle;
    private String announcementDescription;
    private String announcementContent;
    private String announcementDate;
    private Integer announcementUserID;

    public Announcement() {
    }

    public Announcement(int announcementID, String announcementTitle) {
        this.announcementID = announcementID;
        this.announcementTitle = announcementTitle;
    }

    public Announcement(String announcementTitle, String announcementDescription) {
        this.announcementTitle = announcementTitle;
        this.announcementDescription = announcementDescription;
    }

    public Announcement(int announcementID, String announcementTitle, String announcementDescription, String announcementContent, String announcementDate, Integer announcementUserID) {
        this.announcementID = announcementID;
        this.announcementTitle = announcementTitle;
        this.announcementDescription = announcementDescription;
        this.announcementContent = announcementContent;
        this.announcementDate = announcementDate;
        this.announcementUserID = announcementUserID;
    }

    public Integer getAnnouncementUserID() {
        return announcementUserID;
    }

    public void setAnnouncementUserID(Integer announcementUserID) {
        this.announcementUserID = announcementUserID;
    }

    public int getAnnouncementID() {
        return announcementID;
    }

    public String getAnnouncementTitle() {
        return announcementTitle;
    }

    public String getAnnouncementDescription() {
        return announcementDescription;
    }

    public String getAnnouncementContent() {
        return announcementContent;
    }

    public String getAnnouncementDate() {
        return announcementDate;
    }

    public void setAnnouncementID(int announcementID) {
        this.announcementID = announcementID;
    }

    public void setAnnouncementTitle(String announcementTitle) {
        this.announcementTitle = announcementTitle;
    }

    public void setAnnouncementDescription(String announcementDescription) {
        this.announcementDescription = announcementDescription;
    }

    public void setAnnouncementContent(String announcementContent) {
        this.announcementContent = announcementContent;
    }

    public void setAnnouncementDate(String announcementDate) {
        this.announcementDate = announcementDate;
    }
}
