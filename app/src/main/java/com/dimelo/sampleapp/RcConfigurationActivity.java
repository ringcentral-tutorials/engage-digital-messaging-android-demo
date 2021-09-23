package com.dimelo.sampleapp;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.dimelo.dimelosdk.main.Dimelo;

import java.util.ArrayList;

public class RcConfigurationActivity extends AppCompatActivity {
    private Dimelo dimelo;
    private SwitchCompat switchCompat;
    private TextInputLayout textInputLayout;
    private ArrayList<RcSourceModel> allData;
    private RcSourceModel confSelected;


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
        allData =  RcSourceModel.listData;
        RecyclerView recycleView = findViewById(R.id.listView);
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        RcSourceAdaptater rcConfigAdaptater = new RcSourceAdaptater(allData, this);
        recycleView.setAdapter(rcConfigAdaptater);
        rcConfigAdaptater.setOnItemClickListener(new RcSourceAdaptater.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, RcSourceModel rcConf) {
                confSelected = rcConf;
            }
        });
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

        if (confSelected != null) {
            RcConfig.savedStringInSharedPreference(this, RcConfig.RC_CONF_NAME, RcSourceModel.objectToJson(confSelected));
            RcConfig.setupDimelo(this);
        }
        setResult(RESULT_CANCELED);
        finish();
    }
}
