package com.example.saada.project_idea.Fragments;


import java.util.regex.Matcher;
import java.util.regex.Pattern;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.database.Cursor;
import android.os.Bundle;
import android.content.SharedPreferences.Editor;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.saada.project_idea.activities.DashboardActivity;
import com.example.saada.project_idea.BO.UserBO;
import com.example.saada.project_idea.DataBase.DataBaseHandler;
import com.example.saada.project_idea.R;
import com.example.saada.project_idea.Utils.CustomToast;
import com.example.saada.project_idea.Utils.SessionManager;
import com.example.saada.project_idea.Utils.Utils;

public class Login_Fragment extends Fragment implements OnClickListener {
    private static View view;

    private static EditText emailid, password;
    private static Button loginButton;
    private static TextView forgotPassword, signUp;
    private static CheckBox show_hide_password;
    private static LinearLayout loginLayout;
    private static Animation shakeAnimation;
    private static FragmentManager fragmentManager;
    Activity activity;
    private DataBaseHandler dataBaseHandler;
    String getEmailId,getPassword;
    SessionManager session;
    UserBO userBO;


    public Login_Fragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.login_layout, container, false);
        userBO=new UserBO();
        activity=getActivity();
        session=new SessionManager(getContext());
        if (session.loggedin()){
            startActivity(new Intent(getActivity(), DashboardActivity.class));
        }
        initViews();
        setListeners();
        return view;
    }

    // Initiate Views
    private void initViews() {
        fragmentManager = getActivity().getSupportFragmentManager();

        emailid = (EditText) view.findViewById(R.id.login_emailid);
        password = (EditText) view.findViewById(R.id.login_password);
        loginButton = (Button) view.findViewById(R.id.loginBtn);
        forgotPassword = (TextView) view.findViewById(R.id.forgot_password);
        signUp = (TextView) view.findViewById(R.id.createAccount);
        show_hide_password = (CheckBox) view
                .findViewById(R.id.show_hide_password);
        loginLayout = (LinearLayout) view.findViewById(R.id.login_layout);


        // Load ShakeAnimation
        shakeAnimation = AnimationUtils.loadAnimation(getActivity(),
                R.anim.shake);
        dataBaseHandler=new DataBaseHandler(getContext());


        // Setting text selector over textviews
        XmlResourceParser xrp = getResources().getXml(R.drawable.text_selector);
        try {
            ColorStateList csl = ColorStateList.createFromXml(getResources(),xrp);
            forgotPassword.setTextColor(csl);
            show_hide_password.setTextColor(csl);
            signUp.setTextColor(csl);
        } catch (Exception e) {
        }
    }

    // Set Listeners
    private void setListeners() {
        loginButton.setOnClickListener(this);
        forgotPassword.setOnClickListener(this);
        signUp.setOnClickListener(this);

        // Set check listener over checkbox for showing and hiding password
        show_hide_password.setOnCheckedChangeListener(new OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(CompoundButton button,
                                                 boolean isChecked) {

                        // If it is checkec then show password else hide
                        // password
                        if (isChecked) {

                            show_hide_password.setText(R.string.hide_pwd);// change
                            // checkbox
                            // text

                            password.setInputType(InputType.TYPE_CLASS_TEXT);
                            password.setTransformationMethod(HideReturnsTransformationMethod
                                    .getInstance());// show password
                        } else {
                            show_hide_password.setText(R.string.show_pwd);// change
                            // checkbox
                            // text

                            password.setInputType(InputType.TYPE_CLASS_TEXT
                                    | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            password.setTransformationMethod(PasswordTransformationMethod
                                    .getInstance());// hide password

                        }

                    }
                });
    }

    @Override
    public void onClick(View v) {
        Boolean a=checkValidation();
        switch (v.getId()) {
            case R.id.loginBtn:
                if(a ==true){
                    login(getEmailId,getPassword);
                }

                break;

            case R.id.forgot_password:

                // Replace forgot password fragment with animation
                fragmentManager.beginTransaction().setCustomAnimations(R.anim.right_enter, R.anim.left_out).replace(R.id.frameContainer,
                                new ForgotPassword_Fragment(),Utils.ForgotPassword_Fragment).commit();
                break;
            case R.id.createAccount:

                // Replace signup frgament with animation
                fragmentManager
                        .beginTransaction()
                        .setCustomAnimations(R.anim.right_enter, R.anim.left_out)
                        .replace(R.id.frameContainer, new SignUp_Fragment(),
                                Utils.SignUp_Fragment).commit();
                break;
        }

    }

    // Check Validation before login
    private Boolean checkValidation() {
        boolean flag;
        // Get email id and password
        getEmailId = emailid.getText().toString();
        getPassword = password.getText().toString();


        // Check patter for email id
        Pattern p = Pattern.compile(Utils.regEx);
        Matcher m = p.matcher(getEmailId);

        // Check for both field is empty or not
        if (getEmailId.equals("") || getEmailId.length() == 0
                || getPassword.equals("") || getPassword.length() == 0) {
            loginLayout.startAnimation(shakeAnimation);
            new CustomToast().Show_Toast(getActivity(), view,
                    "Enter both credentials.");
            flag=false;

        }
        else{
            flag=true;
        }
        return flag;

    }
    private void login(String email,String pass){
        Cursor c=dataBaseHandler.getUser(email,pass);
        if(c != null && c.moveToFirst()){
            String dbemail= c.getString(c.getColumnIndex("EMAIL"));
            String dbpassword= c.getString(c.getColumnIndex("PASSWORD"));
            String dbnumber= c.getString(c.getColumnIndex("NUMBER"));
          String dbname= c.getString(c.getColumnIndex("NAME"));

            SharedPreferences pref = getContext().getSharedPreferences("Options", Context.MODE_PRIVATE);
            Editor editor=pref.edit();
            editor.putString("nameo",dbname);
            editor.putString("emailo",dbemail);
            editor.putString("numbero",dbnumber);
            editor.commit();

            if (email.equals(dbemail) && pass.equals(dbpassword)) {
                session.setLoggedIn(true);
                Intent i = new Intent(getActivity(), DashboardActivity.class);

                getActivity().startActivity(i);
                getActivity().overridePendingTransition(R.anim.right_enter, R.anim.left_out);
                Toast.makeText(getContext(),"Welcome "+dbname,Toast.LENGTH_SHORT).show();

            }

            c.close();
        }
        else {
            new CustomToast().Show_Toast(getActivity(), view,
                    "User Not Registered");
        }

    }
}