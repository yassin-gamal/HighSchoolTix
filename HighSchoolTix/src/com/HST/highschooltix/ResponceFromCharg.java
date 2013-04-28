package com.HST.highschooltix;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

public class ResponceFromCharg extends Activity
{
Button responceButton;
	public void onCreate(Bundle savedInstanceState)
    {
		 super.onCreate(savedInstanceState);
	        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
	        setContentView(R.layout.responcechargxml);
	        responceButton=(Button) findViewById(R.id.responcebt);
	        
	        responceButton.setOnClickListener(new OnClickListener() 
	        {
				
				public void onClick(View v) 
				{
					// TODO Auto-generated method stub
					Intent intent = new Intent();
	                setResult(RESULT_OK, intent);
	                finish();
					
				}
			});
    }
	
	
}
