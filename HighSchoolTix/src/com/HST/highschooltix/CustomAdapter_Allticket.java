package com.HST.highschooltix;

import java.util.ArrayList;





import java.util.HashMap;







import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomAdapter_Allticket extends BaseAdapter
{

	private static ArrayList<HashMap<String,String>> ArrayList;
	private LayoutInflater myiInflater;
	Context cc;
	
	HashMap<String, String> hashMap;
	public CustomAdapter_Allticket(Context context,ArrayList<HashMap<String,String>> results)
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
			convertView = myiInflater.inflate(R.layout.allticketbg, null); 
			
			 holder = new ViewHolder();
			// holder.layout=(LinearLayout) convertView.findViewById(R.id.relativerow);
			 holder.text_number = (TextView)convertView.findViewById(R.id.number);
			// holder.scan_statusTextView = (TextView)convertView.findViewById(R.id.scan_status);
			// holder.scan_dateTextView = (TextView)convertView.findViewById(R.id.scan_date);
			// holder.rightImageView=(ImageView) convertView.findViewById(R.id.right);
			 holder.kindTextView=(TextView) convertView.findViewById(R.id.kind);
			 
			
			 
			

		}	
		else
		 {
			 holder = (ViewHolder)convertView.getTag();
		 }
		 convertView.setTag(holder);
		 hashMap=new HashMap<String, String>();
		hashMap= ArrayList.get(position);
	
		 
		  holder.text_number.setText(hashMap.get(List_Activity.NUMBER).toString());
		 // holder.scan_statusTextView.setText(hashMap.get(List_Activity.SCANED).toString());
		  holder.kindTextView.setText(hashMap.get(List_Activity.KIND).toString());
		 // String date=hashMap.get(List_Activity.SCAN_DATE).toString();
//		  
//		  if(date.equalsIgnoreCase("null"))
//		  {
//			  holder.scan_dateTextView.setText("");
//			  holder.rightImageView.setVisibility(View.INVISIBLE);
//		  }
//		  else
//		  {
//		  holder.scan_dateTextView.setText(hashMap.get(List_Activity.SCAN_DATE).toString());
//		  holder.rightImageView.setVisibility(View.VISIBLE);
//		  }

		
		return convertView;
	}
	static class ViewHolder
	{
		
		 TextView text_number;
		// TextView scan_statusTextView;
		// TextView scan_dateTextView;
		 //ImageView rightImageView;
		 TextView kindTextView;

	} 
	
	
}
