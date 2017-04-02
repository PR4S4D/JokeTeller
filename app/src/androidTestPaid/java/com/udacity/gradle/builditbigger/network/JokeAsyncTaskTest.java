package com.udacity.gradle.builditbigger.network;

import android.annotation.SuppressLint;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.text.TextUtils;
import android.util.Log;

import com.udacity.gradle.builditbigger.paid.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.ExecutionException;

/**
 * Created by lshivaram on 4/3/2017.
 */
@RunWith(AndroidJUnit4.class)
public class JokeAsyncTaskTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);

    @SuppressLint("Assert")
    @Test
    public void asyncTaskTest() throws Throwable {

        mActivityRule.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String joke = null;
                try {
                    joke = new JokeAsyncTask(mActivityRule.getActivity()).execute(10).get();
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
                Log.i("Joke ",joke);
                assert !TextUtils.isEmpty(joke);
            }
        });


    }
}
