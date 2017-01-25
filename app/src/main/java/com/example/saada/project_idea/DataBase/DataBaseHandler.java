package com.example.saada.project_idea.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.saada.project_idea.BO.CallBO;
import com.example.saada.project_idea.BO.SmsBO;
import com.example.saada.project_idea.BO.UserBO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by saada on 1/5/2017.
 */

public class DataBaseHandler extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 4;

    // Database Name
    private static final String DATABASE_NAME = "ProjectDatabase";
    // SMS table name
    private static final String TABLE_SMS = "SMS";
    // SMS Table Columns names
    private static final String KEY_ID = "ID";
    private static final String KEY_NAME = "NAME";
    private static final String KEY_Number = "PHONENUMBER";
    private static final String KEY_Date = "DATETIME";
    private static final String KEY_Sms = "SMSTEXT";


    //  User table name
    private static final String TABLE_USER = "USER";
    //  User Columns names
    private static final String KEY_UID = "ID";
    private static final String KEY_UNAME = "NAME";
    private static final String KEY_UEMAIL = "EMAIL";
    private static final String KEY_UNUMBER = "NUMBER";
    private static final String KEY_UPASSWORD = "PASSWORD";
    private static final String KEY_UCONFIRMPASSWORD = "CPASSWORD";

    private static final String TABLE_CALL = "CALL";
    // SMS Table Columns names
    private static final String KEY_CID = "ID";
    private static final String KEY_CNAME = "NAME";
    private static final String KEY_CNumber = "PHONENUMBER";
    private static final String KEY_CDate = "DATETIME";


    public DataBaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
  
    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_SMS_TABLE = "CREATE TABLE " + TABLE_SMS + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME + " TEXT," + KEY_Number + " TEXT," + KEY_Date +" TEXT,"+ KEY_Sms +" TEXT"+" )";
        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "(" + KEY_UID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_UNAME + " TEXT," + KEY_UEMAIL + " TEXT," + KEY_UNUMBER +" TEXT,"+ KEY_UPASSWORD +" TEXT,"+ KEY_UCONFIRMPASSWORD +" TEXT"+" )";
        String CREATE_CALL_TABLE = "CREATE TABLE " + TABLE_CALL + "(" + KEY_CID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_CNAME + " TEXT," + KEY_CNumber + " TEXT," + KEY_CDate +" TEXT"+" )";

        db.execSQL(CREATE_SMS_TABLE);
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_CALL_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SMS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CALL);

        // Create tables again
        onCreate(db);
    }
    public long addSms(SmsBO smsBO) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, smsBO.getName()); //  Name
        values.put(KEY_Number, smsBO.getNumber()); //  Number
        values.put(KEY_Date, smsBO.getDate()); // Date
        values.put(KEY_Sms, smsBO.getMessage()); // message
        // Inserting Row

        long check= db.insert(TABLE_SMS, null, values);


        db.close(); // Closing database connection
        return check;
    }
    // Getting single Sms
    public Cursor getSms(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from contacts where id="+id+"", null );
        return res;
    }
    // Getting All Sms
    public List<SmsBO> getAllSMS() {
        List<SmsBO> SmsList = new ArrayList<SmsBO>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_SMS;
       SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
       // looping through all rows and adding to list
       if (cursor.moveToFirst()) {
            do {
            SmsBO smsBO = new SmsBO();
                smsBO.setName(cursor.getString(1));
                smsBO.setNumber(cursor.getString(2));
                smsBO.setDate(cursor.getString(3));
                smsBO.setMessage(cursor.getString(4));

                // Adding contact to list
                SmsList.add(smsBO);
                } while (cursor.moveToNext());
            }

        return SmsList;
    }
    // Updating single Sms
    public boolean updateSms(Integer id, String name, String phone, String date,String msg) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", KEY_NAME);
        contentValues.put("phone", KEY_Number);
        contentValues.put("email", KEY_Date);
        contentValues.put("street", KEY_Sms);
        db.update("contacts", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }
    // Deleting single Sms
    public Integer deleteSms(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("contacts",
                "id = ? ",
                new String[] { Integer.toString(id) });
    }


    public long adduser(UserBO userBO) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues valuesuser = new ContentValues();
        valuesuser.put(KEY_NAME, userBO.getUserName()); //  Name
        valuesuser.put(KEY_UEMAIL, userBO.getEmail()); //  Number
        valuesuser.put(KEY_UNUMBER, userBO.getPhone()); // Date
        valuesuser.put(KEY_UPASSWORD, userBO.getPassword()); // message
        valuesuser.put(KEY_UCONFIRMPASSWORD, userBO.getCpassword()); // message

        // Inserting Row
        long check= db.insert(TABLE_USER, null, valuesuser);


        db.close(); // Closing database connection
        return check;
    }
    public Cursor getUser(String email,String pass) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "SELECT * FROM USER WHERE "+ KEY_UEMAIL+"="+"'"+email+"'" +" and "+ KEY_UPASSWORD + " = "+ "'"+pass+"'"+"", null );
        return res;

    }

    public long addCall(CallBO callBO) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_CNAME, callBO.getName()); //  Name
        values.put(KEY_CNumber, callBO.getNumber()); //  Number
        values.put(KEY_CDate, callBO.getDate()); // Date
        // Inserting Row

        long check= db.insert(TABLE_CALL, null, values);


        db.close(); // Closing database connection
        return check;
    }
    public List<CallBO> getAllCall() {
        List<CallBO> CallList = new ArrayList<CallBO>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_CALL;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                CallBO callBO = new CallBO();
                callBO.setName(cursor.getString(1));
                callBO.setNumber(cursor.getString(2));
                callBO.setDate(cursor.getString(3));

                // Adding contact to list
                CallList.add(callBO);
            } while (cursor.moveToNext());
        }

        return CallList;
    }









}
