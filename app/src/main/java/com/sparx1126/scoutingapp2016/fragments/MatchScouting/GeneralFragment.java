package com.sparx1126.scoutingapp2016.fragments.MatchScouting;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.sparx1126.scoutingapp2016.R;
import com.sparx1126.scoutingapp2016.controls.HorizontalNumberPicker;

import org.gosparx.scouting.aerialassist.dto.ScoutingGeneral;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GeneralFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GeneralFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GeneralFragment extends Fragment {

    private ScoutingGeneral sg;
    private OnFragmentInteractionListener mListener;
    private Boolean wasCreated = false;

    // controls on the screen
    private HorizontalNumberPicker penaltyCountNumberPicker;
    private HorizontalNumberPicker techFoulCountNumberPicker;
    private EditText penaltyCommentsEditText;
    private EditText techFoulCommentsEditText;
    private EditText generalCommentsEditText;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     */
    // TODO: Rename and change types and number of parameters
    public static GeneralFragment newInstance(ScoutingGeneral sg) {
        GeneralFragment fragment = new GeneralFragment();
        Bundle args = new Bundle();
        fragment.setScoutingGeneral(sg);
        fragment.setArguments(args);
        return fragment;
    }

    public GeneralFragment() {
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
        View result = inflater.inflate(R.layout.fragment_general, container, false);

        penaltyCountNumberPicker = (HorizontalNumberPicker)result.findViewById(R.id.genPenatlies);
        techFoulCountNumberPicker = (HorizontalNumberPicker)result.findViewById(R.id.genTechnicalFouls);
        penaltyCommentsEditText = (EditText)result.findViewById(R.id.genPenaltyComments);
        techFoulCommentsEditText = (EditText)result.findViewById(R.id.genTechFoulComments);
        generalCommentsEditText = (EditText)result.findViewById(R.id.genComments);


        if(sg != null){
            penaltyCountNumberPicker.setValue(sg.getNumberOfPenalties());
            techFoulCountNumberPicker.setValue(sg.getNumberOfTechnicalFouls());
            penaltyCommentsEditText.setText(sg.getCommentsOnPenalties());
            techFoulCommentsEditText.setText(sg.getCommentsOnTechnicalFouls());
            generalCommentsEditText.setText(sg.getGeneralComments());
        }
        wasCreated = true;
        return result;
    }

    @Override
    public void onResume() {
        super.onResume();

        if(sg != null){
            penaltyCountNumberPicker.setValue(sg.getNumberOfPenalties());
            techFoulCountNumberPicker.setValue(sg.getNumberOfTechnicalFouls());
            penaltyCommentsEditText.setText(sg.getCommentsOnPenalties());
            techFoulCommentsEditText.setText(sg.getCommentsOnTechnicalFouls());
            generalCommentsEditText.setText(sg.getGeneralComments());
        }
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

    public void setScoutingGeneral(ScoutingGeneral sg){
        this.sg = sg;
    }

    public ScoutingGeneral getScoutingGeneral(){
        if(sg == null)
            sg = new ScoutingGeneral();

        if (wasCreated)
        {
            sg.setNumberOfPenalties(penaltyCountNumberPicker.getValue());
            sg.setNumberOfTechnicalFouls(techFoulCountNumberPicker.getValue());
            sg.setCommentsOnPenalties(penaltyCommentsEditText.getText().toString());
            sg.setCommentsOnTechnicalFouls(techFoulCommentsEditText.getText().toString());
            sg.setGeneralComments(generalCommentsEditText.getText().toString());
        }

        return sg;
    }
}
