package com.example.saada.project_idea.BO;

import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by saada on 1/4/2017.
 */

public class SmsBO implements Serializable {

   private String Name;
    private String Number;
    private String Date;
    private String Message;

    public SmsBO(String name, String number, String date, String messgage) {
        this.Name = name;
        this.Number = number;
        Date = date;
        Message = messgage;
    }


    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getNumber() {
        return Number;
    }

    public void setNumber(String number) {
        Number = number;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String messgage) {
        Message = messgage;
    }

    public  SmsBO(){


    }
}
