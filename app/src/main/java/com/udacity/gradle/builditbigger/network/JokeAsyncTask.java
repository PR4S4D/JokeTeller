package com.udacity.gradle.builditbigger.network;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.slp.displayjoke.JokeActivity;
import com.slp.joke.provider.jokeApi.JokeApi;

import java.io.IOException;


public class JokeAsyncTask extends AsyncTask<Integer, Void, String> {
    private static final String JOKE_END_POINT = "https://jokeendpoint-163320.appspot.com/_ah/api/";
    private JokeApi myApiService = null;
    private ProgressBar progressBar;
    private Button tellJoke;
    private Context context;

    public JokeAsyncTask(Context context) {
        this.context = context;
    }

    public JokeAsyncTask(Context context, ProgressBar progressBar, Button tellJoke) {
        this.progressBar = progressBar;
        this.tellJoke = tellJoke;
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        showProgress();

    }

    private void showProgress() {
        if (null != progressBar && tellJoke != null) {
            progressBar.setVisibility(View.VISIBLE);
            tellJoke.setVisibility(View.GONE);
        }
    }

    private void stopProgress() {
        if (null != progressBar && tellJoke != null) {
            progressBar.setVisibility(View.GONE);
            tellJoke.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected String doInBackground(Integer... params) {
        if (myApiService == null) {

            //for server side verification
/*           JokeApi.Builder builder = new JokeApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                    .setRootUrl(JOKE_END_POINT);*/

            // for local verification
            JokeApi.Builder builder = new JokeApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });

            myApiService = builder.build();
        }

        int numberOfJokes = params[0];
        try {
            return myApiService.getJoke(numberOfJokes).execute().getJoke();
        } catch (IOException e) {
            return e.getMessage();
        }
    }


    @Override
    protected void onPostExecute(String joke) {
        super.onPostExecute(joke);
        Intent intent = new Intent(context, JokeActivity.class);
        intent.putExtra(JokeActivity.JOKE, joke);
        context.startActivity(intent);

        stopProgress();
    }
}
