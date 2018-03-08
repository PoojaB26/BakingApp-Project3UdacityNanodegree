package poojab26.bakingapp.Interfaces;

import java.util.List;

import poojab26.bakingapp.model.Recipe;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by poojab26 on 02-Mar-18.
 */


public interface RetrofitInterface {
    @GET("baking.json")
    Call<List<Recipe>> getRecipeList();

}
