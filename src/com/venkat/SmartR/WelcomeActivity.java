package com.venkat.SmartR;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class WelcomeActivity extends ListActivity 
{
	private dataBase mDbHelper;
	
	private static final int ACTIVITY_CREATE=0;
	private static final int ACTIVITY_EDIT=1;
	@Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welome_activity);
    }
    
	@Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {
    	super.onCreateOptionsMenu(menu);
        MenuInflater mi = getMenuInflater();
        mi.inflate(R.menu.rem_list, menu);
        return true;
    }
    
    private void createReminder() 
    {
    	Intent i = new Intent(this, RemindEditActivity.class);
    	startActivityForResult(i, ACTIVITY_CREATE);
    }
    
    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) 
    { 
    	switch(item.getItemId()) 
    	{
    		case R.id.menu_insert:
    				createReminder(); 
    				return true; 
    	}
    	return super.onMenuItemSelected(featureId, item);
    }
    
    private void fillData() 
    {
    	Cursor remindersCursor = mDbHelper.fetchAllReminders(); 
    	startManagingCursor(remindersCursor); 
    	String[] from = new String[]{dataBase.KEY_TITLE}; 
    	int[] to = new int[]{R.id.text1}; 
    	SimpleCursorAdapter reminders =	new SimpleCursorAdapter(this, R.layout.row_elem,remindersCursor, from, to); 
    	setListAdapter(reminders); 
    }
    	
    private void setListAdapter(SimpleCursorAdapter reminders) 
    {
		// TODO Auto-generated method stub
	}

	protected void onListItemClick(ListView l, View v, int position, long id)
    {
    	//super.onListItemClick(l, v, position, id);
    	Intent i = new Intent(this, dataBase.class);
    	i.putExtra(dataBase.KEY_ROWID, id); 
    	startActivityForResult(i,ACTIVITY_EDIT);
    }
    	
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
    	super.onActivityResult(requestCode, resultCode, intent);
    	fillData(); 
    }
    	
    @Override
    public boolean onContextItemSelected(MenuItem item)
    { 
    	switch(item.getItemId())
    	{
    		
    	}
    	return super.onContextItemSelected(item);
    }
    
}