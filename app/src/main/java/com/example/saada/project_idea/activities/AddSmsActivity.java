package com.example.saada.project_idea.activities;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.example.saada.project_idea.BO.SmsBO;
import com.example.saada.project_idea.DataBase.DataBaseHandler;
import com.example.saada.project_idea.R;
import com.example.saada.project_idea.Recievers.SMSBroadcast;

import java.util.ArrayList;
import java.util.Calendar;


public class AddSmsActivity extends AppCompatActivity implements View.OnClickListener {

    private Button bStart, bCancel, bTimeSelect,date;
    ImageButton bPhone;
    static final int TIME_DIALOG_ID = 1;
    private static final int REQUEST_CODE = 1;
    Calendar c;
    public int  hour=0, minute=0;
    private int mHour, mMinute;
    private AlarmManager aManager;
    private PendingIntent pIntent;
    public String sPhone, sSms;
    private EditText etPhone, etSms;
    int coun = 0;
    Toolbar toolbar;
    String number;
    String names;
    String com;
    SmsBO data = new SmsBO();
    ArrayList<SmsBO> SmsList = new ArrayList<>();
    DataBaseHandler db;
    Bundle b;

    /*Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message m) {
            *//** Creating a bundle object to pass currently set Time to the fragment *//*
            b = m.getData();
            *//** Getting the Hour of day from bundle *//*
            h = b.getInt("Hour");
            *//** Getting the Minute of the hour from bundle *//*
            mi = b.getInt("Minute");

            *//** Displaying a short time message containing time set by Time picker dialog fragment *//*

        }
    };*/
    public AddSmsActivity() {
        // Assign current Date and Time Values to Variables
        c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);


    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_sms_fragment);
       initViews();
        bStart.setOnClickListener(this);
        bCancel.setOnClickListener(this);
        bPhone.setOnClickListener(this);
        bTimeSelect.setOnClickListener(this);
        date.setOnClickListener(this);


        toolbar.setTitle("Short Message Service (SMS) ");
        db = new DataBaseHandler(getApplication());
    }
    private void initViews(){
        etPhone = (EditText) findViewById(R.id.etPhone);
        etSms = (EditText) findViewById(R.id.etSms);
        bStart = (Button) findViewById(R.id.bStart);
        bCancel = (Button) findViewById(R.id.bCancel);
        bTimeSelect = (Button) findViewById(R.id.bTime);
        bPhone = (ImageButton) findViewById(R.id.bCPhone);
        date=(Button)findViewById(R.id.date);
        toolbar = (Toolbar) findViewById(R.id.my_tool);
    }
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bCPhone) {
            Uri uri = Uri.parse("content://contacts");
            Intent intent = new Intent(Intent.ACTION_PICK, uri);
            intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
            startActivityForResult(intent, REQUEST_CODE);
        }
        else if (v.getId() == R.id.bStart) {
            int a ,b;
            sPhone = etPhone.getText().toString();
            sSms = etSms.getText().toString();
            etSms.getText().clear();
            Intent i = new Intent(AddSmsActivity.this, SMSBroadcast.class);
            i.putExtra("exPhone", sPhone);
            i.putExtra("exSmS", sSms);
            pIntent = PendingIntent.getBroadcast(getApplicationContext(), coun++, i, PendingIntent.FLAG_UPDATE_CURRENT);
            aManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            c.setTimeInMillis(System.currentTimeMillis());
            a=hour;
            b=minute;
            c.set(Calendar.HOUR_OF_DAY, a);
            c.set(Calendar.MINUTE, b);
            aManager.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pIntent);
           /* hour = mHour;
            minute = mMinute;*/
            if (hour>12){
                com = hour + " : " + minute + " pm";
            }
            else {
                com = hour + " : " + minute + " am";

            }
            //Toast.makeText(getActivity(), "Sms scheduled! " + sSms, Toast.LENGTH_SHORT).show();
          SaveSmsData(names, number, com, sSms);



        } else if (v.getId() == R.id.bCancel) {
            aManager.cancel(pIntent);
            Toast.makeText(getApplication(), "Cancel", Toast.LENGTH_SHORT).show();
            finish();
        } else if (v.getId() == R.id.bTime) {
            /*DialogFragment newFragment = new TimePickerFragment(mHandler);
            newFragment.show(getFragmentManager(), "timePicker");*/
            showDialog(TIME_DIALOG_ID);
        }
        else if (v.getId() == R.id.date) {
           /* DialogFragment newFragment = new DatePickerFragment(mHandler);
            newFragment.show(getFragmentManager(), "datePicker");
        */}
    }

    //Choose phone in contact and set edit text

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent i) {

        // TODO Auto-generated method stub

        super.onActivityResult(requestCode, resultCode, i);


        if (requestCode == REQUEST_CODE) {

            if (resultCode == RESULT_OK) {
                Uri uri = i.getData();
                String[] projection = {ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME};
                Cursor cursor = getApplication().getContentResolver().query(uri, projection, null, null, null);
                cursor.moveToFirst();
                int numberColumnIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                number = cursor.getString(numberColumnIndex);
                names = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                etPhone.setText(number);

            }

        }

    }

    public void SaveSmsData(String n, String nu, String dat, String msg) {
        long check = db.addSms(new SmsBO(n, nu, dat, msg));
        if (check != -1) {
            Toast.makeText(getApplication(), "Data Added", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getApplication(), "Data Not Added", Toast.LENGTH_SHORT).show();
        }

    }

    private TimePickerDialog.OnTimeSetListener mTimeSetListener =

            new TimePickerDialog.OnTimeSetListener() {

                public void onTimeSet(TimePicker view, int hourOfDay, int min) {

                    hour = hourOfDay;

                    minute = min;

                    // Set the Selected Date in Select date Button

                    bTimeSelect.setText(hour+":"+minute);

                }

            };
    protected Dialog onCreateDialog(int id) {

        switch (id) {

            // create a new TimePickerDialog with values you want to show

            case TIME_DIALOG_ID:

                return new TimePickerDialog(this, mTimeSetListener, hour, minute, false);

        }

        return null;

    }

}
