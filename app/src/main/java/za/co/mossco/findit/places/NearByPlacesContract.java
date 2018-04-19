package za.co.mossco.findit.places;

import java.util.List;

import za.co.mossco.findit.model.Result;

public interface NearByPlacesContract {
    interface View {
        void displayNearByPlaces(List<Result> resultList);

        void showProgressDialog();

        void dismissProgressDialog();

        void showFailedToLoadPlacesErrorMessage();

        void showErrorOccurredMessage(String errorMessage);
    }

    interface UserActionsListener {
        void loadNearByPlaces(String latiAndLongi, String category);
    }
}
