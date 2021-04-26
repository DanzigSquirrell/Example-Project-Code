package com.example.finalassignment.spaceNasaImage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.finalassignment.R;

/**
 * Empty activity for the fragment to function properly on a phone
 *
 * @author Dan
 * @version 1
 */
public class EmptyActivity extends AppCompatActivity {
    /**
     * Sends info to details fragment to be processed
     * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty);

        Bundle dataToSend = getIntent().getExtras();
        DetailsFragment frag = new DetailsFragment();
        frag.setArguments(dataToSend);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction().replace(R.id.Frame, frag);
        ft.commit();
    }
}
