package com.goodkredit.myapplication.fragments;

import android.content.Context;
import android.os.Bundle;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.adapter.NavigationDrawerAdapter;
import com.goodkredit.myapplication.base.BaseFragment;
import com.goodkredit.myapplication.model.NavDrawerItem;

import java.util.ArrayList;
import java.util.List;

public class MainDrawerFragment extends BaseFragment {
    private static String TAG = MainDrawerFragment.class.getSimpleName();

    private static RecyclerView recyclerView;
    private static ActionBarDrawerToggle mDrawerToggle;
    private static DrawerLayout mDrawerLayout;
    private static NavigationDrawerAdapter adapter;
    private static View containerView;
    private static String[] titles = null;
    private static int[] icons = {
            R.drawable.img_nav_services,
            R.drawable.ic_voucher,
            R.drawable.ic_rfid,
            R.drawable.ic_whatsnew,
            R.drawable.ic_merchants,
            R.drawable.ic_deposit,
            R.drawable.ic_transactions,
            R.drawable.ic_soa,
            R.drawable.ic_sponsor,
            R.drawable.ic_refer_a_friend,
            R.drawable.ic_support,
            R.drawable.ic_settings,
            R.drawable.ic_logout};

    private FragmentDrawerListener drawerListener;

    public MainDrawerFragment() {

    }

    public NavigationDrawerAdapter getAdapter() {
        if (adapter != null) {
            return adapter;
        }
        return null;
    }

    public void selectItem(int position) {
        recyclerView.findViewHolderForAdapterPosition(position).itemView.performClick();
    }


    public void setDrawerListener(FragmentDrawerListener listener) {
        this.drawerListener = listener;
    }

    public static List<NavDrawerItem> getData() {
        List<NavDrawerItem> data = new ArrayList<>();
        // preparing navigation drawer items
        for (int i = 0; i < titles.length; i++) {
            NavDrawerItem navItem = new NavDrawerItem();
            navItem.setTitle(titles[i]);
            navItem.setIcon(icons[i]);

            data.add(navItem);
        }
        return data;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // drawer labels
        try {
            titles = getActivity().getResources().getStringArray(R.array.nav_drawer_labels);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflating view layout
        View layout = inflater.inflate(R.layout.fragment_drawer, container, false);

        try {
            recyclerView = layout.findViewById(R.id.drawerList);

            adapter = new NavigationDrawerAdapter(getViewContext(), getData());
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getViewContext()));
            recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getViewContext(), recyclerView, new ClickListener() {
                @Override
                public void onClick(View view, int position) {
                    drawerListener.onDrawerItemSelected(view, position);
                    mDrawerLayout.closeDrawer(containerView);
                }

                @Override
                public void onLongClick(View view, int position) {

                }
            }));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return layout;
    }


    public void setUp(int fragmentId, DrawerLayout drawerLayout, final Toolbar toolbar) {
        try {
            containerView = getActivity().findViewById(fragmentId);
            mDrawerLayout = drawerLayout;
            mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
                @Override
                public void onDrawerOpened(View drawerView) {
                    super.onDrawerOpened(drawerView);
                    getActivity().invalidateOptionsMenu();
                    hideSoftKeyboard();
                }

                @Override
                public void onDrawerClosed(View drawerView) {
                    super.onDrawerClosed(drawerView);
                    getActivity().invalidateOptionsMenu();
                }

                @Override
                public void onDrawerSlide(View drawerView, float slideOffset) {
                    super.onDrawerSlide(drawerView, slideOffset);
                    toolbar.setAlpha(1 - slideOffset / 2);
                }
            };

            mDrawerLayout.setDrawerListener(mDrawerToggle);
            mDrawerLayout.post(new Runnable() {
                @Override
                public void run() {
                    mDrawerToggle.syncState();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }


    }

    public interface FragmentDrawerListener {
        void onDrawerItemSelected(View view, int position);
    }
}
