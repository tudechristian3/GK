package com.goodkredit.myapplication.fragments.gkearn;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.gkearn.GKEarnHomeActivity;
import com.goodkredit.myapplication.base.BaseFragment;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;
import com.synnapps.carouselview.ViewListener;

public class GKEarnUnRegisteredMemberFragment extends BaseFragment implements View.OnClickListener {

    //Carousel View
    private CarouselView carouselView;
    private int NUMBER_OF_PAGES = 3;

    //Register
    private LinearLayout btn_registertoearn;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gkearn_unregistered_member, container, false);
        setHasOptionsMenu(false);
        try {
            init(view);
            initData();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }

    private void init(View view) {
        carouselView = (CarouselView) view.findViewById(R.id.carouselView);
        carouselView.setPageCount(NUMBER_OF_PAGES);

        carouselView.setViewListener(viewListener);

        btn_registertoearn = view.findViewById(R.id.btn_registertoearn);
        btn_registertoearn.setOnClickListener(this);
    }

    private ViewListener viewListener = new ViewListener() {
        @Override
        public View setViewForPosition(int position) {
            View customView = getLayoutInflater().inflate(R.layout.carousel_gkearn_unregistered_member, null);

            ImageView img = customView.findViewById(R.id.img_gkearn_logo);
            TextView title = customView.findViewById(R.id.txv_gkearn_title);
            TextView desc = customView.findViewById(R.id.txv_gkearn_desc);

            if(position == 0){
                Glide.with(getViewContext())
                        .load(R.drawable.gkearn_what_is_earn)
                        .into(img);

                title.setText("What is EARN?");
                desc.setText(R.string.str_gkearn_what_is_earn);
            }  else if(position == 1){
                Glide.with(getViewContext())
                        .load(R.drawable.gkearn_learn_about_earn)
                        .into(img);

                title.setText("Learn About Earn");
                desc.setText(R.string.str_gkearn_learn_about_earn);
            } else if(position == 2){
                Glide.with(getViewContext())
                        .load(R.drawable.gkearn_features_of_earn)
                        .into(img);

                title.setText("Features of Earn");
                desc.setText(R.string.str_gkearn_features_of_earn);
            }

            return customView;
        }
    };
    private void initData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_registertoearn: {
                GKEarnHomeActivity.start(getViewContext(), GKEarnHomeActivity.GKEARN_FRAGMENT_REGISTER, null);
                break;
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().overridePendingTransition(0, 0);
    }
}
