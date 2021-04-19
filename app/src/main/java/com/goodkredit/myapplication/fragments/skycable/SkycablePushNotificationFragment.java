package com.goodkredit.myapplication.fragments.skycable;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.base.BaseFragment;

public class SkycablePushNotificationFragment extends BaseFragment implements View.OnClickListener {

    private TextView txvNotice;
    private TextView txvMessage;
    private ImageView imgClose;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_skycable_push_notification, container, false);

        init(view);

        initData();

        return view;
    }

    private void init(View view) {

        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        txvMessage = (TextView) view.findViewById(R.id.txvMessage);
        imgClose = (ImageView) view.findViewById(R.id.imgClose);
        imgClose.setOnClickListener(this);
        txvNotice = (TextView) view.findViewById(R.id.txvNotice);
    }

    private void initData() {

        String message = getArguments().getString("MESSAGE");
        String subject = getArguments().getString("SUBJECT");
        txvMessage.setText(message);

        if (subject.contains("CHANGE APPLICATION STATUS REQUEST")) {
            txvNotice.setText("NEW APPLICATION");
        } else if (subject.contains("LINK ACCOUNT REQUEST")) {
            txvNotice.setText("LINK ACCOUNT");
        } else if (subject.contains("PPV SUBSCRIPTION REQUEST")) {
            txvNotice.setText("PPV SUBSCRIPTION");
        } else if (subject.contains("BROADCAST")) {
            txvNotice.setText("BROADCAST");
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgClose: {
                if (isAdded()) {
                    getActivity().finish();
                }
                break;
            }
        }
    }
}
