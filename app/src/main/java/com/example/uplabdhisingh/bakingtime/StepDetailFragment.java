package com.example.uplabdhisingh.bakingtime;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.uplabdhisingh.bakingtime.Details.RecipeDetail;
import com.example.uplabdhisingh.bakingtime.Details.Step;
import com.google.android.exoplayer2.C;
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
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;


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
   // int currentWindow=0;
    long playbackPosition=0;
    private long playerStopPosition;
    private boolean isFullScreen = false;
    private boolean isNull;
    private boolean playerStopped = false;
   public String thumbUrl;
    public String videoURL;
    private String TAG = StepDetailFragment.class.getSimpleName();

    public StepDetailFragment()
    {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {


        View rootView = inflater.inflate(R.layout.fragment_step_detail,container,false);

        checkFullScreen();

        if(savedInstanceState==null)
        {
            /*stepArrayList=savedInstanceState.getParcelableArrayList("STEPS_SELECTED");
            selectedIndex=savedInstanceState.getInt("INDEX_SELECTED");*/
            isFullScreen=false;
        } else
        {
            playbackPosition=savedInstanceState.getLong("EXO_CURRENT_POSITION");

        }
        shortDescTextView=(TextView) rootView.findViewById(R.id.tv_short_description);
        descTextView=(TextView) rootView.findViewById(R.id.tv_description);
        recipePlayerView=(SimpleExoPlayerView) rootView.findViewById(R.id.playerView);

        shortDescTextView.setText(stepArrayList.get(selectedIndex).getShortDescription()+" : ");
        shortDescTextView.setVisibility(View.VISIBLE);
        descTextView.setText(stepArrayList.get(selectedIndex).getDescription());
        descTextView.setVisibility(View.VISIBLE);


        videoURL = stepArrayList.get(selectedIndex).getVideoURL();
        thumbUrl = stepArrayList.get(selectedIndex).getThumbnailURL();

        if(!videoURL.isEmpty())
        {
            initializePlayer(Uri.parse(videoURL));
            if(rootView.findViewById(R.id.linear_recipe_landscape)!=null)
            {
                getActivity().findViewById(R.id.recipe_detail_fragment_container2)
                        .setLayoutParams(new LinearLayout.LayoutParams(-1,-2));
                recipePlayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIXED_WIDTH);
            } /*else if(isInLandscapeMode(getContext())) {
                descTextView.setVisibility(View.GONE);
                shortDescTextView.setVisibility(View.GONE);
            }*/
        } else
            {
            assert recipePlayerView!=null;
            /*recipePlayerView.setForeground(ContextCompat.getDrawable(getContext(),R.drawable.ic_visibility_off_white_36dp));
            recipePlayerView.setLayoutParams(new LinearLayout.LayoutParams(300, 300));*/
            recipePlayerView.setVisibility(View.GONE);
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        }

        String imageUrl = stepArrayList.get(selectedIndex).getThumbnailURL();
        if(imageUrl!="")
        {
            Uri builtUri = Uri.parse(imageUrl).buildUpon().build();
            ImageView thumbnailImageView = (ImageView) rootView.findViewById(R.id.iv_thumbnail);
            Picasso.with(getContext()).load(builtUri).into(thumbnailImageView);
        }

        setRetainInstance(true);
        return rootView;
    }


  /*  public boolean isInLandscapeMode(Context context)
    {
        return (context.getResources().getConfiguration().orientation==Configuration.ORIENTATION_LANDSCAPE);
    }*/

    public void checkFullScreen(){
        Configuration newConfig = new Configuration();
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            isFullScreen = true;
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            isFullScreen = false;
        }
    }

    private void initializePlayer(Uri mediaUri)
    {
        if (recipePlayer == null)
        {
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();

            recipePlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            recipePlayerView.setPlayer(recipePlayer);

            String userAgent = Util.getUserAgent(getContext(), "BakingTime");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(getContext(), userAgent),
                    new DefaultExtractorsFactory(),
                    null,
                    null);


            recipePlayer.prepare(mediaSource);
            if(playbackPosition!=0 && !playerStopped)
            {
                recipePlayer.seekTo(playbackPosition);
            } else {
                recipePlayer.seekTo(playbackPosition);
            }

            //recipePlayer.setPlayWhenReady(true);
           // recipePlayer.seekTo(currentWindow,playbackPosition);
        }
    }


    @SuppressLint("InlinedApi")
    private void hideSystemUi() {
        recipePlayerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

   /* private void releasePlayer() {
        if (recipePlayer != null) {
            playbackPosition = recipePlayer.getCurrentPosition();
            currentWindow = recipePlayer.getCurrentWindowIndex();
            recipePlayer.getPlayWhenReady();
            recipePlayer.release();
            recipePlayer = null;
        }
    }*/

    private void releasePlayer(){
        if(recipePlayer != null) {
            recipePlayer.stop();
            recipePlayer.release();
            recipePlayer = null;
        }
    }


    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("STEPS_SELECTED",stepArrayList);
        outState.putInt("INDEX_SELECTED",selectedIndex);
        outState.putLong("EXO_CURRENT_POSITION",recipePlayer.getCurrentPosition());
    }

    @Override
    public void onStart() {
        super.onStart();
        /*if (Util.SDK_INT > 23) {
            initializePlayer(Uri.parse(videoURL));
        }*/

        initializePlayer(Uri.parse(videoURL));
    }

    @Override
    public void onStop() {
        super.onStop();
       /* if (Util.SDK_INT > 23) {
            releasePlayer();
        }*/

       /* if(recipePlayer!=null)
        {
            recipePlayer.stop();
            recipePlayer.release();
        }*/
       if(recipePlayer!=null)
       {
           playerStopPosition = recipePlayer.getCurrentPosition();
           playerStopped=true;
           releasePlayer();
       }
    }

   /* @Override
    public void onResume() {

        super.onResume();
        hideSystemUi();
        *//*if ((Util.SDK_INT <= 23 || recipePlayer == null)) {
            initializePlayer(Uri.parse(videoURL));
        }*//*
    }
*/
    /*@Override
    public void onDetach() {
        super.onDetach();
        if (recipePlayer!=null) {
            recipePlayer.stop();
            recipePlayer.release();
        }
    }*/

  /*  @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
        *//*if(recipePlayer!=null)
        {
            recipePlayer.stop();
            recipePlayer.release();
        }*//*
    }
*/
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        /*if(recipePlayer!=null)
        {
            recipePlayer.stop();
            recipePlayer.release();
            recipePlayer=null;
        }*/
        releasePlayer();

    }
}
