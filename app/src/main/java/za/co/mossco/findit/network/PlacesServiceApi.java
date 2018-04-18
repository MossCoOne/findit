package za.co.mossco.findit.network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import za.co.mossco.findit.bean.NearByPlacesResponse;

public interface PlacesServiceApi {
    @GET("venues/search?v=20171101&limit=10")
    Call<NearByPlacesResponse> findNearByPlaces(
            @Query("near") String near,
            @Query("radius") double radius,
            @Query("client_id") String clientId,
            @Query("client_secret") String clientSecret);
}
