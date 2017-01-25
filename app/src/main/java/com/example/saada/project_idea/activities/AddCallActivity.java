package com.example.saada.project_idea.activities;

import android.app.AlarmManager;
import android.app.DialogFragment;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.saada.project_idea.BO.CallBO;
import com.example.saada.project_idea.DataBase.DataBaseHandler;
import com.example.saada.project_idea.Fragments.TimePickerFragment;
import com.example.saada.project_idea.R;
import com.example.saada.project_idea.Recievers.CallBroadcast;

import java.util.Calendar;


public class AddCallActivity extends AppCompatActivity implements View.OnClickListener{

    private Button bStart, bCancel, bTimeSelect;
    ImageButton bPhone;
    static final int TIME_DIALOG_ID = 1;
    private static final int REQUEST_CODE = 1;
    Calendar c;
    public int year, month, day, hour, minute;
    private int mHour, mMinute;
    private AlarmManager aManager;
    private PendingIntent pIntent;
    public String sPhone;
    private EditText etPhone;
    int count = 0;
    Toolbar toolbar;
    String number;
    String names;
    String com;
    DataBaseHandler db;



    public AddCallActivity(){
        c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
    }

    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message m){
            /** Creating a bundle object to pass currently set Time to the fragment */
            Bundle b = m.getData();
            /** Getting the Hour of day from bundle */
            mHour = b.getInt("Hour");
            /** Getting the Minute of the hour from bundle */
            mMinute = b.getInt("Minute");
            /** Displaying a short time message containing time set by Time picker dialog fragment */

        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_add_call);
        etPhone = (EditText)findViewById(R.id.etPhone);

        bStart = (Button)findViewById(R.id.bStart);

        bCancel = (Button)findViewById(R.id.bCancel);

        bTimeSelect = (Button)findViewById(R.id.bTime);

        bPhone = (ImageButton)findViewById(R.id.bCPhone);
        toolbar=(Toolbar)findViewById(R.id.my_tool);

        bStart.setOnClickListener(this);
        bCancel.setOnClickListener(this);
        bPhone.setOnClickListener(this);
        bTimeSelect.setOnClickListener(this);
        toolbar.setTitle("Call ");
        db=new DataBaseHandler(getApplication());
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
            sPhone = etPhone.getText().toString();
            Intent i = new Intent(getApplication(), CallBroadcast.class);
            i.putExtra("exPhone", sPhone);
            pIntent = PendingIntent.getBroadcast(getApplication(), count++, i, PendingIntent.FLAG_UPDATE_CURRENT);
            aManager = (AlarmManager) getApplication().getSystemService(Context.ALARM_SERVICE);

            hour=mHour;
            minute = mMinute;
            com=hour+"-"+minute;
            c.setTimeInMillis(System.currentTimeMillis());

            c.set(Calendar.HOUR_OF_DAY, hour);

            c.set(Calendar.MINUTE, minute);


            aManager.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pIntent);

            Toast.makeText(getApplication(), "call scheduled! " , Toast.LENGTH_SHORT).show();
            SaveCallData(names,number,com);



        }
        else if (v.getId() == R.id.bCancel) {
            aManager.cancel(pIntent);

            Toast.makeText(getApplication(), "Cancel", Toast.LENGTH_SHORT).show();
            finish();
        }
        else if (v.getId() == R.id.bTime) {
            DialogFragment newFragment = new TimePickerFragment(mHandler);
            newFragment.show(getFragmentManager(), "timePicker");
        }
    }
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
                names=cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                etPhone.setText(number);

            }

        }

    }
    public void SaveCallData(String n,String nu,String dat ){
        long check= db.addCall(new CallBO(n,nu,dat));
        if(check!= -1)
            Toast.makeText(getApplication(), "Data Added", Toast.LENGTH_SHORT).show();

        else
            Toast.makeText(getApplication(), "Data Not Added", Toast.LENGTH_SHORT).show();
            finish();
    }


}
