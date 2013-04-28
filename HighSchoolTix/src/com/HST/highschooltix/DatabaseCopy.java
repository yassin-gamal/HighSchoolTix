package com.HST.highschooltix;

import java.io.FileOutputStream;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

public class DatabaseCopy
{
	Context context;
	
	String DB_file_path="/data/data/com.HST.highschooltix/databases/HST";
	String DB_name="HST";
	
	public void copy(AssetManager am)
	{
		InputStream in=null;
		OutputStream out=null;
		System.out.println("callllllllllllll");
		try
		{
		//Database from asset folder
		in=am.open(DB_name);	
		//Database copied into application folder
		// File fileTarget = new File(Environment.getExternalStorageDirectory(), DB_file_path);

		File dbtest =new File(Environment.getExternalStorageDirectory(), DB_file_path);
		if(dbtest.exists())
		{
		  // what to do if it does exist    
			System.out.println("DB already created");
		}
		else
		{
		  // what to do if it doesn't exist
			try {
				File f= new File(DB_file_path);
				f.getParentFile().mkdirs();
				out=new FileOutputStream(f);
				
				copyFile(in, out);
				System.out.println("DB created");
			} catch (Exception e) 
			{
				// TODO: handle exception
				System.out.println("error 1:"+e);
			}
			
			
		}

	
		
		in.close();
		in=null;
		out.flush();
		out.close();
		out=null;
		}
		catch(Exception e)
		{
		Log.d("Databse not found in asset folder or other problem",e.toString());	
		System.out.println("error: "+e);
		}
		
		Log.d("Success","Success");
		
	}
	private void copyFile(InputStream in,OutputStream out) throws IOException
	{
		byte buffer[]=new byte[1024];
		int read;
		while((read=in.read(buffer))!=-1)
		{
			out.write(buffer);
		}
		Log.d("Sq1","Success SQL");
	}
	
}
	


