package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.backend.jokeApi.JokeApi;

import java.io.IOException;
import java.lang.ref.WeakReference;

import hr.murielkamgang.jokeviewer.ShowJokesActivity;

public class EndpointsAsyncTask extends AsyncTask<Void, Void, String> {

    private static final String TAG = EndpointsAsyncTask.class.getSimpleName();
    private static JokeApi jokeApiService = null;
    private WeakReference<Context> weakContext;

    EndpointsAsyncTask(Context context) {
        this.weakContext = new WeakReference<>(context);
    }

    @Override
    protected String doInBackground(Void... params) {
        if (jokeApiService == null) {// Only do this once
            JokeApi.Builder builder = new JokeApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            jokeApiService = builder.build();
        }

        try {
            return jokeApiService.getJoke().execute().getData();
        } catch (IOException e) {
            Log.d(TAG, "error while trying to get joke", e);
            return null;
        }
    }

    @Override
    protected void onPostExecute(String result) {
        final Context context;
        if ((context = weakContext.get()) != null) {
            if (context instanceof MainActivity) {
                ((MainActivity) context).progressBar.setVisibility(View.GONE);
            }
            if (result != null) {
                ShowJokesActivity.show(context, result);
            } else {
                Toast.makeText(context, R.string.error_msg_joke_not_found, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onPreExecute() {
        final Context context;
        if ((context = weakContext.get()) != null && context instanceof MainActivity) {
            ((MainActivity) context).progressBar.setVisibility(View.VISIBLE);
        }
    }
}