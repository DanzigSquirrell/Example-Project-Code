package com.example.finalassignment.spaceNasaImage;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.DialogFragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalassignment.DrawerHeaderBuilder;
import com.example.finalassignment.MenuIntentFactory;
import com.example.finalassignment.R;
import com.google.android.material.navigation.NavigationView;

import java.util.Calendar;
import java.util.Date;

/**
 * This class deals with the user interface of the activity. Asks user to enter a date via picker, takes date and sends it to the other classes to do the heavy lifting.
 * also links to favourites page
 *
 * @author Dan Squirrell
 * @version 1
 */
public class SniPicker extends AppCompatActivity {
    Button pickerButton;
    TextView dateConfirm;
    TextView invalidDate;
    Button goButton;
    String apiQuery;
    Button favouriteButton;

    Toolbar myToolbar;

    /**
     * Gets name from splash, initializes fields and sets button click listeners
     * @param savedInstanceState
     * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sni_picker);

        Intent fromSplash = getIntent();
        String username = fromSplash.getStringExtra("NAME");
        Toast.makeText(getApplicationContext(), getString(R.string.Welcome) + username, Toast.LENGTH_SHORT).show();

        myToolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(myToolbar);

        dateConfirm = findViewById(R.id.textView9);
        dateConfirm.setVisibility(View.INVISIBLE);

        invalidDate = findViewById(R.id.textView10);
        invalidDate.setVisibility(View.INVISIBLE);

        goButton = findViewById(R.id.button4);
        goButton.setVisibility(View.INVISIBLE);
        goButton.setOnClickListener(v -> {
            Intent goToItem = new Intent(SniPicker.this, SniItem.class);
            goToItem.putExtra("apiQuery", apiQuery);
            startActivityForResult(goToItem, 345);
        });

        pickerButton = findViewById(R.id.button3);
        pickerButton.setOnClickListener(v -> {
            showDatePickerDialog();
        });

        favouriteButton = findViewById(R.id.button5);
        favouriteButton.setOnClickListener(v -> {
            Intent goToFavs = new Intent(this, SniFavourites.class);
            startActivityForResult(goToFavs, 345);
        });


    }

    /**
     * Convenience method to show the date picker dialog
     * */
    public void showDatePickerDialog() {
        DialogFragment newFragment = new PickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    /**
     * method used to process the users chosen date. If the date is in the future produces a warning. If no errors, loads the buttons and textviews to proceed
     * @param calendar callendar object representing chosen date
     * @param chosenDate String representing chosen date
     * */
    protected void dateSetter(String chosenDate, Calendar calendar) {

        dateConfirm.setText(chosenDate);
        dateConfirm.setVisibility(View.VISIBLE);

        apiQuery = chosenDate;
        Calendar testCalendar = Calendar.getInstance();
        Date testDate = testCalendar.getTime();
        Date userDate = calendar.getTime();

        if (userDate.after(testDate)) {
            invalidDate.setVisibility(View.VISIBLE);
        } else {
            invalidDate.setVisibility(View.INVISIBLE);
            goButton.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Toolbar initialization
     *
     * @param menu menu sent via method call
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate the menu items for use in the actionbar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sni_menu_help, menu);
        return true;
    }

    /**
     * On click handler for toolbar items, shows only one, the help info
     *
     * @param m menu item sent from method call
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem m) {
        switch (m.getItemId()) {
            case R.id.sni_about:
                AlertDialog.Builder builder = new AlertDialog.Builder(SniPicker.this);
                builder.setMessage(R.string.SniAbout);
                builder.setTitle(R.string.about);
                builder.setNegativeButton(R.string.close, (dialog, which) -> dialog.dismiss());
                builder.create();
                builder.show();
                break;
        }
        return true;
    }
}