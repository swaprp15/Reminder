package swap.app.calsync;

import java.util.Calendar;
import java.util.GregorianCalendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.util.Log;
import android.widget.Toast;

public class Alarm extends BroadcastReceiver 
{    
     @Override
     public void onReceive(Context context, Intent intent) 
     {   
    	 try
    	 {
	         PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
	         PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "");
	         wl.acquire();
	
	         // Put here YOUR code.
	         //Toast.makeText(context, "Alarm !!!!!!!!!!", Toast.LENGTH_LONG).show(); // For example
	         
	         CheckForBirthday checkBirthday = new CheckForBirthday();
	         checkBirthday.checkAndNotifyBirthday();
	
	         wl.release();
    	 }
    	 catch(Exception e)
    	 {
    		 Log.e("", e.getMessage());
    	 }
    	 
     }

 public void SetAlarm(Context context)
 {
	 try
	 {
	     AlarmManager am=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
	     Intent i = new Intent(context, Alarm.class);
	     PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, 0);
	     
	     System.currentTimeMillis();
	     
	     Calendar c = Calendar.getInstance();
	     
	     // Trigger at the next midnight
	     c.add(Calendar.DAY_OF_MONTH, 1);
	     c.set(Calendar.HOUR_OF_DAY, 0);
	     c.set(Calendar.MINUTE, 0);
	     c.set(Calendar.SECOND, 0);
	     c.set(Calendar.MILLISECOND, 0);
	     
	     // every 24 hours // 1000 * 60 * 60 * 24
	     am.setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), 1000 * 60 * 60 * 24 , pi); // Millisec * Second * Minute
	     
	     //am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 1000 * 20, pi); // Millisec * Second * Minute
	 }
	 catch(Exception e)
	 {
		 Log.e("", e.getMessage());
	 }
 }

 public void CancelAlarm(Context context)
 {
	 try
	 {
	     Intent intent = new Intent(context, Alarm.class);
	     PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
	     AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
	     alarmManager.cancel(sender);
	 }
	 catch(Exception e)
	 {
		 Log.e("", e.getMessage());
	 }
 }
}
