package com.example.finalassignment.spaceNasaImage;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.finalassignment.R;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DetailsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class DetailsFragment extends Fragment {
    TextView title;
    TextView explanation;
    TextView date;
    TextView url;
    TextView hdUrl;
    Button hideButton;
    Bundle itemInfo;
    ImageView image;
    public DetailsFragment() {
        // Required empty public constructor
    }

    /**
     * gets info from the bundle created in SniItem, Inflates fragment and fills fields with data from bundle
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        itemInfo = getArguments();

        String aDate = itemInfo.getString("Date");
        String aTitle = itemInfo.getString("Title");
        String aExplanation = itemInfo.getString("Explanation");
        String aUrl = itemInfo.getString("Url");
        String aHdUrl = itemInfo.getString("HDUrl");
        boolean aTablet = itemInfo.getBoolean("Tablet");

        // Inflate the layout for this fragment
        View thisItem = inflater.inflate(R.layout.fragment_details, container, false);
        title = thisItem.findViewById(R.id.articleTitle);
        date = thisItem.findViewById(R.id.articleDate);
        explanation = thisItem.findViewById(R.id.articleExplanation);
        hdUrl = thisItem.findViewById(R.id.articleHdUrl);
        url = thisItem.findViewById(R.id.articleUrl);
        hideButton = thisItem.findViewById(R.id.button6);
        image = thisItem.findViewById(R.id.imageView6);
        title.setText(aTitle);
        date.setText(aDate);
        explanation.setText(aExplanation);
        url.setText(aUrl);
        hdUrl.setText(aHdUrl);

        Picasso.get().load(aUrl).into(image);
        hideButton.setOnClickListener(v -> {
            if (aTablet) {
                SniFavourites parent = (SniFavourites) getActivity();
                parent.getSupportFragmentManager().beginTransaction().remove(this).commit();
            } else {
                EmptyActivity parent = (EmptyActivity) getActivity();
                parent.finish();
            }
        });
        return thisItem;
    }
}