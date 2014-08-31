package swap.app.calsync;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DBHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "CalcSync.db";
    
    SQLiteDatabase db;
    Context applicationContext;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        
        db = getWritableDatabase();
        
        applicationContext = context;
    }
    
    // This method is called only once when the database is not yet created.
    // So use this method to set the alarm which will normally be done on BOOT_COMPLETE
    public void onCreate(SQLiteDatabase db) {
    	
    	System.out.println("onCreate was called...");
    	
        db.execSQL(DBHelperContract.SQL_CREATE_ENTRIES);
        
        System.out.println("onCreate was completed successfully...");
        
        // Set alarm
        try
        {
        	Alarm alarm = new Alarm();
        	
        	alarm.SetAlarm(MainActivity.instance.getApplicationContext());
        }
        catch(Exception e)
        {
        	Toast.makeText(applicationContext, "Unable to set alarm", Toast.LENGTH_LONG).show();
        }
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(DBHelperContract.SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
