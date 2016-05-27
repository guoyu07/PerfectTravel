package com.example.perfecttravel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.example.utils.ClearEditText;
import com.example.utils.DButils;
import com.example.utils.utilsClass;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class StationSelectActivity extends Activity  implements OnClickListener {

	@ViewInject(R.id.stationSelect_left)	private  ClearEditText stationSelect_left;//始发城市
	@ViewInject(R.id.stationSelect_arrived)  private  ClearEditText stationSelect_arrived;//到达城市
	@ViewInject(R.id.stationSelect_gv)  private  GridView stationSelect_gv;
	 private  View focusview;//当前屏幕焦点控件
	 private  ActionBar actionBar;
	 private  ImageButton actionbar_station_select_back;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_station_select);
		
		      actionBar = getActionBar();  
		      actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		      actionBar.setCustomView(R.layout.actionbar_station_select);
		       
		      actionbar_station_select_back=(ImageButton) findViewById(R.id.actinbar_station_select_back);
		      actionbar_station_select_back.setOnClickListener(this);
		
             ViewUtils.inject(StationSelectActivity.this);
             
             
             stationSelect_gv.setAdapter(new SimpleAdapter(this,
            		 DButils.getHotCity() ,
            		 R.layout.station_select_item, 
            		 new String[]{"cityName"},
            		 new int[]{R.id.cityName}));
              
             stationSelect_left.requestFocus();//让出发城市获取焦点
             
	}


	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		
		
		stationSelect_gv.setOnItemClickListener(new OnItemClickListener() {
			
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				
				focusview=getCurrentFocus();
				
				if(focusview instanceof  ClearEditText){
					
					HashMap<String,String>  date= (HashMap) stationSelect_gv.getItemAtPosition(arg2);
					
					((EditText) focusview).setText(date.get("cityName"));
					
				}

			}
		});
		
		super.onStart();
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		final Intent intent=new Intent(StationSelectActivity.this, MainActivity.class);
		 
		 ArrayList<String> data=new ArrayList<String>();
		 data.add(stationSelect_left.getText().toString().trim());
		 data.add(stationSelect_arrived.getText().toString().trim());
		
		    intent.putExtra("CitysName", data);
		       
		switch (arg0.getId()) {
		case R.id.actinbar_station_select_back://返回
			
                 if(TextUtils.isEmpty(stationSelect_arrived.getText().toString().trim())
               		  ||TextUtils.isEmpty(stationSelect_left.getText().toString().trim())){
               	
        			 AlertDialog.Builder builder=new Builder(this);
 			       builder.setMessage("车站信息未填写完整,确定返回?");
 			       
 			       builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
 					
 					@Override
 					public void onClick(DialogInterface arg0, int arg1) {
 						// TODO Auto-generated method stub
 						StationSelectActivity.this.setResult(0, intent);  //resultCode==0代表数据不完整
 						   StationSelectActivity.this.finish();
 						   
 					}
 				});
 			       builder.setNegativeButton("取消", null);
 			       builder.show();
               	
               	  
                 }		else{
               	  
               	  Log.i("msg",data.toString());
               	  StationSelectActivity.this.setResult(1, intent);  //resultCode==1代表数据完整
						   StationSelectActivity.this.finish();
               	  
                 }	
			
			break;
		
			
		}
	}
  
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		      
		 if (keyCode == KeyEvent.KEYCODE_BACK) { 
			 
			 final Intent intent=new Intent(StationSelectActivity.this, MainActivity.class);
			    ArrayList<String> data=new ArrayList<String>();
			    data.add(stationSelect_left.getText().toString().trim());
			    data.add(stationSelect_arrived.getText().toString().trim());
			       intent.putExtra("CitysName", data);
			   
			 if(TextUtils.isEmpty(stationSelect_arrived.getText().toString().trim())
              		  ||TextUtils.isEmpty(stationSelect_left.getText().toString().trim())){
              	
       			 AlertDialog.Builder builder=new Builder(this);
			       builder.setMessage("车站信息未填写完整,确定返回?");
			       
			       builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						StationSelectActivity.this.setResult(0, intent);  //resultCode==0代表数据不完整
						   StationSelectActivity.this.finish();
						   
					}
				});
			       builder.setNegativeButton("取消", null);
			       builder.show();
              	
              	  
                }		else{
              	  
              	  Log.i("msg",data.toString());
              	  StationSelectActivity.this.setResult(1, intent);  //resultCode==1代表数据完整
						   StationSelectActivity.this.finish();
              	  
                }	
			
			 
			 
	        } 
		return super.onKeyDown(keyCode, event);
	}

}
