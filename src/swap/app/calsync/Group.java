package swap.app.calsync;

import java.util.ArrayList;
import java.util.List;

public class Group {

	  public String monthName;
	  public final List<String> children = new ArrayList<String>();

	  public Group(String string) {
	    this.monthName = string;
	  }

	} 
