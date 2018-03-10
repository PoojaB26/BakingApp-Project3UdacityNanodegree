package poojab26.bakingapp;

import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;

/**
 * Created by poojab26 on 10-Mar-18.
 */

@RunWith(AndroidJUnit4.class)
public class StepDetailActivityTest {
    @Rule
    public final ActivityTestRule<RecipeListActivity> recipeActivityTestRule = new ActivityTestRule<>(RecipeListActivity.class);

    private IdlingResource idlingResource;

    @Before
    public void registerIdlingResource() {
        idlingResource = recipeActivityTestRule.getActivity().getIdlingResource();
        IdlingRegistry.getInstance().register(idlingResource);
    }

    @Test
    public void checkPlayerViewIsVisible_RecipeItemDetailActivity1() throws InterruptedException {
        Thread.sleep(1000);
        onView(withId(R.id.rvRecipes)).
                perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        Thread.sleep(1000);

      /*  onView(withId(R.id.rvSteps))
                .perform(RecyclerViewActions.actionOnItemAtPosition(3, click()));
*/
        onView(allOf(withId(R.id.rvSteps), isDisplayed())).perform(RecyclerViewActions.actionOnItemAtPosition(3, click()));

           onView(withId(R.id.exoplayer)).check(matches(isDisplayed()));
    }


    @After
    public void unregisterIdlingResource() {
        if (idlingResource != null) {
            IdlingRegistry.getInstance().unregister(idlingResource);
        }
    }

}
