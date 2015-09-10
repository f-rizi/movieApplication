package com.example.emamianrizif.Movie.fragments;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.graphics.Outline;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.emamianrizif.Movie.R;

import java.util.NoSuchElementException;

public class NavigationDrawerFragment extends Fragment implements View.OnClickListener{

    public static final String TAG = NavigationDrawerFragment.class.getSimpleName();

    private NavigationItemActions listener;

    private ActionBarDrawerToggle actionBarDrawerToggle;
    private DrawerLayout drawerLayout;
    private ViewOutlineProvider mOutlineProvider;

    public static NavigationDrawerFragment newInstance() {
        NavigationDrawerFragment fragment = new NavigationDrawerFragment();
        return fragment;
    }

    public NavigationDrawerFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mOutlineProvider = new ClipOutlineProvider();
        return inflater.inflate(R.layout.navigation_drawer_fragment, container, false);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private class ClipOutlineProvider extends ViewOutlineProvider {

        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void getOutline(View view, Outline outline) {
//            final int margin = Math.min(view.getWidth(), view.getHeight()) / 10;
            int size = getResources().getDimensionPixelSize(R.dimen.nav_profile_image_size);

            outline.setOval(0, 0, size, size);
//            outline.setRoundRect(margin, margin, view.getWidth() - margin,
//                    view.getHeight() - margin, margin / 2);
        }

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ImageView navProfileImage = (ImageView) getActivity().findViewById(R.id.nav_profile_image);
        navProfileImage.setOutlineProvider(mOutlineProvider);
        navProfileImage.setClipToOutline(true);

        setItemLayouts();
    }

    private void setItemLayouts() {
        LinearLayout profileItem = (LinearLayout) getActivity().findViewById(R.id.nav_item_profile);
        LinearLayout editItem = (LinearLayout) getActivity().findViewById(R.id.nav_item_edit);
        LinearLayout favoritesItem = (LinearLayout) getActivity().findViewById(R.id.nav_item_favorites);
        LinearLayout locationItem = (LinearLayout) getActivity().findViewById(R.id.nav_item_location);

        LinearLayout settingsItem = (LinearLayout) getActivity().findViewById(R.id.nav_item_settings);
        LinearLayout helpFeedbackItem = (LinearLayout) getActivity().findViewById(R.id.nav_item_help_feedback);
        LinearLayout aboutItem = (LinearLayout) getActivity().findViewById(R.id.nav_item_about);
        
        initNavItem(profileItem, R.drawable.ic_account_box_grey600_24dp, getString(R.string.navigation_profile));
        initNavItem(editItem, R.drawable.ic_border_color_grey600_24dp, getString(R.string.navigation_edit));
        initNavItem(favoritesItem, R.drawable.ic_favorite_grey600_24dp, getString(R.string.navigation_favorites));
        initNavItem(locationItem, R.drawable.ic_place_grey600_24dp, getString(R.string.navigation_location));

        initNavItem(settingsItem, getString(R.string.navigation_settings));
        initNavItem(helpFeedbackItem, getString(R.string.navigation_help_feedback));
        initNavItem(aboutItem, getString(R.string.navigation_about));
        
    }

    private void initNavItem(LinearLayout layout, int imageId, String text) {
        initNavItem(layout, text);

        ImageView imageView = (ImageView) layout.findViewById(R.id.nav_icon);
        imageView.setImageResource(imageId);
        imageView.setVisibility(View.VISIBLE);
    }

    private void initNavItem(LinearLayout layout, String text) {
        layout.setOnClickListener(this);
        TextView textView = (TextView) layout.findViewById(R.id.nav_text);
        textView.setText(text);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = (NavigationItemActions) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public void setup(int start_drawer, final DrawerLayout drawerLayout, final Toolbar toolbar) {
        View containerView = getActivity().findViewById(start_drawer);

        this.drawerLayout = drawerLayout;
        actionBarDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar,
                R.string.drawer_open, R.string.drawer_close) {

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);

                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

                getActivity().invalidateOptionsMenu();
            }
        };

        this.drawerLayout.setDrawerListener(actionBarDrawerToggle);
        this.drawerLayout.post(new Runnable() {
            @Override
            public void run() {
                actionBarDrawerToggle.syncState();
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id) {
            case R.id.nav_item_profile:
                drawerLayout.closeDrawer(Gravity.START);
                break;
            case R.id.nav_item_edit:
                drawerLayout.closeDrawer(Gravity.START);
                break;
            case R.id.nav_item_favorites:
                drawerLayout.closeDrawer(Gravity.START);
                break;
            case R.id.nav_item_location:
                drawerLayout.closeDrawer(Gravity.START);
                break;
            case R.id.nav_item_settings:
                drawerLayout.closeDrawer(Gravity.START);
                break;
            case R.id.nav_item_help_feedback:
                drawerLayout.closeDrawer(Gravity.START);
                break;
            case R.id.nav_item_about:
                drawerLayout.closeDrawer(Gravity.START);
                break;
            default:
                throw new NoSuchElementException("unknown ID_KEY");
        }

        listener.onNavigationItemCLick(view);
    }

    public interface NavigationItemActions {
        public void onNavigationItemCLick(View view);
    }
}
