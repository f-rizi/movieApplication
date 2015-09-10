package com.example.emamianrizif.Movie.fragments;


import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.emamianrizif.Movie.R;
import com.example.emamianrizif.Movie.adapters.MovieCreditsAdapter;
import com.example.emamianrizif.Movie.model.Career;
import com.example.emamianrizif.Movie.model.MovieCredits;

import java.util.List;


public abstract class MovieCreditsListFragment extends Fragment {

    public static final String CREDITS_LIST_KEY = "credits";

    protected List<? extends Career> movieCastList;
    private LayoutInflater layoutInflater;

    private OnCreditsListFragmentInteractionListener mListener;
    private RecyclerView recyclerView;

    private MovieCreditsAdapter adapter;

    public MovieCreditsListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            movieCastList = getArguments().getParcelableArrayList(CREDITS_LIST_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.layoutInflater = inflater;

        return inflater.inflate(R.layout.fragment_person_list_fragemnt, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = (RecyclerView) view.findViewById(R.id.casts);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        adapter = getAdapter();
        adapter.setListener(mListener);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        if(adapter.getItemCount() <= 3) {
            setListViewHeightBasedOnChildren(recyclerView);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnCreditsListFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnCreditsListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnCreditsListFragmentInteractionListener {
        public void onCreditClick(int castId, String name, String imagePath);
    }

    abstract MovieCreditsAdapter getAdapter();


    public void setListViewHeightBasedOnChildren(RecyclerView recyclerView) {
        float height = getResources().getDimension(R.dimen.person_item_layout_height);
        float totalHeight = height * adapter.getItemCount();

        ViewGroup.LayoutParams params = recyclerView.getLayoutParams();
        params.height = (int) totalHeight ;
        recyclerView.setLayoutParams(params);
        recyclerView.requestLayout();
    }
}
