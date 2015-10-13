package com.venkat.SmartR;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;


public class RemindEditActivity extends Activity 
{
	private Button mDateButton;
	private Button mTimeButton;
	private static final int DATE_PICKER_DIALOG = 0;
	private static final int TIME_PICKER_DIALOG = 1;
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String TIME_FORMAT = "kk:mm";
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd kk:mm:ss";
    private Calendar mCalendar;
    private static final int ACTIVITY_CREATE = 0;
    private dataBase mDbHelper;
    
    private EditText mTitleText;
    private EditText mBodyText;
    
	@Override
    public void onCreate(Bundle savedInstanceState) 
	{
        super.onCreate(savedInstanceState);
        mDbHelper = new dataBase(this);
        setContentView(R.layout.edit_activity);
        mDateButton = (Button) findViewById(R.id.date);
        mTimeButton = (Button) findViewById(R.id.time);
        mCalendar = Calendar.getInstance();
        
        mTitleText = (EditText) findViewById(R.id.editTitle);
        mBodyText = (EditText) findViewById(R.id.editInfo);
        
        registerButtonListenersAndSetDefaultText();
    }
	
	@Override
	protected Dialog onCreateDialog(int id) 
	{ 
		switch(id) {
			case DATE_PICKER_DIALOG: 
				return showDatePicker();
			case TIME_PICKER_DIALOG:
				return showTimePicker();
		}
		return super.onCreateDialog(id);
	}
	
	private DatePickerDialog showDatePicker()
	{ 
		DatePickerDialog datePicker = new DatePickerDialog(RemindEditActivity.this,new DatePickerDialog.OnDateSetListener() { 
			public void onDateSet(DatePicker view, int year, int monthOfYear,int dayOfMonth) 
			{
				mCalendar.set(Calendar.YEAR, year); 
				mCalendar.set(Calendar.MONTH, monthOfYear);
				mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth); 
				updateDateButtonText(); 
			}
		}, mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH),mCalendar.get(Calendar.DAY_OF_MONTH)); 
		return datePicker; 
	}
	
	private void updateDateButtonText() 
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT); 
		String dateForButton = dateFormat.format(mCalendar.getTime()); 
		mDateButton.setText(dateForButton); 
	}
	
	private void updateTimeButtonText() 
	{
		SimpleDateFormat timeFormat = new SimpleDateFormat(TIME_FORMAT); 
		String timeForButton = timeFormat.format(mCalendar.getTime()); 
		mTimeButton.setText(timeForButton); 
		
	}
	
	private TimePickerDialog showTimePicker() 
	{
		TimePickerDialog timePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() 
		{ 
			public void onTimeSet(TimePicker view, int hourOfDay, int minute)
			{ 
				mCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay); 
				mCalendar.set(Calendar.MINUTE, minute); 
				updateTimeButtonText(); 
			}
		}, mCalendar.get(Calendar.HOUR_OF_DAY),mCalendar.get(Calendar.MINUTE), true); 
		return timePicker;
		}
	
	private void registerButtonListenersAndSetDefaultText() 
	{
		mDateButton.setOnClickListener(new View.OnClickListener() 
		{
			public void onClick(View v) 
			{ 
				showDialog(DATE_PICKER_DIALOG);
			}
			});
		mTimeButton.setOnClickListener(new View.OnClickListener() 
		{
			
			public void onClick(View v) 
			{
				showDialog(TIME_PICKER_DIALOG);
			}
			}); 
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {
    	super.onCreateOptionsMenu(menu);
        MenuInflater mi = getMenuInflater();
        mi.inflate(R.menu.add_rem, menu);
        return true;
    }
    
    private void createReminder() 
    {
    	Intent i = new Intent(this, RemindEditActivity.class);
    	startActivityForResult(i, ACTIVITY_CREATE);
    }
    
    private void getBack() 
    {
    	Intent i = new Intent(this, WelcomeActivity.class);
    	startActivityForResult(i, ACTIVITY_CREATE);
    }
    
    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) 
    { 
    	switch(item.getItemId()) 
    	{
    		case R.id.addContact:
    				saveState(); 
    				setResult(RESULT_OK); 
    				Toast.makeText(RemindEditActivity.this,getString(R.string.task_saved_message),Toast.LENGTH_SHORT).show();
    				finish();
    				return true;
    				
    		case R.id.cancelAdd:
					getBack(); 
					return true; 
    	}
    	return super.onMenuItemSelected(featureId, item);
    }
    
    private void saveState() {
    	String title = mTitleText.getText().toString(); 
    	String body = mBodyText.getText().toString(); 
    	SimpleDateFormat dateTimeFormat = new SimpleDateFormat(DATE_TIME_FORMAT); 
    	String reminderDateTime = dateTimeFormat.format(mCalendar.getTime()); 
    	long id = mDbHelper.createReminder(title, body, reminderDateTime);
    }
}
