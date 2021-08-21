package it.disco.unimib.GameBook.viewModel;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import it.disco.unimib.GameBook.repository.IVideogameTopRatingRepositoryWithLiveData;

public class VideoGameTopRatingViewModelFactory implements ViewModelProvider.Factory {

    private final IVideogameTopRatingRepositoryWithLiveData iVideogameTopRatingRepositoryWithLiveData;
    private final String stringa;
    private final Application application;

    @NonNull
    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new VideogameTopRatingViewModel(application, iVideogameTopRatingRepositoryWithLiveData, stringa);
    }

    public VideoGameTopRatingViewModelFactory(Application application, IVideogameTopRatingRepositoryWithLiveData iVideogameTopRatingRepositoryWithLiveData, String stringa) {
        this.application = application;
        this.iVideogameTopRatingRepositoryWithLiveData = iVideogameTopRatingRepositoryWithLiveData;
        this.stringa = stringa;
    }
}
