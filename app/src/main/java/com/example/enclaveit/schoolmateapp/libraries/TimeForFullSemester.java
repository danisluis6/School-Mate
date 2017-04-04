package com.example.enclaveit.schoolmateapp.libraries;
import java.util.Calendar;

/**
 * Created by enclaveit on 07/03/2017.
 */

public class TimeForFullSemester {

    final String [] MONTH_OF_YEAR = {"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};

    public String[] getAllMonthSemester(){
        String[] listMonths = new String[11]; // from August to Jun
        Calendar current = Calendar.getInstance();
            if(current.get(Calendar.MONTH) > 7){
                current.set(Calendar.YEAR, current.get(Calendar.YEAR));
            } else {
                current.set(Calendar.YEAR, (current.get(Calendar.YEAR) -1));
            }
        current.set(Calendar.MONTH, 7);

        for (int i =0 ; i< listMonths.length; i++){
            Calendar clone = (Calendar) current.clone();
            clone.add(Calendar.MONTH, i);
            listMonths[i] = MONTH_OF_YEAR[clone.get(Calendar.MONTH)] + " " + clone.get(Calendar.YEAR);
        }
        return listMonths;
    }

    public int getIndexOfCurrentWeekInSemester(){
        /*Get current WeekOfYear to compare with index of listAllWeeksOfYear
        listAllWeeksOfYear       |       Current Week Of Year
                1                           34
                20                          1
                42                          23
                52                          33
                19                          52
        */
        Calendar current = Calendar.getInstance();
        int currentWeekOfYear = current.get(Calendar.WEEK_OF_YEAR);
        int index = (18 + currentWeekOfYear + 1);
        index = (index > 52 ) ? (index - 52 ) : index;
        if(index <= 42 ){ // Current WeekOfYear is in listAllWeeksOfYear
            return index;
        }
        //if user access to system after all the official semester, return the last week.
        return  42;
    }

    public int getIndexOfCurrentMonthInSemester(){
        Calendar current = Calendar.getInstance();
        int currentMonthOfYear = current.get(Calendar.MONTH);
        int index = (11 - 7 + currentMonthOfYear + 1);
        index = (index > 10 ) ? (index - 12 ) : index;
        if(index <= 10 ){ // Current WeekOfYear is in listAllWeeksOfYear
            return index;
        }
        //if user access to system after all the official semester, return the last month.
        return 10;
    }
}
