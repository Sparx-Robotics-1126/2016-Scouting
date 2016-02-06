package com.sparx1126.scoutingapp2016.fragments.Benchmarking;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ToggleButton;

import com.sparx1126.scoutingapp2016.R;

import org.gosparx.scouting.aerialassist.dto.ScoutingInfo;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SoftwareFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SoftwareFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SoftwareFragment extends Fragment {

    private ScoutingInfo si;
    private OnFragmentInteractionListener mListener;
    private boolean wasCreated;
    private ToggleButton startSpy;
    private ToggleButton startNeutral;
    private ToggleButton endNeutral;
    private ToggleButton endCourtyard;
    private EditText autoActions;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment SoftwareFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SoftwareFragment newInstance(ScoutingInfo si) {
        SoftwareFragment fragment = new SoftwareFragment();
        Bundle args = new Bundle();
        fragment.setScoutingInfo(si);
        fragment.setArguments(args);
        return fragment;
    }

    public SoftwareFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View result =  inflater.inflate(R.layout.fragment_software, container, false);

        startSpy = (ToggleButton)result.findViewById(R.id.spy);
        startNeutral = (ToggleButton)result.findViewById(R.id.neutralStart);
        endCourtyard = (ToggleButton)result.findViewById(R.id.courtyard);
        endNeutral = (ToggleButton)result.findViewById(R.id.neutralEnd);
        autoActions = (EditText) result.findViewById(R.id.autoActions);
        if(si != null){
            startSpy.setChecked(si.getAutoStartInSpyPosition());
            startNeutral.setChecked(si.getAutoStartInSpyPosition());
            autoActions.setText(si.getAutoCapabilitiesDescription());
        }
        wasCreated = true;
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
    private void setScoutingInfo(ScoutingInfo si){this.si = si;}

    public ScoutingInfo getScoutingInfo(){
        if(si == null){
            si = new ScoutingInfo();
        }
        if(wasCreated){
            si.setAutoStartInSpyPosition(startSpy.isChecked());
            si.setAutoStartInNeutralZone(startNeutral.isChecked());
            si.setAutoEndInCourtyard(endCourtyard.isChecked());
            si.setAutoEndInNeutralZone(endNeutral.isChecked());
        }
        return si;
    }

}
