package it.disco.unimib.GameBook.viewModel;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import it.disco.unimib.GameBook.models.Response;
import it.disco.unimib.GameBook.repository.IVideogameRepositoryWithLiveData;
import it.disco.unimib.GameBook.utils.Constants;

public class VideogameViewModel extends AndroidViewModel {

    private static final String TAG = "VideogameViewModel";
    private MutableLiveData<Response> mResponseLiveData;
    private IVideogameRepositoryWithLiveData mIVideogameRepositoryWithLiveData;
    private String game;
    private int count;


    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public VideogameViewModel(@NonNull Application application) {
        super(application);
    }


    public LiveData<Response> getResponseLiveData() {
        if (mResponseLiveData == null) {
            loadVideogames();
        }
        return mResponseLiveData;
    }

    private void loadVideogames() {
        mResponseLiveData = mIVideogameRepositoryWithLiveData.fetchVideogames(game);
    }

    public MutableLiveData<Response> getVideogameLiveData() {
        return mResponseLiveData;
    }

}
