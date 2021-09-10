package com.dimelo.sampleapp;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Switch;

import com.dimelo.dimelosdk.main.Dimelo;

public class RcConfigurationActivity extends AppCompatActivity {
    private Dimelo dimelo;
    private Switch switchCompat;
    private TextInputLayout textInputLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rc_configuration_activity);
        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        textInputLayout = findViewById(R.id.user_id);
        dimelo = Dimelo.getInstance();
        String userIdVal = RcConfig.getStringValueFromSharedPreference(this, RcConfig.RC_USER_ID);
        textInputLayout.getEditText().setText(userIdVal == null ? dimelo.getUserIdentifier() : userIdVal);
        switchCompat = findViewById(R.id.thread);
        switchCompat.setChecked(RcConfig.getBooleanValueFromSharedPreference(this, RcConfig.RC_THREAD_ENABLED));
    }

    @Override
    public boolean onSupportNavigateUp() {
        setResult(RESULT_OK);
        finish();
        return true;
    }

    public void update(View v) {
        RcConfig.savedBooleanInsharedPreference(this, RcConfig.RC_THREAD_ENABLED, switchCompat.isChecked());
        Dimelo.getInstance().setThreadsEnabled(switchCompat.isChecked());

        if (textInputLayout.getEditText().getText().toString() != null && !textInputLayout.getEditText().getText().toString().contains(" ")) {
            RcConfig.savedStringInSharedPreference(this, RcConfig.RC_USER_ID, textInputLayout.getEditText().getText().toString());
            dimelo.setUserIdentifier(textInputLayout.getEditText().getText().toString());
        }
        setResult(RESULT_CANCELED);
        finish();
    }
}
