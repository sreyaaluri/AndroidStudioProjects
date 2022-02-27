package com.example.week7_tuesday;

import android.os.Bundle;

import android.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RegisterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegisterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegisterFragment newInstance(String param1, String param2) {
        RegisterFragment fragment = new RegisterFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        DBClass db=new DBClass(getActivity(),"Users");
        View v=inflater.inflate(R.layout.fragment_register, container, false);
        EditText nametxt=v.findViewById(R.id.nametxt);
        EditText agetxt=v.findViewById(R.id.agetxt);
        Button b1=v.findViewById(R.id.sbmtbtn);
        //EditText txt=v.findViewById(R.id.textViewfrag1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
// load First Fragment
             //   String name=txt.getText().toString();
                String name=nametxt.getText().toString();
                String age=agetxt.getText().toString();
         //       db.deletetable("info");

                db.addInfo(1,name,age);
                nametxt.setText("");
                agetxt.setText("");
                MainActivity m=(MainActivity)getActivity();
                m.displayInfo();
            }
        });
        return v;

    }
}