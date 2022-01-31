
package com.dimelo.sampleapp.chats;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ViewFlipper;
import android.util.Log;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.dimelo.dimelosdk.main.RcFragment;
import com.dimelo.dimelosdk.main.Dimelo;
import com.dimelo.sampleapp.RcConfig;
import com.dimelo.sampleapp.R;

public class TabBank extends Fragment implements SampleDimeloTab {

    static private String CHAT_STATE_KEY = "tab_bank_chat_state";
    static private int CLOSED = 0;
    static private int OPEN = 1;

    private RcFragment mDimeloChat;
    private ViewFlipper mViewFlipper;

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(CHAT_STATE_KEY, mViewFlipper.getDisplayedChild() == 0 ? CLOSED : OPEN);
    }

    public boolean onBackPressed() {
        if (mDimeloChat != null) {
            return mDimeloChat.onBackPressed();
        }

        return false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i("UnreadCount", String.valueOf(Dimelo.getInstance().getUnreadCount()));
        return inflater.inflate(R.layout.tab_bank,container,false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentManager childFragmentManager = getChildFragmentManager();
        TextView versionName = view.findViewById(R.id.versionName);
        versionName.setText(getString(R.string.rc_sdk_version)+com.dimelo.dimelosdk.BuildConfig.VERSION_NAME);
        mDimeloChat = (RcFragment)childFragmentManager.findFragmentByTag("dimelo_bank_chat");
        if (mDimeloChat == null) {
            mDimeloChat = Dimelo.getInstance().newRcFragment();
            FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.chat_bank_container, mDimeloChat, "dimelo_bank_chat");
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
        RcConfig.setConfigMessage("tab", "bank");
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

    private void customize(){
        RcFragment.Customization customisation = mDimeloChat.getCustomization();
        customisation.backgroundColor = Color.WHITE;
        customisation.apply();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mDimeloChat = null;
        mViewFlipper = null;
    }
}
