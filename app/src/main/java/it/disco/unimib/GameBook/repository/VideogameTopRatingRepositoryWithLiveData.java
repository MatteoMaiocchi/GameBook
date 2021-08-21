package it.disco.unimib.GameBook.repository;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import it.disco.unimib.GameBook.models.ResponseTopRating;
import it.disco.unimib.GameBook.services.VideoGameService;
import it.disco.unimib.GameBook.services.VideoGameServiceTopRating;
import it.disco.unimib.GameBook.utils.Constants;
import it.disco.unimib.GameBook.utils.ServiceLocator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VideogameTopRatingRepositoryWithLiveData implements IVideogameTopRatingRepositoryWithLiveData{

    private static final String TAG = "VideogameTopRatingViewModel";
    private final VideoGameServiceTopRating videoGameService;

    private final Application application;
    private final MutableLiveData<ResponseTopRating> mResponseLiveData;


    public VideogameTopRatingRepositoryWithLiveData(Application application) {
        this.application = application;
        this.videoGameService = ServiceLocator.getInstance().getGamesTopRatingServiceWithRetrofit();
        this.mResponseLiveData = new MutableLiveData<>();

    }

    @Override
    public MutableLiveData<ResponseTopRating> fetchVideogames(String stringa) {
        Call<ResponseTopRating> call = videoGameService.getGamesTopRating("-rating", Constants.VIDEOGAME_API_KEY, 10);
            call.enqueue(new Callback<ResponseTopRating>() {
                @Override
                public void onResponse(Call<ResponseTopRating> call, Response<ResponseTopRating> response) {
                    if (response.body() != null && response.isSuccessful()) {
                        mResponseLiveData.postValue(response.body());
                    }
                }

                @Override
                public void onFailure(Call<ResponseTopRating> call, Throwable t) {
                    mResponseLiveData.postValue(new it.disco.unimib.GameBook.models.ResponseTopRating(-1, null)); //in caso di errore, count a -1
                }
            });
            return mResponseLiveData;



    }

}


