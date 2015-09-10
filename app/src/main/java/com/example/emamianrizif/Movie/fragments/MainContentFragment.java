package com.example.emamianrizif.Movie.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.emamianrizif.Movie.R;
import com.example.emamianrizif.Movie.views.SlidingTabLayout;

import java.util.NoSuchElementException;

public class MainContentFragment extends Fragment {

    public static final String TAG = MainContentFragment.class.getSimpleName();

    private static final String FRAGMENT_INDEX = "currentFragment";

    private static final int POPULAR_MOVIES = 0;
    private static final int IN_THEATER_MOVIES = 1;
    private static final int UPCOMING_MOVIES = 2;

    private ViewPager viewPager;
    private int currentPage;

    public static MainContentFragment newInstance() {
        MainContentFragment fragment = new MainContentFragment();
        return fragment;
    }

    public MainContentFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_content, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        android.app.FragmentManager fragmentManager = getFragmentManager();
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        viewPager.setAdapter(new ContentFragmentAdapter(fragmentManager));

        if(savedInstanceState != null) {
            int currentIndex = savedInstanceState.getInt(FRAGMENT_INDEX);
        }

        SlidingTabLayout slidingTabLayout = (SlidingTabLayout) view.findViewById(R.id.sliding_tabs);
        slidingTabLayout.setCustomTabView(R.layout.custom_tab_layout, R.id.title, R.id.icon);
        slidingTabLayout.setViewPager(viewPager, currentPage);
    }

    @Override
    public void onPause() {
        super.onPause();
        currentPage = viewPager.getCurrentItem();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(FRAGMENT_INDEX, viewPager.getCurrentItem());
        super.onSaveInstanceState(outState);
    }

    private class ContentFragmentAdapter extends FragmentStatePagerAdapter {
        private String[] pagerTitles = getResources().getStringArray(R.array.movies);

        private static final int FRAGMENTS_ITEMS = 3;

        ContentFragmentAdapter(android.app.FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return pagerTitles[position];
        }

        @Override
        public Fragment getItem(int index) {
            Fragment fragment;

            switch (index) {
                case POPULAR_MOVIES:
                    fragment = new PopularMoviesFragment();
                    break;

                case IN_THEATER_MOVIES:
                    fragment = new InTheaterMoviesFragment();
                    break;

                case UPCOMING_MOVIES:
                    fragment = new UpcomingMoviesFragment();
                    break;

                default:
                    throw new NoSuchElementException();
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return FRAGMENTS_ITEMS;
        }
    }
}
