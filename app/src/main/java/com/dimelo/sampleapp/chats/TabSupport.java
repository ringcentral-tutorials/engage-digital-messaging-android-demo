package com.dimelo.sampleapp.chats;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dimelo.dimelosdk.main.RcFragment;
import com.dimelo.dimelosdk.main.Dimelo;
import com.dimelo.sampleapp.R;

public class TabSupport extends Fragment implements SampleDimeloTab {
    RcFragment mDimeloChat;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tab_support,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        FragmentManager childFragmentManager = getChildFragmentManager();
        mDimeloChat = (RcFragment)childFragmentManager.findFragmentByTag("dimelo_support_chat");
        if (mDimeloChat == null) {
            mDimeloChat = Dimelo.getInstance().newRcFragment();
            FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.chat_support_container, mDimeloChat, "dimelo_support_chat");
            fragmentTransaction.commit();
        }

        customize();
    }


    private void customize(){
        RcFragment.Customization customisation = mDimeloChat.getCustomization();
        customisation.backgroundColor = ContextCompat.getColor(getContext(), R.color.blue_400);
        customisation.inputbarBackgroundColor = ContextCompat.getColor(getContext(), R.color.blue_50);
        customisation.userMessageBackgroundColor = Color.WHITE;
        customisation.userMessageTextColor = Color.BLACK;
        customisation.agentMessageBackgroundColor = Color.WHITE;
        customisation.agentMessageTextColor = Color.BLACK;
        customisation.agentNameColor = Color.WHITE;
        customisation.agentTimeColor = Color.WHITE;
        customisation.systemMessageBackgroundColor = Color.WHITE;
        customisation.systemMessageTextColor = Color.BLACK;
        customisation.welcomeMessageTextColor = Color.RED;
        customisation.dateTextColor = Color.WHITE;
        customisation.hourTimeTextColor = Color.WHITE;
        customisation.apply();
    }

    @Override
    public boolean isHandlingBack() {
        return false;
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onDestroyView() {
        super.onDestroy();
        mDimeloChat = null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // DimeloChat is a nested Fragment, we must notify it about onActivityResult
        mDimeloChat.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // DimeloChat is a nested Fragment, we must notify it about onRequestPermissionsResult
        mDimeloChat.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


}
