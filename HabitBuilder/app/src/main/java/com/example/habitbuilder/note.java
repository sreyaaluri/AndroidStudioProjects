package com.example.habitbuilder;

import android.os.Bundle;

import android.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link note#newInstance} factory method to
 * create an instance of this fragment.
 */
public class note extends Fragment {

    // the fragment initialization parameters
    private static final String ARG_TITLE = "mTitle";
    private static final String ARG_RATING = "mRating";

    private String mTitle;
    private String mRating;

    public note() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param name Parameter 1.
     * @param rating Parameter 2.
     * @return A new instance of fragment note.
     */
    public static note newInstance(String name, int rating) {
        note fragment = new note();
        Bundle args = new Bundle();

        String rt = "";
        if(rating == 0) rt ="Bad";
        else if(rating == 2) rt = "Good";
        else rt = "Neutral";

        args.putString(ARG_TITLE, name);
        args.putString(ARG_RATING, rt);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTitle = getArguments().getString(ARG_TITLE);
            mRating = getArguments().getString(ARG_RATING);
        }
    }

    // fragment creates its View object hierarchy
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_note, container, false);
        TextView titleLbl = rootView.findViewById(R.id.nameLbl);
        TextView ratingLbl = rootView.findViewById(R.id.ratingLbl);
        titleLbl.setText(mTitle);
        ratingLbl.setText(mRating);
        return rootView;
    }
}