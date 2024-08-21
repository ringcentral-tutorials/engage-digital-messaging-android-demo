package com.dimelo.sampleapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.dimelo.dimelosdk.main.Dimelo;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class RcConfigurationActivity extends AppCompatActivity {
    private Dimelo dimelo;
    private SwitchCompat switchCompat;
    private TextInputLayout textInputLayout;
    private TextInputLayout userNameInputLayout;
    private TextInputLayout companyInputLayout;
    private TextInputLayout emailInputLayout;
    private TextInputLayout firstnameInputLayout;
    private TextInputLayout lastnameInputLayout;
    private TextInputLayout homePhoneInputLayout;
    private TextInputLayout mobilePhoneInputLayout;
    private ArrayList<RcSourceModel> allData;
    private RcSourceModel rcSelectedSource;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rc_configuration_activity);
        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        textInputLayout = findViewById(R.id.user_id);
        userNameInputLayout = findViewById(R.id.user_name);
        companyInputLayout = findViewById(R.id.company);
        emailInputLayout = findViewById(R.id.email);
        firstnameInputLayout = findViewById(R.id.firstname);
        lastnameInputLayout = findViewById(R.id.lastname);
        homePhoneInputLayout = findViewById(R.id.homephone);
        mobilePhoneInputLayout = findViewById(R.id.mobilephone);
        dimelo = Dimelo.getInstance();
        String userIdVal = RcConfig.getStringValueFromSharedPreference(this, RcConfig.RC_USER_ID);
        textInputLayout.getEditText().setText(userIdVal == null ? dimelo.getUserIdentifier() : userIdVal);

        String userNameVal = RcConfig.getStringValueFromSharedPreference(this, RcConfig.RC_USER_NAME_VAL);
        userNameInputLayout.getEditText().setText(userNameVal == null ? dimelo.getUserName() : userNameVal);

        String companyVal = RcConfig.getStringValueFromSharedPreference(this, RcConfig.RC_COMPANY_VAL);
        companyInputLayout.getEditText().setText(companyVal == null ? dimelo.getCompany() : companyVal);

        String emailVal = RcConfig.getStringValueFromSharedPreference(this, RcConfig.RC_EMAIL_VAL);
        emailInputLayout.getEditText().setText(emailVal == null ? dimelo.getEmail() : emailVal);

        String firstnameVal = RcConfig.getStringValueFromSharedPreference(this, RcConfig.RC_FIRSTNAME_VAL);
        firstnameInputLayout.getEditText().setText(firstnameVal == null ? dimelo.getFirstname() : firstnameVal);

        String lastnameVal = RcConfig.getStringValueFromSharedPreference(this, RcConfig.RC_LASTNAME_VAL);
        lastnameInputLayout.getEditText().setText(lastnameVal == null ? dimelo.getLastname() : lastnameVal);

        String homePhoneVal = RcConfig.getStringValueFromSharedPreference(this, RcConfig.RC_HOME_PHONE_VAL);
        homePhoneInputLayout.getEditText().setText(homePhoneVal == null ? dimelo.getHomePhone() : homePhoneVal);

        String mobilePhoneVal = RcConfig.getStringValueFromSharedPreference(this, RcConfig.RC_MOBILE_PHONE_VAL);
        mobilePhoneInputLayout.getEditText().setText(mobilePhoneVal == null ? dimelo.getUserIdentifier() : mobilePhoneVal);

        switchCompat = findViewById(R.id.thread);
        switchCompat.setChecked(RcConfig.getBooleanValueFromSharedPreference(this, RcConfig.RC_THREAD_ENABLED));
        allData =  RcSourceModel.listData;
        RecyclerView recycleView = findViewById(R.id.listView);
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        RcSourceAdapter rcConfigAdaptater = new RcSourceAdapter(allData, this);
        recycleView.setAdapter(rcConfigAdaptater);
        rcConfigAdaptater.setOnItemClickListener(new RcSourceAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, RcSourceModel rcModel) {
                rcSelectedSource = rcModel;
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        setResult(RESULT_OK);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    public void update(View v) {
        RcConfig.savedBooleanInsharedPreference(this, RcConfig.RC_THREAD_ENABLED, switchCompat.isChecked());
        Dimelo.getInstance().setThreadsEnabled(switchCompat.isChecked());

        if (textInputLayout.getEditText() != null && !textInputLayout.getEditText().getText().toString().contains(" ")) {
            RcConfig.savedStringInSharedPreference(this, RcConfig.RC_USER_ID, textInputLayout.getEditText().getText().toString());
            dimelo.setUserIdentifier(textInputLayout.getEditText().getText().toString());
        }

        if (userNameInputLayout.getEditText() != null && !userNameInputLayout.getEditText().getText().toString().contains(" ")) {
            RcConfig.savedStringInSharedPreference(this, RcConfig.RC_USER_NAME_VAL, userNameInputLayout.getEditText().getText().toString());
            dimelo.setUserName(userNameInputLayout.getEditText().getText().toString());
        }

        if (companyInputLayout.getEditText() != null && !companyInputLayout.getEditText().getText().toString().contains(" ")) {
            RcConfig.savedStringInSharedPreference(this, RcConfig.RC_COMPANY_VAL, companyInputLayout.getEditText().getText().toString());
            dimelo.setCompany(companyInputLayout.getEditText().getText().toString());
        }

        if (emailInputLayout.getEditText() != null && !emailInputLayout.getEditText().getText().toString().contains(" ")) {
            RcConfig.savedStringInSharedPreference(this, RcConfig.RC_EMAIL_VAL, emailInputLayout.getEditText().getText().toString());
            dimelo.setEmail(emailInputLayout.getEditText().getText().toString());
        }

        if (firstnameInputLayout.getEditText() != null && !firstnameInputLayout.getEditText().getText().toString().contains(" ")) {
            RcConfig.savedStringInSharedPreference(this, RcConfig.RC_FIRSTNAME_VAL, firstnameInputLayout.getEditText().getText().toString());
            dimelo.setFirstname(firstnameInputLayout.getEditText().getText().toString());
        }

        if (lastnameInputLayout.getEditText() != null && !lastnameInputLayout.getEditText().getText().toString().contains(" ")) {
            RcConfig.savedStringInSharedPreference(this, RcConfig.RC_LASTNAME_VAL, lastnameInputLayout.getEditText().getText().toString());
            dimelo.setLastname(lastnameInputLayout.getEditText().getText().toString());
        }

        if (homePhoneInputLayout.getEditText() != null && !homePhoneInputLayout.getEditText().getText().toString().contains(" ")) {
            RcConfig.savedStringInSharedPreference(this, RcConfig.RC_HOME_PHONE_VAL, homePhoneInputLayout.getEditText().getText().toString());
            dimelo.setHomePhone(homePhoneInputLayout.getEditText().getText().toString());
        }

        if (mobilePhoneInputLayout.getEditText() != null && !mobilePhoneInputLayout.getEditText().getText().toString().contains(" ")) {
            RcConfig.savedStringInSharedPreference(this, RcConfig.RC_MOBILE_PHONE_VAL, mobilePhoneInputLayout.getEditText().getText().toString());
            dimelo.setMobilePhone(mobilePhoneInputLayout.getEditText().getText().toString());
        }

        if (rcSelectedSource != null) {
            RcConfig.savedStringInSharedPreference(this, RcConfig.RC_SOURCE_NAME, RcSourceModel.objectToJson(rcSelectedSource));
            RcConfig.setupDimelo(this);
        }
        setResult(RESULT_CANCELED);
        finish();
    }
}
