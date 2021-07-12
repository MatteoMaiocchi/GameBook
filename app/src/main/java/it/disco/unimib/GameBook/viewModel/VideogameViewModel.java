package it.disco.unimib.GameBook.viewModel;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import it.disco.unimib.GameBook.models.Response;
import it.disco.unimib.GameBook.repository.IVideogameRepositoryWithLiveData;
import it.disco.unimib.GameBook.utils.Constants;

public class VideogameViewModel extends AndroidViewModel {

    private static final String TAG = "VideogameViewModel";
    private MutableLiveData<Response> mResponseLiveData;
    private IVideogameRepositoryWithLiveData mIVideogameRepositoryWithLiveData;
    private String stringa;
    private int page;
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

    public VideogameViewModel(@NonNull Application application, IVideogameRepositoryWithLiveData iVideogameRepositoryWithLiveData, String stringa) {
        super(application);
        this.mIVideogameRepositoryWithLiveData = iVideogameRepositoryWithLiveData;
        this.stringa = stringa;
        Log.d("guarda1: ", stringa);
    }


    public LiveData<Response> getResponseLiveData() {
        Log.d("guarda2: ", stringa);

        if (mResponseLiveData == null) {
            Log.d("Response: ", "LiveData");
            loadVideogames();
        }
        return mResponseLiveData;


        //return  mResponseLiveData = mIVideogameRepositoryWithLiveData.fetchVideogames(this.stringa);
    }

    private void loadVideogames() {
        mResponseLiveData = mIVideogameRepositoryWithLiveData.fetchVideogames(stringa);
    }

    public MutableLiveData<Response> getVideogameLiveData() {
        return mResponseLiveData;
    }

    public void getMoreVideoGame(String stringa) {
        mIVideogameRepositoryWithLiveData.fetchVideogames(stringa);
    }


}
