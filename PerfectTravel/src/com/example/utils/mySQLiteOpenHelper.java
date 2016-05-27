package com.example.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * 专门用来更新数据库的类，封装数据库操作
 * @author hehr
 *
 */
public class mySQLiteOpenHelper  extends  SQLiteOpenHelper {

	public mySQLiteOpenHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase arg0) {
		// TODO Auto-generated method stub
	  
		try {
			
			String Sql="create table  tb_stations("
					+ "_id integer  primary key autoincrement,"
					+ "stationsname varchar,"
					+ "key varchar)";
		      	arg0.execSQL(Sql);
		   	Log.i("msg","db  init(succed!)");
		   	
		} catch (Exception e) {
			// TODO: handle exception

			  e.printStackTrace();
		  	Log.i("msg","db  init(false)");	
			
		}
	
	   	
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	
	
}
