package com.example.enclaveit.schoolmateapp.fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.enclaveit.schoolmateapp.R;
import com.example.enclaveit.schoolmateapp.libraries.RandomFloatFormat;

import java.util.Random;

/**
 * Created by PHA on 07/03/2017.
 */

public class FigureReportFragment extends Fragment {

    TableLayout tableReportFigure;
    private final String[] listSubjects = {"Art", "Biology", "Chemistry", "English", "Geography", "Health", "Logic",
            "Mathematics", "Music", "Philosophy", "Physics", "Programming", "Reading","Science", "Sports"};
    private final String[] listTeachers = {"Doris Wilson", "Amy Smith", "Edna Francis", "Sarah Norris", "Jennie Crigler",
                    "Gladys Swon", "Ruth Carman","Irene Ball", "Wynona James", "Doris Stuart", "Eunice Smith",
                    "Helen Levings", "Lucille Tipton", "Thelma Egbert", "Frances Greeves"};
    private final String[] evaluations = {"Excellent", "Good", "Normal", "Need Improve", "Bad"};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.figure_report_fragment_layout, container, false);

        final int[] rowColor = {getResources().getColor(R.color.row_1),getResources().getColor(R.color.row_2) };
        int[] colorEvaluation = {getContext().getResources().getColor(R.color.excellent),
                getContext().getResources().getColor(R.color.good),
                getContext().getResources().getColor(R.color.normal),
                getContext().getResources().getColor(R.color.improve),
                getContext().getResources().getColor(R.color.bad)};
        RandomFloatFormat rd = new RandomFloatFormat();

        tableReportFigure = (TableLayout) view.findViewById(R.id.tableReportFigure);
        for(int i=0 ; i<listSubjects.length; i++){
            TableRow row = new TableRow(getActivity());
                row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                row.setBackgroundColor((i%2 == 0) ? rowColor[0] : rowColor[1]);
                row.setPadding(0,10,0,10);
                row.setWeightSum(7.0f);
            /*LinearLayout rowLinear = new LinearLayout(getActivity());
                rowLinear.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                rowLinear.setOrientation(LinearLayout.HORIZONTAL);
                row.setWeightSum(4.0f);*/
            int valueToEvaluate = 0;
            for (int j=0; j< 4; j++) {
                TextView data = new TextView(getActivity());
                    data.setGravity(Gravity.CENTER);
                    data.setTextSize(13);
                    data.setPadding(4,0,4,0);
                    data.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, (j == 1 )? 1: 2));
                    if(j == 0 )  data.setText(listSubjects[i]);
                    if (j == 1 ) {
                        data.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                        float grade = rd.randomFloat();
                        data.setText(grade+"");
                        if (grade >= 9.0f) valueToEvaluate = 0;
                        else if(grade >= 7.5f) valueToEvaluate = 1;
                            else if(grade >= 5.0f)  valueToEvaluate = 2;
                                else if(grade >= 4.0f)  valueToEvaluate = 3;
                                    else valueToEvaluate = 4;
                        data.setTextColor(colorEvaluation[valueToEvaluate]);
                    }
                    if(j == 2 )  data.setText(listTeachers[i]);
                    if (j == 3 ) {
                        data.setText(evaluations[valueToEvaluate]);
                        data.setTextColor(colorEvaluation[valueToEvaluate]);
                    }
                row.addView(data);
            }
                tableReportFigure.addView(row);

        }
        Log.i("FIGURE_REPORT", "Table Child: "+ tableReportFigure.getChildCount());
        tableReportFigure.invalidate();
        return view;
    }
}
