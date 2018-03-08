package poojab26.bakingapp.Fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
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

import poojab26.bakingapp.R;
import poojab26.bakingapp.Utils.Constants;
import poojab26.bakingapp.model.Step;

import static poojab26.bakingapp.Fragments.RecipeItemDetailFragment.ARG_STEPS;

public class StepDetailFragment extends Fragment {

    public static final String ARG_STEP_POSITION_ID = "step_position_id";

    private ArrayList<Step> mSteps;
    private int mStepPositionID;
    private boolean mTwoPane = false;
    Button btnNext, btnPrev;
    private View rootView;

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
    private final String STEPS_POSITION = "steps_position";
    private final String PLAYER_POSITION = "player_position";
    private final String TWO_PANE = "two_pane";

    private MediaSource mVideoSource;
    private boolean mExoPlayerFullscreen = false;
    private FrameLayout mFullScreenButton;
    private ImageView mFullScreenIcon;
    private Dialog mFullScreenDialog;

    private int mResumeWindow;
    private long mResumePosition;
    FrameLayout frameLayout;
    String path;

    int layout_element;

    public StepDetailFragment() {
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
            mStepPositionID = savedInstanceState.getInt(STEPS_POSITION);
            playbackPosition = savedInstanceState.getLong(PLAYER_POSITION);
         //   mTwoPane = savedInstanceState.getBoolean(TWO_PANE);
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
        outState.putInt(STEPS_POSITION, mStepPositionID);
        outState.putLong(PLAYER_POSITION, playbackPosition);
        //outState.putBoolean(TWO_PANE, mTwoPane);

        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(Constants.TAG, "OnCreateView");
        rootView = inflater.inflate(R.layout.fragment_step_item, container, false);
        btnNext = rootView.findViewById(R.id.btnNext);
        btnPrev = rootView.findViewById(R.id.btnPrev);

        playerView = rootView.findViewById(R.id.exoplayer);
        frameLayout =  rootView.findViewById(R.id.main_media_frame);
        TextView tvDescription = rootView.findViewById(R.id.tvStepDescription);



      /*  PlaybackControlView controlView = playerView.findViewById(R.id.exo_controller);
        mFullScreenIcon = controlView.findViewById(R.id.exo_fullscreen_icon);
        mFullScreenButton = controlView.findViewById(R.id.exo_fullscreen_button);
*/
        if(frameLayout!=null && mSteps!=null) {

            tvDescription.setText(mSteps.get(mStepPositionID).getDescription());
            if(mTwoPane)
                layout_element = R.id.sw600;
            else layout_element = R.id.frame_step_detail;

            btnNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mStepPositionID < mSteps.size() - 1) {
                        StepDetailFragment fragment = new StepDetailFragment();
                        fragment.setSteps(mSteps);
                        fragment.setPosition(mStepPositionID + 1);
                        fragment.setTwoPane(mTwoPane);
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(layout_element, fragment, null)
                                .commit();
                    }

                }
            });

            btnPrev.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mStepPositionID > 0) {

                        StepDetailFragment fragment = new StepDetailFragment();
                        fragment.setSteps(mSteps);
                        fragment.setPosition(mStepPositionID - 1);
                        fragment.setTwoPane(mTwoPane);
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(layout_element, fragment, null)
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

    public void setTwoPane(boolean twoPane){ mTwoPane = twoPane;}


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

      //  hideSystemUi();
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

   /* @SuppressLint("InlinedApi")
    private void hideSystemUi() {
        playerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }*/

    private void hideVideoView(){
        playerView.setVisibility(View.GONE);
    }

}
