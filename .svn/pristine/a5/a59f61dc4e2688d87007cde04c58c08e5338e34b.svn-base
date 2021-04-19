package com.goodkredit.myapplication.fragments.voting;

import android.os.Bundle;
import androidx.annotation.NonNull;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.base.BaseFragment;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.model.votes.VotesPostEvent;


public class VotesParticipantsFragmentTwo extends BaseFragment {

    private ImageView mPicLogo;
    private TextView txv_event_name;
    private TextView txv_event_desc;
    private TextView txv_votecloses;

    private String eventlogo;
    private String eventname;
    private String eventdesc;
    private String votecloses;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_votes_participants_two, container, false);
        setHasOptionsMenu(true);
        getActivity().invalidateOptionsMenu();

        try {
            init(view);

            initData();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_view_history).setVisible(false);
        super.onPrepareOptionsMenu(menu);
    }

    private void init(View view) {

        mPicLogo = (ImageView) view.findViewById(R.id.mPicLogo);
        txv_event_name = (TextView) view.findViewById(R.id.txv_event_name);
        txv_event_desc = (TextView) view.findViewById(R.id.txv_event_desc);
        txv_votecloses = (TextView) view.findViewById(R.id.txv_votecloses);

    }

    private void initData() {

        if(getArguments() != null){
            VotesPostEvent votesPostObject = getArguments().getParcelable("VotesPostObject");

            eventlogo = votesPostObject.getEventPictureURL();
            eventname = CommonFunctions.replaceEscapeData(votesPostObject.getEventName());
            eventdesc = CommonFunctions.replaceEscapeData(votesPostObject.getEventDescription());
            votecloses = CommonFunctions.getDateTimeFromDateTime(CommonFunctions.convertDate(votesPostObject.getEventDateTimeEnd()));

        }
        Glide.with(getViewContext())
                .load(eventlogo)
                .apply(new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                        .placeholder(R.drawable.generic_placeholder_gk_background)
                        .fitCenter())
                .into(mPicLogo);

        txv_event_name.setText(eventname);
        txv_event_desc.setText(eventdesc);
        txv_votecloses.setText("Voting closes on ".concat(votecloses));

    }
}
