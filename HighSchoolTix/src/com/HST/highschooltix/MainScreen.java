package com.HST.highschooltix;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainScreen extends Activity
{
	Button bookButton;
	Button scanButton;
	String user_name;
	String password;
	 Button logoutBT;
	 DatabaseCopy dbcopy;

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
	        	
	        setContentView(R.layout.activity_main);
//	        dbcopy=new DatabaseCopy();
//			  AssetManager am = null;
//		       am=getAssets();
//		       dbcopy.copy(am);
	        logoutBT=(Button) findViewById(R.id.logout);
	        bookButton=(Button) findViewById(R.id.bookapps);
	        scanButton=(Button) findViewById(R.id.scanapps);
	        
	        bookButton.setOnClickListener(new OnClickListener()
	        {
				
				public void onClick(View v)
				{

					Intent intent=new Intent(MainScreen.this,EventList_Activity.class);
					startActivity(intent);
					finish();
					
				}
			});
	        scanButton.setOnClickListener(new OnClickListener()
	        {
				
				public void onClick(View v)
				{
					// TODO Auto-generated method stub
					//Toast.makeText(getBaseContext(), "SCAN",Toast.LENGTH_SHORT).show();
					startActivity(new Intent(MainScreen.this,Home.class));
					finish();
					
				}
			});
	        
	        logoutBT.setOnClickListener(new OnClickListener() {
				
	  				public void onClick(View v) 
	  				{
	  					// TODO Auto-generated method stub
	  				
	  					Intent intent=new Intent(MainScreen.this,Login.class);
	  	                 Save_Login("no","no");
	  	                 
	  	                 startActivity(new Intent(MainScreen.this, Login.class));
	  	                 finish();
	  				}
	  			});
	        
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
	        
}
