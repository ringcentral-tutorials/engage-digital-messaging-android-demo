package com.dimelo.sampleapp.chats;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dimelo.dimelosdk.main.Dimelo;
import com.dimelo.sampleapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.Random;
import java.util.UUID;

public class TabStart extends Fragment implements SampleDimeloTab {

    Random random;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tab_start, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        random = new Random();

        setListeners(view);
    }

    private void sendNotificationExample(String message){
        JSONObject notif = new JSONObject();
        try {
            notif.put("t", "m");
            notif.put("uuid", UUID.randomUUID().toString());
            notif.put("d", new Date().getTime() / 1000);
            notif.put("tr", true);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Bundle payload = new Bundle();
        payload.putString("appdata", notif.toString());
        payload.putString("dimelo", "1.0");
        payload.putString("alert", message);
        Dimelo.consumeReceivedRemoteNotification(getActivity(), payload, null);
    }

    private void setListeners(final View root){
        root.findViewById(R.id.open_full_screen).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Dimelo.getInstance().openChatActivity(getActivity());
            }
        });
        root.findViewById(R.id.short_notif).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            String[] messages = {"Hi!", "Hello", "What's up?", "Thanks!", "Kthanxbye", "Thank you!", "OMG, REALLY?", "I don't think so.", "Maybe tomorrow.", "How about next Tuesday?", "This weekend is fine by me.", "Slightly longer message to be displayed as a non-truncated one."};
                sendNotificationExample(messages[random.nextInt(messages.length - 1)]);
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
                sendNotificationExample(messages[random.nextInt(messages.length - 1)]);
            }
        });
    }

    @Override
    public boolean isHandlingBack(){
        return false;
    }

}