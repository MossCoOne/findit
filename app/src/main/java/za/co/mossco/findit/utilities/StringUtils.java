package za.co.mossco.findit.utilities;

public class StringUtils {
    public static String parseImageUrl(String photoReference) {
        String baseUrl = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400";
        return baseUrl.concat("&photoreference=").concat(photoReference).concat("&key=").concat(Constants.GOOGLE_API_KEY);
    }
}
