package it.disco.unimib.GameBook.services;

import it.disco.unimib.GameBook.models.Response;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface VideoGameService {
    @GET("top-headlines")
    Call<Response> getTopHeadlines(@Header("Authorization") String apiKey);
}
