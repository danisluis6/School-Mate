package com.example.enclaveit.schoolmateapp.alert;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.enclaveit.schoolmateapp.R;

/**
 * Created by enclaveit on 16/12/2016.
 */

public class AlertDialogFee {

    private static android.app.AlertDialog alertDialog;

    public static AlertDialog onCreateDialog(final Activity activity, String titlex, String messagex, final Context context) {
        // Initial AlertDialogFee and draw componet concerning inside AlertDialogFee
        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(activity);

        // Get Layout AlertDialogFee Android
        LayoutInflater inflater = activity.getLayoutInflater();
        View layout = inflater.inflate(R.layout.alert_annouce_fee, (ViewGroup) activity.findViewById(R.id.layout_root));
        builder.setView(layout);
        // Set Content for AlertDailog
        Button OK = (Button)layout.findViewById(R.id.ok);
        Button Cancle = (Button)layout.findViewById(R.id.cancle);

        final TextView title = (TextView) layout.findViewById(R.id.title);
        final TextView description = (TextView) layout.findViewById(R.id.description);

        title.setText("Thông báo học phí");
        description.setText("Sắp tới nhà trường tổ chức thu học phí đợt I: 2016-2017");

        // Add Event for button
        OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO
            }
        });
        Cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.cancel();
            }
        });
        alertDialog = builder.create();
        return alertDialog;
    }
}
