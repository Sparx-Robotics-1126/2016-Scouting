package org.gosparx.scouting.aerialassist.dto;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ToggleButton;

import com.sparx1126.scoutingapp2016.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AcquisitionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AcquisitionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AcquisitionFragment extends Fragment {
    private ScoutingInfo si;
    private OnFragmentInteractionListener mListener;
    private ToggleButton humanAcq;
    private ToggleButton floorAcq;
    private ToggleButton prefAcq;
    private ToggleButton portcullisBoulder;
    private ToggleButton chevalBoulder;
    private ToggleButton moatBoulder;
    private ToggleButton rampartsBoulder;
    private ToggleButton drawbridgeBoulder;
    private ToggleButton sallyportBoulder;
    private ToggleButton rockwallBoulder;
    private ToggleButton roughterrainBoulder;
    private ToggleButton lowbarBoulder;
    private boolean wasCreated;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment AcquisitionFragment.
     */
    public static AcquisitionFragment newInstance(ScoutingInfo si) {
        AcquisitionFragment fragment = new AcquisitionFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public AcquisitionFragment() {
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
        View result =  inflater.inflate(R.layout.fragment_acquisition, container, false);
        humanAcq = (ToggleButton)result.findViewById(R.id.humanAcq);
        floorAcq = (ToggleButton)result.findViewById(R.id.floorAcq);
        prefAcq = (ToggleButton)result.findViewById(R.id.preferredAcq);
        portcullisBoulder = (ToggleButton)result.findViewById(R.id.portcullisBoulder);
        chevalBoulder = (ToggleButton)result.findViewById(R.id.chevalBoulder);
        moatBoulder =(ToggleButton) result.findViewById(R.id.moatBoulder);
        rampartsBoulder = (ToggleButton)result.findViewById(R.id.rampartsBoulder);
        drawbridgeBoulder = (ToggleButton)result.findViewById(R.id.drawbridgeBoulder);
        sallyportBoulder = (ToggleButton)result.findViewById(R.id.sallyportBoulder);
        rockwallBoulder = (ToggleButton)result.findViewById(R.id.rockwallBoulder);
        roughterrainBoulder = (ToggleButton)result.findViewById(R.id.roughterrainBoulder);
        lowbarBoulder = (ToggleButton)result.findViewById(R.id.lowbarBoulder);

        if(si != null){
            humanAcq.setChecked(si.getAcquiresBouldersFromHumanPlayer());
            floorAcq.setChecked(si.getAcquiresBouldersFromFloor());
            prefAcq.setChecked(si.getPreferredBoulderSource() == prefAcq.getTextOn());
            portcullisBoulder.setChecked(si.getCanCarryBouldersOverPortcullis());
            chevalBoulder.setChecked(si.getCanCarryBouldersOverCheval());
            moatBoulder.setChecked(si.getCanCarryBouldersOverMoat());
            rampartsBoulder.setChecked(si.getCanCarryBouldersOverRamparts());
            drawbridgeBoulder.setChecked(si.getCanCarryBouldersOverDrawbridge());
            sallyportBoulder.setChecked(si.getCanCarryBouldersOverSallyport());
            rockwallBoulder.setChecked(si.getCanCarryBouldersOverRockwall());
            roughterrainBoulder.setChecked(si.getCanCarryBouldersOverRoughterrain());
            lowbarBoulder.setChecked(si.getCanCarryBouldersOverLowbar());
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
    public ScoutingInfo getScoutingInfo() {
        if (si == null) {
            si = new ScoutingInfo();
        }
        if(wasCreated){
            si.setAcquiresBouldersFromHumanPlayer(humanAcq.isChecked());
            si.setAcquiresBouldersFromFloor(floorAcq.isChecked());
            si.setPreferredBoulderSource(prefAcq.getText().toString());
            si.setCanCarryBouldersOverPortcullis(portcullisBoulder.isChecked());
            si.setCanCarryBouldersOverCheval(chevalBoulder.isChecked());
            si.setCanCarryBouldersOverMoat(moatBoulder.isChecked());
            si.setCanCarryBouldersOverRamparts(rampartsBoulder.isChecked());
            si.setCanCarryBouldersOverDrawbridge(rampartsBoulder.isChecked());
            si.setCanCarryBouldersOverSallyport(sallyportBoulder.isChecked());
            si.setCanCarryBouldersOverRockwall(rockwallBoulder.isChecked());
            si.setCanCarryBouldersOverRoughterrain(roughterrainBoulder.isChecked());
            si.setCanCarryBouldersOverLowbar(lowbarBoulder.isChecked());
        }
        return si;
    }


}
