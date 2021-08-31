package it.disco.unimib.GameBook.viewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import it.disco.unimib.GameBook.models.Response;
import it.disco.unimib.GameBook.models.ResponseTopRating;
import it.disco.unimib.GameBook.repository.IVideogameRepositoryWithLiveData;
import it.disco.unimib.GameBook.repository.IVideogameTopRatingRepositoryWithLiveData;

public class VideogameTopRatingViewModel extends AndroidViewModel {

    private static final String TAG = "VideogameTopRatingViewModel";
    private MutableLiveData<ResponseTopRating> mResponseLiveData;
    private IVideogameTopRatingRepositoryWithLiveData mIVideogameTopRatingRepositoryWithLiveData;
    private String stringa;
    private int page;
    private int count;


    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public VideogameTopRatingViewModel(@NonNull Application application) {
        super(application);
    }

    public VideogameTopRatingViewModel(@NonNull Application application, IVideogameTopRatingRepositoryWithLiveData iVideogameTopRatingRepositoryWithLiveData, String stringa) {
        super(application);
        this.mIVideogameTopRatingRepositoryWithLiveData = iVideogameTopRatingRepositoryWithLiveData;
        this.stringa = stringa;
    }


    public LiveData<ResponseTopRating> getResponseLiveData() {

        if (mResponseLiveData == null) {
            loadVideogames();
        }
        return mResponseLiveData;
    }

    private void loadVideogames() {
        mResponseLiveData = mIVideogameTopRatingRepositoryWithLiveData.fetchVideogames(stringa);
    }

    public MutableLiveData<ResponseTopRating> getVideogameLiveData() {
        return mResponseLiveData;
    }

    public void getMoreVideoGame(String stringa) {
        mIVideogameTopRatingRepositoryWithLiveData.fetchVideogames(stringa);
    }


}
