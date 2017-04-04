package com.example.enclaveit.schoolmateapp.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.enclaveit.schoolmateapp.R;
import com.example.enclaveit.schoolmateapp.asynctasks.GetAllVacationsAsyncTask;
import com.example.enclaveit.schoolmateapp.asynctasks.GetAllWeeksAsyncTask;
import com.example.enclaveit.schoolmateapp.asynctasks.TimeTableAsyncTask;
import com.example.enclaveit.schoolmateapp.libraries.ActivityBase;
import com.example.enclaveit.schoolmateapp.libraries.TimeForFullSemester;
import com.example.enclaveit.schoolmateapp.libraries.Toaster;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by enclaveit on 06/03/2017.
 */

public class ActivityTimeTable extends ActivityBase implements View.OnTouchListener , View.OnClickListener, RadioGroup.OnCheckedChangeListener, AdapterView.OnItemSelectedListener{
    TimeForFullSemester timeForFullSemester;
    LinearLayout linearTitleOfDay, linearLessons, linearTimeTable, linearEachDay;
    HorizontalScrollView horizontalScrollViewDayOfWeek, horizontalScrollViewTimeTable;
    boolean isTimeTableHorizontalScroll =false, isDayOfWeekHorizontalScroll = false;
    ScrollView scrollViewTimeTable,scrollViewLesson;
    boolean isScrollTimeTable = false, isScrollLesson = false;

    /*Image button to view Timetable after choose type of timetable and time*/
    ImageButton imgBtnViewTimeTable;
    Spinner timetableChooseTimeWeekly, timetableChooseTimeMonthly;
    RadioGroup timetableTypeOfTimetable;
    RadioButton rBtnWeekly, rBtnMonthly;

    private String[] titleOfDay;
    public String[]  listMonthFullSemester;
    public JSONObject[] listVacationJSONObject;
    private int[] bgSubject = {R.color.bg_Art, R.color.bg_Assembly, R.color.bg_Biology, R.color.bg_Chemistry, R.color.bg_Civic_Education,
            R.color.bg_Classroom_Activities, R.color.bg_English, R.color.bg_Geography, R.color.bg_History,
            R.color.bg_Informatics, R.color.bg_Literature, R.color.bg_Mathematics, R.color.bg_Music,
            R.color.bg_Physical_Education, R.color.bg_Physics, R.color.bg_Physical_Education,};
    float density;
    BroadcastReceiver broadcastReceiverTimeTable;
    TimeTableAsyncTask timeTableAsyncTask;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);
        //set Toolbar Title
        setToolbarTitle(getResources().getString(R.string.timetable));
        /*Get Density to set width and height for layouts and other elements*/
        DisplayMetrics dm = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(dm);
        density = dm.density;
        Log.i("TIME_TABLE", "Density = "+density);
        /*Create and init data for Spinner choose time*/
        initChooseTime();

        /*Init List Vacation*/
        initVacations();

        /*Create the DayOfWeek's Title
        * Each Day of Week has layout_width = "150dp"
        *                   layout_height = "match_parent"*/
        linearTitleOfDay = (LinearLayout) findViewById(R.id.linearTitleOfDay);

        /*Create the Time-line (Lesson) and TimeTable after getting data from server
        * Each Lesson has layout_width = "match_parent"     layout_height = "80dp"*/
        linearLessons = (LinearLayout) findViewById(R.id.linearLessons);

        linearTimeTable = (LinearLayout) findViewById(R.id.linearTimeTable);

        /*Set event when scroll-vertical and horizontal*/
        horizontalScrollViewTimeTable = (HorizontalScrollView) findViewById(R.id.horizontalScrollViewTimeTable);
        horizontalScrollViewTimeTable.setOnTouchListener(this);
        horizontalScrollViewDayOfWeek = (HorizontalScrollView) findViewById(R.id.horizontalScrollViewDayOfWeek);
        horizontalScrollViewDayOfWeek.setOnTouchListener(this);
        scrollViewTimeTable = (ScrollView) findViewById(R.id.scrollViewTimeTable);
        scrollViewTimeTable.setOnTouchListener(this);
        scrollViewLesson = (ScrollView) findViewById(R.id.scrollViewLesson);
        scrollViewLesson.setOnTouchListener(this);

        /*Action for change time*/
        imgBtnViewTimeTable = (ImageButton) findViewById(R.id.imgBtnViewTimeTable);
        imgBtnViewTimeTable.setOnClickListener(this);
        /*Action for change type of TimeTable and reload the combo box to choose week or month*/
        timetableTypeOfTimetable = (RadioGroup) findViewById(R.id.timetableTypeOfTimetable);
        timetableTypeOfTimetable.setOnCheckedChangeListener(this);
        rBtnWeekly = (RadioButton) findViewById(R.id.rBtnWeekly);
        rBtnMonthly = (RadioButton) findViewById(R.id.rBtnMonthly);
        rBtnWeekly.setChecked(true);    //init Weekly is the first choice

        /*Asynctask load TimeTable from server*/
        timeTableAsyncTask = new TimeTableAsyncTask(ActivityTimeTable.this);

        /*Create Broadcast to receive data after finished get data from server*/
        IntentFilter intentFilter = new IntentFilter("com.example.enclaveit.schoolmateapp.CUSTOM_INTENT");
        broadcastReceiverTimeTable = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                //Log.i("Message", "On Receiver Web Service ActivityTimeTable");
                String result = intent.getStringExtra("result");
                if(result.equalsIgnoreCase("complete")){
                    //Log.i("Message", "Received success");
                    loadListLessons(timeTableAsyncTask.getJSONArrayLessons());

                    int length = timeTableAsyncTask.getJSONArrayTitleOfDay().length();
                    //Recreate Title of day width
                    linearTitleOfDay.setLayoutParams(new FrameLayout.LayoutParams((int)(length*150*density), LinearLayout.LayoutParams.MATCH_PARENT));
                    //Recreate TimeTable width
                    linearTimeTable.setLayoutParams(new FrameLayout.LayoutParams((int)(length*150*density), LinearLayout.LayoutParams.MATCH_PARENT));
                    titleOfDay= new String[length];
                    for (int i = 0; i < length; i++){
                        try {
                            titleOfDay[i] = timeTableAsyncTask.getJSONArrayTitleOfDay().getString(i);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    setTitleOfDay(titleOfDay);

                    /*Asynctask for loading timetable*/
                    new LoadTimeTableAsyncTask().execute(timeTableAsyncTask.getJSONArrayTimeTables());
                    Toaster.makeToast(getApplicationContext(), "Get Server Data Successfully", 1000);
                }else {
                    Toaster.makeToast(getApplicationContext(), "Cannot get data from server. Please check your internet !", 2000);
                }
            }
        };
        /*Register our receiver*/
        this.registerReceiver(broadcastReceiverTimeTable, intentFilter);
    }

    private void initVacations() {
        //Get and init listVacationJSONObject in onPostExecute of GetAllVacationsAsyncTask
        final GetAllVacationsAsyncTask getAllVacationsAsyncTask = new GetAllVacationsAsyncTask(ActivityTimeTable.this);
        getAllVacationsAsyncTask.execute("http://10.0.3.2:8886/vacation");
    }

    public void initListVacationJSONObject(JSONObject[] listVacationJSONObject){
        this.listVacationJSONObject = listVacationJSONObject;
    }

    private void initChooseTime() {
        timeForFullSemester = new TimeForFullSemester();
        timetableChooseTimeWeekly = (Spinner) findViewById(R.id.timetableChooseTimeWeekly);
        /*Create list Week Of Year to set Adapter to Spinner Weekly*/
        /*Init listAllWeeksOfYear in onPostExecute of GetAllWeeksAsyncTask*/
        GetAllWeeksAsyncTask getAllWeeksAsyncTask = new GetAllWeeksAsyncTask(this);
        getAllWeeksAsyncTask.execute("http://10.0.3.2:8886/weekofyear");
        /*Create list Month of full Semester*/
        listMonthFullSemester = timeForFullSemester.getAllMonthSemester();
        timetableChooseTimeMonthly = (Spinner) findViewById(R.id.timetableChooseTimeMonthly);
        /*Set Adapter for Spinner Monthly*/
        ArrayAdapter<String> adapterForMonthly = new ArrayAdapter<String>(this,
                android.R.layout.simple_expandable_list_item_1, listMonthFullSemester);
        adapterForMonthly.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        timetableChooseTimeMonthly.setAdapter(adapterForMonthly);
        timetableChooseTimeMonthly.setOnItemSelectedListener(this);
    }

    public void initForWeekly(String[] listAllWeeksOfYear) {
        /*Set Adapter for Spinner Weekly*/
        ArrayAdapter<String> adapterForWeekly = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, listAllWeeksOfYear);
        adapterForWeekly.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        timetableChooseTimeWeekly.setAdapter(adapterForWeekly);
        timetableChooseTimeWeekly.setOnItemSelectedListener(this);
        //Select current Week in the list of week in full semester
        timetableChooseTimeWeekly.setSelection(timeForFullSemester.getIndexOfCurrentWeekInSemester() - 1);
        /*Init timetable for first time*/
        timeTableAsyncTask.execute("http://10.0.3.2:8886/timetable?classID=6&date="+timetableChooseTimeWeekly.getSelectedItem().toString());
    }

    private void setTitleOfDay(String[] listTitleOfDay) {
        linearTitleOfDay.removeAllViews();
        ViewGroup.LayoutParams layoutDayOfWeek = new ViewGroup.LayoutParams((int)(150*density), LinearLayout.LayoutParams.MATCH_PARENT);
        for (String titleOfDay : listTitleOfDay ){
            TextView txtDayOfWeek  = new TextView(ActivityTimeTable.this);
            txtDayOfWeek.setLayoutParams(layoutDayOfWeek);
            txtDayOfWeek.setText(titleOfDay);
            txtDayOfWeek.setGravity(Gravity.CENTER);
            txtDayOfWeek.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            linearTitleOfDay.addView(txtDayOfWeek);
        }
    }

    private void loadListLessons(JSONArray jsonArrayLessons) {
        String lesson = "";
        linearLessons.removeAllViews();
        for(int i=0; i<jsonArrayLessons.length(); i++){
            try {
                JSONObject jsonObject = jsonArrayLessons.getJSONObject(i);
                lesson = jsonObject.getString("lessonTime");
                TextView txtLesson  = new TextView(this);
                txtLesson.setLayoutParams(new ViewGroup.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int)(80*density)));
                txtLesson.setText(lesson);
                txtLesson.setGravity(Gravity.LEFT);
                txtLesson.setTextSize(12);
                txtLesson.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                linearLessons.addView(txtLesson);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public class LoadTimeTableAsyncTask extends AsyncTask<JSONArray, String, String> {
        @Override
        protected String doInBackground(final JSONArray... jsonArray) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONArray jsonArrayTimeTables = jsonArray[0];
                /*Set layout params for each Subject
                * Each Subject has layout_width = "150dp    layout_height = "80dp"  (included the margin)*/
                    DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Calendar calendar = Calendar.getInstance();
                    LinearLayout.LayoutParams subjectLayout = new LinearLayout.LayoutParams((int)(144*density), (int)(74*density));
                    subjectLayout.setMargins((int)(3*density),(int)(3*density),(int)(3*density),(int)(3*density));
                    int indexOfListTimeTable = 0;
                    JSONObject timetableObject ;
                    for(int j=0; j<titleOfDay.length; j++) {
                        linearEachDay = new LinearLayout(ActivityTimeTable.this);
                        linearEachDay.setLayoutParams(new LinearLayout.LayoutParams((int) (150 * density), LinearLayout.LayoutParams.MATCH_PARENT));
                        linearEachDay.setOrientation(LinearLayout.VERTICAL);
                        for (int i = 0; i < 11; i++) {
                            // Create new container for each subject: subjectName and teacherName
                            LinearLayout timetableElement = new LinearLayout(ActivityTimeTable.this);
                            timetableElement.setLayoutParams(subjectLayout);
                            timetableElement.setOrientation(LinearLayout.VERTICAL);

                            TextView txtSubject = new TextView(ActivityTimeTable.this);
                            txtSubject.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                            txtSubject.setGravity(Gravity.CENTER_HORIZONTAL);
                            txtSubject.setPadding(5, 5, 5, 5);
                            txtSubject.setTextSize(14);
                        /*Time Lunch from 12 PM to 1 PM*/
                            if (i == 5) {
                                txtSubject.setText("Time Lunch");
                                timetableElement.addView(txtSubject);
                                timetableElement.setBackgroundResource(R.color.bg_lunch);
                            } else {
                                //Log.i("Time Table", "Index: "+indexOfListTimeTable);
                                if(indexOfListTimeTable < jsonArrayTimeTables.length()) {
                                    try {
                                        timetableObject = jsonArrayTimeTables.getJSONObject(indexOfListTimeTable);
                                        //Log.i("Time Table", "JSON Object: "+timetableObject +"--------- Index: "+indexOfListTimeTable);
                                        calendar.setTime(sdf.parse(timetableObject.getJSONObject("plan_for_day").getString("date")));
                                        //If that day is vacation
                                        if(timetableObject.getJSONObject("subject").getString("subjectName").equalsIgnoreCase("Vacation")
                                                && i == (timetableObject.getJSONObject("lesson").getInt("lessonID") - 1)){
                                            for (JSONObject vacationJSONObject : listVacationJSONObject){
                                                String checkDate =((calendar.get(Calendar.MONTH) +1 < 10)?"0":"")+(calendar.get(Calendar.MONTH) +1 )+"-"+((calendar.get(Calendar.DATE) < 10)?"0":"")+calendar.get(Calendar.DATE);
                                                if(checkDate.equals(vacationJSONObject.getString("vacationDayAndMonth"))){
                                                    txtSubject.setText(vacationJSONObject.getString("vacationName"));
                                                    break;
                                                }
                                            }
                                            txtSubject.setTextSize(18);
                                            linearEachDay.addView(txtSubject);
                                            linearEachDay.setGravity(Gravity.CENTER);
                                            linearEachDay.setBackgroundResource(R.color.bg_vacation);
                                            indexOfListTimeTable++;
                                            break;
                                        }
                                    /*int colIndex;
                                    if(rBtnWeekly.isChecked())
                                         colIndex = calendar.get(Calendar.DAY_OF_WEEK) - 2; //SUNDAY is 1, MONDAY is index 2
                                    else
                                        colIndex = calendar.get(Calendar.DATE) - 1;*/
                                        int rowIndex = timetableObject.getJSONObject("lesson").getInt("lessonID") - 1;  //LessonID start from 1
                                        //Log.i("Index", "("+i+","+j+")   "+"("+rowIndex+","+colIndex+")");
                                        if (i == rowIndex){// && j == colIndex) {    // if current timetableElement has subject
                                            txtSubject.setText(timetableObject.getJSONObject("subject").getString("subjectName"));
                                            timetableElement.addView(txtSubject);   //Add Subject Name
                                            try {   // try to get teacherName except for Assembly and Classroom Activities
                                                String teacherName = timetableObject.getJSONObject("teacher").getString("teacherName");
                                                //In order to reduce process, should add teacher if it exist
                                                TextView txtTeacher = new TextView(ActivityTimeTable.this);
                                                txtTeacher.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                                                txtTeacher.setGravity(Gravity.CENTER_HORIZONTAL);
                                                txtTeacher.setPadding(5, 5, 5, 5);
                                                txtTeacher.setTextSize(11);
                                                txtTeacher.setText("(" + teacherName + ")");
                                                timetableElement.addView(txtTeacher);   //Add Teacher Name
                                            } catch (JSONException e) {
                                                Log.i("Time Table", "It's not a subject");
                                            }
                                            timetableElement.setBackgroundResource(bgSubject[timetableObject.getJSONObject("subject").getInt("subjectID") - 1]);
                                            indexOfListTimeTable++; //increase to get next timetableObject
                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                            linearEachDay.addView(timetableElement);
                        }
                        linearTimeTable.addView(linearEachDay);
                    }
                }
            });
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String aVoid) {
            super.onPostExecute(aVoid);
        }
    };


    /*Handle with event when scroll ScrollView and HorizontalView*/
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
            switch (view.getId()) {
                case R.id.horizontalScrollViewDayOfWeek:
                    isTimeTableHorizontalScroll = false;
                    isDayOfWeekHorizontalScroll = true;
                    horizontalScrollViewDayOfWeek.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
                        @Override
                        public void onScrollChanged() {
                            if(!isTimeTableHorizontalScroll && isDayOfWeekHorizontalScroll)
                                horizontalScrollViewTimeTable.smoothScrollTo(horizontalScrollViewDayOfWeek.getScrollX(), horizontalScrollViewDayOfWeek.getScrollY());
                        }
                    });
                    break;
                case R.id.horizontalScrollViewTimeTable:
                    isTimeTableHorizontalScroll = true;
                    isDayOfWeekHorizontalScroll = false;
                    horizontalScrollViewTimeTable.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
                        @Override
                        public void onScrollChanged() {
                            if(!isDayOfWeekHorizontalScroll && isTimeTableHorizontalScroll)
                                horizontalScrollViewDayOfWeek.smoothScrollTo(horizontalScrollViewTimeTable.getScrollX(), horizontalScrollViewDayOfWeek.getScrollY());
                        }
                    });
                    break;
                case R.id.scrollViewLesson:
                    isScrollLesson = true;
                    isScrollTimeTable = false;
                    scrollViewLesson.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
                        @Override
                        public void onScrollChanged() {
                            if(!isScrollTimeTable && isScrollLesson)
                                scrollViewTimeTable.smoothScrollTo(scrollViewLesson.getScrollX(), scrollViewLesson.getScrollY());;
                        }
                    });
                    break;
                case R.id.scrollViewTimeTable:
                    isScrollLesson = false;
                    isScrollTimeTable = true;
                    scrollViewTimeTable.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
                        @Override
                        public void onScrollChanged() {
                            if(!isScrollLesson && isScrollTimeTable)
                                scrollViewLesson.smoothScrollTo(scrollViewTimeTable.getScrollX(), scrollViewTimeTable.getScrollY());;
                        }
                    });
                    break;
            }
        }
        return false;
    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.imgBtnViewTimeTable:
                Log.i("TIME_TABLE", "View Timetable");
                //TODO - implement action for view timetable
                if(rBtnWeekly.isChecked()){
                    linearTimeTable.removeAllViews();
                    timeTableAsyncTask = new TimeTableAsyncTask(ActivityTimeTable.this);
                    timeTableAsyncTask.execute("http://10.0.3.2:8886/timetable?classID=6&date="+timetableChooseTimeWeekly.getSelectedItem().toString());
                }
                else if(rBtnMonthly.isChecked()){
                    linearTimeTable.removeAllViews();
                    timeTableAsyncTask = new TimeTableAsyncTask(ActivityTimeTable.this);
                    timeTableAsyncTask.execute("http://10.0.3.2:8886/timetable?classID=6&month="+timetableChooseTimeMonthly.getSelectedItem().toString());
                }
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedID) {
        if (checkedID == R.id.rBtnWeekly){
            timetableChooseTimeMonthly.setVisibility(View.GONE);
            timetableChooseTimeWeekly.setVisibility(View.VISIBLE);
        }else {
            timetableChooseTimeMonthly.setVisibility(View.VISIBLE);
            timetableChooseTimeWeekly.setVisibility(View.GONE);
            timetableChooseTimeMonthly.setSelection(timeForFullSemester.getIndexOfCurrentMonthInSemester());
        }
    }

    //Change size and color of Spinner when item selected
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
        ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.colorPrimary));
        ((TextView) parent.getChildAt(0)).setTextSize(13);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {}

    @Override
    protected void onDestroy() {
        this.unregisterReceiver(broadcastReceiverTimeTable);
        super.onDestroy();
    }
}