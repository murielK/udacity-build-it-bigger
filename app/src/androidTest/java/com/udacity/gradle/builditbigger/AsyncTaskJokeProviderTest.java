package com.udacity.gradle.builditbigger;

import android.util.Log;

import java.util.concurrent.ExecutionException;

public class AsyncTaskJokeProviderTest extends ApplicationTest {

    private static final String TAG = "AsyncTaskJokeProviderTest";

    public void testJokeIsNotEmpty() {

        final EndpointsAsyncTask endpointsAsyncTask = new EndpointsAsyncTask(getContext());
        endpointsAsyncTask.execute();

        String result = null;

        try {
            result = endpointsAsyncTask.get();
        } catch (InterruptedException e) {
            Log.d(TAG, "", e);
        } catch (ExecutionException e) {
            Log.d(TAG, "", e);
        }

        assertNotNull(result);
    }

}
