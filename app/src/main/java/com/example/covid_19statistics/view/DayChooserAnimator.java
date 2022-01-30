package com.example.covid_19statistics.view;

import android.graphics.Color;
import android.widget.TextView;

public class DayChooserAnimator {


    public static void animate(TextView select, TextView noSelect, TextView noSelect2){

        select.setTextSize(19f);
        select.setTextColor(Color.WHITE);

        noSelect.setTextSize(16f);
        noSelect.setTextColor(Color.LTGRAY);

        noSelect2.setTextSize(16f);
        noSelect2.setTextColor(Color.LTGRAY);

    }

}
