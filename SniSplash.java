package com.example.finalassignment.spaceNasaImage;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.finalassignment.DrawerHeaderBuilder;
import com.example.finalassignment.MenuIntentFactory;
import com.example.finalassignment.R;
import com.google.android.material.navigation.NavigationView;

/**
 * This class serves as the launching point for my portion of the app. Shows a simple edit text in order to take in the user's name
 * and then sends it to the following page for use. Includes a simple progress bar as a demo for completion.
 * @author Dan Squirrell
 * @version 1.0
 *
 */
public class SniSplash extends AppCompatActivity {
    /**
     * The button to use
     */
    Button loadButton;
    /**
     * The progress bar to use
     */
    ProgressBar progress;
    /**
     * The EditText to use
     */

    EditText name;


    /**
     * when the view is created. initializes views, toolbar and navigation drawer
     *
     * @param savedInstanceState
     */
    Toolbar myToolbar;
    DrawerLayout drawer;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sni_main);

        loadButton = findViewById(R.id.button2);
        progress = findViewById(R.id.progressBar);
        name = findViewById(R.id.editText);

        myToolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(myToolbar);

        drawer = findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                drawer,
                myToolbar,
                R.string.open,
                R.string.close
        );

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(menuItem -> {
            //Builder to create the Intent based on the menuItem
            Intent intent = MenuIntentFactory.createIntent(this, menuItem);
            startActivity(intent);
            return true;
        });
        new DrawerHeaderBuilder().setActivity(this).setNavigationView(navigationView).buildHeader();


        loadButton.setOnClickListener(v -> {
            progress.setProgress(0);
            /**
             * A simple timer, from CountDownTimer class, this is used to simulate the progress bar filling, since there is nothing that needs progress tracked as of yet.
             *
             */
            CountDownTimer countDownTimer =
                    new CountDownTimer(2500, 1) {
                        public void onTick(long millisUntilFinished) {
                            progress.setProgress(Math.abs((int) millisUntilFinished / 25 - 100));

                        }

                        public void onFinish() {
                            Intent toList = new Intent(SniSplash.this, SniPicker.class);
                            toList.putExtra("NAME", name.getText().toString());
                            startActivityForResult(toList, 345);
                        }
                    };
            countDownTimer.start();
        });


    }

    /**
     * Creates a menu, from which items can be selected on the toolbar
     *
     * @param menu Menu sent by method call
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate the menu items for use in the actionbar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sni_menu_help, menu);
        return true;
    }

    /**
     * handles click events on the toolbar items
     *
     * @param m the menu item that is clicked
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem m) {
        switch (m.getItemId()) {
            case R.id.sni_about:
                AlertDialog.Builder builder = new AlertDialog.Builder(SniSplash.this);
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
