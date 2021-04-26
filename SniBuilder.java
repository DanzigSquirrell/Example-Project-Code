package com.example.finalassignment.spaceNasaImage;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class is mostly for convenience, takes in a json object and returns a SniObject
 *
 * @author Dan Squirrell
 * @version 1
 */
public class SniBuilder {
    private final static String DATE = "date";
    private final static String TITLE = "title";
    private final static String EXPLANATION = "explanation";
    private final static String URL = "url";
    private final static String HD_URL = "hdurl";

    private JSONObject json;
    private SniObject sni;

    /**
     * Takes in json object from SniItem async task
     * @param json
     * */
    SniBuilder(JSONObject json) {
        this.sni = new SniObject();
        this.json = json;
    }

    /**
     * Converts json object to SniObject via the json string tags
     * */
    public SniObject build() throws JSONException {
        if (json != null) {
            sni.setDate(json.getString(DATE));
            sni.setExplanation(json.getString(EXPLANATION));
            sni.setTitle(json.getString(TITLE));
            sni.setUrl(json.getString(URL));
            sni.setHdurl(json.getString(HD_URL));
            return sni;
        } else return null;
    }
}
