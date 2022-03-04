package com.example.moodtracker;

import android.os.Bundle;

import android.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Note#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Note extends Fragment {

    // the fragment initialization parameters
    private static final String ARG_DATE = "mDate";
    private static final String ARG_NOTE = "mNote";

    private String mDate;
    private String mNote;

    public Note() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param date Parameter 1.
     * @param note Parameter 2.
     * @return A new instance of fragment Note.
     */
    // TODO: Rename and change types and number of parameters
    public static Note newInstance(String date, String note) {
        Note fragment = new Note();
        Bundle args = new Bundle();
        args.putString(ARG_DATE, date);
        args.putString(ARG_NOTE, note);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mDate = getArguments().getString(ARG_DATE);
            mNote = getArguments().getString(ARG_NOTE);
        }
    }

    // fragment creates its View object hierarchy
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_note, container, false);
        TextView dateLbl = rootView.findViewById(R.id.dateLbl);
        TextView noteLbl = rootView.findViewById(R.id.noteLbl);
        dateLbl.setText("Date: "+mDate);
        noteLbl.setText(mNote);
        return rootView;
    }

//    // view setup
//    @Override
//    public void onViewCreated(View view, Bundle savedInstanceState) {
//        TextView dateLbl = getActivity().findViewById(R.id.dateLbl);
//        TextView noteLbl = getActivity().findViewById(R.id.noteLbl);
//        dateLbl.setText("Date: "+mDate);
//        noteLbl.setText(mNote);
//    }
}