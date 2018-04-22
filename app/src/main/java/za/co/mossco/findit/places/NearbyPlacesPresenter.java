package za.co.mossco.findit.places;

import java.util.List;

import za.co.mossco.findit.model.PlacesRepository;
import za.co.mossco.findit.model.PlacesRepositoryImpl;
import za.co.mossco.findit.model.Result;

public class NearbyPlacesPresenter implements NearByPlacesContract.UserActionsListener {
    private PlacesRepository placesRepository;
    private NearByPlacesContract.View view;

    public NearbyPlacesPresenter(NearByPlacesContract.View view) {
        placesRepository = new PlacesRepositoryImpl();
        this.view = view;
    }


    @Override
    public void loadNearByPlaces(String latiAndLongi, String category) {
        view.showProgressDialog();
        placesRepository.findNearByPlaces(latiAndLongi, category, new PlacesRepository.NearByPlacesCallback() {
            @Override
            public void onNearByPlacesLoaded(List<Result> resultList) {
                view.displayNearByPlaces(resultList);
                view.dismissProgressDialog();
            }

            @Override
            public void onErrorOccurred(String errorMessage) {
                view.showErrorOccurredMessage(errorMessage);
            }
        });
    }
}
