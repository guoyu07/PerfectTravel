package com.example.perfecttravel;

import java.util.ArrayList;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.adapter.StationsBaseAdapter;
import com.example.utils.utilsClass;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class StationResultActivity extends Activity {

	
	@ViewInject(R.id.station_result_listview) ListView  stationResultListview;//整个页面上用来填充数据的listview
	@ViewInject(R.id.station_result_title_text) TextView stationResulttitle;
	private String resultDate;//上一页取到的未经解析的数据
    private  ArrayList<Map<String,Object>>  TrainsInfo;	//用来盛放解析后的数据
	private  ProgressDialog dialog;
    private  ActionBar actionBar;
    private  ImageButton actionbar_station_result_back;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_station_result);
		
		resultDate=getIntent().getBundleExtra("stationsInfo").getString("TrainInfo").toString().trim();
		
		
	      actionBar = getActionBar();  
	      actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
	      actionBar.setCustomView(R.layout.actionbar_station_result);
	      
	      actionbar_station_result_back=(ImageButton) findViewById(R.id.actinbar_station_result_back);
	      actionbar_station_result_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				  StationResultActivity.this.finish();
				
			}
		});
	      
		ViewUtils.inject(StationResultActivity.this);//将activy注入
		stationResulttitle.setText(getIntent().getBundleExtra("stationsInfo").getString("citys").toString().trim());
		
	   //	Log.i("msg", "数据传递成功："+resultDate);
		
	}


	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		
	dialog=utilsClass.showProgessDialog(StationResultActivity.this, "数据解析中...");
    dialog.show();
	try {
		
			TrainsInfo=utilsClass.analyzeStation(new JSONObject(resultDate));
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			dialog.cancel();
			Toast.makeText(StationResultActivity.this, "数据解析异常", Toast.LENGTH_SHORT).show();
		}
	
	
		//数据成功解析，准备填充数据
	 stationResultListview.setAdapter(new StationsBaseAdapter(TrainsInfo, StationResultActivity.this));
	           dialog.cancel();
		
	}

	
	
	
}
