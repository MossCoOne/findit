package za.co.mossco.findit.network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import za.co.mossco.findit.model.PlacesResponse;

public interface PlacesServiceApi {
    @GET("nearbysearch/json?")
    Call<PlacesResponse> findPlacesAround(
            @Query("location") String longitudeAndLatitude,
            @Query("radius") double radius,
            @Query("type") String category,
            @Query("key") String apiKey);
}
