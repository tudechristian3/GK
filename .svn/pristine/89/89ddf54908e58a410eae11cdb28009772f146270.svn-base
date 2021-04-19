package com.goodkredit.myapplication.fragments.projectcoop;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.base.BaseFragment;
import com.goodkredit.myapplication.common.CommonFunctions;

public class PushNotificationPromptFragment extends BaseFragment implements View.OnClickListener {

    private TextView txvNotice;
    private TextView txvMessage;
    private ImageView imgClose;
    private ImageView imgHeader;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_push_notification_prompt, container, false);

        init(view);

        initData();

        return view;
    }

    private void init(View view) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        txvMessage = (TextView) view.findViewById(R.id.txvMessage);
        imgClose = (ImageView) view.findViewById(R.id.imgClose);
        imgClose.setOnClickListener(this);
        imgHeader = (ImageView) view.findViewById(R.id.imgHeader);
        txvNotice = (TextView) view.findViewById(R.id.txvNotice);
    }

    private void initData() {
        String message = getArguments().getString("MESSAGE");
        String subject = getArguments().getString("SUBJECT");
        txvMessage.setText(message);

        if (subject.contains("RELEASEVOUCHER")) {
            txvNotice.setText("RELEASED VOUCHERS");
        } else if (subject.contains("CPMPC")) {
            txvNotice.setText("BROADCAST");
            Glide.with(getContext())
                    .load(R.drawable.cpmpc_header)
                    .apply(new RequestOptions()
                            .fitCenter()
                            .override(CommonFunctions.getScreenWidth(getViewContext()), CommonFunctions.getScreenHeight(getViewContext())))
                    .into(imgHeader);
        } else {
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
