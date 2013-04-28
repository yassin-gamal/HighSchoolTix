package com.HST.highschooltix;

import java.io.IOException;
import java.net.HttpURLConnection;

import java.util.ArrayList;
import java.util.Calendar;

import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;



import com.innerfence.chargeapi.ChargeRequest;
import com.innerfence.chargeapi.ChargeResponse;




import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;



public class CheckoutpageActivity extends Activity
{
	Database_Openhelper database_Openhelper;
	Bundle bundle;
	String URL="http://111.118.248.140/HighSchool/script/forgotpass.php";
	String fee_Url="http://obscure-depths-9305.herokuapp.com/fees.json";
	String promocode_url;
	String title;
	String price;
	String date;
	String type_ticket;
	String type_ticket1;
	String maulanNumber;
	String nameOF;
	String maual_number;
	
	EditText promocodeEditText;
	TextView eventnameTextView;
	TextView cart_total;
	TextView oderfee;
	TextView time_to_left;
	TextView dateTextView;
	TextView ticketTypeTextView;
	Button checkoutButton;
	ImageButton promocodeButton;
	EditText nameEditText;
	EditText manualEditText;
	Spinner spinner;
	LinearLayout manualLinearLayout;
	RelativeLayout numberOfTicketLayout;
	int person;
   double price_double;
   double processingFee;
   String response;
   long second2;
   CustomHttpClient customHttpClient;
  long rmaintime;
   long t;
   long timeBlinkInMilliseconds;  
   boolean blink;  
   Handler mHandler;
   String id;
	CountDownTimer countDownTimer;
	ArrayList<NameValuePair> postParameter;
	String oderfeestring;
	String Cart_total_string;
	private long totalTimeCountInMilliseconds = 15*60* 1000;
	TextView  textViewShowTime;
	 Countdown countdown;
	 String processingfee_normal="0.99";
	 double processing_fee=0.99;
	 HttpURLConnection connection;
	 ProgressBar progressBar;
	 CustomHttpClient client;
	 String res;
	 String respomce;
	 
	 String pos;
    @SuppressLint("ParserError")
	public void onCreate(Bundle savedInstanceState)
    {
    	
    	
        final DialogInterface.OnClickListener _installCCTerminalListener =
                new DialogInterface.OnClickListener()
                {
                    
                    public final void onClick( DialogInterface dialog, int which )
                    {
                        // When the user taps on Install, we'll automatically
                        // open the market link to Credit Card Terminal. The
                        // link is stored at
                        // ChargeRequest.CCTERMINAL_MARKET_LINK

                        AlertDialog d = (AlertDialog)dialog;

                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(ChargeRequest.CCTERMINAL_MARKET_LINK));
                        d.getContext().startActivity(intent);
                    }
                };
    	
    	
    	
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.checkoutxml);
        Load_EvenID();
        bundle=getIntent().getExtras();
        date=bundle.getString("date").toString();
        title=bundle.get("title").toString();
        price=bundle.get("price").toString();
        pos=bundle.get("pos").toString();
        type_ticket=bundle.getString("type").toString();
        type_ticket1=bundle.getString("type1").toString();
        
        database_Openhelper=new Database_Openhelper(getApplicationContext());
        //Toast.makeText(getBaseContext(), price.toString(),Toast.LENGTH_SHORT).show();
        dateTextView=(TextView) findViewById(R.id.date);
        dateTextView.setText(date);
        eventnameTextView=(TextView) findViewById(R.id.eventname);
        spinner=(Spinner) findViewById(R.id.spnner1);
        cart_total=(TextView) findViewById(R.id.cart_amount);
        oderfee=(TextView) findViewById(R.id.oderfee);
        manualLinearLayout=(LinearLayout) findViewById(R.id.manual_entry);
        numberOfTicketLayout=(RelativeLayout) findViewById(R.id.numberofticket);
        
        manualEditText=(EditText) findViewById(R.id.manualEditextbox);
        nameEditText=(EditText) findViewById(R.id.name);
        
       // promocodeButton=(ImageButton) findViewById(R.id.promocodebt);
        progressBar=(ProgressBar) findViewById(R.id.progressBar1);
        checkoutButton=(Button) findViewById(R.id.bookbt);
        time_to_left=(TextView) findViewById(R.id.countdown);
       // promocodeEditText=(EditText) findViewById(R.id.promocodeEditext);
        ticketTypeTextView=(TextView) findViewById(R.id.ticket_type);
        ticketTypeTextView.setText(type_ticket1);
        
        List<String> spinner_list = new ArrayList<String>();
        spinner_list.add("1"); spinner_list.add("2"); spinner_list.add("3");spinner_list.add("4"); spinner_list.add("5");spinner_list.add("6"); spinner_list.add("7");
        spinner_list.add("8"); spinner_list.add("9"); spinner_list.add("10");
        ArrayAdapter<String> dataAdapter_spinner_list = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, spinner_list);
    	dataAdapter_spinner_list.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    	spinner.setAdapter(dataAdapter_spinner_list);
        eventnameTextView.setText(title);
        price_double=Double.parseDouble(price);
       

//        promocodeButton.setOnClickListener(new OnClickListener()
//        {
//			
//			public void onClick(View v) 
//			{
//				// TODO Auto-generated method stub
//				
//				if(promocodeEditText.getText().toString().trim().length()==0)
//				{
//					
//						promocodeEditText.setError("please enter promocode.");
//					
//				
//				}
//				else
//				{
//				 new ShowDialogAsyncTask().execute();
//				}
//			
//			}
//		});
        
        
        
		
        	try 
        	{
				processing_fee=FeeAmount(fee_Url);
			
        	} catch (Exception e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
        	System.out.println("respon=  "+processing_fee);
        	
		
        
        if(pos.equalsIgnoreCase("m"))
        {
        	manualLinearLayout.setVisibility(View.VISIBLE);
        	numberOfTicketLayout.setVisibility(View.INVISIBLE);
        	
        }
        if(pos.equalsIgnoreCase("s"))
        {
        	manualLinearLayout.setVisibility(View.INVISIBLE);
        	numberOfTicketLayout.setVisibility(View.VISIBLE);
        }
        
        spinner.setOnItemSelectedListener(new OnItemSelectedListener()
        {

			public void onItemSelected(AdapterView<?> adapter, View arg1,
					int arg2, long arg3) 
			{
				// TODO Auto-generated method stub
				String item;
				item=(String) adapter.getItemAtPosition(arg2);
				 person=Integer.parseInt(item);
				// processing_fee=0.99;
				 
				 processingFee=person*processing_fee;
				// double ds=(double) Math.round(processingFee * 100) / 100;
				 oderfeestring=Double.toString((double) Math.round(processingFee * 100) / 100);
				 oderfee.setText("$"+Double.toString((double) Math.round(processingFee * 100) / 100));
				// oderfeestring=oderfee.getText().toString();
				 double cart_amount=person*price_double+processingFee;
				 if(processing_fee==0.99)
				 {
				 Cart_total_string=Double.toString(cart_amount);
				 cart_total.setText("$"+Double.toString(cart_amount));
				 }
				 else
				 {
					 Cart_total_string=Double.toString(cart_amount)+0;
					 cart_total.setText("$"+Double.toString(cart_amount)+0); 
				 }
				 
				 
				 
				
			}

			public void onNothingSelected(AdapterView<?> arg0) 
			{
				// TODO Auto-generated method stub
				
			}
		});
        
       

    	checkoutButton.setOnClickListener(new OnClickListener() 
    	{
			
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				nameOF=nameEditText.getText().toString();
			      if(pos.equalsIgnoreCase("m"))
			        {
			       
			        maulanNumber=manualEditText.getText().toString();
			        
			          if(maulanNumber.length()==0||nameOF.length()==0)
			          {
			        	  
			        	  if(nameOF.length()==0)
				          {
				        	  nameEditText.setError("please enter name");
				          }
			        	  if(maulanNumber.length()==0)
				          {
				        	  manualEditText.setError("please enter ticket number");
				          }
			          }
			          else
			          {
			        	  maual_number=maulanNumber.trim();
			        		database_Openhelper.Inserte_order(id, eventnameTextView.getText().toString(),Integer.toString(person).toString(),processingfee_normal,Cart_total_string,nameOF,oderfeestring,date,type_ticket1,maual_number);
							startActivity(new Intent(CheckoutpageActivity.this,CartActivity.class));
							finish();
			          }
			         
			        }
			        if(pos.equalsIgnoreCase("s"))
			        {
			        	
			        	if(nameOF.length()==0)
				          {
				        	  nameEditText.setError("please enter name");
				          }
			        	else
			        	{
			        		 maual_number="no";
			        		database_Openhelper.Inserte_order(id, eventnameTextView.getText().toString(),Integer.toString(person).toString(),processingfee_normal,Cart_total_string,nameOF,oderfeestring,date,type_ticket1,maual_number);
							startActivity(new Intent(CheckoutpageActivity.this,CartActivity.class));
							finish();
			        	}
			        }
			
				
			}
		});
        
    }
    
    public void onActivityResult( int requestCode, int resultCode, Intent data )
    {
        super.onActivityResult( requestCode, resultCode, data );

        // When the transaction is complete, the calling activity's
        // onActivityResult() method will be called. You can validate
        // that it's returning from our app by confirming the request
        // code matches ours.
        if( requestCode == ChargeRequest.CCTERMINAL_REQUEST_CODE )
        {
            ChargeResponse chargeResponse = new ChargeResponse( data );

            String message;
            String title;
            String recordId = null;

            Bundle extraParams = chargeResponse.getExtraParams();
            if( null != extraParams )
            {
                recordId = chargeResponse.getExtraParams().getString("record_id");
            }

            // You may want to perform different actions based on the
            // response code. This example shows an alert with the
            // response data when the charge is approved.
            if ( chargeResponse.getResponseCode() == ChargeResponse.Code.APPROVED )
            {
                title = "Charged!";
                message = String.format(
                    "Record: %s\n" +
                    "Transaction ID: %s\n" +
                    "Amount: %s %s\n" +
                    "Card Type: %s\n" +
                    "Redacted Number: %s",
                    recordId,
                    chargeResponse.getTransactionId(),
                    chargeResponse.getAmount(),
                    chargeResponse.getCurrency(),
                    chargeResponse.getCardType(),
                    chargeResponse.getRedactedCardNumber()
                );
            }
            else // other response code values are documented in ChargeResponse.h
            {
                title = "Not Charged!";
                message = String.format( "Record: %s", recordId );
            }

            // Generally you would do something app-specific here,
            // like load the record specified by recordId, record the
            // success or failure, etc. Since this sample doesn't
            // actually do much, we'll just pop an alert.
            new AlertDialog.Builder(this)
                .setTitle( title )
                .setMessage( message )
                .setNeutralButton( android.R.string.ok, null )
                .create()
                .show();
            
            
            ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
     		postParameters.add(new BasicNameValuePair("m",message));
     		postParameters.add(new BasicNameValuePair("t", title));
     		postParameters.add(new BasicNameValuePair("rid", recordId));
     		try 
    			{
    				response = customHttpClient.executeHttpPost("http://111.118.248.140/v/responce.php",postParameters);
    				System.out.println(response);
    				String s=response.trim();
    				
    				if(s.equalsIgnoreCase("Successful"))
    				{
    					//Toast.makeText(getBaseContext(), "Succesfully...",Toast.LENGTH_SHORT).show();
//    					Intent intent=new Intent(EmailActivity.this,NapActivity.class);
//    					startActivity(intent);
//    					finish();
    				}
    				else
    				{
    					//Toast.makeText(getBaseContext(), "There is problem...",Toast.LENGTH_SHORT).show();
    				}
    			}					 
    		catch (Exception e)
    		{
    // TODO Auto-generated catch block
    			e.printStackTrace();
    		}
       
        }
    }
    
    
    private void Save_Time(long timemile)
	  {
		    SharedPreferences sharedPreferences = getSharedPreferences("TIME", MODE_PRIVATE);
		    SharedPreferences.Editor editor = sharedPreferences.edit();
		    editor.putLong("timemile",timemile);
		   
		    
		
		
		    editor.commit();

	}
    
    public void Load_Time()
    {
    	try {
    		
    		SharedPreferences preferences=getSharedPreferences("TIME", MODE_PRIVATE);
    		rmaintime=preferences.getLong("timemile",123);
    		
    		
			
		} catch (Exception e) {
			// TODO: handle exception
		}
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
    @Override
    public void onBackPressed() 
   {
	
    
	   super.onBackPressed();
//	   if(user_name.equalsIgnoreCase("no")||password.equalsIgnoreCase("no"))
//       {
	//	   startActivity(new Intent(CheckoutpageActivity.this,EventList_Activity.class));
       	
//       }else
//       {
    	   finish();
    	   System.gc();
//	}
	   
     
     
    }
    
    @Override
    protected void onDestroy() {
     super.onDestroy();
     if (database_Openhelper  != null) {
      database_Openhelper.close();
     }
    }

    
    private class ShowDialogAsyncTask extends AsyncTask<Void, Integer, Void>{
      	 
        int progress_status;
         
        @Override
     protected void onPreExecute() 
        {
      // update the UI immediately after the task is executed
      super.onPreExecute();
      client=new CustomHttpClient();
    
      postParameter = new ArrayList<NameValuePair>();
      
      //postParameter.add(new BasicNameValuePair("email",));
      promocode_url="http://obscure-depths-9305.herokuapp.com//orders/discount.json?code="+promocodeEditText.getText().toString().trim();
       progress_status = 0;

     progressBar.setVisibility(View.VISIBLE);
       
     }
         
     @Override
     protected Void doInBackground(Void... params) 
     {

		try {
			
			respomce=client.executeHttpGet(promocode_url);
			 res=respomce.toString();
			System.out.println(res);
	    	//res=res.replaceAll("\\s+",",");
	    	
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			//Toast.makeText(getBaseContext(),"exception  "+e,Toast.LENGTH_SHORT).show();
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
       String prormo_report=PromocodeJson(res);
      progressBar.setVisibility(View.INVISIBLE);
      try{
      if(prormo_report.equalsIgnoreCase("true"))
  	{
    	  
    		processing_fee=0.0;
   		 processingFee=person*processing_fee;
   			// double ds=(double) Math.round(processingFee * 100) / 100;
   			 oderfeestring=Double.toString((double) Math.round(processingFee * 100) / 100);
   			 oderfee.setText("$"+Double.toString((double) Math.round(processingFee * 100) / 100));
   			// oderfeestring=oderfee.getText().toString();
   			 double cart_amount=person*price_double+processingFee;
   			 Cart_total_string=Double.toString(cart_amount)+0;
   			 cart_total.setText("$"+Double.toString(cart_amount)+0);

     		Toast.makeText(getBaseContext(),"promocode accept",Toast.LENGTH_SHORT).show();
  		//date_notfound.setVisibility(View.VISIBLE);
  		
//  		Toast.makeText(getBaseContext(),"login successfull..",Toast.LENGTH_SHORT).show();
//  		Intent intent=new Intent(ForgetScreen.this,ConfromScreenForgotpass.class);
//			startActivity(intent);
    	
			
  	}
  	else
  	{
  	  Toast.makeText(getBaseContext(),"promocode not valid",Toast.LENGTH_SHORT).show();
//  		Intent intent=new Intent(CheckoutpageActivity.this,ConfromScreenForgotpass.class);
//			startActivity(intent);
  	}
      }catch (Exception e) 
      {
		// TODO: handle exception
    	  System.out.println("Error ="+e);
	 }
       //messaegTextView.setText("download complete");
      
     }
       }
    
    public String PromocodeJson(String res)
    {
    	String promo_status = null;
    	try {
    		
    		 JSONObject json_data=new JSONObject(res);
		      promo_status=json_data.get("success").toString();
		      System.out.println("promo_status:"+promo_status);
    
			//promo_status=jsonObject.getString("success").toString();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
		return promo_status;
    	
    }
    
    
    public double FeeAmount(String feeUrl) throws Exception
    {
    	customHttpClient=new CustomHttpClient();
    	String responce_res=customHttpClient.executeHttpGet(fee_Url);
    	//JSONArray array=new JSONArray(responce_res);
    	JSONObject jsonObject=new JSONObject(responce_res) ;
    	//jsonObject=jsonObject;
   //JSONObject jsonObject = null;
    	String threshold=jsonObject.getString("threshold");
    	double threshold_double=Double.parseDouble(threshold);
    	System.out.println("price_double= "+price_double);
    	System.out.println("threshold_double= "+threshold_double);
    	if(price_double<=threshold_double)
    	{
    		processing_fee=Double.parseDouble(jsonObject.getString("lower"));
    		processingfee_normal=jsonObject.getString("lower");
    	}
    	else
    	{
    	
    		processing_fee=Double.parseDouble(jsonObject.getString("higher"));
    		processingfee_normal=jsonObject.getString("higher");
    	}
    	return processing_fee;
    }
    
}
    
