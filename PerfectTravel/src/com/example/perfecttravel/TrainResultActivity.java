package com.example.perfecttravel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.bean.Train;
import com.example.utils.SildingFinishLayout;
import com.example.utils.SildingFinishLayout.OnSildingFinishListener;
import com.example.utils.utilsClass;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class TrainResultActivity extends Activity   {

	private  String  trainNumber;
	private  ArrayList<Map<String,Object>>  stationsInfo;
	
	@ViewInject(R.id.train_result_listview)   ListView   train_result_listview;
	
	private ActionBar actionBar;
    private  ImageButton actionbar_train_result_back;
    private  TextView   actionbar_train_result_text;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_train_result);
		ViewUtils.inject(this);

		  actionBar = getActionBar();  
	      actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
	      actionBar.setCustomView(R.layout.actionbar_train_result);
		
	      actionbar_train_result_back=(ImageButton) findViewById(R.id.actionbar_train_result_back);
	      actionbar_train_result_text=(TextView) findViewById(R.id.actionbar_train_result_text);
	      
	      //设置手势动画
	      SildingFinishLayout mSildingFinishLayout = (SildingFinishLayout) findViewById(R.id.sildingFinishLayout_train);  
	        mSildingFinishLayout .setOnSildingFinishListener(new OnSildingFinishListener() {  
	  
	                    @Override  
	                    public void onSildingFinish() {  
	                        TrainResultActivity.this.finish();  
	                    }  
	                });  
	        
	      
		trainNumber=getIntent().getBundleExtra("trainDate").getString("train");
		     String  date=getIntent().getBundleExtra("trainDate").getString("data");
		  
		    actionbar_train_result_text.setText(trainNumber.toString());
		  
		     actionbar_train_result_back.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					
					TrainResultActivity.this.finish();
					
				}
			});
		  
				try {
					
			 stationsInfo=utilsClass.analyzeTrain(new JSONObject(date));  
					 
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		   
				ArrayList<Map<String,String>> data=new ArrayList<Map<String,String>>();
				  
				for(int i=0;i<stationsInfo.size();i++){
					
					Train train=new Train();
					train=(Train) stationsInfo.get(i).get("stationInfo");
					
					Map<String, String>  map=new HashMap<String, String>();
					
					map.put("trainNum", trainNumber.trim());
					map.put("stationName", train.getStation_name().trim());
					map.put("arrivedTime", train.getArrived_time().trim());
					map.put("leaveTime", train.getLeave_time().trim());
					map.put("mileage", train.getMileage().trim());
					
					          data.add(map);
					
				}
				
		
    SimpleAdapter adapter=new SimpleAdapter(this, 
		data, 
		R.layout.train_result_item, 
		new String[]{"trainNum","stationName","arrivedTime","leaveTime","mileage"},
		new int[]{R.id.train_result_item_trainnum,R.id.train_result_item_stationname,R.id.train_result_item_arrivedtime,R.id.train_result_item_leavetime,R.id.train_result_item_mileage});
				
			train_result_listview.setAdapter(adapter);
			
			  mSildingFinishLayout .setTouchView(train_result_listview);
			  
	
	}	
	
}
