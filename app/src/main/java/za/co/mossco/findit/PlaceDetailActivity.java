package za.co.mossco.findit;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import za.co.mossco.findit.databinding.ActivityPlaceDetailBinding;
import za.co.mossco.findit.model.Result;
import za.co.mossco.findit.places.PlacesActivity;
import za.co.mossco.findit.utilities.StringUtils;

public class PlaceDetailActivity extends AppCompatActivity {
    private static String results = "current_place";
    private static String place = "location";
    private String currentLocation;
    private String currentCategory;

    private Result selectedResults;
    private ActivityPlaceDetailBinding binding;

    public static Intent getInstance(Context context, Result result, String location) {
        Intent detailIntent = new Intent(context, PlaceDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(results, new Gson().toJson(result));
        bundle.putString(place, location);
        detailIntent.putExtras(bundle);
        return detailIntent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_place_detail);
        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Details");


        String resultsJsonString = getIntent().getStringExtra(results);
        currentLocation = getIntent().getStringExtra(place);
        initializeUI(resultsJsonString);
    }

    private void initializeUI(String resultsJsonString) {
        selectedResults = new Gson().fromJson(resultsJsonString, Result.class);
        binding.tvPlaceName.setText(selectedResults.getName());
        currentCategory = selectedResults.getTypes().get(0);
        binding.tvPlaceCategory.setText(currentCategory);
        binding.tvPlaceRating.setRating(selectedResults.getRating());
        if (selectedResults.getOpeningHours() != null) {
            binding.tvPlaceOpen.setText(isPlaceOpen(selectedResults.getOpeningHours().getOpenNow()));
        } else {
            binding.tvPlaceOpen.setText("Time not available");
        }

        loadPlaceImage();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(PlacesActivity.getInstance(getApplicationContext(), currentCategory, currentLocation));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(results, new Gson().toJson(selectedResults));
        super.onSaveInstanceState(outState);
    }

    private String isPlaceOpen(boolean isPlaceOpen) {
        return isPlaceOpen ? "Yes" : "No";
    }

    private void loadPlaceImage() {
        if (selectedResults.getPhotos() != null) {
            String imageUrl = StringUtils.parseImageUrl(selectedResults.getPhotos().get(0).getPhotoReference());
            Picasso.get()
                    .load(imageUrl)
                    .placeholder(R.drawable.ic_image_placeholder)
                    .error(R.drawable.ic_error)
                    .into(binding.imPlaceImage);
            Log.d("Image url", imageUrl);
        } else {
            Picasso.get()
                    .load(R.drawable.ic_error)
                    .placeholder(R.drawable.ic_image_placeholder)
                    .error(R.drawable.ic_error)
                    .into(binding.imPlaceImage);
        }
    }

}
