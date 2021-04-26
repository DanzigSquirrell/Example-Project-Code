package com.example.finalassignment.spaceNasaImage;

/**
 * Simple object class to hold the information about each item created
 */
public class SniObject {
    private String date = "";
    private String title = "";
    private String explanation = "";
    private String url = "";
    private String hdurl = "";
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public SniObject() {
    }

    public SniObject(String date, String explanation, String title, String url, String hdurl, long id) {
        setDate(date);
        setTitle(title);
        setExplanation(explanation);
        setUrl(url);
        setHdurl(hdurl);
        setId(id);
    }

    public String getDate() {
        return date;
    }

    void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    void setTitle(String title) {
        this.title = title;
    }

    public String getExplanation() {
        return explanation;
    }

    void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public String getUrl() {
        return url;
    }

    void setUrl(String url) {
        this.url = url;
    }

    public String getHdurl() {
        return hdurl;
    }

    void setHdurl(String hdurl) {
        this.hdurl = hdurl;
    }
}
