package poojab26.bakingapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;

import poojab26.bakingapp.Utils.Constants;
import poojab26.bakingapp.model.Step;

public class StepItemFragment extends Fragment {

    private ArrayList<Step> mSteps;
    private int mStepPositionID;
    Button btnNext, btnPrev;

    private SimpleExoPlayer player;
    private SimpleExoPlayerView playerView;

    private long playbackPosition;
    private int currentWindow;
    private boolean playWhenReady = true;

    String path;

    public StepItemFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_step_item, container, false);
        btnNext = rootView.findViewById(R.id.btnNext);
        btnPrev = rootView.findViewById(R.id.btnPrev);

        playerView = rootView.findViewById(R.id.player_view);

        TextView tvDescription = rootView.findViewById(R.id.tvStepDescription);
        tvDescription.setText(mSteps.get(mStepPositionID).getDescription());
        path = mSteps.get(mStepPositionID).getVideoURL();

         StepItemFragment fragment = new StepItemFragment();
        fragment.setSteps(mSteps);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mStepPositionID<mSteps.size()-1) {
                    StepItemFragment fragment = new StepItemFragment();
                    fragment.setSteps(mSteps);
                    fragment.setPosition(mStepPositionID + 1);
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.item_detail_container, fragment, null)
                            .commit();
                }

            }
        });

        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mStepPositionID>0) {

                    StepItemFragment fragment = new StepItemFragment();
                    fragment.setSteps(mSteps);
                    fragment.setPosition(mStepPositionID - 1);
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.item_detail_container, fragment, null)
                            .commit();

                }

            }
        });


        return rootView;
    }



    public void setSteps(ArrayList<Step> steps){
        mSteps = steps;
    }

    public void setPosition(int position){
        mStepPositionID = position;
    }


    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            if(!path.equals(""))
                initializePlayer();
            else hideVideoView();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        hideSystemUi();
        if ((Util.SDK_INT <= 23 || player == null)) {
            if(!path.equals(""))
                initializePlayer();
            else hideVideoView();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(Constants.TAG, "onPause" + path);

        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(Constants.TAG, "onStop" + path);

        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    private void initializePlayer() {
        if (player == null) {
            player = ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(getActivity()),
                    new DefaultTrackSelector(), new DefaultLoadControl());
            playerView.setPlayer(player);
            player.setPlayWhenReady(playWhenReady);
            player.seekTo(currentWindow, playbackPosition);
        }

        MediaSource mediaSource =
                buildMediaSource(Uri.parse(path));
        player.prepare(mediaSource, true, false);
    }

    private void releasePlayer() {
        if (player != null) {
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            playWhenReady = player.getPlayWhenReady();
            player.release();
            player = null;
        }
    }

    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource.Factory(new DefaultHttpDataSourceFactory("exoplayer-codelab"))
                .createMediaSource(uri);
    }

    @SuppressLint("InlinedApi")
    private void hideSystemUi() {
        playerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    private void hideVideoView(){
        playerView.setVisibility(View.GONE);
    }

}
