package com.HST.highschooltix;



import java.io.BufferedReader;


import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.StringTokenizer;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class EventDetailActivity extends Activity
{
	CustomHttpClient customHttpClient;
	Bundle bundle;
	String id;
	String URL;
	String responce;
	ProgressBar progressBar;
	String res;
	ArrayList<HashMap<String, String>> DataList;
	JSONArray jArray;
	HashMap<String,String> map;
	
	private static final int ZooZ_Activity_ID = 1;
	static final String TITLE="title";
	static final String ADDRESS="address";
	static final String DATE="date";
	static final String ID="id";
	static final String CITY="city";
	static final String STUDENT_PRICE="student_price";
	static final String PRICE="price";
	static final String BEGIN="sales_start";
	static final String END="sales_end";
	static final String COLOR="ticket_color";
	
	
	  private RadioGroup radioSexGroup;
	  private RadioButton general_RadioButton;
	  private RadioButton student_RadioButton;
	  
	
	
	
	TextView nameTextView;
	TextView addressTextView;
	TextView dateTimeTextView;
	TextView generalPriceTextView;
	TextView studentPriceTextView;
	TextView salesbeginTextView;
	TextView salesendTextView;
	TextView ticketcolorTextView;
	Button send_bookButton;
	Button manual_bookButton;
	String student_price;
	String genral_price;
	String user_name;
	String password;
	String event_name;
	String event_date;
	 Button logoutBT;
	 
	 Intent intent;
	 
		LinearLayout scanticketlayLayout;
		LinearLayout boxofficeLayout;
		LinearLayout eventLayout;
		LinearLayout ticketLayout;
	 
	HttpURLConnection connection;
	 StringTokenizer stringTokenizer;
	  String[] newShortMonths = { "January", "February", "March", "April", "May", "June", "July", "August", "September",
		        "October", "November", "December" };
	    DateFormatSymbols symbols;
	    DateFormat format;
	    DateFromatChanger changer;
	 public void onCreate(Bundle savedInstanceState)
	    {
	        super.onCreate(savedInstanceState);
	        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
	        Load_login();
	        Load_EvenID();
	        if(user_name.equalsIgnoreCase("no")||password.equalsIgnoreCase("no"))
	        {
	        	finish();
	        }
	        else
	        {
	        setContentView(R.layout.eventdetailnew);
	        bundle=getIntent().getExtras();
	       // progressBar=(ProgressBar) findViewById(R.id.progressBar1);
	        nameTextView=(TextView) findViewById(R.id.eventname);
	        addressTextView=(TextView) findViewById(R.id.address);
	        dateTimeTextView=(TextView) findViewById(R.id.datetime);
	        generalPriceTextView=(TextView) findViewById(R.id.genral_price);
	        studentPriceTextView=(TextView) findViewById(R.id.student_price);
	        
	        send_bookButton=(Button) findViewById(R.id.genaral_bookbt);
	        manual_bookButton=(Button) findViewById(R.id.student_bookbt);
	        
	        general_RadioButton=(RadioButton) findViewById(R.id.general_redio);
	        student_RadioButton=(RadioButton) findViewById(R.id.student_redio);
	        
	        
	        salesbeginTextView=(TextView) findViewById(R.id.salesbegin);
	        salesendTextView=(TextView) findViewById(R.id.salesend);
	        ticketcolorTextView=(TextView) findViewById(R.id.ticketcolor);
	        
	        scanticketlayLayout=(LinearLayout) findViewById(R.id.scanbtlayout);
            boxofficeLayout=(LinearLayout) findViewById(R.id.boxfiicebtlayout);
            eventLayout=(LinearLayout) findViewById(R.id.eventbtlayout);
            ticketLayout=(LinearLayout) findViewById(R.id.ticketbtlayout);
	        
	     //   id=bundle.getString("id").toString();
	        logoutBT=(Button) findViewById(R.id.logout);
	        student_price=bundle.getString("S").toString();
	        if(student_price.equalsIgnoreCase("null"))
	        		{
	        	student_price="0.";
	        		}
	        
	        	genral_price=bundle.getString("G").toString();
	        
	        if(genral_price.equalsIgnoreCase("null"))
	        		{
	        	genral_price="0.";
	        		}
	         stringTokenizer=new  StringTokenizer(STUDENT_PRICE, ".");
	         symbols = new DateFormatSymbols();
        	 //   symbols.setMonths(newMonths);
        	    symbols.setShortMonths(newShortMonths);
	         format = new SimpleDateFormat("MMM dd yyyy", symbols);
	        generalPriceTextView.setText("$"+genral_price+"0");
	        studentPriceTextView.setText("$"+student_price+"0");
	        
	       // Toast.makeText(getBaseContext(), id,Toast.LENGTH_SHORT).show();
	        id=id.trim();
	        URL="http://obscure-depths-9305.herokuapp.com/events/"+id+".json";
	        
	        
	   	 try{
 	    	responce=customHttpClient.executeHttpGet(URL);
 	    	 res=responce.toString();
 	    	System.out.println("responce of service is="+res);
 	    	
 	    	 JsonValue(res);
 	    	
 	       
 	    	
 	    	
 	    	}catch (Exception e) 
 	    	{
 	    	// TODO: handle exception
 	    	System.out.println("Eception is="+e);
 	    	}
	        //new ShowDialogAsyncTask().execute();
	        
	        
	       general_RadioButton.setOnClickListener(new OnClickListener()
	       {
			
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub
				student_RadioButton.setChecked(false);
				general_RadioButton.setChecked(true);
				
				 intent=new Intent(EventDetailActivity.this,CheckoutpageActivity.class);
				intent.putExtra("price",genral_price);
				intent.putExtra("type","Genral price");
				intent.putExtra("type1","general");
				intent.putExtra("date",dateTimeTextView.getText().toString());
				intent.putExtra("title",nameTextView.getText().toString());
			}
		});
	       general_RadioButton.setChecked(true);
	       student_RadioButton.setChecked(false);
	  	 
	       student_RadioButton.setOnClickListener(new OnClickListener()
	       {
			
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub
				general_RadioButton.setChecked(false);
				student_RadioButton.setChecked(true);
				
				
				 intent=new Intent(EventDetailActivity.this,CheckoutpageActivity.class);
				intent.putExtra("price",student_price);
				intent.putExtra("type","Student price");
				intent.putExtra("type1","student");
				intent.putExtra("date",dateTimeTextView.getText().toString());
				intent.putExtra("title",nameTextView.getText().toString());
		
				
			}
		});
	        
	        

	        scanticketlayLayout.setOnClickListener(new OnClickListener()
	        {
				
				public void onClick(View v) {
					// TODO Auto-generated method stub
				//	Toast.makeText(getBaseContext(),"CALL", Toast.LENGTH_SHORT).show();
					
					startActivity(new Intent(EventDetailActivity.this,EventListScan.class));
					finish();
				}
			});
	        
	        ticketLayout.setOnClickListener(new OnClickListener() 
	        {
				
				public void onClick(View v)
				{
					// TODO Auto-generated method stub
					startActivity(new Intent(EventDetailActivity.this,List_Activity.class));
					finish();
					
				}
			});
	        boxofficeLayout.setOnClickListener(new OnClickListener() {
				
	  			public void onClick(View v) 
	  			{
	  				// TODO Auto-generated method stub
	  				startActivity(new Intent(EventDetailActivity.this,EventList_Activity.class));
	  				finish();
	  				
	  			}
	  		});
	        eventLayout.setOnClickListener(new OnClickListener() 
	        {
				
				public void onClick(View v) 
				{
					// TODO Auto-generated method stub
					Intent intent=new Intent(EventDetailActivity.this,EventList_Activity.class);
					startActivity(intent);
					finish();
					
				}
			});
	        
	        send_bookButton.setClickable(false);
	       manual_bookButton.setClickable(false);
	       send_bookButton.setOnClickListener(new OnClickListener() 
	        {
				
			
				public void onClick(View arg0)
				{
					if(student_RadioButton.isChecked())
					{
						 intent=new Intent(EventDetailActivity.this,CheckoutpageActivity.class);
							intent.putExtra("price",student_price);
							intent.putExtra("type","Student price");
							intent.putExtra("type1","student");
							intent.putExtra("date",dateTimeTextView.getText().toString());
							intent.putExtra("title",nameTextView.getText().toString());
							intent.putExtra("pos","s");
					}
					if(general_RadioButton.isChecked())
					{
						 intent=new Intent(EventDetailActivity.this,CheckoutpageActivity.class);
							intent.putExtra("price",genral_price);
							intent.putExtra("type","Genral price");
							intent.putExtra("type1","general");
							intent.putExtra("date",dateTimeTextView.getText().toString());
							intent.putExtra("title",nameTextView.getText().toString());	
							intent.putExtra("pos","s");
					}
      // Toast.makeText(getBaseContext(), "Normal",Toast.LENGTH_SHORT).show();
//					Intent intent=new Intent(EventDetailActivity.this,CheckoutpageActivity.class);
//					intent.putExtra("price",genral_price);
//					intent.putExtra("type","Genral price");
//					intent.putExtra("type1","General");
//					intent.putExtra("date",dateTimeTextView.getText().toString());
//					intent.putExtra("title",nameTextView.getText().toString());
					intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT|Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(intent);
					finish();
					

					
				}
			});
	       
	       
	        
	       manual_bookButton.setOnClickListener(new OnClickListener() 
	        {
				
			
				public void onClick(View arg0) 
				{
					if(student_RadioButton.isChecked())
					{
						 intent=new Intent(EventDetailActivity.this,CheckoutpageActivity.class);
							intent.putExtra("price",student_price);
							intent.putExtra("type","Student price");
							intent.putExtra("type1","student");
							intent.putExtra("date",dateTimeTextView.getText().toString());
							intent.putExtra("title",nameTextView.getText().toString());
							intent.putExtra("pos","m");
					}
					if(general_RadioButton.isChecked())
					{
						 intent=new Intent(EventDetailActivity.this,CheckoutpageActivity.class);
							intent.putExtra("price",genral_price);
							intent.putExtra("type","Genral price");
							intent.putExtra("type1","general");
							intent.putExtra("date",dateTimeTextView.getText().toString());
							intent.putExtra("title",nameTextView.getText().toString());	
							intent.putExtra("pos","m");
					}
					//Toast.makeText(getBaseContext(), "Manual",Toast.LENGTH_SHORT).show();
//
//					Intent intent=new Intent(EventDetailActivity.this,CheckoutpageActivity.class);
//					intent.putExtra("price",student_price);
//					intent.putExtra("type","Student price");
//					intent.putExtra("type1","Student");
//					intent.putExtra("date",dateTimeTextView.getText().toString());
//					intent.putExtra("title",nameTextView.getText().toString());
					intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT|Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(intent);
					finish();

					
				}
			});
	       
	        logoutBT.setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) 
				{
					// TODO Auto-generated method stub
				
					Intent intent=new Intent(EventDetailActivity.this,Login.class);
	                 Save_Login("no","no");
	                 
	                 startActivity(new Intent(EventDetailActivity.this, Login.class));
	                 finish();
				}
			});
	       
	        }
	    }
	 
	 
	 public void JsonValue(String result)
		{
			int k = 0;
			try{
				DataList = new ArrayList<HashMap<String, String>>();

			      JSONObject json_data=new JSONObject(result);
			      Date date = null;
			      Date startdate = null;
			      Date enddate = null;
			      
			      changer=new DateFromatChanger();
			      SimpleDateFormat sdfSource = new SimpleDateFormat("dd/MM/yy");

			    	  map = new HashMap<String, String>();
			    	 
			             //json_data = jArray.getJSONObject(0);
			             map.put(TITLE,json_data.get(TITLE).toString());
			             map.put(ADDRESS,json_data.get(ADDRESS).toString());
			         	try {
							
							date = sdfSource.parse(changer.DateString(json_data.get(DATE).toString()));
							startdate = sdfSource.parse(changer.DateString(json_data.get(BEGIN).toString()));
							enddate = sdfSource.parse(changer.DateString(json_data.get(END).toString()));
							
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
			            
		            
			             map.put(DATE,changer.DateWantedFromate(format.format(date)).toString());
			             map.put(ID,json_data.get(ID).toString());
			             map.put(CITY,json_data.get(CITY).toString());
			             map.put(STUDENT_PRICE,json_data.get(STUDENT_PRICE).toString());
			             map.put(PRICE,json_data.get(PRICE).toString());
			             map.put(BEGIN, changer.DateWantedFromate(format.format(startdate)).toString());
			             map.put(END, changer.DateWantedFromate(format.format(enddate)).toString());
			             map.put(COLOR, json_data.get(COLOR).toString());
			             
			             DataList.add(map);

					map=new HashMap<String, String>();
					map=DataList.get(0);
					nameTextView.setText(map.get(TITLE).toString());
					event_name=map.get(TITLE).toString();
					
					addressTextView.setText(map.get(ADDRESS).toString());
					dateTimeTextView.setText(map.get(DATE).toString());
					event_date=map.get(DATE).toString();
					
					salesbeginTextView.setText(map.get(BEGIN).toString());
					salesendTextView.setText(map.get(END).toString());
					ticketcolorTextView.setText(map.get(COLOR).toString());
					System.out.println("Name="+map.get(TITLE).toString());
					System.out.println("Address="+map.get(ADDRESS).toString());
					System.out.println("Date="+map.get(DATE).toString());

			        
			     
			      }
			      catch(JSONException e1)
			      {
			    	 
			    	  System.out.println(e1);
			    	 
			      }
		}
	 
	 
	 private class ShowDialogAsyncTask extends AsyncTask<Void, Integer, Void>{
	   	 
	        int progress_status;
	         
	        @Override
	     protected void onPreExecute() 
	        {
	      // update the UI immediately after the task is executed
	      super.onPreExecute();
	       
	       //Toast.makeText(Login.this,"Invoke onPreExecute()", Toast.LENGTH_SHORT).show();
	    
	       progress_status = 0;
	
	     //progressBar.setVisibility(View.VISIBLE);
	       
	     }
	         
	     @Override
	     protected Void doInBackground(Void... params) {
	       

	    	 try{
	    	    	responce=customHttpClient.executeHttpGet(URL);
	    	    	 res=responce.toString();
	    	    	System.out.println("responce of service is="+res);
	    	    	
	    	       
	    	    	
	    	    	
	    	    	}catch (Exception e) 
	    	    	{
	    	    	// TODO: handle exception
	    	    	System.out.println("Eception is="+e);
	    	    	}
	         return null;
	     }
	     
	     @Override
	     protected void onProgressUpdate(Integer... values)
	     {
	      super.onProgressUpdate(values);

	       
	     }
	      
	     @Override
	     protected void onPostExecute(Void result) 
	     {
	      super.onPostExecute(result);
	       
	     // progressBar.setVisibility(View.INVISIBLE);
	      manual_bookButton.setClickable(true);
	      send_bookButton.setClickable(true);
	      JsonValue(res);
	       
	      
	     }
	       }
	 
	
	 

	 
	    public void Load_login()
	    {
	    	try {
	    		
	    		SharedPreferences preferences=getSharedPreferences("LOGIN", MODE_PRIVATE);
	    		user_name=preferences.getString("username", "no");
	    		password=preferences.getString("password", "no");
	    		
				
			} catch (Exception e) {
				// TODO: handle exception
			}
	    }
	    
	    
	    private void Save_Login(String user_name,String password)
		  {
			    SharedPreferences sharedPreferences = getSharedPreferences("LOGIN", MODE_PRIVATE);
			    SharedPreferences.Editor editor = sharedPreferences.edit();
			    editor.putString("username",user_name);
			    editor.putString("password",password);
			  
			    editor.commit();

		}
	    
	    public void onBackPressed() 
	    {
	 	
	     
	 	   super.onBackPressed();

	 	   
	      
	      
	     }
	    public void Load_EvenID()
	    {
	    	try {
	    		
	    		SharedPreferences preferences=getSharedPreferences("ID", MODE_PRIVATE);
	    		id=preferences.getString("ID", "no");
	    		
	    		
				
			} catch (Exception e) {
				// TODO: handle exception
			}
	    }
	    
	    
	    
//	    public void addListenerOnButton() 
//	    {
//	    	 
//	    	radioSexGroup = (RadioGroup) findViewById(R.id.redio_type);
//	    	btnDisplay = (Button) findViewById(R.id.genaral_bookbt);
//	     
//	    	btnDisplay.setOnClickListener(new OnClickListener() 
//	    	{
//	     
//	    	
//	    		public void onClick(View v) {
//	     
//	    		        // get selected radio button from radioGroup
//	    			int selectedId = radioSexGroup.getCheckedRadioButtonId();
//	     
//	    			// find the radiobutton by returned id
//	    		        radioSexButton = (RadioButton) findViewById(selectedId);
//	     
//	    			Toast.makeText(getBaseContext(),
//	    				"camll", Toast.LENGTH_SHORT).show();
//	     
//	    		}
//	     
//	    	});
//	    }
}
