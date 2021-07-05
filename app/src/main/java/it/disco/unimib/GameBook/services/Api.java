package it.disco.unimib.GameBook.services;

import it.disco.unimib.GameBook.utils.Constants;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Api {
    public static VideoGameService videoGameService;

    public static VideoGameService getApi(){
        if (videoGameService == null) {
            Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.VIDEOGAME_API_BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
            videoGameService = retrofit.create(VideoGameService.class);
            return videoGameService;
        } else {
            return videoGameService;
        }
    }


}
