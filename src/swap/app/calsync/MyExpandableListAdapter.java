package swap.app.calsync;

import java.lang.reflect.Array;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.util.SparseArray;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;
import android.widget.Toast;

public class MyExpandableListAdapter extends BaseExpandableListAdapter {

    @Override
    public boolean areAllItemsEnabled()
    {
        return true;
    }

    private Context context;

    private ArrayList<Group> groups;

    //private ArrayList<ArrayList<Vehicle>> children;

    public MyExpandableListAdapter(Context context, ArrayList<Group> groups
            /*ArrayList<ArrayList<Vehicle>> children*/) {
        this.context = context;
        this.groups = groups;
        //this.children = children;
    }

    /**
     * A general add method, that allows you to add a Vehicle to this list
     * 
     * Depending on if the category opf the vehicle is present or not,
     * the corresponding item will either be added to an existing group if it 
     * exists, else the group will be created and then the item will be added
     * @param vehicle
     */

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return groups.get(groupPosition).children.get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }
    
    // Return a child view. You can load your custom layout here.
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
            View convertView, ViewGroup parent) {
    	
        Child children = (Child) getChild(groupPosition, childPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.child_layout, null);
        }
        TextView tvName = (TextView) convertView.findViewById(R.id.tvChild);
        tvName.setText(children.getPersonName());
        
        TextView tvDay = (TextView) convertView.findViewById(R.id.tvChildDay);
        tvDay.setText(children.getFormattedDayString());
        

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return groups.get(groupPosition).children.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groups.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return groups.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    // Return a group view. You can load your custom layout here.
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView,
            ViewGroup parent) {
    	Group group = (Group) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.listrow_group, null);
        }
        
        //Group g = groups.get(groupPosition);
        
        ((TextView) convertView).setText(group.getMonthName());
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int arg0, int arg1) {
        return true;
    }

}

/*
public class MyExpandableListAdapter extends BaseExpandableListAdapter {

	  private final SparseArray<Group> groups;
	  public LayoutInflater inflater;
	  public Activity activity;

	  public MyExpandableListAdapter(Activity act, SparseArray<Group> groups) {
	    activity = act;
	    this.groups = groups;
	    inflater = act.getLayoutInflater();
	  }

	  @Override
	  public Object getChild(int groupPosition, int childPosition) {
	    return groups.get(groupPosition).children.get(childPosition);
	  }

	  @Override
	  public long getChildId(int groupPosition, int childPosition) {
	    return childPosition;
	  }

	  @Override
	  public View getChildView(int groupPosition, final int childPosition,
	      boolean isLastChild, View convertView, ViewGroup parent) {
	    final String children = (String) getChild(groupPosition, childPosition);
	    TextView text = null;
	    if (convertView == null) {
	      convertView = inflater.inflate(R.layout.listrow_details, null);
	    }
	    text = (TextView) convertView.findViewById(R.id.textView1);
	    text.setText(children);
	    
	    //convertView.setFocusableInTouchMode(false);
	    /*
	    convertView.setOnClickListener(new OnClickListener() {
	      @Override
	      public void onClick(View v) {
	        Toast.makeText(activity, children,
	            Toast.LENGTH_SHORT).show();
	      }
	      
	      
	      
	    });
	    */
	    //convertView.createContextMenu(menu);
	    
//	    @Override
//	      public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo info)
//	      {
//	    	  
//	      }
	    
	    /*
	    ArrayList<View> touchable = convertView.getTouchables();
	    
	    for (View v : touchable)
	    {
	    	v.setEnabled(false);
	    }
	    */
	    
	    
	    /*
	    return convertView;
	  }
	  
	  

	  @Override
	  public int getChildrenCount(int groupPosition) {
	    return groups.get(groupPosition).children.size();
	  }

	  @Override
	  public Object getGroup(int groupPosition) {
	    return groups.get(groupPosition);
	  }

	  @Override
	  public int getGroupCount() {
	    return groups.size();
	  }

	  @Override
	  public void onGroupCollapsed(int groupPosition) {
	    super.onGroupCollapsed(groupPosition);
	  }

	  @Override
	  public void onGroupExpanded(int groupPosition) {
	    super.onGroupExpanded(groupPosition);
	  }

	  @Override
	  public long getGroupId(int groupPosition) {
	    return groupPosition;
	  }

	  @Override
	  public View getGroupView(int groupPosition, boolean isExpanded,
	      View convertView, ViewGroup parent) {
	    if (convertView == null) {
	      convertView = inflater.inflate(R.layout.listrow_group, null);
	    }
	    Group group = (Group) getGroup(groupPosition);
	    ((CheckedTextView) convertView).setText(group.monthName);
	    ((CheckedTextView) convertView).setChecked(isExpanded);
	    
	    
	    return convertView;
	  }

	  @Override
	  public boolean hasStableIds() {
	    return true;
	  }

	  @Override
	  public boolean isChildSelectable(int groupPosition, int childPosition) {
	    return true;
	  }
	} 
	*/