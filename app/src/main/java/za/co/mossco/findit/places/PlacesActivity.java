package za.co.mossco.findit.places;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import za.co.mossco.findit.PlacesAdapter;
import za.co.mossco.findit.R;
import za.co.mossco.findit.databinding.ActivityPlacesBinding;
import za.co.mossco.findit.model.Result;

public class PlacesActivity extends AppCompatActivity implements NearByPlacesContract.View, View.OnClickListener {
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 111;
    private final String TAG = PlacesActivity.class.getCanonicalName();
    PlacesAdapter.PlacesClickListener placesClickListener = new PlacesAdapter.PlacesClickListener() {
        @Override
        public void onPlaceClicked(Result result) {
            //open detailed activity
        }
    };
    private FusedLocationProviderClient fusedLocationClient;
    private String currentLocation;
    private ActivityPlacesBinding binding;
    private RecyclerView nearByPlacesRecyclerView;
    private NearbyPlacesPresenter nearbyPlacesPresenter;
    private String longiAndLati;
    private String category;
    private ProgressDialog nearByPlacesProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_places);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        final Spinner spinner = findViewById(R.id.categories_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.category_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        category = spinner.getSelectedItem().toString();
        nearByPlacesRecyclerView = findViewById(R.id.rv_nearby_places);
        nearbyPlacesPresenter = new NearbyPlacesPresenter(this);
        binding.btnFindNearbyLocation.setOnClickListener(this);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category = spinner.getSelectedItem().toString().toLowerCase();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setUpRecyclerView(List<Result> resultList) {

        PlacesAdapter placesAdapter = new PlacesAdapter(resultList, placesClickListener);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);

        nearByPlacesRecyclerView.setLayoutManager(gridLayoutManager);
        nearByPlacesRecyclerView.setItemAnimator(new DefaultItemAnimator());
        nearByPlacesRecyclerView.setAdapter(placesAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!isFineLocationPermissionsGranted()) {
            requestPermissions();
        } else {
            getLastLocation();
        }
    }

    private boolean isFineLocationPermissionsGranted() {
        int permissionState = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        boolean shouldProvideRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_FINE_LOCATION);

        if (shouldProvideRationale) {
            startLocationPermissionRequest();
        } else {
            startLocationPermissionRequest();
        }
    }

    private void startLocationPermissionRequest() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                REQUEST_PERMISSIONS_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSIONS_REQUEST_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    requestPermissions();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


    @SuppressLint("MissingPermission")
    private void getLastLocation() {
        fusedLocationClient.getLastLocation()
                .addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful() && task.getResult() != null) {
                            Location location = task.getResult();
                            currentLocation = getCurrentPlaceName(location);
                            binding.tvCurrentLocationLabel.setText(currentLocation);
                            longiAndLati = Double.toString(location.getLatitude()).concat(",").concat(Double.toString(location.getLongitude()));
                            //placesPresenter.loadNearbyPlaces(location.getLatitude(), location.getLongitude());
                        } else {
                            Log.i(TAG, "Error while getting location");
                            System.out.println(TAG + task.getException());
                        }
                    }
                });

    }

    private String getCurrentPlaceName(Location location) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses;
        String city = null;
        try {
            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            city = addresses.get(0).getLocality();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return city;
    }

    @Override
    public void displayNearByPlaces(List<Result> resultList) {
        setUpRecyclerView(resultList);
    }

    @Override
    public void showProgressDialog() {
        nearByPlacesProgressDialog = new ProgressDialog(this);
        nearByPlacesProgressDialog.setTitle(getString(R.string.places_loading));
        nearByPlacesProgressDialog.setMessage(getString(R.string.please_wait_message));
        nearByPlacesProgressDialog.setIndeterminate(true);
        nearByPlacesProgressDialog.show();
    }

    @Override
    public void dismissProgressDialog() {
        nearByPlacesProgressDialog.dismiss();
    }

    @Override
    public void showFailedToLoadPlacesErrorMessage() {

    }

    @Override
    public void showErrorOccurredMessage(String errorMessage) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_find_nearby_location:
                nearbyPlacesPresenter.loadNearByPlaces(longiAndLati, category);
                break;
        }
    }
}
