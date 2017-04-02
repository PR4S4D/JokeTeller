package com.udacity.gradle.builditbigger.free;



        import android.os.Bundle;
        import android.support.annotation.NonNull;
        import android.support.v4.app.Fragment;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Button;
        import android.widget.ProgressBar;
        import android.widget.Toast;

        import com.google.android.gms.ads.AdListener;
        import com.google.android.gms.ads.AdRequest;
        import com.google.android.gms.ads.AdView;
        import com.google.android.gms.ads.InterstitialAd;
        import com.udacity.gradle.builditbigger.R;
        import com.udacity.gradle.builditbigger.network.JokeAsyncTask;
        import com.udacity.gradle.builditbigger.network.NetworkUtils;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private static final String INTERSTITIAL_AD_UNIT_ID ="ca-app-pub-3940256099942544/1033173712";

    private ProgressBar progressBar;
    private Button tellJoke;
    private View root;
    AdView mAdView;
    InterstitialAd interstitialAd;

    public MainActivityFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_main_activity, container, false);
         mAdView = (AdView) root.findViewById(R.id.adView);
        progressBar = (ProgressBar) root.findViewById(R.id.progressBar);
        tellJoke = (Button) root.findViewById(R.id.tell_joke);
        initializeInterstitialAd();
        setButtonOnClick();
        showAd(mAdView);
        return root;
    }

    private void initializeInterstitialAd() {
        interstitialAd = new InterstitialAd(root.getContext());
        AdRequest adRequest = getAdRequest();
        interstitialAd.setAdUnitId(INTERSTITIAL_AD_UNIT_ID);
        interstitialAd.loadAd(adRequest);
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
                showJoke();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
      interstitialAd.loadAd(getAdRequest());
    }

    private void setButtonOnClick() {
        tellJoke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(NetworkUtils.isNetworkAvailable(root.getContext())){
                    if (interstitialAd.isLoaded()) {
                        interstitialAd.show();
                    } else {
                        showJoke();
                    }
                }else{
                    Toast.makeText(root.getContext(), R.string.no_network,Toast.LENGTH_SHORT).show();

                }


            }
        });
    }

    private void showAd(AdView mAdView) {
            AdRequest adRequest = getAdRequest();
            mAdView.loadAd(adRequest);
        }

        @NonNull
        private AdRequest getAdRequest() {
            return new AdRequest.Builder()
                    .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                    .build();
        }

    private void showJoke() {
        new JokeAsyncTask(root.getContext(), progressBar, tellJoke).execute(20);
    }


}
