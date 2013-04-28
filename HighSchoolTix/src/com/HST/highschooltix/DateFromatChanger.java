package com.HST.highschooltix;

import java.util.StringTokenizer;

import android.widget.Toast;

public class DateFromatChanger
{
	StringTokenizer stringTokenizer;

	public String DateString(String d)
	{
		stringTokenizer=new StringTokenizer(d,"-");
		while(stringTokenizer.hasMoreTokens())
		{
			String yy=stringTokenizer.nextToken();
			String mm=stringTokenizer.nextToken();
			String dd=stringTokenizer.nextToken();
			d=dd+"/"+mm+"/"+yy;
			
		}
		
		
		
		return d;
		
	}
	public String DateWantedFromate(String d)
	{

		
		 
		    String[] result = d.split("\\s");
		    for (int x=0; x<result.length; x++) 
		    {
		      System.out.println(result[x]);
		      
		    }
		    String mm =result[0];
		    String dd =result[1];
		    String yy =result[2];
		    
		    d=mm+" "+dd+", "+yy;
		   System.out.println("data ,,,,,,,......="+d);
		return d;
	}
	
}
