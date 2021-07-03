package it.disco.unimib.GameBook.utils;

import android.app.Application;


import it.disco.unimib.GameBook.services.VideoGameService;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceLocator {

    private static ServiceLocator instance = null;

    private ServiceLocator() {}

    public static ServiceLocator getInstance() {
        if (instance == null) {
            synchronized(ServiceLocator.class) {
                instance = new ServiceLocator();
            }
        }
        return instance;
    }

    public VideoGameService getNewsServiceWithRetrofit() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.VIDEOGAME_API_BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        return retrofit.create(VideoGameService.class);
    }


}

