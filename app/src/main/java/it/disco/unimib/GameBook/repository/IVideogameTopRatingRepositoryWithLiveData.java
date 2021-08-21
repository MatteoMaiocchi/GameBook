package it.disco.unimib.GameBook.repository;

import androidx.lifecycle.MutableLiveData;

import it.disco.unimib.GameBook.models.Response;
import it.disco.unimib.GameBook.models.ResponseTopRating;


public interface IVideogameTopRatingRepositoryWithLiveData {
    MutableLiveData<ResponseTopRating> fetchVideogames(String stringa);
}
