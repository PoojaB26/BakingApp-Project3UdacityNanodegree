package poojab26.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;


import poojab26.bakingapp.Interfaces.RetrofitInterface;
import poojab26.bakingapp.Utils.APIClient;
import poojab26.bakingapp.Utils.Constants;
import poojab26.bakingapp.adapters.RecipeAdapter;
import poojab26.bakingapp.model.Ingredient;
import poojab26.bakingapp.model.Recipe;
import poojab26.bakingapp.model.Step;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

import java.util.ArrayList;
import java.util.List;

/**
 * An activity representing a list of Items. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link RecipeItemDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class RecipeListActivity extends AppCompatActivity {


    private List<Recipe> recipeList = new ArrayList<>();


    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    RecipeAdapter recipeAdapter;
    RetrofitInterface retrofitInterface;
    RecyclerView recipeRecyclerView;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_item_list);

        Timber.plant(new Timber.DebugTree());


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        if (findViewById(R.id.item_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        recipeRecyclerView = (RecyclerView) findViewById(R.id.rvRecipes);
        layoutManager = new LinearLayoutManager(this);
        recipeRecyclerView.setLayoutManager(layoutManager);
        loadRecipes();

    }


    private void loadRecipes() {

        retrofitInterface = APIClient.getClient().create(RetrofitInterface.class);

        Call<List<Recipe>> call = retrofitInterface.getRecipeList();
        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {

                final List<Recipe> recipes = response.body();


                recipeRecyclerView.setAdapter(new RecipeAdapter(recipes, new RecipeAdapter.OnItemClickListener() {
                    @Override public void onItemClick(int position) {
                        Log.d("TAG", "inside main");
                        ArrayList<Ingredient> ingredients = recipes.get(position).getIngredients();
                        ArrayList<Step> steps = recipes.get(position).getSteps();

                        Bundle bundle = new Bundle();
                        bundle.putInt(RecipeItemDetailFragment.ARG_ITEM_ID, position);
                        bundle.putParcelableArrayList(RecipeItemDetailFragment.ARG_INGREDIENT, ingredients);
                        bundle.putParcelableArrayList(RecipeItemDetailFragment.ARG_STEPS, steps);


                        Log.d(Constants.TAG, steps.get(position).getDescription() );
                        for(int i = 0; i<steps.size(); i++) {
                            Log.d(Constants.TAG, steps.get(i).getDescription());
                        }

                        Intent intent = new Intent(RecipeListActivity.this, RecipeItemDetailActivity.class);
                        intent.putExtra(RecipeItemDetailFragment.ARG_ITEM_ID, position);
                        intent.putExtra("bundle", bundle);

                        startActivity(intent);

                    }
                } , RecipeListActivity.this, false));
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Log.d("Error", t.getMessage());


            }
        });


    }

}