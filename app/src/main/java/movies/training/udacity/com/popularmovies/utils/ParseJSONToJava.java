package movies.training.udacity.com.popularmovies.utils;

import android.graphics.Bitmap;
import android.util.Log;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

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
    public Object convertToJava(String json){
        Log.i(TAG, "ParseJSONToJava.convertToJava() inside method - param value: " + json);
        mapper = new ObjectMapper();
        MoviesInfo moviesObject = null;

        try {
            Log.i(TAG, "ParseJSONToJava.convertToJava() inside try/catch block - param value: " + json);

            moviesObject = mapper.readValue(json, MoviesInfo.class);

/**         for (int i = 0; i < weatherObject.getList().size(); i++){
                Log.i(TAG, "ParseJSONToJava.convertToJava() inside for loop - object size: " + weatherObject.getList().size() + " - count: " + i);
                for (int k = 0; k < weatherObject.getList().get(i).getWeather().size(); k++){
                    Log.i(TAG, "ParseJSONToJava.convertToJava() inside other for loop - object size: " + weatherObject.getList().get(i).getWeather().size());
                    Bitmap imageIcon = queryRequest.getImageWithQuery(weatherObject.getList().get(i).getWeather().get(k).getIcon());
                    weatherObject.getList().get(i).getWeather().get(k).setIconByte(imageIcon);
                }
            } **/

        } catch (JsonGenerationException ex) {
            Log.i(TAG, "ParseJSONToJava.convertToJava() inside catch block: Exception - JsonGenerationException - message: " + ex.getMessage());
            ex.printStackTrace();

        } catch (JsonMappingException ex) {
            Log.i(TAG, "ParseJSONToJava.convertToJava() inside catch block: Exception - JsonMappingException - message: " + ex.getMessage());
            ex.printStackTrace();

        } catch (IOException ex) {
            Log.i(TAG, "ParseJSONToJava.convertToJava() inside catch block: Exception - IOException - message: " + ex.getMessage());
            ex.printStackTrace();

        }

        return moviesObject;

    }
}
