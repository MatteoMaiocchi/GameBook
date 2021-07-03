package it.disco.unimib.GameBook.repository;

import android.app.Application;

import androidx.annotation.NonNull;


import java.util.List;

import it.disco.unimib.GameBook.models.Response;
import it.disco.unimib.GameBook.models.VideoGame;
import it.disco.unimib.GameBook.services.VideoGameService;
import it.disco.unimib.GameBook.utils.Constants;
import it.disco.unimib.GameBook.utils.ServiceLocator;
import retrofit2.Call;
import retrofit2.Callback;


/**
 * Repository to get news using NewsService and ArticleDao.
 */
public class VideoGameResponseCallback implements IVideoGameResponseCallback {

    private static final String TAG = "NewsRepository";

    private final VideoGameService videoGameService;
    private final ResponseCallback responseCallback;

    public VideoGameResponseCallback(ResponseCallback responseCallback, Application application) {
        this.videoGameService = ServiceLocator.getInstance().getNewsServiceWithRetrofit();
        this.responseCallback = responseCallback;
    }

    @Override
    public void fetchVideoGame(String searchGame) {


        Call<Response> call = videoGameService.getGames(searchGame, Constants.VIDEOGAME_API_KEY);

        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(@NonNull Call<Response> call, @NonNull retrofit2.Response<Response> response) {
                if (response.body() != null && response.isSuccessful()) {
                    List<VideoGame> videoGameList = response.body().getVideoGameList();
                    responseCallback.onResponse(videoGameList);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Response> call, @NonNull Throwable t) {
                responseCallback.onFailure(t.getMessage());
            }
        });

    }
}
