package it.disco.unimib.GameBook.repository;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;


import java.util.List;

import it.disco.unimib.GameBook.models.Response;
import it.disco.unimib.GameBook.models.VideoGame;
import it.disco.unimib.GameBook.services.Api;
import it.disco.unimib.GameBook.services.VideoGameService;
import it.disco.unimib.GameBook.utils.Constants;
import it.disco.unimib.GameBook.utils.ServiceLocator;
import retrofit2.Call;
import retrofit2.Callback;


public class VideoGameResponseCallback implements IVideoGameResponseCallback {

    private static final String TAG = "VideoGameRepository";

    private final VideoGameService videoGameService;
    private final ResponseCallback responseCallback;

    public VideoGameResponseCallback(ResponseCallback responseCallback) {
        this.videoGameService = ServiceLocator.getInstance().getNewsServiceWithRetrofit();
        //this.videoGameService = Api.getApi();
        this.responseCallback = responseCallback;
    }

    @Override
    public void fetchVideoGame(String stringa) {


        Call<Response> call = videoGameService.getGames(stringa,Constants.VIDEOGAME_API_KEY);

        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(@NonNull Call<Response> call, @NonNull retrofit2.Response<Response> response) {
                Log.d("pirla", " successo " + response.code());
                if (response.body() != null && response.isSuccessful()) {

                    List<VideoGame> videoGameList = response.body().getVideoGameList();
                    responseCallback.onResponse(videoGameList);
                }

                if (response.code() == 401){
                    // Magic is here ( Handle the error as your way )
                    Log.d("bella", "responde: " + response);
                }


            }

            @Override
            public void onFailure(@NonNull Call<Response> call, @NonNull Throwable t) {
                Log.d("pirla", " uè ");
                responseCallback.onFailure(t.getMessage());
            }
        });

    }
}
