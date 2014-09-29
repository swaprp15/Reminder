package swap.app.calsync;

import java.io.Console;
import java.net.CacheRequest;
import java.util.Calendar;

import swap.app.calsync.DBHelperContract.FeedEntry;

import android.app.Application;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

public class CheckForBirthday extends Application implements Runnable {

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		try
		{
			checkAndNotifyBirthday();
		}
		catch(Exception e)
		{
			Log.e("", e.getMessage());
		}
	}
	
	public void checkAndNotifyBirthday()
	{
		
		try
		{
		
			System.out.println("In check for birthday");
			
			Calendar c = Calendar.getInstance(); 
			
			System.out.println(c.get(Calendar.SECOND));
			
			
			int month = c.get(Calendar.MONTH);
			int day = c.get(Calendar.DATE);
			month++;
		
		//Toast.makeText(MyApp.getInstance().getApplicationContext(), "Checking for " + day + "/" + month , Toast.LENGTH_LONG).show(); // For example
        
		
		/*
		int month = 1;
		int day = 1;
		*/
		// Here check for any birthdays today and give notifications
		
		
		
			DBHelper mDbHelper = new DBHelper(MyApp.getInstance().getApplicationContext());
			
			SQLiteDatabase db = mDbHelper.getReadableDatabase();
	
			// Define a projection that specifies which columns from the database
			// you will actually use after this query.
			String[] projection = {
			    FeedEntry.COLUMN_NAME,
			    FeedEntry.COLUMN_MONTH,
			    FeedEntry.COLUMN_DAY
			    };
	
			String whereCols = FeedEntry.COLUMN_MONTH + " == ?" + " AND " +  FeedEntry.COLUMN_DAY + " == ?";
			String[] whereValues = { Integer.toString(month), Integer.toString(day) };
			
			//Toast.makeText(MyApp.getInstance().getApplicationContext(), "Before query" , Toast.LENGTH_LONG).show(); // For example
			
			
			String sortOrder = FeedEntry.COLUMN_NAME;
			
			Cursor cursor = db.query(
				    FeedEntry.TABLE_NAME,  // The table to query
				    projection,                               // The columns to return
				    whereCols,                                // The columns for the WHERE clause
				    whereValues,                            // The values for the WHERE clause
				    null,                                     // don't group the rows
				    null,                                     // don't filter by row groups
				    sortOrder                                 // The sort order
				    );
			
			//Toast.makeText(MyApp.getInstance().getApplicationContext(), "Before instantiating NB" , Toast.LENGTH_LONG).show(); // For example
			
			
			NotificationBuilder builder = new NotificationBuilder();
			
			//Toast.makeText(MyApp.getInstance().getApplicationContext(), "Executed" , Toast.LENGTH_LONG).show(); // For example
			
			
			while(cursor.moveToNext())
			{
				System.out.println(cursor.getString(cursor.getColumnIndexOrThrow(FeedEntry.COLUMN_NAME)));
				
				//Toast.makeText(MyApp.getInstance().getApplicationContext(), cursor.getString(cursor.getColumnIndexOrThrow(FeedEntry.COLUMN_NAME)) + " has Birthday" , Toast.LENGTH_LONG).show(); // For example
				
				String title = cursor.getString(cursor.getColumnIndexOrThrow(FeedEntry.COLUMN_NAME));
				String text = "It's " + title + "'s birthday today!";
				
				builder.BuildNotification(title, text);
			}
			
			cursor.close();
			mDbHelper.close();
		}
		catch(Exception e)
		{
			//Toast.makeText(MyApp.getInstance().getApplicationContext(),  "Error!" , Toast.LENGTH_LONG).show(); // For example
			Log.e("", e.getMessage());
		}
		finally
		{
			
		}
	}

}
