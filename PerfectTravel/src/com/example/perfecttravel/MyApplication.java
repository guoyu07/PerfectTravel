package com.example.perfecttravel;


import com.example.utils.mySQLiteOpenHelper;
import com.thinkland.sdk.android.SDKInitializer;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
public class MyApplication extends Application {

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		
		super.onCreate();
		
		//初始化聚合SDK
		SDKInitializer.initialize(getApplicationContext()); 
		  
	}
	
}
