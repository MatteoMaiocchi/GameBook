package it.disco.unimib.GameBook.viewModel;


import android.app.Application;
import android.os.IInterface;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import it.disco.unimib.GameBook.repository.IVideogameRepositoryWithLiveData;

public class VideoGameViewModelFactory implements ViewModelProvider.Factory {

    private final IVideogameRepositoryWithLiveData iVideogameRepositoryWithLiveData;
    private final String stringa;
    private final Application application;

    @NonNull
    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new VideogameViewModel(application, iVideogameRepositoryWithLiveData, stringa);
    }

    public VideoGameViewModelFactory(Application application, IVideogameRepositoryWithLiveData iVideogameRepositoryWithLiveData, String stringa) {
        this.application = application;
        this.iVideogameRepositoryWithLiveData = iVideogameRepositoryWithLiveData;
        this.stringa = stringa;
    }
}
