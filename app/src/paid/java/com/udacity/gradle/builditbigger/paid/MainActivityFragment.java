package com.udacity.gradle.builditbigger.paid;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.udacity.gradle.builditbigger.R;
import com.udacity.gradle.builditbigger.network.JokeAsyncTask;
import com.udacity.gradle.builditbigger.network.NetworkUtils;

public class MainActivityFragment extends Fragment {


    private ProgressBar progressBar;
    private Button tellJoke;
    private View root;

    public MainActivityFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_main_activity, container, false);
        progressBar = (ProgressBar) root.findViewById(R.id.progressBar);
        tellJoke = (Button) root.findViewById(R.id.tell_joke);
        setButtonOnClick();
        return root;
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    private void setButtonOnClick() {
        tellJoke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NetworkUtils.isNetworkAvailable(root.getContext())) {
                    showJoke();
                } else {
                    Toast.makeText(root.getContext(), R.string.no_network, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void showJoke() {
        new JokeAsyncTask(root.getContext(), progressBar, tellJoke).execute(20);
    }

}
