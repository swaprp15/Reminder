package swap.app.calsync;

import java.util.Calendar;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import android.app.IntentService;
import android.content.Intent;

public class BackgroundService extends IntentService {

	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	
	public BackgroundService()
	{
		super("Hi");
	}
	
	public BackgroundService(String name) {
		super(name);
		
		System.out.println("In background service constructor");
		
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		// TODO Auto-generated method stub
		
		// Here create an instance of ScheduledExecutorService and run it every day 7 am
		
		/*
		Calendar c = Calendar.getInstance(); 
		int hourOfTheDay = c.get(Calendar.HOUR_OF_DAY);
	
		int delayInHours = 0;
		
		if(hourOfTheDay <= 7)
		{
			delayInHours = 7 - hourOfTheDay;
		}
		else
		{
			delayInHours = 31 - hourOfTheDay;
		}
		
		CheckForBirthday checkBirthdays = new CheckForBirthday();
		
		// Schedule activity at 7 am daily
		// Can provide customised option and use it as an offset
		scheduler.scheduleAtFixedRate(checkBirthdays, delayInHours, 24, TimeUnit.HOURS);
		*/
		
		System.out.println("In background service onHandleIntent");
		intent.getData();
		
		
		CheckForBirthday checkBirthdays = new CheckForBirthday();
		
		scheduler.scheduleAtFixedRate(checkBirthdays, 10, 10, TimeUnit.SECONDS);
		
	}

}
