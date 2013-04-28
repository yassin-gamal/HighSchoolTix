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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class EventList_Activity extends Activity  implements Filterable
{
	ListView  listView;
	Button backButton;
	CustomHttpClient customHttpClient;
	CustomEventAdapter adapter;
	static final String TITLE="title";
	static final String ADDRESS="address";
	static final String DATE="date";
	static final String ID="id";
	static final String CITY="city";
	static final String STUDENT_PRICE="student_price";
	static final String PRICE="price";
	RelativeLayout searchLayout;
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
	  String[] newShortMonths = { "January", "February", "March", "April", "May", "June", "July", "August", "September",
		        "October", "November", "December" };
	    DateFormatSymbols symbols;
	    DateFormat format;
	    DateFromatChanger changer;
	CheeseFilter filter;
	
//	String URL="http://111.118.248.140/HighSchool/script/test.php";
	String URL="http://obscure-depths-9305.herokuapp.com/events.json";
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
        	 setContentView(R.layout.eventmain);
            
             
             Load_auth_token();
       // customHttpClient=new CustomHttpClient();
        
        listView=(ListView) findViewById(android.R.id.list);
        progressBar=(ProgressBar) findViewById(R.id.progressBar1);
        searchEditText=(EditText) findViewById(R.id.search);
        searchLayout=(RelativeLayout) findViewById(R.id.search_layout);
    
        symbols = new DateFormatSymbols();
        
        scanticketlayLayout=(LinearLayout) findViewById(R.id.scanbtlayout);
        boxofficeLayout=(LinearLayout) findViewById(R.id.boxfiicebtlayout);
        eventLayout=(LinearLayout) findViewById(R.id.eventbtlayout);
        ticketLayout=(LinearLayout) findViewById(R.id.ticketbtlayout);
 
   	    symbols.setShortMonths(newShortMonths);
        format = new SimpleDateFormat("MMM dd yyyy", symbols);

        logoutBT=(Button) findViewById(R.id.logout);
    
        new ShowDialogAsyncTask().execute();
        String responce=null;
        
        scanticketlayLayout.setOnClickListener(new OnClickListener()
        {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
			//	Toast.makeText(getBaseContext(),"CALL", Toast.LENGTH_SHORT).show();
				startActivity(new Intent(EventList_Activity.this,EventListScan.class));
				finish();
			}
		});
        ticketLayout.setOnClickListener(new OnClickListener() 
        {
			
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				startActivity(new Intent(EventList_Activity.this,List_Activity.class));
				finish();
				
			}
		});

        listView.setOnItemClickListener(new OnItemClickListener()
        {

			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) 
			{
				// TODO Auto-generated method stub

				
				HashMap<String,String> hashMap=new HashMap<String, String>();
				hashMap=DataList.get(position);
				String id=hashMap.get(ID).toString();
				String student_price=hashMap.get(STUDENT_PRICE).toString();
				String genral_price=hashMap.get(PRICE).toString().toString();
				Intent intent=new Intent(EventList_Activity.this,EventDetailActivity.class);
				intent.putExtra("id",id);
				Save_EventID(id);
				intent.putExtra("G",genral_price);
				intent.putExtra("S",student_price);
				startActivity(intent);
				
			}
		});
        
        logoutBT.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub
			
				Intent intent=new Intent(EventList_Activity.this,Login.class);
                 Save_Login("no","no");
                 
                 startActivity(new Intent(EventList_Activity.this, Login.class));
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

    	
    	 backButton=(Button) findViewById(R.id.back_bt);
         backButton.setOnClickListener(new OnClickListener() 
         {
 			
 			public void onClick(View v) 
 			{
 				// TODO Auto-generated method stub
 				startActivity(new Intent(EventList_Activity.this,Login.class));
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
		             DataList.add(map);
		            // AddressList.add(json_data.get(ADDRESS).toString());
		             System.out.println("jeson object:     "+jArray.getJSONObject(i));
		             
		         }
		   
			
				
		        adapter=new CustomEventAdapter(this,DataList);      
		        listView.setAdapter(adapter);
		        
		     
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
			      String success_string=json_data.get("success").toString();
			      
			     // String error_mesg=json_data.get("error").toString();
			      System.out.println("value of sss: "+success_string);
			      success_string=success_string.trim();
			      if(success_string.equalsIgnoreCase("false"))
			      {
			    	  String error_mesg=json_data.get("error").toString();
			    	  //Toast.makeText(getBaseContext(), error_mesg, Toast.LENGTH_LONG).show();
			    	  Intent intent=new Intent(EventList_Activity.this,Login.class);
			    	  intent.putExtra("state","0");
			    	  intent.putExtra("kind","no");
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
			    	  Intent intent=new Intent(EventList_Activity.this,Login.class);
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
	
//	private void showdialog(String title_st,String add_st,String city_st,String price_st,String st_price_st) {
//        final Dialog dialog = new Dialog(EventList_Activity.this);
//        dialog.setContentView(R.layout.test);
//        dialog.setTitle(title_st);
//
//        // set the custom dialog components - text, image and button
//      //  TextView title = (TextView) dialog.findViewById(R.id.title);
//        TextView address = (TextView) dialog.findViewById(R.id.address);
//        TextView city = (TextView) dialog.findViewById(R.id.city);
//      //  TextView price = (TextView) dialog.findViewById(R.id.price);
//       // TextView student_price = (TextView) dialog.findViewById(R.id.student_price);
//        
//       // title.setText(title_st);
//        address.setText(add_st);
//        city.setText(city_st);
////        price.setText(price_st);
////        student_price.setText(st_price_st);
//
//        Button dialogButton = (Button) dialog.findViewById(R.id.button1);
//        // if button is clicked, close the custom dialog
//        dialogButton.setOnClickListener(new OnClickListener() {
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
//
//        dialog.show();
//
//    }

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
    
    private void Save_EventID(String ID)
	  {
		    SharedPreferences sharedPreferences = getSharedPreferences("ID", MODE_PRIVATE);
		    SharedPreferences.Editor editor = sharedPreferences.edit();
		    editor.putString("ID",ID);
		    editor.commit();

	}
    
    @Override
    public void onBackPressed() 
   {
	
    
	   super.onBackPressed();
//	   if(user_name.equalsIgnoreCase("no")||password.equalsIgnoreCase("no"))
//       {
		   startActivity(new Intent(EventList_Activity.this,MainScreen.class));
       	
//       }else
//       {
//    	   finish();
//	}
	   
     
     
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
       
//      progressBar.setProgress(values[0]);
//      messaegTextView.setText("downloading " +values[0]+"%");
       
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
                    if (map.get(EventList_Activity.ADDRESS).toLowerCase().contains(constraint))
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

            adapter = new CustomEventAdapter(EventList_Activity.this, resultData);
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
