package com.chavez.eduardo.trendingfeed;

import android.app.SearchManager;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.twitter.sdk.android.tweetui.SearchTimeline;
import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;

import java.util.ArrayList;
import java.util.List;

import BaseDeDatos.RoutineContract;
import BaseDeDatos.RoutineModel;

import static android.content.Context.SEARCH_SERVICE;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TwitterListFrg.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TwitterListFrg#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TwitterListFrg extends Fragment implements Validator.ValidationListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private static String SEARCH_QUERY = "#Fitness";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ListView listadoTwitter;
    private ImageButton getQueryInput;
    private TextView currentHashtag;

    @NotEmpty(message = "Ingrese un hashtag para la busqueda")
    EditText hashtagInput;

    private OnFragmentInteractionListener mListener;

    Validator validator;

    public TwitterListFrg() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TwitterListFrg.
     */
    // TODO: Rename and change types and number of parameters
    public static TwitterListFrg newInstance(String param1, String param2) {
        TwitterListFrg fragment = new TwitterListFrg();
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_twitter_list_frg, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        validator = new Validator(this);
        validator.setValidationListener(this);

        listadoTwitter = (ListView) getActivity().findViewById(R.id.listadoTweet);
        getQueryInput = (ImageButton) getActivity().findViewById(R.id.setQuery);
        hashtagInput = (EditText) getActivity().findViewById(R.id.hashtagInput);
        currentHashtag = (TextView) getActivity().findViewById(R.id.currentHashtag);
        currentHashtag.setText("Hashtag mostrado " + SEARCH_QUERY);

        getQueryInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validator.validate();
            }
        });
    }

    private void captureData() {
        SEARCH_QUERY = "#"+hashtagInput.getText().toString();
        currentHashtag.setText("Hashtag mostrado " + SEARCH_QUERY);
        setUpQueryList();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onValidationSucceeded() {
        captureData();
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error:errors){
            View view = error.getView();
            String message = error.getCollatedErrorMessage(getContext());

            if (view instanceof EditText){
                ((EditText)view).setError(message);
            } else {
                Toast.makeText(getContext(),message,Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onResume() {
        super.onResume();
        setUpQueryList();
    }


    private void setUpQueryList() {
        SearchTimeline searchTimeline = new SearchTimeline.Builder().query(SEARCH_QUERY).build();

        final TweetTimelineListAdapter timelineListAdapter = new TweetTimelineListAdapter(getContext(),searchTimeline);
        listadoTwitter.setAdapter(timelineListAdapter);
        listadoTwitter.setEmptyView(getActivity().findViewById(R.id.loadingTwitt));


    }



}
