package com.example.enclaveit.schoolmateapp.libraries;

import android.util.Log;

import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.List;

/**
 * Created by enclaveit on 08/03/2017.
 */

public class MyBarDataSet extends BarDataSet {
    public MyBarDataSet(List<BarEntry> yVals, String label) {
        super(yVals, label);
    }

    @Override
    public int getColor(int index){
        if(getEntryForXIndex(index).getVal() >= 9.0f) // more than 9
            return mColors.get(0);
        else if(getEntryForXIndex(index).getVal() >= 7.5f) // more than 7.5
            return mColors.get(1);
        else if(getEntryForXIndex(index).getVal() >= 5.0f) // more than 5
            return mColors.get(2);
        else if(getEntryForXIndex(index).getVal() >= 4.0f) // more than 4
            return mColors.get(3);
        else
            return mColors.get(4);
    }

}
