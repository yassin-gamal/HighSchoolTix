package com.HST.highschooltix;

import java.util.ArrayList;



import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class Database_Openhelper extends SQLiteOpenHelper
{
	Cursor cur;
	private static final String DB_NAME="HST";
	private static final int DB_VERSION=10;
	private SQLiteDatabase db;
	ArrayList<String> list;
	//DatabaseCopy db_copy;
	
	@Override
	public void onCreate(SQLiteDatabase db)
	{
		// TODO Auto-generated method stub
	
	
		
		
	}
	
	
	public Database_Openhelper(Context context)
	{
		super(context,DB_NAME,null,DB_VERSION);
		db=getWritableDatabase();
		// TODO Auto-generated constructor stub
	}
	public void close() 
	{
	   db.close();
	}
	
	protected void onDestroy()
	{
		 db.close();
	}


	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
	{
		// TODO Auto-generated method stub
		
	}



	
	public void Inserte_order(String event_id,String event_name,String number_of_ticket,String processing_fee,String total,String name,String orderfee,String date,String kind,String manual_number)
	{
		db=getWritableDatabase();
		String sql="INSERT INTO [cartitme](event_id,eventname,number_of_ticket,processing_fee,total,name,total_processing_fee,event_date,ticket_type,manual_number) VALUES('"+event_id+"','"+event_name+"','"+number_of_ticket+"','"+processing_fee+"','"+total+"','"+name+"','"+orderfee+"','"+date+"','"+kind+"','"+manual_number+"')";
		try
		{
			db.execSQL(sql);
			System.out.println("sql run ");
			
		}
		catch (Exception e)
		{
			// TODO: handle exception
			System.out.println("sql error:="+e);
		}
	}
	
	public void DeleteCart()
	{
		db=getWritableDatabase();
		String sql=" DELETE FROM [cartitme]";
		try
		{
			db.execSQL(sql);
		}catch (Exception e) 
		{
			// TODO: handle exception
			System.out.println("sql error:="+e);
		}
	}
	
	public Cursor getCartData()
	{
		String sql="select * from [cartitme]";
		try
		{
		cur=db.rawQuery(sql,null);
			
		}catch (Exception e) 
		{
			// TODO: handle exception
		}
		return cur;
	}
	

		
	
}