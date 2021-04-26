package com.example.finalassignment.spaceNasaImage;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finalassignment.R;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * This class is the bu
 */
public class SniItem extends AppCompatActivity {
    /**
     * holds the date portion of the url
     */
    String apiQuery;
    /**Image on page*/
    ImageView image;
    /**Created object*/
    SniObject sniObject;
    /**Attributes of object:*/
    TextView title;
    TextView explanation;
    TextView imageUrl;
    TextView hdUrl;
    TextView date;
    /**Database*/
    SQLiteDatabase db;
    /**Favourite button*/
    ImageButton favourite;
    /**Snackbar for adding to favourites*/
    Snackbar snackbar;

    /**
     * when the view is created
     * initializes fields, loads AsyncTask, and database. Handles logic for favourites button
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sni_item);

        Intent fromPicker = getIntent();
        apiQuery = fromPicker.getStringExtra("apiQuery");

        image = findViewById(R.id.imageView2);
        title = findViewById(R.id.textView11);
        explanation = findViewById(R.id.textView12);
        imageUrl = findViewById(R.id.HDLink);
        hdUrl = findViewById(R.id.Source);
        date = findViewById(R.id.textView15);
        favourite = findViewById(R.id.imageButton);

        SniAsync async = new SniAsync();
        async.execute(apiQuery);

        SniDB sniDB = new SniDB(this);
        db = sniDB.getWritableDatabase();

        String[] columns = {SniDB.ID_COLUMN, SniDB.DATE, SniDB.EXPLANATION, SniDB.TITLE, SniDB.URL, SniDB.HD_URL};
        Cursor result = db.query(false, SniDB.TABLE_NAME, columns, null, null, null, null, null, null);

        favourite.setOnClickListener(v -> {
            if (existsInDB(sniObject.getDate())) {
                db.delete(SniDB.TABLE_NAME, SniDB.DATE + "=?", new String[]{sniObject.getDate()});
                favourite.setImageDrawable(getDrawable(R.drawable.heart_empty));
                snackbar = Snackbar.make(findViewById(android.R.id.content), getString(R.string.FavDel), Snackbar.LENGTH_LONG);
                snackbar.show();

            } else {
                ContentValues cv = new ContentValues();
                cv.put(SniDB.DATE, sniObject.getDate());
                cv.put(SniDB.EXPLANATION, sniObject.getExplanation());
                cv.put(SniDB.TITLE, sniObject.getTitle());
                cv.put(SniDB.URL, sniObject.getUrl());
                cv.put(SniDB.HD_URL, sniObject.getHdurl());

                sniObject.setId(db.insert(SniDB.TABLE_NAME, "NullColumnName", cv));


                favourite.setImageDrawable(getDrawable(R.drawable.heart_full));
                snackbar = Snackbar.make(findViewById(android.R.id.content), getString(R.string.FavAdd), Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });

    }

    /**
     * Takes the json on the Api page and creats a json object. Converts Json object into SniObject, Populates Text and image views on screen with item details
     * @author Dan Squirrell
     * @version 1
     * */
    public class SniAsync extends AsyncTask<String, Integer, Object> {
        /**
         * takes url and converts json on api to json obect, builds SniObject
         * @param strings
         * */
        @Override
        protected Object doInBackground(String... strings) {

            try {
                URL url = new URL("https://api.nasa.gov/planetary/apod?api_key=DgPLcIlnmN0Cwrzcg3e9NraFaYLIDI68Ysc6Zh3d&date=" + apiQuery);

                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

                String jsonFromUrl = readJson(in);
                JSONObject json = new JSONObject(jsonFromUrl);

                sniObject = new SniBuilder(json).build();

            } catch (
                    MalformedURLException mfe) {

            } catch (
                    IOException ioe) {


            } catch (JSONException e) {

            }
            return sniObject;
        }

        /**
         * after the object is converted, populates text and image views with item information
         * @param o object passed from doInBackground
         * */
        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            sniObject = (SniObject) o;


            String titleFromO = sniObject.getTitle();
            Log.i("Title", titleFromO);
            title.setText(sniObject.getTitle());
            explanation.setText(sniObject.getExplanation());
            imageUrl.setText(getString(R.string.Source) + ": " + sniObject.getUrl());
            hdUrl.setText(getString(R.string.HDLink) + ": \n" + Html.fromHtml(sniObject.getHdurl()));
            date.setText(sniObject.getDate());
            if (existsInDB(sniObject.getDate()))
                favourite.setImageDrawable(getDrawable(R.drawable.heart_full));
            else favourite.setImageDrawable(getDrawable(R.drawable.heart_empty));


            Picasso.get().load(sniObject.getUrl()).into(image);
        }

        /**
         * Convenience method to parse json from url
         * @param rd reader sent from method call
         * */
        private String readJson(Reader rd) throws IOException {
            StringBuilder sb = new StringBuilder();
            int cp;
            while ((cp = rd.read()) != -1) {
                sb.append((char) cp);
            }
            return sb.toString();
        }

    }

    /**
     * Checks if specific item exists in database, mostly used for favourites activity
     * @param s Date string sent from method call
     * */
    public boolean existsInDB(String s) {
        SniDB sniDB = new SniDB(this);
        db = sniDB.getWritableDatabase();

        String[] columns = {SniDB.ID_COLUMN, SniDB.DATE, SniDB.EXPLANATION, SniDB.TITLE, SniDB.URL, SniDB.HD_URL};
        String[] selectionArgs = {s};
        Cursor result = db.query(false, SniDB.TABLE_NAME, columns, SniDB.DATE + "=?", selectionArgs, null, null, null, "1");

        boolean exists = (result.getCount() > 0);
        return exists;
    }
}
