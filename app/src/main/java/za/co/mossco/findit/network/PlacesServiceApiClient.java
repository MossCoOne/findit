package za.co.mossco.findit.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import za.co.mossco.findit.utilities.Constants;

public class PlacesServiceApiClient {

    private static PlacesServiceApi placesServiceApi;

    private PlacesServiceApiClient() {

    }

    public static PlacesServiceApi getInstance() {
        Retrofit retrofit;
        if (placesServiceApi == null) {
            Gson gson = new GsonBuilder()
                    .create();
            retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.GOOGLE_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
            placesServiceApi = retrofit.create(PlacesServiceApi.class);
        }
        return placesServiceApi;
    }
}
