package za.co.mossco.findit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class PlacesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places);
        Spinner spinner = findViewById(R.id.categories_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.category_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

//        PlacesServiceApi weatherServiceApi = PlacesServiceApiClient.getInstance();
//
//        weatherServiceApi.findNearByPlaces( "Halfway House ZA", 5000,Constants.API_KEY, Constants.APP_SECRET_KEY)
//                .enqueue(new Callback<NearByPlacesResponse>() {
//                    @Override
//                    public void onResponse(Call<NearByPlacesResponse> call, Response<NearByPlacesResponse> response) {
//                        if (response.isSuccessful()){
//                            Log.d("response",response.body().getResponse().getVenues().get(0).toString());
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<NearByPlacesResponse> call, Throwable t) {
//                    Log.d("Error", t.getMessage());
//                    }
//                });
    }
}
