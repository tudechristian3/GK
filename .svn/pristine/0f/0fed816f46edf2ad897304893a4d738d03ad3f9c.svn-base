package com.goodkredit.myapplication.fragments.coopassistant.help;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.goodkredit.myapplication.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;

public class CoopGuideFragment extends DialogFragment {
  private Button btnBack;
  
  private Button btnNext;
  
  private ImageView closeImg;
  
  private TextView[] dots;
  
  private LinearLayout dotsLayout;
  
  List<GuideModel> models = new ArrayList<GuideModel>();
  
  private ViewPager viewPager;
  
  ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {
      public void onPageScrollStateChanged(int param1Int) {}
      
      public void onPageScrolled(int param1Int1, float param1Float, int param1Int2) {}
      
      public void onPageSelected(int param1Int) {
        CoopGuideFragment.this.addBottomDots(param1Int);
        if (param1Int == CoopGuideFragment.this.models.size() - 1) {
          CoopGuideFragment.this.btnNext.setText("Got It");
          CoopGuideFragment.this.btnBack.setVisibility(View.GONE);
          return;
        } 
        if (param1Int == 0) {
          CoopGuideFragment.this.btnBack.setVisibility(View.GONE);
          CoopGuideFragment.this.btnNext.setVisibility(View.VISIBLE);
          return;
        } 
        CoopGuideFragment.this.btnNext.setText("Next");
        CoopGuideFragment.this.btnBack.setVisibility(View.VISIBLE);
      }
    };
  
  private void addBottomDots(int paramInt) {
    this.dots = new TextView[this.models.size()];
    int[] arrayOfInt1 = getResources().getIntArray(R.array.array_dot_active);
    int[] arrayOfInt2 = getResources().getIntArray(R.array.array_dot_inactive);
    this.dotsLayout.removeAllViews();
    int i = 0;
    while (true) {
      TextView[] arrayOfTextView = this.dots;
      if (i < arrayOfTextView.length) {
        arrayOfTextView[i] = new TextView(requireContext());
        this.dots[i].setText((CharSequence)Html.fromHtml("&#8226;"));
        this.dots[i].setTextSize(35.0F);
        this.dots[i].setTextColor(arrayOfInt2[paramInt]);
        this.dotsLayout.addView((View)this.dots[i]);
        i++;
        continue;
      } 
      if (arrayOfTextView.length > 0)
        arrayOfTextView[paramInt].setTextColor(arrayOfInt1[paramInt]); 
      return;
    } 
  }
  
  private int getItem(int paramInt) {
    return this.viewPager.getCurrentItem() + paramInt;
  }
  
  public void onCreate(@Nullable Bundle paramBundle) {
    super.onCreate(paramBundle);
    setStyle(STYLE_NO_FRAME, R.style.MyDialogTheme);
  }
  
  @NotNull
  public Dialog onCreateDialog(Bundle paramBundle) {
    Dialog dialog = super.onCreateDialog(paramBundle);
    ((Window)Objects.requireNonNull(dialog.getWindow())).requestFeature(1);
    return dialog;
  }
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle) {
    View view = paramLayoutInflater.inflate(R.layout.layout_loan_guide, paramViewGroup, false);
    models.add(new GuideModel(R.drawable.guide1, "Open your Coop Mini App."));
    models.add(new GuideModel(R.drawable.guide2, "Tap the Loan Services icon on menu."));
    models.add(new GuideModel(R.drawable.guide3, "Tap APPLY button to continue."));
    models.add(new GuideModel(R.drawable.guide4, "Enter the desired amount and other needed details then tap SUBMIT button."));
    models.add(new GuideModel(R.drawable.guide5, "Your Coop will review your application and wait for their approval."));

    dotsLayout = (LinearLayout)view.findViewById(R.id.layoutDots);
    btnNext = (Button)view.findViewById(R.id.nextBtn);
    btnBack = (Button)view.findViewById(R.id.backBtn);
    viewPager = (ViewPager)view.findViewById(R.id.viewPager);
    closeImg = (ImageView)view.findViewById(R.id.closeImg);


    closeImg.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            Dialog dialog = getDialog();
            if (dialog != null)
              dialog.dismiss(); 
          }
        });
    btnNext.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            int i = getItem(1);
            if (i < models.size()) {
              viewPager.setCurrentItem(i);
              return;
            } 
            ((Dialog)Objects.requireNonNull(getDialog())).dismiss();
          }
        });
    btnBack.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            int i = getItem(-1);
            if (i >= 0)
              viewPager.setCurrentItem(i);
          }
        });
    CoopGuideAdapter coopGuideAdapter = new CoopGuideAdapter();
    viewPager.setAdapter(coopGuideAdapter);
    viewPager.addOnPageChangeListener(viewPagerPageChangeListener);
    viewPager.setOffscreenPageLimit(models.size());
    addBottomDots(0);
    return view;
  }
  
  public void onStart() {
    super.onStart();
    Dialog dialog = getDialog();
    if (dialog != null) {
      ((Window)Objects.requireNonNull(dialog.getWindow())).setLayout(-1, -1);
      dialog.getWindow().setBackgroundDrawable((Drawable)new ColorDrawable(0));
    } 
  }
  
  public class CoopGuideAdapter extends PagerAdapter {
    public void destroyItem(ViewGroup param1ViewGroup, int param1Int, Object param1Object) {
      param1ViewGroup.removeView((View)param1Object);
    }
    
    public int getCount() {
      return CoopGuideFragment.this.models.size();
    }
    
    @NotNull
    public Object instantiateItem(@NotNull ViewGroup param1ViewGroup, int param1Int) {
      View view = ((LayoutInflater)CoopGuideFragment.this.requireContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.coop_guide_item, param1ViewGroup, false);
      ImageView imageView = (ImageView)view.findViewById(R.id.image);
      TextView textView = (TextView)view.findViewById(R.id.title);
      imageView.setImageResource(((GuideModel)CoopGuideFragment.this.models.get(param1Int)).getImage());
      textView.setText(((GuideModel)CoopGuideFragment.this.models.get(param1Int)).getTitle());
      param1ViewGroup.addView(view);
      return view;
    }
    
    public boolean isViewFromObject(@NotNull View param1View, @NotNull Object param1Object) {
      return (param1View == param1Object);
    }
  }
  
  static class GuideModel {
    private int image;
    
    private String title;
    
    public GuideModel(int param1Int, String param1String) {
      this.image = param1Int;
      this.title = param1String;
    }
    
    public int getImage() {
      return this.image;
    }
    
    public String getTitle() {
      return this.title;
    }
    
    public void setImage(int param1Int) {
      this.image = param1Int;
    }
    
    public void setTitle(String param1String) {
      this.title = param1String;
    }
  }
}
