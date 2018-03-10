package poojab26.bakingapp.Fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import poojab26.bakingapp.R;
import poojab26.bakingapp.RecipeItemDetailActivity;
import poojab26.bakingapp.RecipeListActivity;
import poojab26.bakingapp.Utils.Constants;
import poojab26.bakingapp.adapters.StepsAdapter;
import poojab26.bakingapp.model.Ingredient;
import poojab26.bakingapp.model.Step;


public class RecipeItemDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";
    public static final String ARG_RECIPE_NAME = "recipe_name";
    public static final String ARG_STEPS = "steps";
    public static final String ARG_INGREDIENT = "ingredients";


    private int mPositionID;
    private boolean mTwoPane;
    private ArrayList<Ingredient> mIngredients;
    private ArrayList<Step> mSteps;
    RecipeItemDetailActivity mParentActivity;

    @BindView(R.id.tvIngredientsList)TextView tvIngredientsList;
    @BindView(R.id.rvSteps) RecyclerView stepsRecyclerView;
    RecyclerView.LayoutManager layoutManager;

    public RecipeItemDetailFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            mPositionID = bundle.getInt(ARG_ITEM_ID, 0);
            mIngredients = bundle.getParcelableArrayList(ARG_INGREDIENT);
            mSteps = bundle.getParcelableArrayList(ARG_STEPS);
        }



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.recipe_item_detail, container, false);
        ButterKnife.bind(this, rootView);

        setupIngredientsList();
        setupStepsAdapter();

        return rootView;
    }

    private void setupIngredientsList() {
        StringBuilder stringIngredients = new StringBuilder();
        String quantity, ingredient;
        for (int i = 0; i < mIngredients.size(); ++i) {
            quantity = mIngredients.get(i).getQuantity() + " "+ mIngredients.get(i).getMeasure();
            ingredient = mIngredients.get(i).getIngredient();

            stringIngredients.append(quantity + " " + ingredient + "\n");
        }

        tvIngredientsList.setText(stringIngredients);
    }

    private void setupStepsAdapter() {
        layoutManager = new LinearLayoutManager(getActivity());
        stepsRecyclerView.setLayoutManager(layoutManager);
        stepsRecyclerView.setAdapter(new StepsAdapter(mParentActivity, mTwoPane, mSteps, new StepsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }
        }));
    }


    public void setId(int id) {
        mPositionID = id;
    }
    public void setTwoPane(boolean twoPane){ mTwoPane = twoPane;}
    public void setParentActivity(RecipeItemDetailActivity parentActivity) {
        mParentActivity = parentActivity;
    }
}
