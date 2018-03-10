package poojab26.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;


import butterknife.BindView;
import butterknife.ButterKnife;
import poojab26.bakingapp.Fragments.RecipeItemDetailFragment;
import poojab26.bakingapp.IdlingResource.SimpleIdlingResource;
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


    @Nullable
    private SimpleIdlingResource idlingResource;

    RetrofitInterface retrofitInterface;
    RecyclerView.LayoutManager layoutManager;

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.rvRecipes) RecyclerView recipeRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_item_list);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());
        setupRecyclerView();


    }

    private void setupRecyclerView() {
        if(findViewById(R.id.sw_grid)!=null)
             layoutManager = new GridLayoutManager(this, 2);
        else
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
                        ArrayList<Ingredient> ingredients = recipes.get(position).getIngredients();
                        ArrayList<Step> steps = recipes.get(position).getSteps();

                        Bundle bundle = new Bundle();
                        bundle.putInt(RecipeItemDetailFragment.ARG_ITEM_ID, position);
                        bundle.putString(RecipeItemDetailFragment.ARG_RECIPE_NAME, recipes.get(position).getName());
                        bundle.putParcelableArrayList(RecipeItemDetailFragment.ARG_INGREDIENT, ingredients);
                        bundle.putParcelableArrayList(RecipeItemDetailFragment.ARG_STEPS, steps);

                        Intent intent = new Intent(RecipeListActivity.this, RecipeItemDetailActivity.class);
                        intent.putExtra(RecipeItemDetailFragment.ARG_ITEM_ID, position);
                        intent.putExtra(Constants.BUNDLE_RECIPE, bundle);

                        startActivity(intent);

                    }
                }));
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Log.d("Error", t.getMessage());


            }
        });


    }

    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (idlingResource == null) {
            idlingResource = new SimpleIdlingResource();
        }
        return idlingResource;
    }

}
