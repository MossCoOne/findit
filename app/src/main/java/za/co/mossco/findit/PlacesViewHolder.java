package za.co.mossco.findit;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import za.co.mossco.findit.PlacesAdapter.PlacesClickListener;
import za.co.mossco.findit.databinding.PlacesRowBinding;
import za.co.mossco.findit.model.Result;

public class PlacesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    ImageView placeIconImageView;
    TextView placeNameTextView, placeCategoryTextView, placeDistanceTextView;
    private PlacesClickListener placesClickListener;
    private Result currentResult;


    PlacesViewHolder(PlacesRowBinding placesRowBinding, PlacesClickListener placesClickListener) {
        super(placesRowBinding.getRoot());
        placeIconImageView = placesRowBinding.ivPlaceIcon;
        placeNameTextView = placesRowBinding.tvVenueName;
        placeCategoryTextView = placesRowBinding.tvVenueCategory;
        placeDistanceTextView = placesRowBinding.tvDistance;
        this.placesClickListener = placesClickListener;
        placesRowBinding.getRoot().setOnClickListener(this);
    }

    public void setCurrentPlace(Result currentResult) {
        this.currentResult = currentResult;
    }

    @Override
    public void onClick(View view) {
        placesClickListener.onPlaceClicked(currentResult);
    }
}
