package poojab26.bakingapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;

import java.lang.reflect.Array;
import java.util.ArrayList;

import poojab26.bakingapp.Fragments.RecipeItemDetailFragment;
import poojab26.bakingapp.Utils.Constants;
import poojab26.bakingapp.model.Ingredient;
import poojab26.bakingapp.model.Recipe;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeAppWidget extends AppWidgetProvider {

    static String mRecipeName;
    static StringBuilder mIngredients;
    static int recipe_id;
   static Bundle mBundle;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_app_widget);
        views.setTextViewText(R.id.tvRecipeName, mRecipeName);
        views.setTextViewText(R.id.tvIngredientsWidget, mIngredients);

        Intent intent = new Intent(context, RecipeListActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        views.setOnClickPendingIntent(R.id.widget_container, pendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    public static void setBundle(Bundle bundle) {
        mBundle = bundle;
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    public static void setRecipeName(String recipeName){
        mRecipeName = recipeName;

    }

    public static void setRecipeID(int ID){
        recipe_id = ID;

    }

    public static void setIngredients(StringBuilder ingredients){
        mIngredients = ingredients;

    }


}

