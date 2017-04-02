package com.slp.jokes;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.Random;

public class Jokes {

    private Properties jokes;
    private int numberOfJokes;

    private static final String JOKES_PROPERTIES = "jokes.properties";
    private static final int TOTAL_NUMBER_OF_JOKES = 20;
    private static final int DEFAULT_JOKE = 1;

    public Jokes() {
        this.numberOfJokes = TOTAL_NUMBER_OF_JOKES;
        initializeJokes();
    }

    public Jokes(int numberOfJokes) {
        if (numberOfJokes < TOTAL_NUMBER_OF_JOKES) {
            this.numberOfJokes = numberOfJokes;
        } else {
            this.numberOfJokes = TOTAL_NUMBER_OF_JOKES;
        }
        initializeJokes();
    }

    private void initializeJokes() {
        try {
            jokes = new Properties();
            jokes.load(getClass().getClassLoader().getResourceAsStream(JOKES_PROPERTIES));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public String getJoke() {
        Random random = new Random();
        int rand = random.nextInt(numberOfJokes) + 1;
        String joke = jokes.getProperty(String.valueOf(rand));
        if (null == joke) {
            joke = jokes.getProperty(String.valueOf(DEFAULT_JOKE));
        }
        return joke;
    }


}