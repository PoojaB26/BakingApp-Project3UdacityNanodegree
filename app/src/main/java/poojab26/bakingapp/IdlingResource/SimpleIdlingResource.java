package poojab26.bakingapp.IdlingResource;

import android.support.annotation.Nullable;
import android.support.test.espresso.IdlingResource;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by poojab26 on 10-Mar-18.
 */

public class SimpleIdlingResource implements IdlingResource {

    @Nullable
    private volatile ResourceCallback callback;

    private final AtomicBoolean isIdleNow = new AtomicBoolean(true);

    @Override
    public String getName() {
        return this.getClass().getName();
    }

    @Override
    public boolean isIdleNow() {
        return isIdleNow.get();
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        this.callback = callback;
    }

    public void setIdleState(boolean state) {
        isIdleNow.set(state);
        if (state && callback != null) {
            callback.onTransitionToIdle();
        }
    }
}
