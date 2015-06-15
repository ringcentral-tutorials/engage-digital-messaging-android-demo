package com.dimelo.sampleapp.chats;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dimelo.dimelosdk.main.Chat;
import com.dimelo.sampleapp.DimeloWrap;
import com.dimelo.sampleapp.R;

public class TabSupport extends Fragment implements SampleDimeloTab {
    Chat mDimeloChat;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_support,container,false);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mDimeloChat = DimeloWrap.getDimelo().newChatFragment();
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.chat_support_container, mDimeloChat);
        fragmentTransaction.commit();

        customize();
    }


    private void customize(){
        Chat.Customization customisation = mDimeloChat.getCustomization();

        customisation.backgroundColor = getResources().getColor(R.color.white);

        customisation.setUserMessageBubbleDrawable(R.drawable.row_user_message_bubble, getResources().getColor(R.color.blue_500));
        customisation.setAgentMessageBubbleDrawable(R.drawable.row_agent_message_bubble, Color.LTGRAY);
        customisation.setSystemMessageBubbleDrawable(R.drawable.row_system_message_bubble, Color.LTGRAY);

        customisation.userMessageTextColor = Color.WHITE;

        customisation.agentMessageTextColor = Color.BLACK;
        customisation.agentNameColor = Color.GRAY;

        customisation.systemMessageTextColor = Color.BLACK;
        customisation.apply();
    }

    @Override
    public boolean isChatDisplayed() {
        return isVisible();
    }
}