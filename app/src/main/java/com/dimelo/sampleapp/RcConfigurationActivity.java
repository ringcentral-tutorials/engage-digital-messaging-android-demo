package com.dimelo.sampleapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.dimelo.dimelosdk.main.Dimelo;

import java.util.ArrayList;

public class RcConfigurationActivity extends AppCompatActivity {
    private Dimelo dimelo;
    private SwitchCompat switchCompat;
    private TextInputLayout textInputLayout;
    private ArrayList<RcSourceModel> allData;
    private RcSourceModel rcSourceSelected;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rc_configuration_activity);
        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        textInputLayout = findViewById(R.id.user_id);
        dimelo = Dimelo.getInstance();
        String userIdVal = RcConfig.getStringValueFromSharedPreference(this, RcConfig.RC_USER_ID);
        textInputLayout.getEditText().setText(userIdVal == null ? dimelo.getUserIdentifier() : userIdVal);
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
                rcSourceSelected = rcModel;
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

        if (textInputLayout.getEditText().getText().toString() != null && !textInputLayout.getEditText().getText().toString().contains(" ")) {
            RcConfig.savedStringInSharedPreference(this, RcConfig.RC_USER_ID, textInputLayout.getEditText().getText().toString());
            dimelo.setUserIdentifier(textInputLayout.getEditText().getText().toString());
        }

        if (rcSourceSelected != null) {
            RcConfig.savedStringInSharedPreference(this, RcConfig.RC_SOURCE_NAME, RcSourceModel.objectToJson(rcSourceSelected));
            RcConfig.setupDimelo(this);
        }
        setResult(RESULT_CANCELED);
        finish();
    }
}
