package poojab26.bakingapp;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;

import poojab26.bakingapp.Fragments.RecipeItemDetailFragment;
import poojab26.bakingapp.Fragments.StepDetailFragment;
import poojab26.bakingapp.Utils.Constants;
import poojab26.bakingapp.model.Step;

/**
 * Created by poojab26 on 07-Mar-18.
 */

public class StepDetailActivity extends AppCompatActivity {

    Bundle extras;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);
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




    }


}
