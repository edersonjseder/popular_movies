package movies.training.udacity.com.popularmovies.utils;

import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by root on 08/10/16.
 */

public class ConnectionPathUtils {

    private static final String TAG = ConnectionPathUtils.class.getSimpleName();

    static final String URL_TOP_RATED = "https://api.themoviedb.org/3/movie/";
    static final String URL_PATH = "https://api.themoviedb.org/3/discover/movie";
    static final String KEY = "";

    static final String IMG_URL  = "http://image.tmdb.org/t/p/w500/";
    static final String SORT_BY  = "sort_by";

    static final String CERTIFICATION_COUNTRY  = "certification_country";
    static final String LANGUAGE  = "language";

    final static String PARAM_RELEASE_YEAR = "primary_release_year";
    final static String DUMMY_KEY = "api_key";

    /* The number of release year we want our API to return */
    private static final int year = 2018;

    private static final String country = "en_US";
    private static final String rate = "R";

    /**
     * Builds the URL used to talk to the movie server.
     *
     * @return The URL to use to query the movie server.
     */
    public static URL buildUrl() {

        Uri builtUri = Uri.parse(URL_PATH).buildUpon()
                .appendQueryParameter(PARAM_RELEASE_YEAR, Integer.toString(year))
                .appendQueryParameter(DUMMY_KEY, KEY)
                .build();

        URL url = null;

        try {

            url = new URL(builtUri.toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Built URI " + url);

        return url;
    }

    /**
     * This method builds the URL for the most popular movies
     *
     * @param popular
     * @return
     */
    public static URL buildMostPopularUrl(String[] popular) {
        Log.v(TAG, "buildMostPopularUrl() Inside method: " + popular[0]);

        Uri builtUri = Uri.parse(URL_PATH).buildUpon()
                .appendQueryParameter(SORT_BY, popular[0])
                .appendQueryParameter(DUMMY_KEY, KEY)
                .build();

        URL url = null;

        try {

            url = new URL(builtUri.toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Built URI " + url);

        return url;
    }

    /**
     * This method builds the URL for the top rated movies
     *
     * @param highestRated
     * @return
     */
    public static URL buildHighestRatedUrl(String[] highestRated) {
        Log.v(TAG, "buildHighestRatedUrl() Inside method: " + highestRated[0]);

        Uri builtUri = Uri.parse(URL_TOP_RATED).buildUpon()
                .appendEncodedPath(highestRated[0])
                .appendQueryParameter(DUMMY_KEY, KEY)
                .appendQueryParameter(LANGUAGE, country)
                .build();

        URL url = null;

        try {

            url = new URL(builtUri.toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Built URI " + url);

        return url;
    }

    /**
     * This method builds the URL to retrieve the images to be put on the screen
     *
     * @param imageUrl
     * @return
     */
    public static Uri buildImageUrl(String imageUrl) {

        Uri builtUri = Uri.parse(IMG_URL).buildUpon()
                .appendEncodedPath(imageUrl)
                .build();

        Log.v(TAG, "Built Image URI " + builtUri.toString());

        return builtUri;
    }

    /**
     * This method does the query on the API to retrieve the JSON result
     *
     * @param generatedUrl The given URL built
     * @return The Json result got from the API Url
     * @throws IOException
     */
    public static String doQuery(URL generatedUrl) throws IOException {
        HttpURLConnection connection = null;
        BufferedReader bufferedReader = null;
        String result = "";

        try{

            // Gets the URL with the param cityName, and with it concatenated on the url gets the JSON weather info
            URL url = generatedUrl;

            // Open the connection with the URL here
            connection = (HttpURLConnection) url.openConnection();

            // Check if the open connection is returned
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK){

                // Gets the JSON info
                InputStreamReader inputStreamReader = new InputStreamReader(connection.getInputStream());
                bufferedReader = new BufferedReader(inputStreamReader, 8192);
                String line = null;

                // Read the info here
                while ((line = bufferedReader.readLine()) != null){
                    result += line;
                }
            }

        } finally {
            Log.i(TAG, "doQuery() inside finally block");
            if (connection != null){
                bufferedReader.close();
                connection.disconnect();
            }
        }

        Log.i(TAG, "Result: (" + result + ")");

        return result;
    }

}
