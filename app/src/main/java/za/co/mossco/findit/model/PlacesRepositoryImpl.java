package za.co.mossco.findit.model;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import za.co.mossco.findit.network.PlacesServiceApi;
import za.co.mossco.findit.network.PlacesServiceApiClient;
import za.co.mossco.findit.utilities.Constants;

public class PlacesRepositoryImpl implements PlacesRepository {
    private PlacesServiceApi placesServiceApi;

    public PlacesRepositoryImpl() {
        placesServiceApi = PlacesServiceApiClient.getInstance();
    }

    @Override
    public void findNearByPlaces(String latitudeAndLongitude, String category, final NearByPlacesCallback nearByPlacesCallback) {
        placesServiceApi.findPlacesAround(latitudeAndLongitude, Constants.RADIUS, category, Constants.GOOGLE_API_KEY)
                .enqueue(new Callback<PlacesResponse>() {
                    @Override
                    public void onResponse(Call<PlacesResponse> call, Response<PlacesResponse> response) {
                        if (response.isSuccessful()) {
                            nearByPlacesCallback.onNearByPlacesLoaded(response.body().getResults());
                            nearByPlacesCallback.onErrorOccurred(response.body().getStatus());
                        }
                    }

                    @Override
                    public void onFailure(Call<PlacesResponse> call, Throwable t) {
                        nearByPlacesCallback.onErrorOccurred(t.getMessage());
                    }
                });
    }
}
