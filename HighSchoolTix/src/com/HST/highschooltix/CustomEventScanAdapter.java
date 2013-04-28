package com.HST.highschooltix;
import java.util.ArrayList;

import java.util.HashMap;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

public class CustomEventScanAdapter extends BaseAdapter 
{

EventList_Activity EventList = new EventList_Activity();
	private static ArrayList<HashMap<String,String>> ArrayList;
	private LayoutInflater myiInflater;
	Context cc;

	
	HashMap<String, String> hashMap;
	public CustomEventScanAdapter(Context context,ArrayList<HashMap<String,String>> results)
	{
		ArrayList = results;
		cc=context;
	    myiInflater = LayoutInflater.from(context);
	    
	     
	}

	
	public int getCount() {
		return ArrayList.size();
	
	}

	
	public Object getItem(int position) {
		return ArrayList.get(position);
	}

	
	public long getItemId(int position) {
		return position;
	}

	
	public View getView(int position, View convertView, ViewGroup parent) {
		 final ViewHolder holder;
		
		if(convertView==null)
		{
			myiInflater = (LayoutInflater) cc.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = myiInflater.inflate(R.layout.eventscanbg, null); 
			
			 holder = new ViewHolder();
			// holder.layout=(LinearLayout) convertView.findViewById(R.id.relativerow);
			 holder.text_name = (TextView)convertView.findViewById(R.id.name);
			 holder.address = (TextView)convertView.findViewById(R.id.date_address);
			
			 
			

		}	
		else
		 {
			 holder = (ViewHolder)convertView.getTag();
		 }
		 convertView.setTag(holder);
		 hashMap=new HashMap<String, String>();
		hashMap= ArrayList.get(position);
	
		 
		  holder.text_name.setText(hashMap.get(EventList_Activity.TITLE).toString());
		  holder.address.setText(hashMap.get(EventList_Activity.DATE).toString()+"  "+hashMap.get(EventList_Activity.ADDRESS).toString());
		 

		
		return convertView;
	}
	static class ViewHolder
	{
		
		 TextView text_name;
		 TextView address;
		 TextView willwoTextView;

	}
 
	
	  
	
}
