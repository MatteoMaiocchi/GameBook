package it.disco.unimib.GameBook.repository;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import it.disco.unimib.GameBook.models.Response;
import it.disco.unimib.GameBook.models.VideoGame;
import it.disco.unimib.GameBook.services.VideoGameService;
import it.disco.unimib.GameBook.utils.Constants;
import it.disco.unimib.GameBook.utils.ServiceLocator;
import retrofit2.Call;
import retrofit2.Callback;

public class VideogameRepositoryWithLiveData implements IVideogameRepositoryWithLiveData{

    private static final String TAG = "VideogameViewModel";
    private final VideoGameService videoGameService;

    private final Application application;
    private final MutableLiveData<Response> mResponseLiveData;


    public VideogameRepositoryWithLiveData(Application application) {
        this.application = application;
        this.videoGameService = ServiceLocator.getInstance().getNewsServiceWithRetrofit();
        this.mResponseLiveData = new MutableLiveData<>();

    }

    @Override
    public MutableLiveData<Response> fetchVideogames(String stringa) {
        Call<Response> call = videoGameService.getGames(stringa,Constants.VIDEOGAME_API_KEY);

        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(@NonNull Call<Response> call, @NonNull retrofit2.Response<Response> response) {
                if (response.body() != null && response.isSuccessful()) {
                    Log.d("non sono ", "null");
                    List<VideoGame> videoGameList = response.body().getVideoGameList();
                    mResponseLiveData.postValue(response.body());
                }
                Log.d("code: ", ""+response.code());
            }

            @Override
            public void onFailure(@NonNull Call<Response> call, @NonNull Throwable t) {
                mResponseLiveData.postValue(new Response(-1, null)); //in caso di errore, count a -1
                Log.d("code: ", "");
            }
        });
        return mResponseLiveData;
    }

}


