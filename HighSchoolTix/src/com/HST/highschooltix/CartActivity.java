package com.HST.highschooltix;

import java.util.ArrayList;


import java.util.HashMap;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.w3c.dom.Text;

import com.innerfence.chargeapi.ChargeRequest;
import com.innerfence.chargeapi.ChargeResponse;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class CartActivity extends Activity
{
	static final String TITLE="title";
	static final String PERSON="person";
	static final String AMOUNT="amount";
	static final String FEEAMOUNT="feeamount";
	static final String TOTAL="total";
	static final String TOTALFEE="feetotal";
	static final String DATE="date";
	static final String EVETN_ID="event_id";
	static final String TYPE="type";
	static final String TICKET_NUMBRE="number";
	static final String HOLDER_NAME="name_user";
	
	String user_name;
	 Button logoutBT;
	 static int  cartcount=1;
	   String response;
	   GetTicketUrl getTicketUrl;
	CountDownTimer countDownTimer;
	//private long totalTimeCountInMilliseconds = 2*60* 1000;
	private long totalTimeCountInMilliseconds = 620000;
	TextView  textViewShowTime;
	 Countdown countdown;
		TextView time_to_left;
		LinearLayout refreshLayout;
		Button backbt;
		Button refreshbt;
		String CartGrandtotal;
		double FinalTotal=0.0;
		 long second2;
		 Database_Openhelper database_Openhelper;
		 Cursor cursor;
		 ArrayList<HashMap<String,String>> cartDataArrayList;
		 HashMap<String,String> hashMap;
		 //CustomCartAdapter cartAdapter;
		 cartAdapterNew cartAdapter;
		 ListView listView;
		static TextView cartAmountTextView;
		 Button checkoutButton;
		 CustomHttpClient customHttpClient;
	
		static Toast toast ;
		 
	public void onCreate(Bundle savedInstanceState)
    {
    	
        final DialogInterface.OnClickListener _installCCTerminalListener =
                new DialogInterface.OnClickListener()
                {
                    
                    public final void onClick( DialogInterface dialog, int which )
                    {
                    

                        AlertDialog d = (AlertDialog)dialog;

                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(ChargeRequest.CCTERMINAL_MARKET_LINK));
                        d.getContext().startActivity(intent);
                    }
                };
                
                final DialogInterface.OnClickListener _backlistner =
                        new DialogInterface.OnClickListener()
                        {
                            
                            public final void onClick( DialogInterface dialog, int which )
                            {
                            

                                AlertDialog d = (AlertDialog)dialog;

                               finish();
                               
                              
                            }
                        };
		 super.onCreate(savedInstanceState);
	        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
	        setContentView(R.layout.cartmain);
	        Load_login();
	        time_to_left=(TextView) findViewById(R.id.countdown);
	        listView=(ListView) findViewById(android.R.id.list);
	        cartAmountTextView=(TextView) findViewById(R.id.cart_amount);
	        
	        refreshLayout=(LinearLayout) findViewById(R.id.refresh);
	        backbt=(Button) findViewById(R.id.back_bt);
	        refreshbt=(Button) findViewById(R.id.refreshbt);
	        
	        refreshLayout.setVisibility(View.INVISIBLE);
	        
	        logoutBT=(Button) findViewById(R.id.logout);
	        checkoutButton=(Button) findViewById(R.id.bookbt);
	        cartDataArrayList=new ArrayList<HashMap<String,String>>();
	        
	        database_Openhelper=new Database_Openhelper(getApplicationContext());
	        
	        cursor=database_Openhelper.getCartData();
	        cursor.moveToFirst();
	        while (!(cursor.isAfterLast()))
	        {
	        	hashMap=new HashMap<String, String>();
	        	hashMap.put(TITLE,cursor.getString(0).toString());
	        	hashMap.put(PERSON,cursor.getString(1).toString());
	        	hashMap.put(FEEAMOUNT,cursor.getString(2).toString());
	        	hashMap.put(TOTAL,cursor.getString(3).toString());
	        	hashMap.put(HOLDER_NAME,cursor.getString(4).toString());
	        	
	        //	hashMap.put(AMOUNT,cursor.getString().toString());
	        	hashMap.put(EVETN_ID, cursor.getString(5).toString());
	        	FinalTotal=Double.parseDouble(cursor.getString(3).toString())+FinalTotal;
	        	hashMap.put(TOTALFEE,cursor.getString(6).toString());
	        	hashMap.put(DATE,cursor.getString(7).toString());
	        	hashMap.put(TYPE,cursor.getString(8).toString());
	        	hashMap.put(TICKET_NUMBRE,cursor.getString(9).toString());
	        	cartDataArrayList.add(hashMap);
	        	
				System.out.println("item O: "+cursor.getString(0).toString());
				System.out.println("item 1: "+cursor.getString(1).toString());
				System.out.println("item 2: "+cursor.getString(2).toString());
				System.out.println("item 3: "+cursor.getString(3).toString());
				System.out.println("item 4: "+cursor.getString(4).toString());
				System.out.println("item 5: "+cursor.getString(5).toString());
				System.out.println("item 6: "+cursor.getString(6).toString());
				System.out.println("item 7: "+cursor.getString(7).toString());
				System.out.println("item 7: "+cursor.getString(8).toString());
				
				System.out.println("item 9: "+cursor.getString(9).toString());
				cursor.moveToNext();
			}
	        cursor.close();
	        cartAdapter=new cartAdapterNew(CartActivity.this,cartDataArrayList);
	        
	        
	        listView.setAdapter(cartAdapter);
	        
	        cartAmountTextView.setText("$"+Double.toString((double) Math.round(FinalTotal  * 100) / 100));
	       
	        if(Countdown.second2==1)
	        {
	        	 countdown = new Countdown();
	        	 //Toast.makeText(getBaseContext(), "Call",Toast.LENGTH_SHORT).show();
	        	// database_Openhelper.DeleteCart();
	        }
	        
	    

	        
	        countDownTimer = new CountDownTimer(totalTimeCountInMilliseconds, 1000) {
	            // 500 means, onTick function will be called at every 500 milliseconds 

	            
	            public void onTick(long leftTimeInMilliseconds)
	            {
	            	//Load_Time();
	            	//leftTimeInMilliseconds=rmaintime;
	           // System.out.println("rmaintime "+rmaintime);
	                long seconds = leftTimeInMilliseconds / 1000;
	                //Save_Time(seconds);
	                
	                time_to_left.setText(countdown.time);

	            second2=seconds;
	          
	            }
	            
	            public void onFinish() 
	            {

	            	System.out.println("second time on finish ="+second2);
	            	
	            
//	            	if(second2==1)
//	            	{
	            		//Toast.makeText(getApplicationContext(),"FINISH CART",Toast.LENGTH_SHORT).show();
	            		cartAmountTextView.setText("$0.0");
	            		
	            		cartDataArrayList.clear();
	            		cartAdapter=new cartAdapterNew(getApplicationContext(), cartDataArrayList);
	            		cartAdapter.notifyDataSetChanged();
	            		listView.setAdapter(cartAdapter);
	            		
	            		database_Openhelper.DeleteCart();
	            		
	            		//Toast.makeText(getApplicationContext(),"Cart refresh",Toast.LENGTH_SHORT).show();
	            		database_Openhelper.close();
	            		refreshLayout.setVisibility(View.VISIBLE);
	           		 
	            		
	            		
	            		
	            	//}
	                try {
						//playSound(SetNap.this, getAlarmUri());
					} catch (IllegalStateException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
//					} catch (IOException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
					}
	               // getAlarmUri();
	                
	            }

	        }.start();
	        
	        backbt.setOnClickListener(new OnClickListener()
	        {
				
				public void onClick(View v)
				{
					// TODO Auto-generated method stub
					finish();
					
				}
			});
	        refreshbt.setOnClickListener(new OnClickListener() 
	        {
				
				public void onClick(View v)
				{
					// TODO Auto-generated method stub
					finish();
				}
			});

	        
	        
	        checkoutButton.setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) 
				
				{
					// TODO Auto-generated method stub
		              // Create the ChargeRequest using the default
	                // constructor.
	                ChargeRequest chargeRequest = new ChargeRequest();
	
	                // 2-way Integration
	                //
	                // Credit Card Terminal will return to the activity
	                // that started it when the transaction is complete.
	                //
	                // You can also include app-specific parameters by
	                // calling the setExtraParams() method and passing in
	                // a Bundle object. The extra params will be
	                // accessible to you when we return to your app.
	                //
	                // In this sample, we include an app-specific
	                // "record_id" parameter set to the value 123. You may
	                // call extra parameters anything you like.
	                Bundle extraParams = new Bundle();
	                extraParams.putString( "record_id", "123" );
	                chargeRequest.setExtraParams( extraParams );
	
	                // Finally, we can supply customer and transaction
	                // data so that it will be pre-filled for submission
	                // with the charge.
	                chargeRequest.setAddress("123 Test St");
	                chargeRequest.setAmount(cartAmountTextView.getText().toString());
	                chargeRequest.setCurrency("USD");
	                chargeRequest.setCity("Nowhereville");
	                chargeRequest.setCompany("Company Inc");
	                chargeRequest.setCountry("US");
	                chargeRequest.setDescription("Test transaction");
	                chargeRequest.setEmail("john@example.com");
	                chargeRequest.setFirstName("John");
	                chargeRequest.setInvoiceNumber("321");
	                chargeRequest.setLastName("Doe");
	                chargeRequest.setPhone("555-1212");
	                chargeRequest.setState("HI");
	                chargeRequest.setZip("98021");
	              
	               
	
	                // Submitting the request will launch Credit Card
	                // Terminal from the passed in Activity
	                try
	                {
	                    chargeRequest.submit(CartActivity.this );
	                }
	                catch( ChargeRequest.ApplicationNotInstalledException ex )
	                {
	                    // An ApplicationNotInstalledException is thrown
	                    // when we determine that Credit Card Terminal is
	                    // not installed on the device. We suggest showing
	                    // the user an error with a easy way to download
	                    // the app by showing an AlertDialog similar to
	                    // the one below.
	
	                    new AlertDialog.Builder( CartActivity.this )
	                        .setTitle( "Credit Card Terminal Not Installed" )
	                        .setMessage( "You'll need to install Credit Card Terminal before you can use this feature. Tap Install below to begin the installation process." )
	                        .setPositiveButton( "Install", _installCCTerminalListener )
	                        .setNegativeButton( android.R.string.cancel, null )
	                        .create()
	                        .show();
	                }
					
				}
			});
	        
//	        checkoutButton.setOnClickListener(new OnClickListener() 
//	        {
//				
//				public void onClick(View v) 
//				{
//					// TODO Auto-generated method stub
//					countDownTimer.cancel();
//					countdown.countDownTimer.cancel();
//					//Toast.makeText(getBaseContext(), "Cancle",Toast.LENGTH_SHORT).show();
//					Intent intent=new Intent(CartActivity.this,ResponceFromCharg.class);
//					startActivityForResult(intent,0);
//				}
//			});
	    
	  
    }
	
	
	
    public void onActivityResult( int requestCode, int resultCode, Intent data )
    {
        super.onActivityResult( requestCode, resultCode, data );

        // When the transaction is complete, the calling activity's
        // onActivityResult() method will be called. You can validate
        // that it's returning from our app by confirming the request
        // code matches ours.
        
        if (requestCode == 0) 
 	   {
 	      if (resultCode == RESULT_OK)
 	      {
 	    	
 	    	 // Toast.makeText(getBaseContext(),"call",Toast.LENGTH_SHORT).show();
 	    	  for(int i=0;i<cartDataArrayList.size();i++)
 	    	  {
 	    		  HashMap<String,String> hashMap=new HashMap<String, String>();
 	    		 hashMap=cartDataArrayList.get(i);
 	    		 String event_id=hashMap.get(EVETN_ID);
 	    		String type=hashMap.get(TYPE);
 	    		String username=hashMap.get(HOLDER_NAME);
 	    		String ticket_number=hashMap.get(TICKET_NUMBRE);
 	    		System.out.println(type);
 	    		System.out.println(event_id);
 	    		int qnt=Integer.parseInt(hashMap.get(PERSON).trim());
 	    		System.out.println(type);
 	    		System.out.println(event_id);
 	    		System.out.println(qnt+"");
 	    		
 	    		while(qnt>0)
 	    		{
 	    			  //getTicketUrl=new GetTicketUrl(event_id,type,"xyz","vaibs.malviya25@gmail.com");
 	    			
 	    			 new Thread(
 	    				    getTicketUrl=new GetTicketUrl(event_id,type,username,user_name.trim(),ticket_number)
 	    				).start();
 	    			 cartcount++;
 	    			 
 	    		//String ticket_json=getTicketUrl.Gerate_ticket(event_id,type,"xyz","vaibs.malviya25@gmail.com");
 	    	     //System.out.println("ticket_json ="+ ticket_json);
 	    	     qnt--;
 	    		}
 	    		
 	    		
 	    		 
 	    	  }
 	    	  System.out.println("getTicketUrl.count="+getTicketUrl.count);
 	    	 System.out.println("cartcount="+cartcount);
 	    	  
// 	        new AlertDialog.Builder(this)
//            .setTitle("Email alret")
//            .setMessage( "Email will send on your mail id. please download your ticket from your mail id..")
//            .setNeutralButton( android.R.string.ok, null )
//            .create()
//            .show();
// 	    	 Toast.makeText(getBaseContext(),"Email will send on your mail id. please download your ticket from your mail id..", Toast.LENGTH_LONG).show();
// 	    	 finish();
// 	    	 if (getTicketUrl.count==cartcount) 
//	    		{
 	    	 
// 	    	 
 	    	AlertDialog alertDialog = new AlertDialog.Builder(
                    CartActivity.this).create();

    // Setting Dialog Title
    alertDialog.setTitle("Email alert");

    // Setting Dialog Message
    alertDialog.setMessage( "Email will send on your mail id. please download your ticket from your mail id..");

    // Setting Icon to Dialog
    alertDialog.setIcon(R.drawable.right);

    // Setting OK Button
    alertDialog.setButton("OK", new DialogInterface.OnClickListener() 
    {
            public void onClick(DialogInterface dialog, int which) 
            {
            // Write your code here to execute after dialog closed
            	database_Openhelper.DeleteCart();
        		
        		//Toast.makeText(getApplicationContext(),"Cart refresh",Toast.LENGTH_SHORT).show();
        		database_Openhelper.close();
            	finish();
           
            }
    });
    alertDialog.show();

    // Showing Alert Message
 	    	
// 	        new AlertDialog.Builder(this)
//            .setTitle("Email alret")
//            .setMessage( "Email will send on your mail id. please download your ticket from your mail id..")
//            .setNeutralButton( android.R.string.ok, null )
//            .create()
//            .show();
					
				}
 	    	 
 	    	  
 	    	  
 	      
 	  }
        
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
    
	
    private void Save_Login(String user_name,String password)
		  {
			    SharedPreferences sharedPreferences = getSharedPreferences("LOGIN", MODE_PRIVATE);
			    SharedPreferences.Editor editor = sharedPreferences.edit();
			    editor.putString("username",user_name);
			    editor.putString("password",password);
			  
			    editor.commit();

		}
    
    
    public void Load_login()
    {
    	try {
    		
    		SharedPreferences preferences=getSharedPreferences("LOGIN", MODE_PRIVATE);
    		user_name=preferences.getString("username", "no");
    		//password=preferences.getString("password", "no");
    		
			
		} catch (Exception e) {
			// TODO: handle exception
		}
    }
    
}
