package swap.app.calsync;

import java.lang.reflect.Field;
import java.util.Calendar;

import swap.app.calsync.DBHelperContract.FeedEntry;
import android.R.bool;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;


class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the current date as the default date in the picker
		final Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);
		
		// Didn't work
		try {
	        Field f[] = this.getClass().getDeclaredFields();
	        for (Field field : f) {
	            if (field.getName().equals("mYearPicker") || field.getName().equals("mYearSpinner")) {
	                field.setAccessible(true);
	                Object yearPicker = new Object();
	                yearPicker = field.get(this);
	                ((View) yearPicker).setVisibility(View.GONE);
	            }
	        }
	    } 
	    catch (SecurityException e) {
	        Log.d("ERROR", e.getMessage());
	    } 
	    catch (IllegalArgumentException e) {
	        Log.d("ERROR", e.getMessage());
	    } 
	    catch (IllegalAccessException e) {
	        Log.d("ERROR", e.getMessage());
	    }
		
		// Create a new instance of DatePickerDialog and return it
		return new DatePickerDialog(getActivity(), this, year, month, day);
	}
	
	public void onDateSet(DatePicker view, int year, int month, int day) {
	// Do something with the date chosen by the user
		
		Activity activity = getActivity();
		
		if(activity == null)
			return;
		
		EditText editDate = (EditText) activity.findViewById(R.id.editDate);
		
		editDate.setText(day + "/" + month + "/" + year);
		
	}
}

public class AddBirthdayActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_birthday);
		// Show the Up button in the action bar.
		setupActionBar();
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_birthday, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void openDatePicker(View view)
	{
		DialogFragment newFragment = new DatePickerFragment();
	    newFragment.show(getSupportFragmentManager(), "datePicker");
	}
	
	
	
	public void addBirthday(View view)
	{
		DBHelper mDbHelper = new DBHelper(getApplicationContext());
		
		
		
		// Gets the data repository in write mode
		SQLiteDatabase db = mDbHelper.getWritableDatabase();

		System.out.println("Created database");
		
		EditText editDate = (EditText) findViewById(R.id.editDate);
		EditText editName = (EditText) findViewById(R.id.editName);
		
		DayMonth dayMonth = new DayMonth();
		
		GetDayMonth(editDate.getText().toString(), dayMonth);
		
		// Create a new map of values, where column names are the keys
		ContentValues values = new ContentValues();
		values.put(FeedEntry.COLUMN_NAME, editName.getText().toString());
		values.put(FeedEntry.COLUMN_MONTH, dayMonth.getMonth());
		values.put(FeedEntry.COLUMN_DAY, dayMonth.getDay());

		// Insert the new row, returning the primary key value of the new row
		long newRowId;
		newRowId = db.insert(
		         FeedEntry.TABLE_NAME,
		         "null",
		         values);
		
		System.out.println("Added a birthday for " + editName.toString());
	}

	
	public boolean GetDayMonth(String date, DayMonth obj)
	{
		String [] parts = date.split("[//|-]");
		
		if(parts.length < 2)
		{
			return false;
		}
		
		obj.setDay(Integer.parseInt(parts[0]));
		obj.setMonth(Integer.parseInt(parts[1]));
	
		return true;
	}
}

class DayMonth
{
	int day, month;

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}
	
}
