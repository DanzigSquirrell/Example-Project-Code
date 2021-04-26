package com.example.finalassignment.spaceNasaImage;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.finalassignment.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * This class does the majority of the work for my portion of the app. It holds the list view that will eventually hold the elements
 * of the downloaded page. Long pressing on each element will open an alert with option to delete, while tapping an element will
 * open a new view.
 *
 * @author Dan Squirrell
 * @version 2
 */
public class SniFavourites extends AppCompatActivity {
    /**
     * the ListView to use
     */
    ListView list;
    /**
     * The Array list to hold the elements to be displayed
     */
    ArrayList<SniObject> favourites = new ArrayList<>();
    /**
     * The adapter to alter the listview
     */
    BaseAdapter a;
    /**
     * The database
     */

    SQLiteDatabase db;
    /**
     * Determines if device is a tablet
     */
    boolean tablet;
    /**
     * Framelayout to hold details fragment
     */
    FrameLayout f;
    /**
     * fragment to show details when on tablet
     */
    DetailsFragment frag;
    /**
     * When this view is created
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sni_favourites);

        /**Set Frame to layout frame*/
        f = findViewById(R.id.Frame);
        /**if Frame is null, it is not a tablet, set tablet false*/
        tablet = f != null;

        /**Set list for favourites*/
        list = findViewById(R.id.ListView);

        list.setAdapter(a = new myListAdapter());

        /**Database initialization*/
        SniDB sniDB = new SniDB(this);
        db = sniDB.getWritableDatabase();

        String[] columns = {SniDB.ID_COLUMN, SniDB.DATE, SniDB.EXPLANATION, SniDB.TITLE, SniDB.URL, SniDB.HD_URL};
        Cursor result = db.query(false, SniDB.TABLE_NAME, columns, null, null, null, null, null, null);

        int idIndex = result.getColumnIndex(SniDB.ID_COLUMN);
        int dateIndex = result.getColumnIndex(SniDB.DATE);
        int expIndex = result.getColumnIndex(SniDB.EXPLANATION);
        int titleIndex = result.getColumnIndex(SniDB.TITLE);
        int urlIndex = result.getColumnIndex(SniDB.URL);
        int hdUrlIndex = result.getColumnIndex(SniDB.HD_URL);
        /**fill favourite array with items from array*/
        while (result.moveToNext()) {
            long id = result.getLong(idIndex);
            String date = result.getString(dateIndex);
            String explanation = result.getString(expIndex);
            String title = result.getString(titleIndex);
            String url = result.getString(urlIndex);
            String hdUrl = result.getString(hdUrlIndex);

            favourites.add(new SniObject(date, explanation, title, url, hdUrl, id));
        }
    }

    /**
     * This inner class creates the list adapter to alter the elements displayed on the listview
     */
    private class myListAdapter extends BaseAdapter {
        /**
         * @return returns the size of the arraylist initialized above
         */
        public int getCount() {
            return favourites.size();
        } //This function tells how many objects to show

        /**
         * @param position the position in the list of the item on the list
         * @return returns the string at position p
         */
        public SniObject getItem(int position) {
            return favourites.get(position);
        }

        /**
         * @param p the position of the item in the list
         * @return returns the database id of the item at position p (not yet implemented)
         */
        public long getItemId(int p) {
            return getItem(p).getId();
        }

        /**
         * adds functionality to the listview, can long press or tap to move to different views.
         *
         * @param p       position in the list
         * @param thisRow The view to be changed for the item
         * @param parent  the parent of this view
         * @return returns the altered view
         */
        public View getView(int p, View thisRow, ViewGroup parent) {
            /**Inflate rows*/
            thisRow = getLayoutInflater().inflate(R.layout.sni_row_layout, parent, false);
            TextView titleDate = thisRow.findViewById(R.id.list_item);
            titleDate.setText(getItem(p).getTitle() + "\n" + getItem(p).getDate());
            ImageView thumbImage = thisRow.findViewById(R.id.imageView3);
            Picasso.get().load(getItem(p).getUrl()).into(thumbImage);
            /**Set list onclick to send info to details fragment*/
            list.setOnItemClickListener((parent1, view, position, id) -> {
                Bundle itemInfo = new Bundle();
                itemInfo.putString("Title", getItem(position).getTitle());
                itemInfo.putString("Explanation", getItem(position).getExplanation());
                itemInfo.putString("Date", getItem(position).getDate());
                itemInfo.putString("Url", getItem(position).getUrl());
                itemInfo.putString("HDUrl", getItem(position).getHdurl());
                itemInfo.putBoolean("Tablet", tablet);
                /**If tablet, set details fragment to the right, otherwise load new page*/
                if (tablet) {
                    frag = new DetailsFragment();
                    frag.setArguments(itemInfo);
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction().replace(R.id.Frame, frag);
                    ft.commit();

                } else {
                    Intent goToDetails = new Intent(SniFavourites.this, EmptyActivity.class);
                    goToDetails.putExtras(itemInfo);
                    startActivityForResult(goToDetails, 345);
                }

            });
            /**Delete from database and list*/
            list.setOnItemLongClickListener((parent1, view, position, id) -> {
                String alert1 = getResources().getString(R.string.deleteFavorites) + "?";


                AlertDialog.Builder builder = new AlertDialog.Builder(SniFavourites.this);
                builder.setMessage(alert1);
                builder.setTitle(R.string.alertDialog);
                builder.setNegativeButton(R.string.close, (dialog, which) -> dialog.dismiss());
                builder.setPositiveButton(R.string.Yes, (dialog, which) -> {
                    db.delete(SniDB.TABLE_NAME, SniDB.DATE + "=?", new String[]{getItem(position).getDate()});
                    favourites.remove(getItem(position));

                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.Deleted), Toast.LENGTH_SHORT).show();
                    if (tablet && frag.isVisible()) {
                        FragmentTransaction ft = getSupportFragmentManager().beginTransaction().remove(frag);
                        ft.commit();
                    }

                    a.notifyDataSetChanged();
                });

                builder.create();
                builder.show();

                return true;
            });

            return thisRow;
        }
    }
}
