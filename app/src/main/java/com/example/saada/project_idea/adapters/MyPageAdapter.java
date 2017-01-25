package com.example.saada.project_idea.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.saada.project_idea.BO.SmsBO;
import com.example.saada.project_idea.Fragments.CallListFragment;
import com.example.saada.project_idea.Fragments.Contact_list_fragment;
import com.example.saada.project_idea.Fragments.Sms_list_fragment;

import java.util.List;


public class MyPageAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    List<SmsBO> listsms;

    public MyPageAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;


    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                Sms_list_fragment frag1 = new Sms_list_fragment();
                return frag1;
            case 1:
                CallListFragment frag2 = new CallListFragment();
                return frag2;
            case 2:
                Contact_list_fragment frag3 = new Contact_list_fragment();
                return frag3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
