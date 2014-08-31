package swap.app.calsync;

import java.util.Random;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

public class NotificationBuilder {

	private MyApp instance;
	
	static int mId = 0;
	
	public NotificationBuilder() {
		// TODO Auto-generated constructor stub
	
		instance = MyApp.getInstance();
	}
	
	@SuppressLint("NewApi")
	public void BuildNotification(String title, String text)
	{

		NotificationCompat.Builder mBuilder =
		        new NotificationCompat.Builder(instance.getApplicationContext())
		        .setSmallIcon(R.drawable.notification_icon)
		        .setContentTitle(title)
		        .setContentText(text);
		// Creates an explicit intent for an Activity in your app
		Intent resultIntent = new Intent(instance.getApplicationContext(), MainActivity.class);


		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
		{
			// The stack builder object will contain an artificial back stack for the
			// started Activity.
			// This ensures that navigating backward from the Activity leads out of
			// your application to the Home screen.
			TaskStackBuilder stackBuilder = TaskStackBuilder.create(instance.getApplicationContext());
			// Adds the back stack for the Intent (but not the Intent itself)
			stackBuilder.addParentStack(MainActivity.class);
			// Adds the Intent that starts the Activity to the top of the stack
			stackBuilder.addNextIntent(resultIntent);
			PendingIntent resultPendingIntent =
			        stackBuilder.getPendingIntent(
			            0,
			            PendingIntent.FLAG_UPDATE_CURRENT
			        );
			mBuilder.setContentIntent(resultPendingIntent);
		
		}
		else
		{
			Random generator = new Random();
			PendingIntent pendingIntent = PendingIntent.getActivity(instance.getApplicationContext(), generator.nextInt(), resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
			
			mBuilder.setContentIntent(pendingIntent);
		}
		NotificationManager mNotificationManager =
		    (NotificationManager) instance.getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
		// mId allows you to update the notification later on.
		mNotificationManager.notify(++mId, mBuilder.build());

	}
		
}
