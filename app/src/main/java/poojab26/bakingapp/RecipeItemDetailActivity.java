package poojab26.bakingapp;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import poojab26.bakingapp.Fragments.RecipeItemDetailFragment;
import poojab26.bakingapp.Fragments.StepDetailFragment;
import poojab26.bakingapp.Utils.Constants;
import poojab26.bakingapp.model.Ingredient;
import poojab26.bakingapp.model.Step;

/**
 * An activity representing a single Item detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link RecipeListActivity}.
 */
public class RecipeItemDetailActivity extends AppCompatActivity {
    private final static String RECIPE_NAME = "recipe_name";
    private final static String INGREDIENT_LIST = "ingredient_list";
    private final static String STEPS_LIST = "steps_list";
    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    ArrayList<Ingredient> ingredientList;
    ArrayList<Step> stepsList;
    Bundle extras;
    static String recipeName;

    @BindView(R.id.fab) FloatingActionButton fab;
    @BindView(R.id.toolbar) Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_item_detail);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        if(savedInstanceState!=null) {
            recipeName = savedInstanceState.getString(RECIPE_NAME);
            ingredientList = savedInstanceState.getParcelableArrayList(INGREDIENT_LIST);
            stepsList = savedInstanceState.getParcelableArrayList(STEPS_LIST);

        }
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RecipeAppWidget.setRecipeName(recipeName);

                String quantity, ingredient;
                StringBuilder ingredientString = new StringBuilder();
                for(int i=0; i<ingredientList.size(); i++){
                    quantity = ingredientList.get(i).getQuantity() + " "+ ingredientList.get(i).getMeasure();
                    ingredient = ingredientList.get(i).getIngredient();
                    ingredientString.append(quantity + " " + ingredient + "\n");

                 }
                RecipeAppWidget.setIngredients(ingredientString);
                Intent intent = new Intent(RecipeItemDetailActivity.this, RecipeAppWidget.class);
                intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);

                int[] ids = AppWidgetManager.getInstance(getApplication())
                        .getAppWidgetIds(new ComponentName(getApplication(), RecipeAppWidget.class));
                intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
                sendBroadcast(intent);
            }
        });

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if (findViewById(R.id.sw600) != null) {
            mTwoPane = true;
        }




        if (savedInstanceState == null) {
            int position_ID = getIntent().getIntExtra(RecipeItemDetailFragment.ARG_ITEM_ID, 0);
            extras = getIntent().getBundleExtra(Constants.BUNDLE_RECIPE);
            if(extras!=null)
            {
                recipeName = extras.getString(RecipeItemDetailFragment.ARG_RECIPE_NAME);
                ingredientList  = extras.getParcelableArrayList(RecipeItemDetailFragment.ARG_INGREDIENT);
                stepsList = extras.getParcelableArrayList(RecipeItemDetailFragment.ARG_STEPS);
            }




            RecipeItemDetailFragment fragment = new RecipeItemDetailFragment();
                fragment.setArguments(extras);
                fragment.setTwoPane(mTwoPane);
                fragment.setParentActivity(RecipeItemDetailActivity.this);

            getSupportFragmentManager().beginTransaction()
                        .replace(R.id.item_detail_container, fragment, null)
                        .commit();



        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(recipeName);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(RECIPE_NAME, recipeName);
        outState.putParcelableArrayList(INGREDIENT_LIST, ingredientList);
        outState.putParcelableArrayList(STEPS_LIST, stepsList);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            navigateUpTo(new Intent(this, RecipeListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



}
