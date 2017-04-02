package com.slp.displayjoke;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.CardView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;


public class JokeActivity extends AppCompatActivity {

    public static final String JOKE = "joke";
    private static final List<String> imageList = new ArrayList<>(Arrays.asList("one", "two", "three", "four", "five","six","seven","eight","nine","ten","eleven","twelve","thirteen","fourteen"));
    private CardView funnyImageCard;
    private CardView jokeCard;
    private TextView joke_tv;
    private ImageView funnyImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke);
        initializeView();
        String jokeString = getIntent().getStringExtra(JOKE);
        joke_tv.setText(jokeString);
        setCardViews();
    }

    private void setCardViews() {
        Palette.PaletteAsyncListener paletteListener = new Palette.PaletteAsyncListener() {
            public void onGenerated(Palette palette) {
                int defaultColor = 0x000000;
                int darkMutedColor = palette.getDarkMutedColor(defaultColor);
                int lightMutedColor = palette.getLightMutedColor(defaultColor);
                int darkVibrantColor = palette.getDarkVibrantColor(defaultColor);

                Palette.Swatch vibrant = palette.getVibrantSwatch();
                funnyImageCard.setCardBackgroundColor(darkMutedColor);

                if(vibrant != null){
                    jokeCard.setCardBackgroundColor(vibrant.getRgb());
                    joke_tv.setTextColor(vibrant.getTitleTextColor());
                }else{
                    jokeCard.setCardBackgroundColor(lightMutedColor);
                    joke_tv.setTextColor(darkMutedColor);
                }

            }
        };


        int imageId = getResources().getIdentifier(getImageName(), "drawable", getPackageName());
        Picasso.with(this).load(imageId).into(funnyImage);
        Bitmap myBitmap = BitmapFactory.decodeResource(getResources(), imageId);
        if (myBitmap != null && !myBitmap.isRecycled()) {
            Palette.from(myBitmap).generate(paletteListener);
        }
    }

    private String getImageName() {
        Random random = new Random();
        int imageId = random.nextInt(imageList.size());
        return imageList.get(imageId);
    }

    private void initializeView() {
        funnyImageCard = (CardView) findViewById(R.id.funny_image_card);
        jokeCard = (CardView) findViewById(R.id.joke_card);
        joke_tv = (TextView) findViewById(R.id.joke);
        funnyImage = (ImageView) findViewById(R.id.funny_image);
    }
}
