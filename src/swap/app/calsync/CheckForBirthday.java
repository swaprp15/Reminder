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

public class CheckForBirthday extends Application implements Runnable {

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		Calendar c = Calendar.getInstance(); 
		
		System.out.println(c.get(Calendar.SECOND));
		
		/*
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DATE);
		*/
		
		int month = 1;
		int day = 1;
		
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
		
		NotificationBuilder builder = new NotificationBuilder();
		
		while(cursor.moveToNext())
		{
			System.out.println(cursor.getString(cursor.getColumnIndexOrThrow(FeedEntry.COLUMN_NAME)));
			
			String title = cursor.getString(cursor.getColumnIndexOrThrow(FeedEntry.COLUMN_NAME));
			String text = cursor.getString(cursor.getColumnIndexOrThrow(FeedEntry.COLUMN_NAME)) + " has birthday today!";
			
			builder.BuildNotification(title, text);
		}
		
	}

}
