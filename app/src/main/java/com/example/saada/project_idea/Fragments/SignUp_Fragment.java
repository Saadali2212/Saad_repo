package com.example.saada.project_idea.Fragments;


import java.util.regex.Matcher;
import java.util.regex.Pattern;


import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.saada.project_idea.activities.MainContainer;
import com.example.saada.project_idea.BO.UserBO;
import com.example.saada.project_idea.DataBase.DataBaseHandler;
import com.example.saada.project_idea.R;
import com.example.saada.project_idea.Utils.CustomToast;
import com.example.saada.project_idea.Utils.Utils;

public class SignUp_Fragment extends Fragment implements OnClickListener {
    private static View view;
    private static EditText fullName, emailId, mobileNumber, password, confirmPassword;
    private static TextView login;
    private static Button signUpButton;
    private static CheckBox terms_conditions;
    private DataBaseHandler db;
    Boolean flag;

    public SignUp_Fragment() {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.signup_layout, container, false);
        initViews();
        setListeners();
        return view;
    }

    private void initViews() {
        fullName = (EditText) view.findViewById(R.id.fullName);
        emailId = (EditText) view.findViewById(R.id.userEmailId);
        mobileNumber = (EditText) view.findViewById(R.id.mobileNumber);
        password = (EditText) view.findViewById(R.id.password);
        confirmPassword = (EditText) view.findViewById(R.id.confirmPassword);
        signUpButton = (Button) view.findViewById(R.id.signUpBtn);
        login = (TextView) view.findViewById(R.id.already_user);
        terms_conditions = (CheckBox) view.findViewById(R.id.terms_conditions);
        db=new DataBaseHandler(getContext());
        XmlResourceParser xrp = getResources().getXml(R.drawable.text_selector);
        try {
            ColorStateList csl = ColorStateList.createFromXml(getResources(),
                    xrp);
            login.setTextColor(csl);
            terms_conditions.setTextColor(csl);
        } catch (Exception e) {
        }
    }

    // Set Listeners
    private void setListeners() {
        signUpButton.setOnClickListener(this);
        login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signUpBtn:
                Boolean a;
                // Call checkValidation method
                a=checkValidation();
                if (a==true) {
                    new MainContainer().replaceLoginFragment();
                }
                break;

            case R.id.already_user:

                // Replace login fragment
                new MainContainer().replaceLoginFragment();
                break;
        }

    }

    // Check Validation Method
    private Boolean checkValidation() {

        // Get all edittext texts
        String getFullName = fullName.getText().toString();
        String getEmailId = emailId.getText().toString();
        String getMobileNumber = mobileNumber.getText().toString();
        String getPassword = password.getText().toString();
        String getConfirmPassword = confirmPassword.getText().toString();
        UserBO user=new UserBO();
        user.setUserName(getFullName);
        user.setEmail(getEmailId);
        user.setPhone(getMobileNumber);
        user.setPassword(getPassword);
        user.setCpassword(getConfirmPassword);
        // Pattern match for email id
        Pattern p = Pattern.compile(Utils.regEx);
        Matcher m = p.matcher(getEmailId);

        // Check if all strings are null or not
        if (getFullName.equals("") || getFullName.length() == 0
                && getEmailId.equals("") || getEmailId.length() == 0
                && getMobileNumber.equals("") || getMobileNumber.length() == 0
                && getPassword.equals("") || getPassword.length() == 0
                && getConfirmPassword.equals("")
                && getConfirmPassword.length() == 0
                ) {

            new CustomToast().Show_Toast(getActivity(), view,
                    "All fields are required.");
            flag=false;
        }
   /*         // Check if email id valid or not
        else if (!m.find())
            new CustomToast().Show_Toast(getActivity(), view,
                    "Your Email Id is Invalid.");*/

            // Check if both password should be equal
        else if (!getConfirmPassword.equals(getPassword)) {
            new CustomToast().Show_Toast(getActivity(), view,
                    "Both password doesn't match.");
            flag=false;
        }
            // Make sure user should check Terms and Conditions checkbox
        else if (!terms_conditions.isChecked()) {
            new CustomToast().Show_Toast(getActivity(), view,
                    "Please select Terms and Conditions.");
            flag=false;
        }
            // Else do signup or do your stuff
        else {
                long check=db.adduser(user);
            if(check!= -1)
                new CustomToast().Show_Toast(getActivity(), view,
                        "User Registered");
            else
                new CustomToast().Show_Toast(getActivity(), view,
                        "User Not Registered");


            flag=true;
        }
        return flag;
    }

}