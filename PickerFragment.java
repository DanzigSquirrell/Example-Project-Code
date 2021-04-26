package com.example.finalassignment.spaceNasaImage;

import androidx.fragment.app.DialogFragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.DatePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * This fragment handles the date picker from SniPicker. Loads a calendar popup and processes the information
 *
 * @author Dan Squirrell
 * @version 1
 */
public class PickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    int day;
    int month;
    int year;

    /**
     * sets default calendar value and creates the pop up dialog
     * @param savedInstanceState
     * */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //set default date to today's date
        final Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        //create and return dialog
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    /**
     * After the user has chosen a date, his method formats it and sends it to SniPiker via dateSetter method.
     * @param dayOfMonth
     * @param year
     * @param month
     * @param view the dialog popup
     * */
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String pickedDate = dateFormat.format(calendar.getTime());

        ((SniPicker) getActivity()).dateSetter(pickedDate, calendar);
        //getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK,displayResult);

    }
}
