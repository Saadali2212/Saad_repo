package com.example.saada.project_idea.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.saada.project_idea.activities.AddSmsActivity;
import com.example.saada.project_idea.adapters.SMSAdapter;
import com.example.saada.project_idea.BO.SmsBO;
import com.example.saada.project_idea.DataBase.DataBaseHandler;
import com.example.saada.project_idea.R;

import java.util.ArrayList;
import java.util.List;


public class Sms_list_fragment extends Fragment  {

    // public static String[] prgmNameList = {"Let Us C", "C++", "Java", "Jsp", "Microsoft .Net", "Android", "PHP", "Jquery", "JavaScript", "DLD", "English", "Computer Vision", "Software Engineering"};
    ListView listView;
    int count;
    Sms_list_fragment c;
    DataBaseHandler db;
    List<SmsBO> SmsBOArrayList=new ArrayList<>();
    SMSAdapter adapter;
    RelativeLayout r;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sms_list, container, false);
        listView = (ListView) view.findViewById(R.id.listView);
        r=(RelativeLayout)view.findViewById(R.id.layout);
        db=new DataBaseHandler(getContext());
        r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                r.setVisibility(View.INVISIBLE);
                Intent smsI=new Intent(getActivity(),AddSmsActivity.class);
                startActivity(smsI);
            }
        });
        return view;


    }

    @Override
    public void onResume() {
        super.onResume();
            SmsBOArrayList.clear();
            SmsBOArrayList=db.getAllSMS(); // reload the items from database
            if(SmsBOArrayList.size()!=0) {
                r.setVisibility(View.INVISIBLE);
                listView.setVisibility(View.VISIBLE);
            }
            adapter=new SMSAdapter(getActivity(), SmsBOArrayList);
             adapter.swapSMS(SmsBOArrayList);
            listView.setAdapter(adapter);
    }


}



