package za.co.mossco.findit.model;

import java.util.List;

public interface PlacesRepository {
    void findNearByPlaces(String latitudeAndLongitude, String category, NearByPlacesCallback nearByPlacesCallback);

    interface NearByPlacesCallback {
        void onNearByPlacesLoaded(List<Result> resultList);

        void onErrorOccurred(String errorMessage);
    }
}
