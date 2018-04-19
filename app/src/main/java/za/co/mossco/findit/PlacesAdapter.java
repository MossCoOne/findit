package za.co.mossco.findit;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;

import za.co.mossco.findit.databinding.PlacesRowBinding;
import za.co.mossco.findit.model.Result;
import za.co.mossco.findit.utilities.StringUtils;

public class PlacesAdapter extends RecyclerView.Adapter<PlacesViewHolder> {
    private List<Result> resultList;
    private PlacesClickListener placesClickListener;

    public PlacesAdapter(List<Result> resultList, PlacesClickListener placesClickListener) {
        this.resultList = resultList;
        this.placesClickListener = placesClickListener;
    }

    @NonNull
    @Override
    public PlacesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        PlacesRowBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext())
                , R.layout.places_row, parent, false);
        return new PlacesViewHolder(binding, placesClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull PlacesViewHolder holder, int position) {
        final Result currentResult = resultList.get(position);
        if (!currentResult.getTypes().isEmpty()) {
            holder.placeCategoryTextView.setText(currentResult.getTypes().get(0));
        } else {
            holder.placeCategoryTextView.setText("Undefined");
        }
        holder.placeDistanceTextView.setText("Distance : 8 KM");
        holder.placeNameTextView.setText(currentResult.getName());
        if (!currentResult.getPhotos().isEmpty()) {
            String imageUrl = StringUtils.parseImageUrl(currentResult.getPhotos().get(0).getPhotoReference());
            Picasso.get()
                    .load(imageUrl)
                    .placeholder(R.drawable.ic_image_placeholder)
                    .error(R.drawable.ic_error)
                    .into(holder.placeIconImageView);
            Log.d("Adapter url", imageUrl);
        } else {
            Picasso.get()
                    .load("ddd")
                    .placeholder(R.drawable.ic_image_placeholder)
                    .error(R.drawable.ic_error)
                    .into(holder.placeIconImageView);
        }

        holder.setCurrentPlace(currentResult);
    }

    @Override
    public int getItemCount() {
        return resultList.size();
    }

    public interface PlacesClickListener {
        void onPlaceClicked(Result result);
    }

}
