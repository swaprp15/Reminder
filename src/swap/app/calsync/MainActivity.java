package swap.app.calsync;

import java.util.ArrayList;
import java.util.List;


import swap.app.calsync.DBHelperContract.FeedEntry;
import android.app.Activity;
import android.app.ExpandableListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.ExpandableListContextMenuInfo;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;



//public class MainActivity extends ExpandableListActivity 
public class MainActivity extends Activity
{
	
	private static SQLiteDatabase db;
	
	//static MyExpandableListAdapter adapter;

	public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    
	static ArrayList<Group> groups = new ArrayList<Group>();

	
	static ExpandableListView listView;
	
	static TextView textViewInListView;
	
	static Activity instance;
	
	PopupWindow popUp;
	LinearLayout layout;
    TextView tv;
    LayoutParams params;
    LinearLayout mainLayout;
    Button but;
    boolean click = true;
	
    public SQLiteDatabase getReadableDaabase()
    {
    	return db;
    }
    
    public void dropTable()
    {
		DBHelper mDbHelper = new DBHelper(getApplicationContext());
		
		SQLiteDatabase db = mDbHelper.getWritableDatabase();
    	
    	db.execSQL(DBHelperContract.SQL_DROP_TABLE);
    }
    
    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		instance = this;
		//dropTable();
		
		// Read from DB
		
		DBHelper mDbHelper = new DBHelper(getApplicationContext());
		
		 db = mDbHelper.getReadableDatabase();

		
		 listView = (ExpandableListView) findViewById(R.id.expandableListView1);
		 

		 //listView.setChoiceMode(choiceMode)
		 
		 //listView.setAdapter(adapter);
		 
		registerForContextMenu(listView);
		 
		 fetchBirthdays();
	
    }
    
    @Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
    	Log.i("", "Click");
        ExpandableListView.ExpandableListContextMenuInfo info = (ExpandableListView.ExpandableListContextMenuInfo) menuInfo;

        int type = ExpandableListView.getPackedPositionType(info.packedPosition);
        int groupPosition = ExpandableListView.getPackedPositionGroup(info.packedPosition);
        int childPosition = ExpandableListView.getPackedPositionChild(info.packedPosition);

        /*
        // Don't Show context menu for groups
        if (type == ExpandableListView.PACKED_POSITION_TYPE_GROUP) {
            menu.setHeaderTitle("Group");
            menu.add(0, 0, 1, "Delete");

            // Show context menu for children
        } else 
        	
        */
        if (type == ExpandableListView.PACKED_POSITION_TYPE_CHILD) {
            //menu.setHeaderTitle("Child");
            menu.add(0, 0, 1, "Delete");
        }
	}

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        ExpandableListView.ExpandableListContextMenuInfo info = (ExpandableListView.ExpandableListContextMenuInfo) item
                .getMenuInfo();

        int type = ExpandableListView.getPackedPositionType(info.packedPosition);
        int groupPosition = ExpandableListView.getPackedPositionGroup(info.packedPosition);
        int childPosition = ExpandableListView.getPackedPositionChild(info.packedPosition);

        /* No need
        if (type == ExpandableListView.PACKED_POSITION_TYPE_GROUP) {
            // do something with parent

        } else 
        */	
        	
        if (type == ExpandableListView.PACKED_POSITION_TYPE_CHILD) {
            // do someting with child
        	
        	//Toast.makeText(this, item.getTitle() + " was selected", Toast.LENGTH_LONG).show();
        	
        	Group group = groups.get(groupPosition);
        	
        	 Child child = groups.get(groupPosition).children.get(childPosition);
        	 
        	//Toast.makeText(this, child.getPersonName() + " " + group.getMonth() + " was long pressed.", Toast.LENGTH_LONG).show();
        	
        	// Delete the entry
        	
        	try
        	{
        		//Toast.makeText(instance, "Trying to delete", Toast.LENGTH_SHORT);
        		
        		db.execSQL(DBHelperContract.getDeleteRowStatement(group.getMonth(), child.getDay(), child.getPersonName()));

        		fetchBirthdays();

        		Toast.makeText(instance, "Deleted!", Toast.LENGTH_SHORT).show();
        	}
        	catch(Exception e)
        	{
        		Log.w("", e.getMessage());
        	}
        	/*
        	TextView childView = (TextView) info.targetView;
        	
        	if(childView != null)
        		Toast.makeText(this, childView.getText() + " was clicked.", Toast.LENGTH_LONG).show();
        	else
        		Toast.makeText(this, " Cant get view", Toast.LENGTH_LONG).show();
        	 */
        }

        return super.onContextItemSelected(item);
    }
    
	public static void fetchBirthdays()
    {
    	try
    	{
    	
    	// Define a projection that specifies which columns from the database
    			// you will actually use after this query.
    			String[] projection = {
    			    FeedEntry.COLUMN_NAME,
    			    FeedEntry.COLUMN_MONTH,
    			    FeedEntry.COLUMN_DAY
    			    };

    			// How you want the results sorted in the resulting Cursor
    			String sortOrder = FeedEntry.COLUMN_MONTH + " ASC, " + FeedEntry.COLUMN_DAY + " ASC";


    			
    			Cursor cursor = db.query(
    			    FeedEntry.TABLE_NAME,  // The table to query
    			    projection,                               // The columns to return
    			    null,                                // The columns for the WHERE clause
    			    null,                            // The values for the WHERE clause
    			    null,                                     // don't group the rows
    			    null,                                     // don't filter by row groups
    			    sortOrder                                 // The sort order
    			    );
    			
    			
    			List<Group> records = new ArrayList<Group>();
    			
    			for(int i = 0; i < 13; i++)
    			{
    				records.add(new Group(i));
    				System.out.println("Added record - " + i);
    			}
    			
    			int month;
    			
    			while(cursor.moveToNext())
    			{
    				month = cursor.getInt(cursor.getColumnIndexOrThrow(FeedEntry.COLUMN_MONTH));
    				
    				// Skipping any mischievous entry in the database which could cause array out of bound exception
    				if(month > 12)
    					continue;
    				
    				/*
    				if(records.get(month) == null)
    				{
    					records.add(month, new ArrayList<String>());
    				}
    				*/
    				
    				int day = cursor.getInt(cursor.getColumnIndexOrThrow(FeedEntry.COLUMN_DAY));
    				
    				String name = cursor.getString(cursor.getColumnIndexOrThrow(FeedEntry.COLUMN_NAME));
    				
    				Group group = records.get(month);
    				
    				Child child = new Child(name, day);
    				
    				group.children.add(child);
    				
    				/*
    				String dayString;
    				
    				if(day == 1 || day == 21 || day == 31)
    					dayString = day + "st";
    				else if (day == 2 || day == 22)
    					dayString = day + "nd";
    				else if(day == 3 || day == 23)
    					dayString = day + "rd";
    				else
    					dayString = day + "th";
    				
    				
    				records.get(month).add(cursor.getString(cursor.getColumnIndexOrThrow(FeedEntry.COLUMN_NAME)) + " " + dayString );
    				*/
    				System.out.println("From DB - Name - " + cursor.getString(cursor.getColumnIndexOrThrow(FeedEntry.COLUMN_NAME)));
    				System.out.println("From DB - Date - " + cursor.getInt(cursor.getColumnIndexOrThrow(FeedEntry.COLUMN_DAY)) + "/" + cursor.getInt(cursor.getColumnIndexOrThrow(FeedEntry.COLUMN_MONTH)));
    			}
    			
    			/*
    			List<ArrayList<String>> records = new ArrayList<ArrayList<String>>();
    			
    			for(int i = 0; i < 13; i++)
    			{
    				records.add(new ArrayList<String>());
    				
    				System.out.println("Added record - " + i);
    			}
    			
    			System.out.println("Initialised...");
    			
    			int month;
    			
    			while(cursor.moveToNext())
    			{
    				month = cursor.getInt(cursor.getColumnIndexOrThrow(FeedEntry.COLUMN_MONTH));
    				
    				// Skipping any mischievous entry in the database which could cause array out of bound exception
    				if(month > 12)
    					continue;
    				
    				if(records.get(month) == null)
    				{
    					records.add(month, new ArrayList<String>());
    				}
    				
    				int day = cursor.getInt(cursor.getColumnIndexOrThrow(FeedEntry.COLUMN_DAY));
    				
    				String dayString;
    				
    				if(day == 1 || day == 21 || day == 31)
    					dayString = day + "st";
    				else if (day == 2 || day == 22)
    					dayString = day + "nd";
    				else if(day == 3 || day == 23)
    					dayString = day + "rd";
    				else
    					dayString = day + "th";
    				
    				
    				records.get(month).add(cursor.getString(cursor.getColumnIndexOrThrow(FeedEntry.COLUMN_NAME)) + " " + dayString );
    				
    				System.out.println("From DB - Name - " + cursor.getString(cursor.getColumnIndexOrThrow(FeedEntry.COLUMN_NAME)));
    				System.out.println("From DB - Date - " + cursor.getInt(cursor.getColumnIndexOrThrow(FeedEntry.COLUMN_DAY)) + "/" + cursor.getInt(cursor.getColumnIndexOrThrow(FeedEntry.COLUMN_MONTH)));
    			}
    			*/
    			
    			
    			
    			//String [] data = records.toArray(new String[records.size()]);
    			
    			int count = 0;
    			
    			
    			// Used array list intead os sparse so clear earlier
    			groups.clear();
    			
    			// Now put the above groups only if it has at least one children
    			
    			for(int i = 1; i < 13; i++)
    			{
    				if(records.get(i).children.size() > 0)
    					groups.add(records.get(i));
    			}
    			
    			/*
    			for(int i = 1; i < 13; i++)
    			{
    				System.out.println("in loop i=" + i);
    				
    				Group group = new Group(i);
    				
    				System.out.println("Month - " + group.getMonthName());
    				
    		          for (int j = 0; j < records.get(i).size(); j++) {
    		        	  System.out.println("In loop j = " + j);
    		            group.children.add(records.get(i).get(j));
    		          }
    		          
    		          if(records.get(i).size() > 0)
    		        	  //groups.append(count++, group);
    		        	  groups.add(group);
    			}
    			*/
    		
    			System.out.println("Filled Groups");
    			
    			//SimpleExpandableListAdapter adapter = new SimpleExpandableListAdapter(context, groupData, expandedGroupLayout, collapsedGroupLayout, groupFrom, groupTo, childData, childLayout, childFrom, childTo)
    			
    			
    			//createData();
    			// * 
    			// * With a single text view for child
    		    
    			MyExpandableListAdapter adapter = new MyExpandableListAdapter(instance,
    		        groups);
    		    
    			listView.setAdapter(adapter);
    			
    		    /*
    			listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener(){

					@Override
					public boolean onChildClick(ExpandableListView parent,
							View view, int groupPosition, int childPosition, long id) {
						// TODO Auto-generated method stub
						
						Toast.makeText(instance, "log pressed",Toast.LENGTH_LONG).show();
							
						return false;
					}
    				
    			});
    		    */
    			
    			/*
    			setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data));
    			
    			ListView listView = getListView();
    			listView.setTextFilterEnabled(true);
    			*/
    			
    		    
    		    // To test creating  service here
    		    // Remove from here and do at the device startup
    		    // http://stackoverflow.com/questions/2974276/run-my-application-in-background-when-i-start-device-power-on-in-android
    		    
    		    /* Not needed anymore
    		    
    		    Intent mServiceIntent = new Intent(this, BackgroundService.class);
    		    mServiceIntent.setData(Uri.parse("Dummy"));
    		    // Make db helper parceable
    		    //mServiceIntent.putExtra("sqliteDatabase", db);
    		    this.startService(mServiceIntent);
    			*/
    		    
    	}
    	catch(Exception e)
    	{
    		Toast.makeText(instance, "Failed to fetch birthdays", Toast.LENGTH_LONG).show();
    	}
    }
    
    /*
    public void createData() {
        for (int j = 0; j < 5; j++) {
          Group group = new Group("Test " + j);
          for (int i = 0; i < 5; i++) {
            group.children.add("Sub Item" + i);
          }
          //groups.append(j, group);
          groups.add(group);
        }
      }
      */

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_activity_actions, menu);
		return  super.onCreateOptionsMenu(menu);
	}
	
	public void sendMessage(View view) {
	    // Do something in response to button
		
		Intent intent = new Intent(this, DisplayMessageActivity.class);
		//ListView listView = (ListView) findViewById(R.id.);
		
//		String message = editText.getText().toString();
//		intent.putExtra(EXTRA_MESSAGE, message);
//		
//		startActivity(intent);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.action_add:
	            addBirthday();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	public void addBirthday()
	{
		Intent intent = new Intent(this, AddBirthdayActivity.class);
		startActivityForResult(intent, 1);

		/*
        popUp = new PopupWindow(this);
        layout = new LinearLayout(this);
        mainLayout = new LinearLayout(this);
        tv = new TextView(this);
        but = new Button(this);
        but.setText("Click Me");
        but.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                if (click) {
                    popUp.showAtLocation(mainLayout, Gravity.BOTTOM, 10, 10);
                    popUp.update(50, 50, 300, 80);
                    click = false;
                } else {
                    popUp.dismiss();
                    click = true;
                }
            }

        });
        params = new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        layout.setOrientation(LinearLayout.VERTICAL);
        tv.setText("Hi this is a sample text for popup window");
        layout.addView(tv, params);
        popUp.setContentView(layout);
        // popUp.showAtLocation(layout, Gravity.BOTTOM, 10, 10);
        mainLayout.addView(but, params);
        setContentView(mainLayout);
		*/
	
	}

}
