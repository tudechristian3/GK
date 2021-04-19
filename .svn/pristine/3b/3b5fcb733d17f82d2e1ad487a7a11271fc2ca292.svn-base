package com.goodkredit.myapplication.fragments.gkappintro;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.base.BaseFragment;
import com.goodkredit.myapplication.utilities.V2Utils;

/**
 * Created by Ban_Lenovo on 3/14/2017.
 */

public class FragmentAppIntroItem extends BaseFragment {

    private static final String KEY_HEADERTEXT = "headertext";
    private static final String KEY_TEXT = "text";
    private static final String KEY_IMAGE = "image";

    TextView headerTextView;
    TextView textView;
    ImageView imageView;

    public static FragmentAppIntroItem newInstance(String headertext, int strRes, int imgRes) {
        Bundle args = new Bundle();
        args.putString(KEY_HEADERTEXT, headertext);
        args.putInt(KEY_TEXT, strRes);
        args.putInt(KEY_IMAGE, imgRes);
        FragmentAppIntroItem fragment = new FragmentAppIntroItem();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Context getContext() {
        return super.getContext();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_app_intro_item, container, false);
        headerTextView = (TextView) view.findViewById(R.id.appIntroDescriptionTitle);
        textView = (TextView) view.findViewById(R.id.appIntroDescription);
        imageView = (ImageView) view.findViewById(R.id.appIntroInfoGraphic);

        Bundle b = getArguments();
        String headerText = b.getString(KEY_HEADERTEXT);
        int textResource = b.getInt(KEY_TEXT);
        int imageResource = b.getInt(KEY_IMAGE);

        headerTextView.setText(V2Utils.setTypeFace(getViewContext(), V2Utils.ROBOTO_BOLD, headerText));

        textView.setText(V2Utils.setTypeFace(getViewContext(), V2Utils.ROBOTO_REGULAR, getString(textResource)));

        Glide.with(getViewContext())
                .load(imageResource)
                .apply(new RequestOptions()
                        .fitCenter()
                        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC))
                .into(imageView);

        return view;
    }
}
