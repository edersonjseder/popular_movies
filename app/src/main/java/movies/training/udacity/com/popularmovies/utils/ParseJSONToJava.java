package movies.training.udacity.com.popularmovies.utils;

import android.graphics.Bitmap;
import android.util.Log;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import movies.training.udacity.com.popularmovies.model.MovieListReviews;
import movies.training.udacity.com.popularmovies.model.MovieListTrailer;
import movies.training.udacity.com.popularmovies.model.MoviesInfo;

/**
 * Created by ederson.js on 14/07/2018.
 */

public class ParseJSONToJava {
    private static final String TAG = ParseJSONToJava.class.getSimpleName();

    private ObjectMapper mapper;
    private ConnectionPathUtils queryRequest;

    /**
     *
     * This method parses from JSON format to Java Objects with the Jackson API
     *
     * @param json
     * @return
     * @Original_Base WerbSystique - http://websystique.com/java/json/jackson-convert-java-object-to-from-json/
     */
    public MoviesInfo convertJsonToMoviesInfoJavaClass(String json){
        Log.i(TAG, "ParseJSONToJava.convertJsonToMoviesInfoJavaClass() inside method - param value: " + json);
        mapper = new ObjectMapper();
        MoviesInfo moviesObject = null;

        try {
            Log.i(TAG, "ParseJSONToJava.convertJsonToMoviesInfoJavaClass() inside try/catch block - param value: " + json);

            moviesObject = mapper.readValue(json, MoviesInfo.class);

/**         for (int i = 0; i < weatherObject.getList().size(); i++){
                Log.i(TAG, "ParseJSONToJava.convertJsonToMoviesInfoJavaClass() inside for loop - object size: " + weatherObject.getList().size() + " - count: " + i);
                for (int k = 0; k < weatherObject.getList().get(i).getWeather().size(); k++){
                    Log.i(TAG, "ParseJSONToJava.convertJsonToMoviesInfoJavaClass() inside other for loop - object size: " + weatherObject.getList().get(i).getWeather().size());
                    Bitmap imageIcon = queryRequest.getImageWithQuery(weatherObject.getList().get(i).getWeather().get(k).getIcon());
                    weatherObject.getList().get(i).getWeather().get(k).setIconByte(imageIcon);
                }
            } **/

        } catch (JsonGenerationException ex) {
            Log.i(TAG, "ParseJSONToJava.convertJsonToMoviesInfoJavaClass() inside catch block: Exception - JsonGenerationException - message: " + ex.getMessage());
            ex.printStackTrace();

        } catch (JsonMappingException ex) {
            Log.i(TAG, "ParseJSONToJava.convertJsonToMoviesInfoJavaClass() inside catch block: Exception - JsonMappingException - message: " + ex.getMessage());
            ex.printStackTrace();

        } catch (IOException ex) {
            Log.i(TAG, "ParseJSONToJava.convertJsonToMoviesInfoJavaClass() inside catch block: Exception - IOException - message: " + ex.getMessage());
            ex.printStackTrace();

        }

        return moviesObject;

    }

    /**
     *
     * This method parses from JSON format to Java Objects with the Jackson API
     *
     * @param json
     * @return
     * @Original_Base WerbSystique - http://websystique.com/java/json/jackson-convert-java-object-to-from-json/
     */
    public MovieListTrailer convertJsonToMovieListTrailerJavaClass(String json){
        Log.i(TAG, "ParseJSONToJava.convertJsonToMovieListTrailerJavaClass() inside method - param value: " + json);
        mapper = new ObjectMapper();
        MovieListTrailer moviesObject = null;

        try {
            Log.i(TAG, "ParseJSONToJava.convertJsonToMovieListTrailerJavaClass() inside try/catch block - param value: " + json);

            moviesObject = mapper.readValue(json, MovieListTrailer.class);

        } catch (JsonGenerationException ex) {
            Log.i(TAG, "ParseJSONToJava.convertJsonToMovieListTrailerJavaClass() inside catch block: Exception - JsonGenerationException - message: " + ex.getMessage());
            ex.printStackTrace();

        } catch (JsonMappingException ex) {
            Log.i(TAG, "ParseJSONToJava.convertJsonToMovieListTrailerJavaClass() inside catch block: Exception - JsonMappingException - message: " + ex.getMessage());
            ex.printStackTrace();

        } catch (IOException ex) {
            Log.i(TAG, "ParseJSONToJava.convertJsonToMovieListTrailerJavaClass() inside catch block: Exception - IOException - message: " + ex.getMessage());
            ex.printStackTrace();

        }

        return moviesObject;

    }

    /**
     *
     * This method parses from JSON format to Java Objects with the Jackson API
     *
     * @param json
     * @return
     * @Original_Base WerbSystique - http://websystique.com/java/json/jackson-convert-java-object-to-from-json/
     */
    public MovieListReviews convertJsonToMovieListReviewsJavaClass(String json){
        Log.i(TAG, "ParseJSONToJava.convertJsonToMovieListReviewsJavaClass() inside method - param value: " + json);
        mapper = new ObjectMapper();
        MovieListReviews moviesObject = null;

        try {
            Log.i(TAG, "ParseJSONToJava.convertJsonToMovieListReviewsJavaClass() inside try/catch block - param value: " + json);

            moviesObject = mapper.readValue(json, MovieListReviews.class);

        } catch (JsonGenerationException ex) {
            Log.i(TAG, "ParseJSONToJava.convertJsonToMovieListReviewsJavaClass() inside catch block: Exception - JsonGenerationException - message: " + ex.getMessage());
            ex.printStackTrace();

        } catch (JsonMappingException ex) {
            Log.i(TAG, "ParseJSONToJava.convertJsonToMovieListReviewsJavaClass() inside catch block: Exception - JsonMappingException - message: " + ex.getMessage());
            ex.printStackTrace();

        } catch (IOException ex) {
            Log.i(TAG, "ParseJSONToJava.convertJsonToMovieListReviewsJavaClass() inside catch block: Exception - IOException - message: " + ex.getMessage());
            ex.printStackTrace();

        }

        return moviesObject;

    }

}
