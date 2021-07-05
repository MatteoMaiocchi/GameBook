package it.disco.unimib.GameBook.repository;

import androidx.lifecycle.MutableLiveData;

import it.disco.unimib.GameBook.models.Response;

import androidx.lifecycle.MutableLiveData;


public interface IVideogameRepositoryWithLiveData {
    MutableLiveData<Response> fetchVideogames( int page);
}
