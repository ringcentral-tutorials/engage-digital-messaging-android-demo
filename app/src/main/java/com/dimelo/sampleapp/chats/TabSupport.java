package com.dimelo.sampleapp.chats;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dimelo.dimelosdk.main.Chat;
import com.dimelo.dimelosdk.main.Dimelo;
import com.dimelo.sampleapp.R;

public class TabSupport extends Fragment implements SampleDimeloTab {
    Chat mDimeloChat;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tab_support,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        FragmentManager childFragmentManager = getChildFragmentManager();
        mDimeloChat = (Chat)childFragmentManager.findFragmentByTag("dimelo_support_chat");
        if (mDimeloChat == null) {
            mDimeloChat = Dimelo.getInstance().newChatFragment();
            FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.chat_support_container, mDimeloChat, "dimelo_support_chat");
            fragmentTransaction.commit();
        }

        customize();
    }


    private void customize(){

        Chat.Customization customisation = mDimeloChat.getCustomization();
        customisation.backgroundColor = getResources().getColor(R.color.blue_400);
        customisation.inputbarBackgroundColor = getResources().getColor(R.color.blue_50);

        customisation.setUserMessageBubbleDrawable(R.drawable.bank_user_bubble, Color.WHITE);
        customisation.setAgentMessageBubbleDrawable(R.drawable.bank_agent_bubble, Color.WHITE);
        customisation.setSystemMessageBubbleDrawable(R.drawable.bank_system_bubble, Color.WHITE);

        customisation.userMessageBackgroundColor = Color.WHITE;
        customisation.userMessageTextColor = Color.BLACK;

        customisation.agentMessageBackgroundColor = Color.WHITE;
        customisation.agentMessageTextColor = Color.BLACK;
        customisation.agentNameColor = Color.WHITE;

        customisation.systemMessageBackgroundColor = Color.WHITE;
        customisation.systemMessageTextColor = Color.WHITE;
        customisation.dateTextColor = Color.WHITE;

        customisation.userMessageBubblePadding = new Chat.Customization.Padding(convertDpToPixel(8), convertDpToPixel(4), convertDpToPixel(24), convertDpToPixel(4));
        customisation.agentMessageBubblePadding = new Chat.Customization.Padding(convertDpToPixel(24), convertDpToPixel(4), convertDpToPixel(8), convertDpToPixel(4));
        customisation.systemMessageBubblePadding = new Chat.Customization.Padding(convertDpToPixel(24), convertDpToPixel(4), convertDpToPixel(8), convertDpToPixel(4));

        customisation.apply();
    }

    public int convertDpToPixel(int dp){
        Resources resources = getActivity().getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return (int)px;
    }

    @Override
    public boolean isHandlingBack() {
        return false;
    }

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
