package com.example.saada.project_idea.BO;

/**
 * Created by saada on 1/8/2017.
 */
public class CallBO {
    public String getNumber() {
        return Number;
    }

    public void setNumber(String number) {
        Number = number;
    }

    public CallBO(String name, String number, String date) {
        Name = name;
        Number = number;
        Date = date;
    }
    public CallBO(){

    }

    public String getDate() {

        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getName() {

        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    private String Name;
    private String Number;
    private String Date;
}
