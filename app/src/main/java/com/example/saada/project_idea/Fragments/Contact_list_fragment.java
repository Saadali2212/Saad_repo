package com.example.saada.project_idea.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.saada.project_idea.adapters.Contacts_adapter;
import com.example.saada.project_idea.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Contact_list_fragment extends Fragment {

    ListView list;
    String namecsv="";
    String phonecsv="";
    String nameArray[];
    String phoneArray[];


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_contact_list_fragment, container, false);
        list=(ListView)view.findViewById(R.id.listView_contacts);
        //getContactsData();
        LoadContactsAysc aysc=new LoadContactsAysc();
        aysc.execute();
        return view;
    }

    public void getContactsData(){
        Cursor phones=getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,null);
        while (phones.moveToFirst()){

            String name=phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String number=phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));


            if(name!=null){
                namecsv +=name + ",";
                phonecsv +=number + ",";
            }
        }
        phones.close();
        nameArray= namecsv.split(",");
        phoneArray= phonecsv.split(",");
    }


   class LoadContactsAysc extends AsyncTask<Void, Void, ArrayList<String>>

    {
        ProgressDialog pd;
        Context context;

        @Override
        protected void onPreExecute() {
        super.onPreExecute();
        pd = ProgressDialog.show(getActivity() , "Loading Contacts","Please Wait");
    }


        @Override
        protected ArrayList<String> doInBackground(Void... params) {
        ArrayList<String> contacts = new ArrayList<String>();
        Cursor c = getActivity().getContentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        while (c.moveToNext()) {
            String contactName = c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phNumber = c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

            contacts.add(contactName + ":" + phNumber);
        }
        c.close();

        return contacts;
    }
        @Override
        protected void onPostExecute(final ArrayList<String> contacts) {
        super.onPostExecute(contacts);
        pd.cancel();

            Set<String> hs = new HashSet<>();
            hs.addAll(contacts);
            contacts.clear();
            contacts.addAll(hs);
            Collections.sort(contacts, String.CASE_INSENSITIVE_ORDER);
            list.setAdapter(new Contacts_adapter(getActivity(),contacts));
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Toast.makeText(getActivity(),contacts.get(position),Toast.LENGTH_SHORT).show();
                }
            });

    }
    }

}

