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
import java.util.Date;
import java.util.StringTokenizer;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Access_Activity extends Activity
{
	Button backButton;
	String state;
	Bundle bundle;
	
	LinearLayout scanticketlayLayout;
	LinearLayout boxofficeLayout;
	LinearLayout eventLayout;
	LinearLayout ticketLayout;
	
	 String[] newShortMonths = { "January", "February", "March", "April", "May", "June", "July", "August", "September",
		        "October", "November", "December" };
	DateFromatChanger changer;
	 DateFormat format;
	  DateFormatSymbols symbols;
	
	TextView titleTextView;
	LinearLayout linearLayout1;
	TextView accessTextView;
	TextView belowaccessTextView;
	Button okButton;
	Button menuButton;
	String type;
	TextView typeTextView;
	TextView testTextView;
	TextView type_name;
	TextView dateTextView;
	TextView timeTextView;
	LinearLayout typeLayout;
	LinearLayout list_menuButton;
	LinearLayout event_menuButton;
	LinearLayout scan_menubutton;
	LinearLayout scanDateLinearLayout;
	LinearLayout scanTimeLinearLayout;
	 HttpURLConnection connection;
	 String auth_token;
	 String barcode_url="http://obscure-depths-9305.herokuapp.com/tickets/scan.json";
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.access_layout);
        Load_auth_token();
        bundle=getIntent().getExtras();
        state=bundle.get("state").toString();
        type=bundle.get("kind").toString();
       // titleTextView=(TextView) findViewById(R.id.titeltext);
        linearLayout1=(LinearLayout) findViewById(R.id.linearLayout1);
        accessTextView=(TextView) findViewById(R.id.access_text);
        belowaccessTextView=(TextView) findViewById(R.id.access_below);
        typeTextView=(TextView) findViewById(R.id.type);
        okButton=(Button) findViewById(R.id.okbt);
        menuButton=(Button) findViewById(R.id.mnbt);
        typeLayout=(LinearLayout) findViewById(R.id.type_layout);
       // testTextView=(TextView) findViewById(R.id.titeltext);
        type_name=(TextView) findViewById(R.id.type_name);
        
        dateTextView=(TextView) findViewById(R.id.date);
        timeTextView=(TextView) findViewById(R.id.time);
        scanDateLinearLayout=(LinearLayout) findViewById(R.id.scan_date_layout);
        scanTimeLinearLayout=(LinearLayout) findViewById(R.id.scan_time_layout);
//        event_menuButton=(LinearLayout) findViewById(R.id.event_menu);
//        list_menuButton=(LinearLayout) findViewById(R.id.list_menu);
//        scan_menubutton=(LinearLayout) findViewById(R.id.scan_menu);
        
        symbols = new DateFormatSymbols();
        symbols.setShortMonths(newShortMonths);
        changer=new DateFromatChanger();
        format = new SimpleDateFormat("MMM dd yyyy", symbols);
        
        
        
        scanticketlayLayout=(LinearLayout) findViewById(R.id.scanbtlayout);
        boxofficeLayout=(LinearLayout) findViewById(R.id.boxfiicebtlayout);
        eventLayout=(LinearLayout) findViewById(R.id.eventbtlayout);
        ticketLayout=(LinearLayout) findViewById(R.id.ticketbtlayout);
        
        boxofficeLayout.setOnClickListener(new OnClickListener() {
 			
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub
				startActivity(new Intent(Access_Activity.this,EventList_Activity.class));
				finish();
				
			}
		});
        scanticketlayLayout.setOnClickListener(new OnClickListener()
        {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
			//	Toast.makeText(getBaseContext(),"CALL", Toast.LENGTH_SHORT).show();
				startActivity(new Intent(Access_Activity.this,EventListScan.class));
				finish();
			}
		});
        ticketLayout.setOnClickListener(new OnClickListener() 
        {
			
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				startActivity(new Intent(Access_Activity.this,List_Activity.class));
				finish();
				
			}
		});
        eventLayout.setOnClickListener(new OnClickListener() 
        {
			
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub
				Intent intent=new Intent(Access_Activity.this,EventList_Activity.class);
				startActivity(intent);
				finish();
				
			}
		});
        
        if(state.equalsIgnoreCase("1"))
        {
           //	titleTextView.setText("Access Granted");
        	scanDateLinearLayout.setVisibility(View.INVISIBLE);
        	scanTimeLinearLayout.setVisibility(View.INVISIBLE);
        	
        	linearLayout1.setBackgroundResource(R.drawable.access);
        	accessTextView.setText("Access Granted");
        	belowaccessTextView.setText("Your access granted");
        	okButton.setBackgroundResource(R.drawable.ok_bg);
        	okButton.setText("Ok");
        	menuButton.setBackgroundResource(R.drawable.ok_bg);
        	menuButton.setText("Menu");
        	typeLayout.setVisibility(View.VISIBLE);
        	type_name.setText("Type :");
        	typeTextView.setText(type);
        }
        if(state.equalsIgnoreCase("0"))
        {
        	scanDateLinearLayout.setVisibility(View.INVISIBLE);
        	scanTimeLinearLayout.setVisibility(View.INVISIBLE);
        	//titleTextView.setText("Access Denied");
        	linearLayout1.setBackgroundResource(R.drawable.dinai);
        	accessTextView.setText("Access Denied !");
        	belowaccessTextView.setText("Your don't have permission to access scan");
        	okButton.setBackgroundResource(R.drawable.redbt);
        	okButton.setText("Ok");
        	menuButton.setBackgroundResource(R.drawable.redbt);
        	menuButton.setText("Menu");
        	typeTextView.setText("");
        	typeLayout.setVisibility(View.INVISIBLE);
        	
        }
        if(state.equalsIgnoreCase("2"))
        {
        	
        
        	scanDateLinearLayout.setVisibility(View.VISIBLE);
        	scanTimeLinearLayout.setVisibility(View.VISIBLE);
        	String holder_name=bundle.get("holder_name").toString();
        	String scan_date=bundle.get("scan_date").toString();
        	
        	linearLayout1.setBackgroundResource(R.drawable.already);
        	okButton.setBackgroundResource(R.drawable.ok_bg);
        	menuButton.setBackgroundResource(R.drawable.ok_bg);
        	accessTextView.setText("");
        	typeLayout.setVisibility(View.VISIBLE);
        	belowaccessTextView.setText("Ticket already scanned");
        	//belowaccessTextView.setText("Holder name");
        	String dat = null;
        	String tim = null; 
        	StringTokenizer stringTokenizer=new StringTokenizer(scan_date, "T");
        	while (stringTokenizer.hasMoreElements())
        	{
				dat =  stringTokenizer.nextElement().toString();
				tim =  stringTokenizer.nextElement().toString();
				System.out.println(dat);
				System.out.println(tim);
				
				
			}
       	SimpleDateFormat sdfSource = new SimpleDateFormat("dd/MM/yy");
        	 Date date = null;
//        	   System.out.println(scan_date);
        	   try {
				date=sdfSource.parse(changer.DateString(dat));
			} catch (ParseException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//        	   
//        	   String dt1 = formatDate(date,"dd-MM-yyyy");
//        	   System.out.println(dt1);
////        	   date=new Date(scan_date);
////        	   String dt2 = formatTime(date,"hh:mm:ss");
////        	   System.out.println(dt2);
        	   
        	   
        	timeTextView.setText(tim);
        	type_name.setText("Holder name :");
        	dateTextView.setText(changer.DateWantedFromate(format.format(date)).toString());
        	typeTextView.setText(holder_name);
        	okButton.setText("Ok");
        	menuButton.setText("Menu");
        	
        }
//        backButton=(Button) findViewById(R.id.back_bt);
//        backButton.setOnClickListener(new OnClickListener() 
//        {
//			
//			public void onClick(View v) 
//			{
//				// TODO Auto-generated method stub
//				finish();
//				
//			}
//		});
        
        menuButton.setOnClickListener(new OnClickListener() 
        {
			
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				 Intent intent=new Intent(Access_Activity.this,MainScreen.class);
			     startActivityForResult(intent, 0);
			}
		});
        
        okButton.setOnClickListener(new OnClickListener() 
        {
			
			public void onClick(View v)
			{
				Intent intent = new Intent("com.google.zxing.client.android.SCAN");
		        startActivityForResult(intent, 0);
			}
		});
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
    	    	 // testTextView.setText(res);
	  

    	      } else if (resultCode == RESULT_CANCELED)
    	      {
	  
    	      }
    	   }
    	}
	
	private void postData(String res)
	{

	    	
	    	 try {
	    		 
//	    		 jArray = new JSONArray(res);
//			      k=jArray.length();
			      JSONObject json_data=new JSONObject(res);
			      String success_string=json_data.get("success").toString();
			      
			     // String error_mesg=json_data.get("error").toString();
			      System.out.println("value of sss: "+success_string);
			      success_string=success_string.trim();
			      if(success_string.equalsIgnoreCase("false"))
			      {
			    	  String error_mesg=json_data.get("error").toString();
			    	  Intent intent=new Intent(Access_Activity.this,Access_Activity.class);
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
			    	  Intent intent=new Intent(Access_Activity.this,Access_Activity.class);
			    	  intent.putExtra("state","1");
			     	  intent.putExtra("kind",kind);
			    	  startActivity(intent);
			    	  finish();
			      }

	    	 } catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					 Toast.makeText(getBaseContext(), "Error "+e, Toast.LENGTH_LONG).show();
					 System.out.println("Error "+e);
				}
		
	}

  
    public String formatDate(Date date, String format) 
    {
   SimpleDateFormat form = new SimpleDateFormat(format);
   return form.format(date);
  }
  
  public String formatTime(Date time, String format) 
  {
   SimpleDateFormat form = new SimpleDateFormat(format);  
   return form.format(time);
  }  
}
