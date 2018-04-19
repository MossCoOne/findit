package za.co.mossco.findit.utilities;

public class StringUtils {
    public static String parseImageUrl(String photoReference) {
        String baseUrl = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400";
//        String apiKey = "AIzaSyAIrZ3UtWmIXLMvlbmkAdCFConQBnGanM4";
//        "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference=CnRtAAAATLZNl354RwP_9UKbQ_5Psy40texXePv4oAlgP4qNEkdIrkyse7rPXYGd9D_Uj1rVsQdWT4oRz4QrYAJNpFX7rzqqMlZw2h2E2y5IKMUZ7ouD_SlcHxYq1yL4KbKUv3qtWgTK0A6QbGh87GB3sscrHRIQiG2RrmU_jF4tENr9wGS_YxoUSSDrYjWmrNfeEHSGSc3FyhNLlBU&key=AIzaSyAIrZ3UtWmIXLMvlbmkAdCFConQBnGanM4"
        return baseUrl.concat("&photoreference=").concat(photoReference).concat("&key=").concat(Constants.GOOGLE_API_KEY);
    }
}
