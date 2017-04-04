package com.example.enclaveit.schoolmateapp.libraries;

import android.content.Context;
import android.os.CountDownTimer;
import android.widget.Toast;

/**
 * Created by enclaveit on 26/12/2016.
 */

public class Toaster {
    private static final int SHORT_TOAST_DURATION = 1000;
    public Toaster(){};

    public static void makeToast(Context context, String message, long duration){
        final Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        new CountDownTimer(Math.max(duration - SHORT_TOAST_DURATION, 1000), 1000){
            @Override
            public void onFinish(){
                toast.show();
            }

            @Override
            public void onTick(long millisUntilFinished){
                toast.show();
            }
        }.start();
    }
}
