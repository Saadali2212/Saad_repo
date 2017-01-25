package com.example.saada.project_idea.Utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by saada on 1/7/2017.
 */

public class SessionManager {
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Context context;

    public SessionManager(Context context) {
        preferences = context.getSharedPreferences("UserSessions", Context.MODE_PRIVATE);
        editor = preferences.edit();
        this.context = context;
    }
    public void setLoggedIn(boolean loggedIn){
        editor.putBoolean("LoggedInMode",loggedIn);
        editor.commit();
    }
    public boolean loggedin(){
        return preferences.getBoolean("LoggedInMode",false);
    }




}
