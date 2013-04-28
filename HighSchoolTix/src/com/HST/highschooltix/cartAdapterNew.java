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

public class cartAdapterNew extends BaseAdapter
{

	private static ArrayList<HashMap<String,String>> ArrayList;
	private LayoutInflater myiInflater;
	Context cc;
	
	HashMap<String, String> hashMap;
	public cartAdapterNew(Context context,ArrayList<HashMap<String,String>> results)
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
			convertView = myiInflater.inflate(R.layout.cartbgnew, null); 
			
			 holder = new ViewHolder();
			// holder.layout=(LinearLayout) convertView.findViewById(R.id.relativerow);
			 holder.text_eventname = (TextView)convertView.findViewById(R.id.eventname);
			 
			 holder.text_person=(TextView) convertView.findViewById(R.id.quntity);
			 holder.text_ticketPrice = (TextView)convertView.findViewById(R.id.ticket_price);
			 holder.text_ticketAmount = (TextView)convertView.findViewById(R.id.ticketamount);
			 
			 holder.text_fee = (TextView)convertView.findViewById(R.id.fee);
			 holder.text_feequntiti = (TextView)convertView.findViewById(R.id.fee_quntity);
			 holder.text_feeAmount = (TextView)convertView.findViewById(R.id.total_fee);
			 
			 holder.text_total=(TextView) convertView.findViewById(R.id.total);
			 holder.text_datetime=(TextView) convertView.findViewById(R.id.datetime);
			
			 
			
			 
			

		}	
		else
		 {
			 holder = (ViewHolder)convertView.getTag();
		 }
		 convertView.setTag(holder);
		 hashMap=new HashMap<String, String>();
		hashMap= ArrayList.get(position);
		String ticketamout_str = null;
		double ticketamount = 0;
		try{
		double feetototal=Double.parseDouble(hashMap.get(CartActivity.TOTALFEE).toString());
		double total=Double.parseDouble(hashMap.get(CartActivity.TOTAL).toString());
		int per=Integer.parseInt(hashMap.get(CartActivity.PERSON).toString());
		 ticketamount=total-feetototal;
		double ttt=ticketamount/per;
	 ticketamout_str=Double.toString((double) Math.round(ttt * 100) / 100);
		
	}catch (Exception e) 
	{
		// TODO: handle exception
		System.out.println(e);
	}
		 
		  holder.text_eventname.setText(hashMap.get(CartActivity.TITLE).toString());
		  holder.text_datetime.setText(hashMap.get(CartActivity.DATE).toString());
		  
		  holder.text_person.setText(hashMap.get(CartActivity.PERSON).toString());
		  holder.text_ticketPrice.setText(ticketamout_str);
		  holder.text_ticketAmount.setText("$"+Double.toString((double) Math.round(ticketamount  * 100) / 100)+0);
		  
		  holder.text_fee.setText("$"+hashMap.get(CartActivity.FEEAMOUNT).toString());
		  holder.text_feequntiti.setText(hashMap.get(CartActivity.PERSON).toString());
		  holder.text_feeAmount.setText("$"+hashMap.get(CartActivity.TOTALFEE).toString());
		  
//		  holder.text_ticketAmount.setText(ticketamout_str+" X "+hashMap.get(CartActivity.PERSON).toString()+"="+"$"+Double.toString((double) Math.round(ticketamount  * 100) / 100)+0);
//		  holder.text_feeAmount.setText("Fee "+hashMap.get(CartActivity.PERSON).toString()+" person"+" X "+hashMap.get(CartActivity.FEEAMOUNT).toString()+" = "+"$"+hashMap.get(CartActivity.TOTALFEE).toString());
		 
		  holder.text_total.setText("$"+hashMap.get(CartActivity.TOTAL).toString());
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
		
		 TextView text_eventname;
		 
		 TextView text_person;
		 TextView text_ticketAmount;
		 TextView text_ticketPrice;
		 
		 TextView text_fee;
		 TextView text_feequntiti;
		 TextView text_feeAmount;
		 TextView text_total;

		 TextView text_datetime;
	} 
	
	
}
