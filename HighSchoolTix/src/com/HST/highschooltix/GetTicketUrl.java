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
import org.json.JSONException;
import org.json.JSONObject;

import android.R.string;
import android.os.AsyncTask;
import android.widget.Toast;

public class GetTicketUrl implements Runnable
{

	String url_ticket="http://obscure-depths-9305.herokuapp.com/tickets/take.json?auth_token=1KSJ51ar3eG5CWj4hyrr";
	String url_event="http://obscure-depths-9305.herokuapp.com/events.json";
	HttpURLConnection connection;

	static  String response = null; 
	static int count=1;
	static int nocount=1;
	String event_id;
	String kind;
	String holdername;
	String email;
	String ticket_number;
	GetTicketUrl(String event_id,String kind,String holdername,String email,String number)
	{
	this.event_id=event_id;
	this.kind=kind;
	this.holdername=holdername;
	this.email=email;
	this.ticket_number=number;
	}


public String Gerate_ticket()
{           
//	event_id="45";
//	kind="general";
//	holdername="xyz";
    
   OutputStreamWriter request = null;
   
        URL url = null;  
        String parameters;
             if(ticket_number.equalsIgnoreCase("no"))
             {
         parameters = "ticket"+"[event_id]"+"="+event_id+"&ticket"+"[kind]"+"="+kind+"&ticket"+"[holder_name]"+"="+holdername+"&ticket[email]="+email; 
             }
             else
             {
          parameters = "ticket"+"[event_id]"+"="+event_id+"&ticket"+"[kind]"+"="+kind+"&ticket"+"[holder_name]"+"="+holdername+"&ticket[email]="+email+"&ticket[number]="+ticket_number;  
             }
     
        //Toast.makeText(this,"Call"+ response, 0).show();    
        try
        {
        	
        	// Toast.makeText(this,"Call again", 0).show();    
            url = new URL(url_ticket);
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

public String Gerate_EventByData(String date)
{           
	date="2012-05-10";
    
   OutputStreamWriter request = null;

        URL url = null;   
        String response = null;         
        String parameters ="search[date_eq]=2013-09-05";   

        //Toast.makeText(this,"Call"+ response, 0).show();    
        try
        {
        	
        	// Toast.makeText(this,"Call again", 0).show();    
            url = new URL(url_event);
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
            System.out.println("event response = "+response);
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


public void othereMehod()

{
	ArrayList<NameValuePair> search = new ArrayList<NameValuePair>();
	search.add(new BasicNameValuePair("date_eq","2012-05-10"));
	CustomHttpClient client=new CustomHttpClient();
	try {
		String resp=client.executeHttpGet("http://obscure-depths-9305.herokuapp.com/events.json?auth_token=Yx9E3qYgpdW45mUjHDue&search[date_eq]=2013-09-05");
		System.out.println("resp = "+resp);
	
	} catch (Exception e) 
	{
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}


public void run() 
{
	// TODO Auto-generated method stub

	String repString = null;
	try {
		 repString=Gerate_ticket();
	} catch (Exception e)
	{
		// TODO: handle exception
		System.out.println(e);
	}
	
	
	if (!(repString.equalsIgnoreCase("no"))&&!(repString.equalsIgnoreCase(null)))
		{
		System.out.println("count= "+count);
		count++;
		}
	else
		{
		System.out.println("nocount= "+nocount);
		nocount--;
		}
	
	System.out.println("repString= "+repString);

	
}

}
