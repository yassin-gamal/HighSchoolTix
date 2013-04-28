package com.HST.highschooltix;

import java.io.BufferedReader;




import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class List_Activity extends Activity
{
	ListView  listView;

	CheeseFilter filter;
	Button backButton;
	static final String TITLE="title";
	static final String ID="id";
	
	static final String NUMBER="number";
	static final String SCAN_DATE="scan_date";
	static final String SCANED="scanned";
	static final String KIND="kind";
	 Button logoutBT;
	CustomHttpClient customHttpClient;
	CustomListAdapter adapter;
	EditText searchEditText;
	RelativeLayout searchLayout;

	 String res;
	static final String SKDU="skdu_code";
	//String URL="http://111.118.248.140/HighSchool/script/test.php";
	String barcode_url="http://obscure-depths-9305.herokuapp.com/tickets/scan.json";
	String EVENT_URL;
	
	String URL_tickets;
	
	 ArrayList<HashMap<String, String>> DataList;
	 ArrayList<HashMap<String, String>> Event_DataList;
	 JSONArray jArray;
	 HashMap<String, String> map;
	 String auth_token;
		String responce;
		String responce_event;
	 String admited;
	 String remaing;
	 TextView admitedTextView;
	 TextView holoTextView;
	 TextView remanigTextView;
	 HttpURLConnection connection;
	 LinearLayout scan_menuButton;
		LinearLayout event_menuButton;
		String user_name;
		String password;
		Spinner spinner1;
		ProgressBar progressBar;
		String All_Ticket_Url;
		
		LinearLayout scanticketlayLayout;
		LinearLayout boxofficeLayout;
		LinearLayout eventLayout;
		LinearLayout ticketLayout;
		
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.listmain);
        customHttpClient=new CustomHttpClient();
       // spinner1 = (Spinner) findViewById(R.id.spinner1);
        holoTextView=(TextView) findViewById(R.id.holo_text);
        remanigTextView=(TextView) findViewById(R.id.remaing_text);
        progressBar =  (ProgressBar) findViewById(R.id.progressBar1);
        searchEditText=(EditText) findViewById(R.id.search);
        searchLayout=(RelativeLayout) findViewById(R.id.search_layout);
        searchLayout.setVisibility(View.INVISIBLE);
        Load_auth_token();

        All_Ticket_Url="http://obscure-depths-9305.herokuapp.com/tickets.json?auth_token="+auth_token;
      
        EVENT_URL="http://obscure-depths-9305.herokuapp.com/events.json?auth_token="+auth_token;
       
       scanticketlayLayout=(LinearLayout) findViewById(R.id.scanbtlayout);
       boxofficeLayout=(LinearLayout) findViewById(R.id.boxfiicebtlayout);
       eventLayout=(LinearLayout) findViewById(R.id.eventbtlayout);
       ticketLayout=(LinearLayout) findViewById(R.id.ticketbtlayout);
        
        listView=(ListView) findViewById(android.R.id.list);

        admitedTextView=(TextView) findViewById(R.id.admited);
        logoutBT=(Button) findViewById(R.id.logout);
        
        remanigTextView.setText("0");
        admitedTextView.setText("0");

    	
        new ShowDialogAsyncTask().execute();
    	
    	try{
        	responce_event=customHttpClient.executeHttpGet(EVENT_URL);
        	String res=responce_event.toString();
        	System.out.println("responce of service is="+res);
        	Event_json(res);
           
        	
        	
        	}catch (Exception e) 
        	{
        	// TODO: handle exception
        	System.out.println("Eception is="+e);
        	}
        
    	 backButton=(Button) findViewById(R.id.back_bt);
         backButton.setOnClickListener(new OnClickListener() 
         {
 			
 			public void onClick(View v) 
 			{
 				// TODO Auto-generated method stub
 				startActivity(new Intent(List_Activity.this,Home.class));
 				finish();
 				
 			}
 		});
         boxofficeLayout.setOnClickListener(new OnClickListener() {
 			
 			public void onClick(View v) 
 			{
 				// TODO Auto-generated method stub
 				startActivity(new Intent(List_Activity.this,EventList_Activity.class));
 				finish();
 				
 			}
 		});
         scanticketlayLayout.setOnClickListener(new OnClickListener()
         {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
			//	Toast.makeText(getBaseContext(),"CALL", Toast.LENGTH_SHORT).show();
				startActivity(new Intent(List_Activity.this,EventListScan.class));
				finish();
			}
		});
         eventLayout.setOnClickListener(new OnClickListener() 
         {
 			
 			public void onClick(View v) 
 			{
 				// TODO Auto-generated method stub
 				Intent intent=new Intent(List_Activity.this,EventList_Activity.class);
 				startActivity(intent);
 				finish();
 				
 			}
 		});

         logoutBT.setOnClickListener(new OnClickListener() {
 			
 			public void onClick(View v) 
 			{
 				// TODO Auto-generated method stub
 			
 			
                  Save_Login("no","no");
                  Toast.makeText(getBaseContext(),"Logout", Toast.LENGTH_SHORT).show();
                  startActivity(new Intent(List_Activity.this, Login.class));
                  finish();
 			}
 		});
         
         

         
         
         searchEditText.addTextChangedListener(new TextWatcher() {
           	 
      	   
     	    public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
     	        // When user changed the Text
     	    getFilter().filter(cs);
     	    }
     	 
     	    
     	    public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,int arg3) {
     	        // TODO Auto-generated method stub
     	 
     	    }
     	 
     	    
     	    public void afterTextChanged(Editable arg0) {
     	        // TODO Auto-generated method stub
     	    }
     	});

    }

    public void JsonValue(String result)
	{
		int k = 0;
		try{
			DataList = new ArrayList<HashMap<String, String>>();
		      jArray = new JSONArray(result);
		      k=jArray.length();
		      JSONObject json_data=null;
		      for(int i=0;i<jArray.length();i++)
		      {
		    	  map = new HashMap<String, String>();
		             json_data = jArray.getJSONObject(i);
		             map.put(SKDU,json_data.getString(SKDU));
		            
		             DataList.add(map);
		            // nameValuePairs.add(r);
		         }
		   

		         LayoutInflater inflater = this.getLayoutInflater();
				
				
		        adapter=new CustomListAdapter(this,DataList);      
		        listView.setAdapter(adapter);
		     admited=DataList.size()+"";   
		     admited=admited.trim();
		     admitedTextView.setText(admited);
		     
		     
		     
		    
		     
		      }
		      catch(JSONException e1)
		      {
		    	 
		    	  System.out.println(e1);
		    	 
		      }
	}
    
    
    public void Event_json(String result)
    {
		int k = 0;
		try{
			Event_DataList = new ArrayList<HashMap<String, String>>();
		      jArray = new JSONArray(result);
		      k=jArray.length();
		      JSONObject json_data=null;

		      for(int i=0;i<jArray.length();i++)
		      {
		    	  map = new HashMap<String, String>();
		    	 
		             json_data = jArray.getJSONObject(i);
		             map.put(TITLE,json_data.get(TITLE).toString());
		            // map.put(ADDRESS,json_data.get(ADDRESS).toString());
		            // map.put(DATE,json_data.get(DATE).toString());
		             map.put(ID,json_data.get(ID).toString());
		            // map.put(CITY,json_data.get(CITY).toString());
		            // map.put(STUDENT_PRICE,json_data.get(STUDENT_PRICE).toString());
		            // map.put(PRICE,json_data.get(PRICE).toString());
		             Event_DataList.add(map);
		            // System.out.println("jeson object:     "+jArray.getJSONObject(i));
		             
		         }
		      List<String> event_nameList=new ArrayList<String>();
		   for(int i=0;i<Event_DataList.size();i++)
		   {
		     map=Event_DataList.get(i);
		     event_nameList.add(map.get(TITLE).toString());
		   }
		   event_nameList.add(0,"Select");
		        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, event_nameList);
		    	dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		    	spinner1.setAdapter(dataAdapter);
		    	spinner1.setSelection(0);
				
//		        adapter=new CustomEventAdapter(this,DataList);      
//		        listView.setAdapter(adapter);
		        
		     
		      }
		      catch(JSONException e1)
		      {
		    	 
		    	  System.out.println(e1);
		    	 
		      }
    }
    
    public void tiskets_data(String result)
    {
    

		int k = 0;
		try{
			DataList = new ArrayList<HashMap<String, String>>();
		      jArray = new JSONArray(result);
		      k=jArray.length();
		      int number_scan=0;
		      int number_remain=0;
		      JSONObject json_data=null;
		      for(int i=0;i<jArray.length();i++)
		      {
		    	  map = new HashMap<String, String>();
		             json_data = jArray.getJSONObject(i);
		             map.put(NUMBER,json_data.get(NUMBER).toString());
		             map.put(KIND,json_data.getString(KIND).toString());
		             map.put(SCAN_DATE, json_data.getString(SCAN_DATE).toString());
		             String number=json_data.get(NUMBER).toString();
		             boolean scan=json_data.optBoolean(SCANED);
		             String scandate=json_data.getString(SCAN_DATE).toString();
		             if(scan==true)
		             {
		            	 map.put(SCANED,"Already scanned");
		            	 number_scan++;
		             }
		             else
		             {
		            	 map.put(SCANED,"Active");
		             }
		             System.out.println("number: "+number);
		             System.out.println("scan: "+scan);
		             System.out.println("scandate: "+scandate);
		             
		             holoTextView.setVisibility(View.INVISIBLE);
		             System.out.println("tickte data: "+json_data);
		            
		             DataList.add(map);
		            // nameValuePairs.add(r);
		         }
		   

		         LayoutInflater inflater = this.getLayoutInflater();
				
				
		        adapter=new CustomListAdapter(this,DataList);      
		        number_remain=DataList.size()-number_scan;
		        remaing=number_remain+"";
		        remaing=remaing.trim();
		        remanigTextView.setText(remaing);
		        listView.setAdapter(adapter);
		        admited=number_scan+"";   
		        admited=admited.trim();
		        admitedTextView.setText(admited);
		        if (DataList.size()==0) 
		        {
		       searchLayout.setVisibility(View.INVISIBLE);
		        	holoTextView.setText("No ticket for event.");
		        	holoTextView.setVisibility(View.VISIBLE);
				}
		        else 
		        {
					searchLayout.setVisibility(View.VISIBLE);
				}
		       
		     
		     
		     
		    
		     
		      }
		      catch(JSONException e1)
		      {
		    	 
		    	  System.out.println(e1);
		    	  try 
		    	  {
		    		  JSONObject json_data=new JSONObject(result);
				      String success_string=json_data.get("success").toString();
				      if(success_string.equalsIgnoreCase("false"))
				      {
				    	  searchLayout.setVisibility(View.INVISIBLE);
				    	  holoTextView.setText("You are not authorized to access this ticket's.");
				        	holoTextView.setVisibility(View.VISIBLE);
				      }
					
				} catch (Exception e) 
				{
					// TODO: handle exception
				}
		    	 
		      }
    }
    
    
    
    public void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
    	   if (requestCode == 0) 
    	   {
    	      if (resultCode == RESULT_OK)
    	      {
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
			      String success_string=json_data.get("success").toString();

			      System.out.println("value of sss: "+success_string);
			      success_string=success_string.trim();
			      if(success_string.equalsIgnoreCase("false"))
			      {
			    	  String error_mesg=json_data.get("error").toString();
			      }
			      if(success_string.equalsIgnoreCase("true"))
			      {
			    	  json_data=json_data.getJSONObject("ticket");

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
      startActivity(new Intent(List_Activity.this,Home.class));
      finish();
    }


    private class ShowDialogAsyncTask extends AsyncTask<Void, Integer, Void>{
      	 
        int progress_status;
         
        @Override
     protected void onPreExecute() 
        {
    
      super.onPreExecute();

       progress_status = 0;

     progressBar.setVisibility(View.VISIBLE);
       
     }
         
     @Override
     protected Void doInBackground(Void... params) {
       
         responce=null;
	    	try{
	    	responce=customHttpClient.executeHttpGet(All_Ticket_Url);
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
      try{
      tiskets_data(res);
      }catch (Exception e) {
		// TODO: handle exception
	}
     }
       }
    
    public class CheeseFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            // TODO Auto-generated method stub

            constraint = constraint.toString().toLowerCase();

            FilterResults newFilterResults = new FilterResults();

            if (constraint != null && constraint.length() > 0) {


                ArrayList<HashMap<String,String>> auxData = new ArrayList<HashMap<String,String>>();
                HashMap<String,String> map;
                for (int i = 0; i < DataList.size(); i++) 
                {
              	  map=new HashMap<String, String>();
              	  map=DataList.get(i);
                    if (map.get(List_Activity.NUMBER).toLowerCase().contains(constraint))
                        auxData.add(DataList.get(i));
                }

                newFilterResults.count = auxData.size();
                newFilterResults.values = auxData;
            } else {

                newFilterResults.count = DataList.size();
                newFilterResults.values = DataList;
            }

            return newFilterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

        	Context  context;
            ArrayList<HashMap<String,String>> resultData = new ArrayList<HashMap<String,String>>();

            resultData = (ArrayList<HashMap<String,String>>) results.values;

            adapter = new CustomListAdapter(List_Activity.this, resultData);
            listView.setAdapter(adapter);

        // notifyDataSetChanged();
        }

    }

	public Filter getFilter()
	{
		// TODO Auto-generated method stub
		 if(filter == null)
             filter = new CheeseFilter();
         return filter;
		
	}
    
}
