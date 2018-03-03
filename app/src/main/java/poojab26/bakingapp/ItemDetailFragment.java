package poojab26.bakingapp;

import android.app.Activity;
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
import java.util.List;

import poojab26.bakingapp.Interfaces.RetrofitInterface;
import poojab26.bakingapp.Utils.Constants;
import poojab26.bakingapp.adapters.RecipeAdapter;
import poojab26.bakingapp.adapters.StepsAdapter;
import poojab26.bakingapp.dummy.DummyContent;
import poojab26.bakingapp.model.Ingredient;
import poojab26.bakingapp.model.Step;

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a {@link ItemListActivity}
 * in two-pane mode (on tablets) or a {@link ItemDetailActivity}
 * on handsets.
 */
public class ItemDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";
    public static final String ARG_STEPS = "steps";
    public static final String ARG_INGREDIENT = "ingredients";


    private int mPositionID;
    private ArrayList<Ingredient> mIngredients;
    private ArrayList<Step> mSteps;

    ListView lvIngredients;

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
    public ItemDetailFragment() {
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
            //TODO this part may be removed for landscape
            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
              //  appBarLayout.setTitle(mItem.content);
            }
       // }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.item_detail, container, false);
        TextView tvID = rootView.findViewById(R.id.item_text);
        lvIngredients = rootView.findViewById(R.id.list_ingredients);
        stepsRecyclerView = rootView.findViewById(R.id.rvSteps);

        setupListViewIngredients();
        setupStepsAdapter();
        tvID.setText(String.valueOf(mPositionID));
        return rootView;
    }

    private void setupStepsAdapter() {
        layoutManager = new LinearLayoutManager(getActivity());
        stepsRecyclerView.setLayoutManager(layoutManager);
        stepsRecyclerView.setAdapter(new StepsAdapter(mSteps, new StepsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Log.d(Constants.TAG, "clicked in detail fragment " + mSteps.get(mPositionID).getDescription());
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
    public void setIngredients(ArrayList<Ingredient> ingredients) {
        mIngredients = ingredients;
    }
    public void setSteps(ArrayList<Step> steps) {
        mSteps = steps;
    }

}
