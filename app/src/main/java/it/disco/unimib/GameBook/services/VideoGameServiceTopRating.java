package it.disco.unimib.GameBook.services;

import it.disco.unimib.GameBook.models.Response;
import it.disco.unimib.GameBook.models.ResponseTopRating;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface VideoGameServiceTopRating {
    @GET("games")
    Call<ResponseTopRating> getGamesTopRating(
            @Query("ordering") String stringa,
            @Query("key") String apiKey,
            @Query("page_size") int page);
}
