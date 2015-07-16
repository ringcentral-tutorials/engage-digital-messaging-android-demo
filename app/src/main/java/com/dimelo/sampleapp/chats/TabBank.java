package com.dimelo.sampleapp.chats;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ViewFlipper;

import com.dimelo.dimelosdk.main.Chat;
import com.dimelo.dimelosdk.main.Dimelo;
import com.dimelo.sampleapp.R;

public class TabBank extends Fragment implements SampleDimeloTab {
    private Chat mDimeloChat;
    private ViewFlipper mViewFlipper;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tab_bank,container,false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mDimeloChat = Dimelo.getInstance().newChatFragment();
        mDimeloChat.setUserVisibleHint(false);
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.chat_bank_container, mDimeloChat);
        fragmentTransaction.commit();

        mViewFlipper = (ViewFlipper) view.findViewById(R.id.viewflipper);
        view.findViewById(R.id.live_support).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mViewFlipper.setInAnimation(view.getContext(), android.support.v7.appcompat.R.anim.abc_grow_fade_in_from_bottom);
                mViewFlipper.setOutAnimation(view.getContext(), android.support.v7.appcompat.R.anim.abc_shrink_fade_out_from_bottom);
                mViewFlipper.setDisplayedChild(1);
                mDimeloChat.setUserVisibleHint(true);
            }
        });
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

        customisation.apply();
    }

    @Override
    public boolean isHandlingBack(){
        if (isVisible() && (mViewFlipper.getDisplayedChild() == 1)) {
            mViewFlipper.setDisplayedChild(0);
            return true;
        }
        return false;
    }

//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        // DimeloChat is a nested Fragment => Propagate setUserVisibleHint
//        if (mDimeloChat != null) {
//            mDimeloChat.setUserVisibleHint(isVisibleToUser);
//        }
//        super.setUserVisibleHint(isVisibleToUser);
//    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mDimeloChat = null;
        mViewFlipper = null;
    }
}