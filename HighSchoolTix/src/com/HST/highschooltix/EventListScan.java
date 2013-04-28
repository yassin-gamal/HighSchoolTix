package com.HST.highschooltix;

import static java.text.DateFormat.FULL;
import static java.text.DateFormat.LONG;
import static java.text.DateFormat.MEDIUM;
import static java.text.DateFormat.SHORT;
import static java.util.Locale.US;

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
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;





import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class EventListScan extends Activity  
{
	ListView  listView;
	Button backButton;
	CustomHttpClient customHttpClient;
	CustomEventScanAdapter adapter;
	static final String TITLE="title";
	static final String ADDRESS="address";
	static final String DATE="date";
	static final String ID="id";
	static final String CITY="city";
	static final String STUDENT_PRICE="student_price";
	static final String PRICE="price";
	static final String STATE="state";
	static final String ZIPCODE="zip";
	static final String LOCATION="location";
	
	EditText searchEditText;
	LinearLayout scan_menuButton;
	LinearLayout list_menuButton;
	LinearLayout event_menuButton;
	 Button logoutBT;
	String auth_token;
	String responce;
	String res;
	String user_name;
	String password;
	
	DateFromatChanger changer;
	
	String[] newMonths = { "JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT",
	        "NOV", "DEC" };
	    String[] newShortMonths = { "January", "February", "March", "April", "May", "June", "July", "August", "September",
	        "October", "November", "December" };
	    String[] newWeekdays = { "", "Monday", "Tuesday", "Webnesday", "Thursday", "Friday",
	        "Saturaday", "Sunday" };
	    String[] shortWeekdays = { "", "monday", "tuesday", "webnesday", "thursday", "friday",
	        "saturaday", "sunday" };
	    DateFormatSymbols symbols;
	    DateFormat format;
	//CheeseFilter filter;
	
//	String URL="http://111.118.248.140/HighSchool/script/test.php";
	String URL;
	String barcode_url="http://obscure-depths-9305.herokuapp.com/tickets/scan.json";
	 ArrayList<HashMap<String, String>> DataList;
	 ArrayList<String> AddressList;;
	 JSONArray jArray;
	 HashMap<String, String> map;
	 HttpURLConnection connection;
	 ProgressBar progressBar;
		
		LinearLayout scanticketlayLayout;
		LinearLayout boxofficeLayout;
		LinearLayout eventLayout;
		LinearLayout ticketLayout;
		TextView holoTextView;
	 
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Load_login();
        if(user_name.equalsIgnoreCase("no")||password.equalsIgnoreCase("no"))
        {
        	finish();
        }
        else
        {
        	 setContentView(R.layout.evetscanlist);
            
             
             Load_auth_token();
             String date_current=giveDate();
             System.out.println("date_current:="+date_current);
            // date_current="2013-04-05";
             
             URL="http://obscure-depths-9305.herokuapp.com/events.json?auth_token="+auth_token+"&search[date_eq]="+date_current;

            	   symbols = new DateFormatSymbols();
            	 //   symbols.setMonths(newMonths);
            	    symbols.setShortMonths(newShortMonths);
//            	    symbols.setWeekdays(newWeekdays);
//            	    symbols.setShortWeekdays(shortWeekdays);

//            	  format = new SimpleDateFormat("dd MMMM yyyy", symbols);
//            	    System.out.println(format.format(new Date()));
            	    holoTextView=(TextView) findViewById(R.id.holo_text);
            	    scanticketlayLayout=(LinearLayout) findViewById(R.id.scanbtlayout);
                    boxofficeLayout=(LinearLayout) findViewById(R.id.boxfiicebtlayout);
                    eventLayout=(LinearLayout) findViewById(R.id.eventbtlayout);
                    ticketLayout=(LinearLayout) findViewById(R.id.ticketbtlayout);
            	 holoTextView.setVisibility(View.INVISIBLE);
            	    format = new SimpleDateFormat("MMM dd yyyy", symbols);
            	    System.out.println(format.format(new Date()));


             
             
             

        
        listView=(ListView) findViewById(android.R.id.list);
        progressBar=(ProgressBar) findViewById(R.id.progressBar1);

        logoutBT=(Button) findViewById(R.id.logout);
    
        new ShowDialogAsyncTask().execute();
        String responce=null;
        
        ticketLayout.setOnClickListener(new OnClickListener() 
        {
			
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				startActivity(new Intent(EventListScan.this,List_Activity.class));
				finish();
				
			}
		});
        boxofficeLayout.setOnClickListener(new OnClickListener() {
			
  			public void onClick(View v) 
  			{
  				// TODO Auto-generated method stub
  				startActivity(new Intent(EventListScan.this,EventList_Activity.class));
  				finish();
  				
  			}
  		});
        eventLayout.setOnClickListener(new OnClickListener() 
        {
			
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub
				Intent intent=new Intent(EventListScan.this,EventList_Activity.class);
				startActivity(intent);
				finish();
				
			}
		});
        listView.setOnItemClickListener(new OnItemClickListener()
        {

			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) 
			{
				
				 Intent intent = new Intent("com.google.zxing.client.android.SCAN");
		        startActivityForResult(intent, 0);
				// TODO Auto-generated method stub
				

			}
		});
        
        logoutBT.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub
			
			//	Intent intent=new Intent(EventList_Activity.this,Login.class);
                 Save_Login("no","no");
                 Toast.makeText(getBaseContext(),"Logout", Toast.LENGTH_SHORT).show();
                 startActivity(new Intent(EventListScan.this, Login.class));
                 finish();
			}
		});


    	
    	 backButton=(Button) findViewById(R.id.back_bt);
         backButton.setOnClickListener(new OnClickListener() 
         {
 			
 			public void onClick(View v) 
 			{
 				// TODO Auto-generated method stub
 				startActivity(new Intent(EventListScan.this,Home.class));
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
			
			//AddressList=new ArrayList<String>();
		      jArray = new JSONArray(result);
		      k=jArray.length();
		      JSONObject json_data=null;
		      System.out.println(jArray.toString());
		      Date date = null;
		      changer=new DateFromatChanger();
		      SimpleDateFormat sdfSource = new SimpleDateFormat("dd/MM/yy");
		      for(int i=0;i<jArray.length();i++)
		      {
		    	  map = new HashMap<String, String>();
		    	 
		             json_data = jArray.getJSONObject(i);
		             map.put(TITLE,json_data.get(TITLE).toString());
		             map.put(ADDRESS,json_data.get(ADDRESS).toString());
		            
            	
            	      //parse the string into Date object
            	     
					try {
						
						date = sdfSource.parse(changer.DateString(json_data.get(DATE).toString()));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		            
	            
		             map.put(DATE,changer.DateWantedFromate(format.format(date)).toString());
		             map.put(ID,json_data.get(ID).toString());
		             map.put(CITY,json_data.get(CITY).toString());
		             map.put(STUDENT_PRICE,json_data.get(STUDENT_PRICE).toString());
		             map.put(PRICE,json_data.get(PRICE).toString());
		             map.put(ZIPCODE,json_data.get(ZIPCODE).toString());
		             map.put(STATE,json_data.get(STATE).toString());
		             map.put(LOCATION,json_data.get(LOCATION).toString());
		             DataList.add(map);
		            // AddressList.add(json_data.get(ADDRESS).toString());
		             System.out.println("jeson object:     "+jArray.getJSONObject(i));
		             
		         }
		   
			
				
		        adapter=new CustomEventScanAdapter(this,DataList);      
		        listView.setAdapter(adapter);
		        if (DataList.size()==0) 
		        {
		      
		        	holoTextView.setVisibility(View.VISIBLE);
				}
		     
		      }
		      catch(JSONException e1)
		      {
		    	 
		    	  System.out.println(e1);
		    	 
		      }
	}
    
    public void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
    	   if (requestCode == 0) 
    	   {
    	      if (resultCode == RESULT_OK)
    	      {
    	    	  //Intent intent1=new Intent(Home.this,Access_Activity.class);
    	    	  String res=Check_ticket(intent.getStringExtra("SCAN_RESULT"), auth_token);
    	    	  if(res.equalsIgnoreCase("no"))
					 {
						 Toast.makeText(getBaseContext(),"something goess wrong...",Toast.LENGTH_SHORT).show();
					 }
    	    	  postData(res);
	  

    	      } else if (resultCode == RESULT_CANCELED)
    	      {
	  
    	      }
    	   }
    	}
    
    private void postData(String res)
	{

	    	
	    	 try {

			      JSONObject json_data=new JSONObject(res);
			      System.out.println(json_data);
			      String success_string=json_data.get("success").toString();
			      System.out.println("value of sss: "+success_string);
			      success_string=success_string.trim();
			      if(success_string.equalsIgnoreCase("false"))
			      {
			    	  String error_mesg=json_data.get("error").toString();
			    	  Intent intent=new Intent(EventListScan.this,Access_Activity.class);
			    	  if(error_mesg.equalsIgnoreCase("Ticket not found"))
			    	  {
			    	  //Toast.makeText(getBaseContext(), error_mesg, Toast.LENGTH_LONG).show();
			    		  intent.putExtra("state","0");
				    	  intent.putExtra("kind","no");
			    
			    	  }
			    	  if(error_mesg.equalsIgnoreCase("Ticket already scanned"))
			    	  {
			    		  json_data=json_data.getJSONObject("ticket");
			    		  String kind=json_data.getString("kind").toString();
			    		  String scan_date=json_data.getString("scan_date").toString();
			    		  String holder_name=json_data.getString("holder_name").toString();
			    		  intent.putExtra("state","2");
			    		  
			    		  intent.putExtra("scan_date",scan_date);
			    		  intent.putExtra("kind",kind);
			    		  intent.putExtra("holder_name",holder_name);
			    	  }
			      
			    	  startActivity(intent);
			    	  finish();
			      }
			      if(success_string.equalsIgnoreCase("true"))
			      {
			    	  json_data=json_data.getJSONObject("ticket");
			    	  
			    	  System.out.println("json_data :"+json_data);
			    	  String kind=json_data.getString("kind").toString();
			    	  System.out.println("kind:"+ kind);
			    	  //Toast.makeText(getBaseContext(), "kind:"+ kind, Toast.LENGTH_LONG).show();
			    	  Intent intent=new Intent(EventListScan.this,Access_Activity.class);
			    	  intent.putExtra("state","1");
			     	  intent.putExtra("kind",kind);
			    	  startActivity(intent);
			      }

	    	 } catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					 Toast.makeText(getBaseContext(), "Error "+e, Toast.LENGTH_LONG).show();
					 System.out.println("Error "+e);
				}
		     
		
	}
    public String Check_ticket(String Skdu, String auth)
    {           
        
       OutputStreamWriter request = null;

            URL url = null;   
            String response = null;         
            String parameters = "auth_token"+"="+auth+"&barcode"+"="+Skdu;   

            //Toast.makeText(this,"Call"+ response, 0).show();    
            try
            {
            	
            	// Toast.makeText(this,"Call again", 0).show();    
                url = new URL(barcode_url);
                connection = (HttpURLConnection) url.openConnection();
                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
                connection.setReadTimeout(10000);
                connection.setConnectTimeout(15000);
                connection.setRequestMethod("POST"); 
             

                request = new OutputStreamWriter(connection.getOutputStream());
                request.write(parameters);
                request.flush();
                request.close();            
                String line = "";               
                InputStreamReader isr = new InputStreamReader(connection.getInputStream());
                BufferedReader reader = new BufferedReader(isr);
                StringBuilder sb = new StringBuilder();
                while ((line = reader.readLine()) != null)
                {
                    sb.append(line + "\n");
                }
                // Response from server after login process will be stored in response variable.                
                response = sb.toString();
                // You can perform UI operations here
                //Toast.makeText(this,"Message from Server: \n"+ response, 0).show();    
                System.out.println(response);
                isr.close();
                
                reader.close();

            }
            catch(IOException e)
            {
                System.out.println("Error:  "+e);
                
                response="no";
            }
			return response;
    }
	
	public void Load_auth_token()
	{
		try
		{
		SharedPreferences sharedPreferences = getSharedPreferences("AUTH", MODE_PRIVATE);
		auth_token = sharedPreferences.getString("auth_token","no");
	 

		}
		catch(Exception e)
		{
			
		}
		
	}
	
	private void showdialog(String title_st,String add_st,String city_st,String price_st,String st_price_st,String state,String zip,String loaction) {
        final Dialog dialog = new Dialog(EventListScan.this);
        dialog.setContentView(R.layout.test);
        dialog.setTitle(title_st);

        // set the custom dialog components - text, image and button
      //  TextView title = (TextView) dialog.findViewById(R.id.title);
        TextView address = (TextView) dialog.findViewById(R.id.address);
        TextView city = (TextView) dialog.findViewById(R.id.city);
        TextView stateTextView = (TextView) dialog.findViewById(R.id.state);
        TextView zipTextView = (TextView) dialog.findViewById(R.id.zipcode);
        TextView locationTextView = (TextView) dialog.findViewById(R.id.location);
      //  TextView price = (TextView) dialog.findViewById(R.id.price);
       // TextView student_price = (TextView) dialog.findViewById(R.id.student_price);
        
       // title.setText(title_st);
        address.setText(add_st);
        city.setText(city_st);
        stateTextView.setText(state);
        zipTextView.setText(zip);
        locationTextView.setText(loaction);
//        price.setText(price_st);
//        student_price.setText(st_price_st);

        Button dialogButton = (Button) dialog.findViewById(R.id.button1);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

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
    
    @Override
    public void onBackPressed() 
   {
	
    
	   super.onBackPressed();

		   startActivity(new Intent(EventListScan.this,Home.class));
       	
 
     
     
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
//txt_percentage.setText("downloading 0%");
     progressBar.setVisibility(View.VISIBLE);
       
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
       
      progressBar.setVisibility(View.INVISIBLE);
    //  postData(response);
      try{
      JsonValue(res);
      }catch (Exception e) {
		// TODO: handle exception
	}
       //messaegTextView.setText("download complete");
      
     }
       }
    
    public String giveDate()
    {
   	DateFormat fmt;
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("America/Los_Angeles"));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date today = new Date();
//        Locale[] locales = { US };
//        int[] styles = { FULL, LONG, MEDIUM, SHORT };
//        String[] styleNames = { "FULL", "LONG", "MEDIUM", "SHORT" };
//       
//        for (Locale locale : locales) 
//        {
//          System.out.println("\nThe Date for " + locale.getDisplayCountry() + ":");
//          for (int i = 0; i < styles.length; i++) 
//          {
//            fmt = DateFormat.getDateInstance(styles[i], locale);
//            System.out.println("\tIn " + styleNames[i] + " is " + fmt.format(today));
//          }
//        }
        return sdf.format(cal.getTime());
    }
 
}
