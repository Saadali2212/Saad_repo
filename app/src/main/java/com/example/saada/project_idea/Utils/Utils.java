package com.example.saada.project_idea.Utils;

import android.content.Context;
import android.content.res.TypedArray;

import com.example.saada.project_idea.R;

/**
 * Created by saada on 12/28/2016.
 */

public class Utils {
    //Email Validation pattern
    public static final String regEx = "\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}\b";

    //Fragments Tags
    public static final String Login_Fragment = "Login_Fragment";
    public static final String SignUp_Fragment = "SignUp_Fragment";
    public static final String ForgotPassword_Fragment = "ForgotPassword_Fragment";


    public static int getToolbarHeight(Context context) {

        final TypedArray styledAttributes = context.getTheme().obtainStyledAttributes(

                new int[]{R.attr.actionBarSize});

        int toolbarHeight = (int) styledAttributes.getDimension(0, 0);

        styledAttributes.recycle();



        return toolbarHeight;

    }



    public static int getTabsHeight(Context context) {

        return (int) context.getResources().getDimension(R.dimen.tabsHeight);

    }

}
