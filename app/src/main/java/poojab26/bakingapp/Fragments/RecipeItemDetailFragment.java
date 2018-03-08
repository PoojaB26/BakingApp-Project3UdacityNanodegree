package poojab26.bakingapp.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
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

import poojab26.bakingapp.R;
import poojab26.bakingapp.RecipeItemDetailActivity;
import poojab26.bakingapp.RecipeListActivity;
import poojab26.bakingapp.StepDetailActivity;
import poojab26.bakingapp.Utils.Constants;
import poojab26.bakingapp.adapters.StepsAdapter;
import poojab26.bakingapp.model.Ingredient;
import poojab26.bakingapp.model.Step;

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a {@link RecipeListActivity}
 * in two-pane mode (on tablets) or a {@link RecipeItemDetailActivity}
 * on handsets.
 */
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

    ListView lvIngredients;
    TextView tvIngredientsList;

    StepsAdapter stepsAdapter;
    RecyclerView stepsRecyclerView;
    RecyclerView.LayoutManager layoutManager;

    /**
     * The dummy content this fragment is presenting.
     */
    private int mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public RecipeItemDetailFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //if (getArguments().containsKey(ARG_ITEM_ID)) {
        // Load the dummy content specified by the fragment
        // arguments. In a real-world scenario, use a Loader
        // to load content from a content provider.
//            mItem = getArguments().getInt(ARG_ITEM_ID);
        //
        //    Log.d(Constants.TAG, "mItem "+mItem);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            mPositionID = bundle.getInt(ARG_ITEM_ID, 0);
            Log.d(Constants.TAG, "position ID from fragment " + mPositionID);
            mIngredients = bundle.getParcelableArrayList(ARG_INGREDIENT);
            mSteps = bundle.getParcelableArrayList(ARG_STEPS);
        }


       /* Activity activity = this.getActivity();
        CollapsingToolbarLayout appBarLayout = activity.findViewById(R.id.toolbar_layout);
        if (appBarLayout != null) {
            //  appBarLayout.setTitle(mItem.content);
        }*/
        // }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recipe_item_detail, container, false);
        TextView tvID = rootView.findViewById(R.id.item_text);
       // lvIngredients = rootView.findViewById(R.id.list_ingredients);
        tvIngredientsList = rootView.findViewById(R.id.tvIngredientsList);
        stepsRecyclerView = rootView.findViewById(R.id.rvSteps);

        setupIngredientsList();
        setupStepsAdapter();
        tvID.setText(String.valueOf(mPositionID));
        return rootView;
    }

    private void setupIngredientsList() {
        StringBuilder stringIngredients = new StringBuilder();
        for (int i = 0; i < mIngredients.size(); ++i) {
            stringIngredients.append(mIngredients.get(i).getIngredient()+"\n");
        }

        tvIngredientsList.setText(stringIngredients);
    }

    private void setupStepsAdapter() {
        layoutManager = new LinearLayoutManager(getActivity());
        stepsRecyclerView.setLayoutManager(layoutManager);
        stepsRecyclerView.setAdapter(new StepsAdapter(mParentActivity, mTwoPane, mSteps, new StepsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

               /* Bundle bundle = new Bundle();
                bundle.putInt(StepDetailFragment.ARG_STEP_POSITION_ID, position);
                Log.d(Constants.TAG, "Recipe Detail Fragment position " + position);
                bundle.putParcelableArrayList(RecipeItemDetailFragment.ARG_STEPS, mSteps);

                Intent intent = new Intent(getActivity(), StepDetailActivity.class);
                intent.putExtra(Constants.BUNDLE_RECIPE, bundle);
                startActivity(intent);*/

               /* Intent intent = new Intent(context, StepDetailActivity.class);
                // intent.putExtra(RecipeItemDetailFragment.ARG_ITEM_ID, position);

                context.startActivity(intent);*/

               /* StepDetailFragment fragment = new StepDetailFragment();
                fragment.setSteps(mSteps);
                fragment.setPosition(position);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.item_detail_container, fragment, null)
                        .commit();*/
            }
        }));
    }

    private void setupListViewIngredients() {
        String[] stringArray_Ing = new String[mIngredients.size()];
        for (int i = 0; i < mIngredients.size(); ++i) {
            stringArray_Ing[i] = mIngredients.get(i).getIngredient();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                R.layout.listview_item, R.id.tv_ingredient_item, stringArray_Ing);


        // Assign adapter to ListView
        lvIngredients.setAdapter(adapter);
    }
    public void setId(int id) {
        mPositionID = id;
    }
    public void setTwoPane(boolean twoPane){ mTwoPane = twoPane;}

    public void setParentActivity(RecipeItemDetailActivity parentActivity) {
        mParentActivity = parentActivity;
    }
}
