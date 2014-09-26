package swap.app.calsync;

import java.util.ArrayList;
import java.util.List;

class Constatnts
{
	static String monthsArr[] = {"", "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
	
}

public class Group {

	  private String monthName;
	  private int month;
	  
	  public String getMonthName() 
	  {
		  return monthName;
	  }

	  public int getMonth() 
	  {
		  return month;
	  }

	  public final List<Child> children = new ArrayList<Child>();

	  // starts from 1
	  public Group(int month) 
	  {
		  this.month = month;
		  this.monthName = Constatnts.monthsArr[month];
	  }
	} 

class Child
{
	String personName;
	int day;
	
	public Child(String name, int day)
	{
		this.personName = name;
		this.day = day;
	}

	public String getPersonName() {
		return personName;
	}

	public int getDay() {
		return day;
	}
	
	private String getFormattedDayString(int day)
	{
		String dayString;
		
		if(day == 1 || day == 21 || day == 31)
			dayString = day + "st";
		else if (day == 2 || day == 22)
			dayString = day + "nd";
		else if(day == 3 || day == 23)
			dayString = day + "rd";
		else
			dayString = day + "th";
		
		return dayString;
	}
	
	public String getFormattedDayString()
	{
		return getFormattedDayString(day);
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return personName + "  " + getFormattedDayString(day);
	}
	
	
	
	
}