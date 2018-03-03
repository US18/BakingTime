package com.example.uplabdhisingh.bakingtime;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.uplabdhisingh.bakingtime.Details.RecipeDetail;
import com.example.uplabdhisingh.bakingtime.Details.Step;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveVideoTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by uplabdhisingh on 02/03/18.
 */

public class StepDetailFragment extends Fragment
{
    SimpleExoPlayerView recipePlayerView;
    SimpleExoPlayer recipePlayer;
    TextView shortDescTextView,descTextView;
    ArrayList<Step> stepArrayList;
    RecipeDetail recipeDetail;
    int selectedIndex;

    private String TAG = StepDetailFragment.class.getSimpleName();

    public StepDetailFragment()
    {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        if(savedInstanceState!=null)
        {
            stepArrayList=savedInstanceState.getParcelableArrayList("STEPS_SELECTED");
            selectedIndex=savedInstanceState.getInt("INDEX_SELECTED");
        } else
        {
            stepArrayList=getArguments().getParcelableArrayList("STEPS_SELECTED");
            if(stepArrayList!=null)
            {
                stepArrayList=getArguments().getParcelableArrayList("STEPS_SELECTED");
                selectedIndex=getArguments().getInt("INDEX_SELECTED");
            } else {
                recipeDetail=getArguments().getParcelable("RecipeDetail");
                stepArrayList=recipeDetail.getSteps();
                selectedIndex=0;
            }
        }

        View rootView = inflater.inflate(R.layout.fragment_step_detail,container,false);

        shortDescTextView=(TextView) rootView.findViewById(R.id.tv_short_description);
        descTextView=(TextView) rootView.findViewById(R.id.tv_description);
        recipePlayerView=(SimpleExoPlayerView) rootView.findViewById(R.id.playerView);

        shortDescTextView.setText(stepArrayList.get(selectedIndex).getShortDescription());
        shortDescTextView.setVisibility(View.VISIBLE);
        descTextView.setText(stepArrayList.get(selectedIndex).getDescription());
        descTextView.setVisibility(View.VISIBLE);
        String videoURL = stepArrayList.get(selectedIndex).getVideoURL();
        if(!videoURL.isEmpty())
        {
            initializePlayer(Uri.parse(videoURL));
        }

        return rootView;
    }


    private void initializePlayer(Uri mediaUri)
    {
        if (recipePlayer == null)
        {
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();

            recipePlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            recipePlayerView.setPlayer(recipePlayer);

            String userAgent = Util.getUserAgent(getContext(), "Baking App");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(getContext(), userAgent),
                    new DefaultExtractorsFactory(),
                    null,
                    null);
            recipePlayer.prepare(mediaSource);
            recipePlayer.setPlayWhenReady(true);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
       outState.putParcelableArrayList("STEPS_SELECTED",stepArrayList);
        outState.putInt("INDEX_SELECTED",selectedIndex);
    }

   private void releasePlayer()
   {
       recipePlayer.stop();
       recipePlayer.release();
   }

  /*  @Override
    public void onDetach() {
        super.onDetach();
        if(recipePlayer!=null)
        {
            releasePlayer();
        }
    } */

   /* @Override
    public void onDestroyView() {
        super.onDestroyView();
        /*if(recipePlayer!=null)
        {
            recipePlayer.stop();
            recipePlayer.release();
            recipePlayer=null;
        }
    } */

    @Override
    public void onStop() {
        super.onStop();
        if(recipePlayer!=null)
        {
            releasePlayer();
        }
    }

  /*  @Override
    public void onPause() {
        super.onPause();
        if(recipePlayer!=null)
        {
            releasePlayer();
        }
    } */

}
