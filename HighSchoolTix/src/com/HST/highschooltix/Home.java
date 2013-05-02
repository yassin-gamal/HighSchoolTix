package com.HST.highschooltix;

import java.io.BufferedReader;


import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;





import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class Home extends Activity
{
	Button scan_Button;
	Button listButton;
	Button eventButton;
	Button logoutBT;
	
	LinearLayout scanlayout;
	LinearLayout listLayout;
	LinearLayout eventLayout;
	String responce;
	String barcode_url="http://obscure-depths-9305.herokuapp.com/tickets/scan.json";
	 JSONArray jArray;
	 String auth_token;
	 String user_name;
	 String password;
	 DatabaseCopy dbcopy;
	 HttpURLConnection connection;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Load_login();
//        if(user_name.equalsIgnoreCase("no")||password.equalsIgnoreCase("no"))
//        {
//        	finish();
//        }
//        else{
        setContentView(R.layout.home);
        
        Load_auth_token();
//        dbcopy = new DatabaseCopy();
//		  AssetManager am = null;
//	       am=getAssets();
//	       dbcopy.copy(am);
        //Toast.makeText(getBaseContext(), auth_token, Toast.LENGTH_LONG).show();
        scanlayout=(LinearLayout) findViewById(R.id.scanlayout);
        listLayout=(LinearLayout) findViewById(R.id.listlayout);
        eventLayout=(LinearLayout) findViewById(R.id.eventlayout);
        logoutBT=(Button) findViewById(R.id.logout);
        
        scan_Button=(Button) findViewById(R.id.scanbt);
        listButton=(Button) findViewById(R.id.listbt);
        eventButton=(Button) findViewById(R.id.eventbt);
        
       
        
        scanlayout.setOnClickListener(new OnClickListener() 
        {
        	
			
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub
//				 Intent intent = new Intent("com.google.zxing.client.android.SCAN");
//			        startActivityForResult(intent, 0);
				Intent intent=new Intent(Home.this,TicketListAll.class);
				startActivity(intent);
				finish();
				
			}
		});
        
        listLayout.setOnClickListener(new OnClickListener() 
        {
			
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				Intent intent=new Intent(Home.this,List_Activity.class);
				startActivity(intent);
				finish();
				
			}
		});
       
        eventLayout.setOnClickListener(new OnClickListener() 
        {
			
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub
				Intent intent=new Intent(Home.this,EventList_Activity.class);
				startActivity(intent);
				finish();
				
			}
		});
  logoutBT.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub
			
				Intent intent=new Intent(Home.this,Login.class);
                 Save_Login("no","no");
                 Toast.makeText(getBaseContext(),"Logout", Toast.LENGTH_SHORT).show();
                 
                 startActivity(new Intent(Home.this, Login.class));
                 finish();
			}
		});
   //     }
        
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
			      System.out.println("value of sss: "+success_string);
			      success_string=success_string.trim();
			      if(success_string.equalsIgnoreCase("false"))
			      {
			    	  String error_mesg=json_data.get("error").toString();
			    	  System.out.println("json_data:...........="+json_data);
			      }
			      if(success_string.equalsIgnoreCase("true"))
			      {
			    	  json_data=json_data.getJSONObject("ticket");
			    	  
			    	  System.out.println("json_data :"+json_data);
			    	  String kind=json_data.getString("kind").toString();
			    	  System.out.println("kind:"+ kind);

			      }

	    	 } catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					 Toast.makeText(getBaseContext(), "Error "+e, Toast.LENGTH_LONG).show();
					 System.out.println("Error "+e);
				}
		     	
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
	    	startActivity(new Intent(Home.this,MainScreen.class));
	       		     
	     
	    }

	    
	   

}
