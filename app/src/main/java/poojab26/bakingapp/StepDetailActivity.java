package poojab26.bakingapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import poojab26.bakingapp.Fragments.RecipeItemDetailFragment;
import poojab26.bakingapp.Fragments.StepDetailFragment;
import poojab26.bakingapp.Utils.Constants;
import poojab26.bakingapp.model.Step;

/**
 * Created by poojab26 on 07-Mar-18.
 */

public class StepDetailActivity extends AppCompatActivity {

    Bundle extras;
    int mStepPositionID;
    ArrayList<Step> mStepArrayList;
    @BindView(R.id.toolbar)
    Toolbar toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());
        Log.d(Constants.TAG, "Activity");

        extras = getIntent().getBundleExtra(Constants.BUNDLE_RECIPE);

            if (extras != null) {

                int position_ID = extras.getInt(StepDetailFragment.ARG_STEP_POSITION_ID);
                ArrayList<Step> stepsList = extras.getParcelableArrayList(RecipeItemDetailFragment.ARG_STEPS);
                StepDetailFragment fragment = new StepDetailFragment();
                fragment.setPosition(position_ID);
                fragment.setSteps(stepsList);
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.frame_step_detail, fragment)
                        .commit();

            }

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }



    }

    public void setTitle(String str){

    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.d(Constants.TAG, "Activity congif");
/*
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            int position_ID = extras.getInt(StepDetailFragment.ARG_STEP_POSITION_ID);
            ArrayList<Step> stepsList = extras.getParcelableArrayList(RecipeItemDetailFragment.ARG_STEPS);
            StepDetailFragment fragment = new StepDetailFragment();
            fragment.setPosition(position_ID);
            fragment.setSteps(stepsList);
            fragment.setFullScreen(true);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frame_step_detail, fragment)
                    .commit();

        }else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            int position_ID = extras.getInt(StepDetailFragment.ARG_STEP_POSITION_ID);
            ArrayList<Step> stepsList = extras.getParcelableArrayList(RecipeItemDetailFragment.ARG_STEPS);
            StepDetailFragment fragment = new StepDetailFragment();
            fragment.setPosition(position_ID);
            fragment.setSteps(stepsList);
            fragment.setFullScreen(false);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frame_step_detail, fragment)
                    .commit();
        }*/



    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return(super.onOptionsItemSelected(item));
    }



}
