package com.dimelo.sampleapp.chats;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
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

        customisation.userMessageBubblePadding = new Chat.Customization.Padding(convertDpToPixel(8), 0, convertDpToPixel(24), 0);
        customisation.agentMessageBubblePadding = new Chat.Customization.Padding(convertDpToPixel(24), 0, convertDpToPixel(8), 0);
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // DimeloChat is a nested Fragment, we must notify it about onActivityResult
        mDimeloChat.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // DimeloChat is a nested Fragment, we must notify it about onRequestPermissionsResult
        mDimeloChat.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public boolean isHandlingBack(){
        if (isVisible() && (mViewFlipper.getDisplayedChild() == 1)) {
            mViewFlipper.setDisplayedChild(0);
            mDimeloChat.setUserVisibleHint(false);
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