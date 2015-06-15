package com.dimelo.sampleapp.chats;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dimelo.sampleapp.DimeloWrap;
import com.dimelo.sampleapp.NotificationDisplayer;
import com.dimelo.sampleapp.R;

import java.util.Random;

public class TabStart extends Fragment implements SampleDimeloTab {

    Random random;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.tab_start,container,false);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        random = new Random();

        Fragment dimeloChat = DimeloWrap.getDimelo().newChatFragment();

        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.chat_start_container, dimeloChat);
        fragmentTransaction.commit();

        setListeners(view);
    }

    private void setListeners(final View root){
        root.findViewById(R.id.open_full_screen).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DimeloWrap.getDimelo().openChatActivity(getActivity());
            }
        });
        root.findViewById(R.id.short_notif).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] messages = {"Hi!", "Hello", "What's up?", "Thanks!", "Kthanxbye", "Thank you!", "OMG, REALLY?", "I don't think so.", "Maybe tomorrow.", "How about next Tuesday?", "This weekend is fine by me.", "Slightly longer message to be displayed as a non-truncated one."};
                NotificationDisplayer.show(root.getContext(), messages[random.nextInt(messages.length - 1)]);
            }
        });

        root.findViewById(R.id.long_notif).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] messages = {
                        "In order to activate your account, please follow these simple steps:\n1. Go to www.mybank.example.com.\n2. Click Personal Account.\n3. Find Activate Your Account button on the left of the screen.\n4. Click it and live happily everafter.",
                        "We are glad to hear from you. One of the available agents will swiftly contact you to respond to your inquiry. Thank you!",
                        "Unfortunately, time is over. We cannot support this conversation longer. Please leave this chat and try to solve your problem on your own. Thank you for your understanding.",
                        "This is a rather long message intended to be truncated when displayed on the lock screen. We hope you understand our intention here.",
                        "Once upon a time there was a little bear-the-pooh who was searching for a pot of honey everywhere."};
                NotificationDisplayer.show(root.getContext(), messages[random.nextInt(messages.length - 1)]);
            }
        });
    }

    private void customize(){

    }

    @Override
    public boolean isChatDisplayed(){
        return false;
    }

}