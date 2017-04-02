package com.slp.joke.provider;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.NotFoundException;
import com.googlecode.objectify.ObjectifyService;
import com.slp.jokes.Jokes;

import java.util.logging.Logger;

import javax.inject.Named;

@Api(
        name = "jokeApi",
        version = "v1",
        resource = "joke",
        namespace = @ApiNamespace(
                ownerDomain = "provider.joke.slp.com",
                ownerName = "provider.joke.slp.com",
                packagePath = ""
        )
)
public class JokeEndpoint {

    private static final Logger logger = Logger.getLogger(JokeEndpoint.class.getName());


    static {
        ObjectifyService.register(Joke.class);
    }


    @ApiMethod(
            name = "getJoke",
            path = "joke",
            httpMethod = ApiMethod.HttpMethod.GET)
    public Joke getJoke(@Named("numberOfJokes") int numberOfJokes) throws NotFoundException {
        logger.info("Getting Joke with ID: ");
        Joke joke = new Joke();
        joke.setJoke(new Jokes(numberOfJokes).getJoke());
        return joke;
    }


}