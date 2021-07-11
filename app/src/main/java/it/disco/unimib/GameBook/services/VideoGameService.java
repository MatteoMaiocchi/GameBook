package it.disco.unimib.GameBook.services;

import it.disco.unimib.GameBook.models.Response;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface VideoGameService {
    @GET("games")
    Call<Response> getGames(
                            @Query("search") String stringa,
                            @Query("key") String apiKey);
}
