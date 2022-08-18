package com.dimelo.sampleapp.chats;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ViewFlipper;

import com.dimelo.dimelosdk.main.RcFragment;
import com.dimelo.dimelosdk.main.Dimelo;
import com.dimelo.sampleapp.RcConfig;
import com.dimelo.sampleapp.R;

import java.text.SimpleDateFormat;

public class TabTelecom extends Fragment implements SampleDimeloTab {
    static private String CHAT_STATE_KEY = "tab_telecom_chat_state";
    static private int CLOSED = 0;
    static private int OPEN = 1;

    private ViewFlipper mViewFlipper;
    private RcFragment mDimeloChat;

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(CHAT_STATE_KEY, mViewFlipper.getDisplayedChild() == 0 ? CLOSED : OPEN);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tab_telecom, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FragmentManager childFragmentManager = getChildFragmentManager();
        mDimeloChat = (RcFragment)childFragmentManager.findFragmentByTag("dimelo_telecom_chat");
        if (mDimeloChat == null) {
            mDimeloChat = Dimelo.getInstance().newRcFragment();
            FragmentTransaction fragmentTransaction = childFragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.chat_telecom_container, mDimeloChat, "dimelo_telecom_chat");
            fragmentTransaction.commit();
        }
        mDimeloChat.setUserVisibleHint(false);

        mViewFlipper = (ViewFlipper) view.findViewById(R.id.viewflipper);
        mViewFlipper.setOutAnimation(view.getContext(), R.anim.abc_shrink_fade_out_from_bottom);
        view.findViewById(R.id.live_support).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mViewFlipper.setInAnimation(view.getContext(), R.anim.abc_grow_fade_in_from_bottom);
                openChat();
            }
        });

        if (savedInstanceState != null){
            int chatState = savedInstanceState.getInt(CHAT_STATE_KEY, CLOSED);
            if (chatState == OPEN){
                openChat();
            }
        }
        customize();
    }

    private void openChat(){
        mViewFlipper.setDisplayedChild(1);
        mDimeloChat.setUserVisibleHint(true);
        RcConfig.setConfigMessage("tab", "telecom");
    }


    private void customize(){
        RcFragment.Customization customisation = mDimeloChat.getCustomization();
        customisation.backgroundColor = getResources().getColor(R.color.purple_500);
        customisation.inputbarBackgroundColor = getResources().getColor(R.color.purple_50);
        customisation.userMessageTextColor = Color.WHITE;
        customisation.agentMessageTextColor = Color.BLACK;
        customisation.agentNameColor = Color.RED;
        customisation.agentTimeColor = Color.GREEN;
        customisation.systemMessageTextColor = Color.BLACK;
        customisation.dateTextColor = Color.RED;
        customisation.dateFont = Typeface.DEFAULT_BOLD;
        customisation.hourTimeTextColor = Color.WHITE;
        customisation.createNewThreadBackgroundColor = Color.RED;
        int paddingReplies = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, getResources().getDimension(R.dimen.rc_quick_replies_padding), getResources().getDisplayMetrics());
        customisation.quickRepliesItemPadding = new RcFragment.Customization.Padding(paddingReplies, paddingReplies, paddingReplies, paddingReplies);
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        customisation.dateFormatter = dateFormatter;
        customisation.messageFont = Typeface.DEFAULT_BOLD;
        customisation.agentTemplateBorderColor = Color.RED;
        customisation.agentTemplateWithImageBodyBackgroundColor = Color.LTGRAY;
        int quickRepliesHeight = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, getResources().getDimension(R.dimen.rc_quick_replies_height), getResources().getDisplayMetrics());
        customisation.quickRepliesBorderWidth = quickRepliesHeight;
        customisation.quickRepliesTextColor = Color.WHITE;
        customisation.quickRepliesTappedTextColor = Color.RED;
        customisation.quickRepliesBorderColor = Color.RED;
        customisation.badgeFont = Typeface.DEFAULT_BOLD;
        customisation.badgeTextColor = Color.RED;
        customisation.backToAllChatsFont = Typeface.DEFAULT_BOLD;
        customisation.threadsListSeparatorColor = Color.BLACK;
        customisation.quickRepliesHorizontalSpacing = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, getResources().getDimension(R.dimen.rc_quick_replies_horizontal_spacing), getResources().getDisplayMetrics());
        customisation.hourTimeFont = Typeface.DEFAULT_BOLD;
        customisation.agentTimeFont = Typeface.DEFAULT_BOLD;
        customisation.hourTimeTextColor = Color.GREEN;
        customisation.agentStructuredMessageItemFont = Typeface.DEFAULT_BOLD;
        customisation.agentStructuredMessageUrlFont = Typeface.DEFAULT_BOLD;
        customisation.agentStructuredMessageSubTitleFont = Typeface.DEFAULT_BOLD;
        customisation.agentStructuredMessageTitleFont = Typeface.DEFAULT_BOLD;
        customisation.agentStructuredMessageTitleColor = Color.BLUE;
        customisation.agentStructuredMessageSubtitleColor = Color.RED;
        customisation.agentStructuredMessageUrlColor = Color.RED;
        customisation.agentStructuredMessageItemColor = Color.RED;
        customisation.agentStructuredMessageItemTappedColor = Color.BLUE;
        customisation.setBackToAllChatsImage(R.drawable.back_all_chat);
        customisation.setLockedThreadImage(R.drawable.back_all_chat);
        customisation.apply();
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mDimeloChat = null;
        mViewFlipper = null;
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

}