package com.example.enclaveit.schoolmateapp.bean;

/**
 * Created by enclaveit on 07/04/2017.
 */

public class Subject {
    private int subjectID;
    private String subjectName;

    public Subject(){}

    public Subject(int subjectID, String subjectName) {
        this.subjectID = subjectID;
        this.subjectName = subjectName;
    }

    public int getSubjectID() {
        return subjectID;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectID(int subjectID) {
        this.subjectID = subjectID;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
}
