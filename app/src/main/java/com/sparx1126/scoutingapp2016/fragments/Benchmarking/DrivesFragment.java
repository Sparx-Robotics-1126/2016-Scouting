package com.sparx1126.scoutingapp2016.fragments.Benchmarking;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.sparx1126.scoutingapp2016.R;

import org.gosparx.scouting.aerialassist.dto.ScoutingInfo;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DrivesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DrivesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DrivesFragment extends Fragment {
    private OnFragmentInteractionListener mListener;

    private ScoutingInfo si;
    private EditText driveSystem;
    private EditText speed;
    private ToggleButton extend;
    private ToggleButton crossPortcullis, crossCheval, crossMoat, crossRamparts, crossDrawBridge,
            crossSallyPort, crossRockWall, crossRoughTerrain, crossLowBar;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment DrivesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DrivesFragment newInstance(ScoutingInfo si) {
        DrivesFragment fragment = new DrivesFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public DrivesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View result = inflater.inflate(R.layout.fragment_drives, container, false);

        driveSystem = (EditText) result.findViewById(R.id.drivesSystem);
        speed = (EditText) result.findViewById(R.id.speed);
        extend = (ToggleButton) result.findViewById(R.id.extend);
        crossCheval = (ToggleButton) result.findViewById(R.id.cheval);

        return result;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }



}
