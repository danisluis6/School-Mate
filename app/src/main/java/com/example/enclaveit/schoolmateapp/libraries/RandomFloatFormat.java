package com.example.enclaveit.schoolmateapp.libraries;

import java.text.DecimalFormat;
import java.util.Random;

/**
 * Created by enclaveit on 08/03/2017.
 */

public class RandomFloatFormat {
    Random rd;
    public RandomFloatFormat(){
        this.rd = new Random();
    }
    public float randomFloat(){
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        return Float.parseFloat(decimalFormat.format(rd.nextFloat()*10));
    }
}
