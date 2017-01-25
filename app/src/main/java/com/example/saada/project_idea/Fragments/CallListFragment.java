package com.example.saada.project_idea.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.saada.project_idea.activities.AddCallActivity;
import com.example.saada.project_idea.adapters.CallAdapter;
import com.example.saada.project_idea.BO.CallBO;
import com.example.saada.project_idea.DataBase.DataBaseHandler;
import com.example.saada.project_idea.R;

import java.util.ArrayList;
import java.util.List;

public class CallListFragment extends Fragment {

    ListView listView;
    int count;
    Sms_list_fragment c;
    DataBaseHandler db;
    List<CallBO> CallBOArrayList = new ArrayList<>();
    CallAdapter adapter;
    RelativeLayout r;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_call_list, container, false);
        listView = (ListView) view.findViewById(R.id.listView);
        r=(RelativeLayout)view.findViewById(R.id.layout);
        db = new DataBaseHandler(getContext());
        r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                r.setVisibility(View.INVISIBLE);
                Intent smsI=new Intent(getActivity(),AddCallActivity.class);
                startActivity(smsI);
            }
        });
        return view;


    }

    @Override
    public void onResume() {
        super.onResume();
        CallBOArrayList.clear();
        CallBOArrayList = db.getAllCall(); // reload the items from database
        if(CallBOArrayList.size()!=0) {
            r.setVisibility(View.INVISIBLE);
            listView.setVisibility(View.VISIBLE);
        }
        adapter = new CallAdapter(getActivity(), CallBOArrayList);
        adapter.swapSMS(CallBOArrayList);
        listView.setAdapter(adapter);

    }
}
