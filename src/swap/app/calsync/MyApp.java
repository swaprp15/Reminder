package swap.app.calsync;

import android.app.Application;

public class MyApp extends Application{

	private static MyApp instance;

	public static MyApp getInstance() {
		return instance;
	}

	public static void setInstance(MyApp instance) {
		MyApp.instance = instance;
	}
	
	@Override
	public void onCreate()
	{
		instance = this;
		super.onCreate();
	}
}
