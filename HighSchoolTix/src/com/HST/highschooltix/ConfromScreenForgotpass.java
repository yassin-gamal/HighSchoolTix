package com.HST.highschooltix;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

public class ConfromScreenForgotpass extends Activity
{

	Button backbt;
	
	  @Override
	    public void onCreate(Bundle savedInstanceState)
	    {
	        super.onCreate(savedInstanceState);
	        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
	        setContentView(R.layout.confromationforgotpass);
	        
	        backbt=(Button) findViewById(R.id.backbt);
	        
	        backbt.setOnClickListener(new OnClickListener() 
	        {
				
				public void onClick(View v) 
				{
					// TODO Auto-generated method stub
					finish();
					
				}
			});
	        
	    }
}
