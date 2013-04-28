package com.HST.highschooltix;

import android.content.Context;
import android.os.CountDownTimer;
import android.widget.Toast;

public class Countdown 
{
	
static	CountDownTimer countDownTimer;
	private long totalTimeCountInMilliseconds =10*60* 1000;
	public static String time=null;
	public static Countdown countdown=new Countdown();
	public static long second2;
	Context context;
	Database_Openhelper database_Openhelper;
	
	

  

  Countdown()
  {
	 
	

	  
	// TODO Auto-generated constructor stub

	 
    countDownTimer = new CountDownTimer(totalTimeCountInMilliseconds, 1000) 
    {
        // 500 means, onTick function will be called at every 500 milliseconds 

        
        public void onTick(long leftTimeInMilliseconds)
        {
        	//Load_Time();
        	//leftTimeInMilliseconds=rmaintime;
       // System.out.println("rmaintime "+rmaintime);
            long seconds = leftTimeInMilliseconds / 1000;
           // Save_Time(seconds);
            


            time=String.format("%02d", (seconds / 60)%60)+ " : " + String.format("%02d", seconds % 60);
            System.out.println(String.format("%02d", seconds / 3600) + " : " + String.format("%02d", (seconds / 60)%60)+ " : " + String.format("%02d", seconds % 60) );
            // format the textview to show the easily readable format
       second2=seconds;
        }
        
        public void onFinish() 
        {

if(second2==1)
	{
	
	countDownTimer.cancel();
	time=String.format(String.format("00")+ " : " + String.format("00"));
	System.out.println("second2 ="+second2);
	}
            
        }

    }.start();
}
}
