package poojab26.bakingapp;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlaybackControlView;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import poojab26.bakingapp.Utils.Constants;
import poojab26.bakingapp.model.Step;

public class StepItemFragment extends Fragment {

    private ArrayList<Step> mSteps;
    private int mStepPositionID;
    Button btnNext, btnPrev;
    private View rootView;
    Context context;

    private SimpleExoPlayer player;
    private SimpleExoPlayerView playerView;

    private long playbackPosition;
    private int currentWindow;
    private boolean playWhenReady = true;

    private final String STATE_RESUME_WINDOW = "resumeWindow";
    private final String STATE_RESUME_POSITION = "resumePosition";
    private final String STATE_PLAYER_FULLSCREEN = "playerFullscreen";
    private final String VIDEO_PATH = "video_path";
    private final String STEPS_OBJECT = "steps_object";
    private final String STEPS_POSITION_ID = "steps_position";

    private MediaSource mVideoSource;
    private boolean mExoPlayerFullscreen = false;
    private FrameLayout mFullScreenButton;
    private ImageView mFullScreenIcon;
    private Dialog mFullScreenDialog;

    private int mResumeWindow;
    private long mResumePosition;
    FrameLayout frameLayout;
    String path;

    ViewGroup mContainer;

    public StepItemFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(Constants.TAG, "onCreate");
        if (savedInstanceState != null) {
            mResumeWindow = savedInstanceState.getInt(STATE_RESUME_WINDOW);
            mResumePosition = savedInstanceState.getLong(STATE_RESUME_POSITION);
            mExoPlayerFullscreen = savedInstanceState.getBoolean(STATE_PLAYER_FULLSCREEN);
            path = savedInstanceState.getString(VIDEO_PATH);
            mSteps = savedInstanceState.getParcelableArrayList(STEPS_OBJECT);
            mStepPositionID = savedInstanceState.getInt(STEPS_POSITION_ID);
        }

        // StepItemFragmentTest fragment = new StepItemFragmentTest();
        // fragment.setSteps(mSteps);

        if(mSteps!=null)
            path = mSteps.get(mStepPositionID).getVideoURL();


    }
    @Override
    public void onSaveInstanceState(Bundle outState) {

        outState.putInt(STATE_RESUME_WINDOW, mResumeWindow);
        outState.putLong(STATE_RESUME_POSITION, mResumePosition);
        outState.putBoolean(STATE_PLAYER_FULLSCREEN, mExoPlayerFullscreen);
        outState.putString(VIDEO_PATH, path);
        outState.putParcelableArrayList(STEPS_OBJECT, mSteps);
        outState.putInt(STEPS_POSITION_ID, mStepPositionID);

        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getContext();
        mContainer = container;
        Log.d(Constants.TAG, "OnCreateView " + container);
        rootView = inflater.inflate(R.layout.fragment_step_item, container, false);
        btnNext = rootView.findViewById(R.id.btnNext);
        btnPrev = rootView.findViewById(R.id.btnPrev);

        playerView = rootView.findViewById(R.id.exoplayer);
        frameLayout =  rootView.findViewById(R.id.main_media_frame);

        PlaybackControlView controlView = playerView.findViewById(R.id.exo_controller);
        mFullScreenIcon = controlView.findViewById(R.id.exo_fullscreen_icon);
        mFullScreenButton = controlView.findViewById(R.id.exo_fullscreen_button);




        if(frameLayout!=null && mSteps!=null) {

            TextView tvDescription = rootView.findViewById(R.id.tvStepDescription);
            tvDescription.setText(mSteps.get(mStepPositionID).getDescription());


            btnNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mStepPositionID < mSteps.size() - 1) {
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
                    if (mStepPositionID > 0) {

                        StepItemFragment fragment = new StepItemFragment();
                        fragment.setSteps(mSteps);
                        fragment.setPosition(mStepPositionID - 1);
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.item_detail_container, fragment, null)
                                .commit();

                    }

                }
            });
        }


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
        if(playerView == null){
            playerView = rootView.findViewById(R.id.exoplayer);

        }
        if ((Util.SDK_INT <= 23 || player == null )) {

            if(!path.equals(""))
                initializePlayer();
            else hideVideoView();
        }

        if (mExoPlayerFullscreen) {
            ((ViewGroup) playerView.getParent()).removeView(playerView);
            mFullScreenDialog.addContentView(playerView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            mFullScreenIcon.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_fullscreen_skrink));
            mFullScreenDialog.show();
        }


    }

    @Override
    public void onPause() {
        super.onPause();

        if (Util.SDK_INT <= 23) {
            // releasePlayer();


            if (playerView != null && playerView.getPlayer() != null) {
                mResumeWindow = playerView.getPlayer().getCurrentWindowIndex();
                mResumePosition = Math.max(0, playerView.getPlayer().getContentPosition());

                playerView.getPlayer().release();
            }

            if (mFullScreenDialog != null)
                mFullScreenDialog.dismiss();

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
      //  if (player == null) {
            initFullscreenDialog();
            initFullscreenButton();
            player = ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(getActivity()),
                    new DefaultTrackSelector(), new DefaultLoadControl());
            playerView.setPlayer(player);
            player.setPlayWhenReady(playWhenReady);
            player.seekTo(currentWindow, playbackPosition);


        MediaSource mediaSource =
                buildMediaSource(Uri.parse(path));
        player.prepare(mediaSource, true, false);
    }

    private void releasePlayer() {
        Log.d(Constants.TAG, "release ");
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


    private void initFullscreenDialog() {

        mFullScreenDialog = new Dialog(getActivity(), android.R.style.Theme_Black_NoTitleBar_Fullscreen) {
            public void onBackPressed() {
                if (mExoPlayerFullscreen)
                    closeFullscreenDialog();
                super.onBackPressed();
            }
        };
    }

    private void openFullscreenDialog() {

        ((ViewGroup) playerView.getParent()).removeView(playerView);
        mFullScreenDialog.addContentView(playerView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mFullScreenIcon.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_fullscreen_skrink));
        mExoPlayerFullscreen = true;
        mFullScreenDialog.show();
    }


    private void closeFullscreenDialog() {
        Log.d(Constants.TAG, "close full screen dialog");
        frameLayout.removeView(playerView);
        frameLayout =  rootView.findViewById(R.id.main_media_frame);
        playerView = rootView.findViewById(R.id.exoplayer);

        mExoPlayerFullscreen = false;
        mFullScreenDialog.dismiss();
        mFullScreenIcon.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_fullscreen_expand));


    }


    private void initFullscreenButton() {


        mFullScreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mExoPlayerFullscreen)
                    openFullscreenDialog();
                else
                    closeFullscreenDialog();
            }
        });
    }

}
