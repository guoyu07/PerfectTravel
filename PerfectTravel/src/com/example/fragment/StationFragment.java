package com.example.fragment;

import java.util.ArrayList;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.perfecttravel.R;
import com.example.perfecttravel.StationResultActivity;
import com.example.perfecttravel.StationSelectActivity;
import com.example.utils.utilsClass;
import com.thinkland.sdk.android.DataCallBack;
import com.thinkland.sdk.android.JuheData;
import com.thinkland.sdk.android.Parameters;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class StationFragment extends Fragment  implements  OnClickListener {

	
	private  RelativeLayout   station_leave_click;//出发城市点击
	private  RelativeLayout   station_arrived_click;//出发城市点击
    private  TextView    station_left_text;//出发城市
    private  TextView    station_arrived_text;//到达城市
    private ImageButton station_select;//查询按钮
    private  RelativeLayout   station_date_click;//日期单击选中
    private  TextView   station_data_show;//日期的显示文本框
    private  ProgressDialog dialog;//进度条对话框
    private  ArrayList<Map<String,Object>>  TrainsInfo;	//用来盛放解析后的数据
    
    private Handler handler=new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			if(msg.what==0x100){//开启线程，请求站到站数据
				
				  //开启查询
	        	   
	        	   dialog=utilsClass.showProgessDialog(getActivity(), "数据查询中，请稍后....");
				      dialog.show();//显示进度条
				      
				      //准备参数
				      Parameters params = new Parameters();
					  params.add("start", station_left_text.getText().toString().trim());//出发城市
					  params.add("end", station_arrived_text.getText().toString().trim());//到达城市
					  params.add("type", "json");
				      
					  JuheData.executeWithAPI(22,//api版本 
							  "http://apis.juhe.cn/train/s2swithprice",//接口路径 
							  JuheData.GET, //请求方式
							  params, 
							  new DataCallBack() {//返回接口
								
								@Override
								public void resultLoaded(int err, String reason, String result) {
									// TODO Auto-generated method stub
									dialog.cancel();//进度条消失
									
									  if (err == 0) {//成功获取数据的情况
										  
										  Log.i("msg","成功情况下result"+result);
										  
										  try {
											  
										      JSONObject resultObject=new JSONObject(result);
											
											Log.i("msg","测试resultObject.getJSONObject().length();"+resultObject)  ;
											 if(resultObject.isNull("result")){//hangban是否存在
													
													Toast.makeText(getActivity(), "线路不存在...", Toast.LENGTH_SHORT).show();
													
												} else{//线路存在
													
													//TrainsInfo=utilsClass.analyzeStation(new JSONObject(result));
												
													
													Intent intent=new Intent(getActivity(), StationResultActivity.class);
													Bundle bundle=new Bundle();
													bundle.putString("citys",station_left_text.getText().toString()+"-"+station_arrived_text.getText().toString());;
													bundle.putString("TrainInfo", result);
													intent.putExtra("stationsInfo", bundle);
													
												 	startActivity(intent);
													
													
												}             
											
								     		} catch (JSONException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
											  
										} else {//非成功获取数据
											
											  Toast.makeText(getActivity(), "数据获取失败...", Toast.LENGTH_SHORT).show();
											  
										}
									  
								  }
							});
	        	   
				
				
			}
			
		}
    	
    };
    
    private  SharedPreferences CityPreferences;//用来记录出发和到达城市的缓存文件
	
    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View loadingView =inflater.inflate(R.layout.fragment_station, null);
		
		station_leave_click=(RelativeLayout) loadingView.findViewById(R.id.fragment_station_leave_click);
		station_arrived_click=(RelativeLayout) loadingView.findViewById(R.id.fragment_station_arrived_click);
		
		station_left_text=(TextView) loadingView.findViewById(R.id.fragment_station_leftStion_text);
		station_arrived_text=(TextView) loadingView.findViewById(R.id.fragment_station_arrivedStion_text);
		
		station_select=(ImageButton) loadingView.findViewById(R.id.fragment_station_selecet);
		station_date_click=(RelativeLayout) loadingView.findViewById(R.id.fragment_station_data);
		station_data_show=(TextView) loadingView.findViewById(R.id.fragment_station_dataShow);		
				
		//初始化显示当天日期
		station_data_show.setText(String.valueOf(utilsClass.getYear())+"-"+String.valueOf(utilsClass.getMouth()+1)+"-"+String.valueOf(utilsClass.getDate()));
		
		  Log.i("msg","prefrence执行");
		CityPreferences=getActivity().getSharedPreferences("Citys", 0);
	    station_arrived_text.setText(CityPreferences.getString("arrivedCity", "北京"));
		station_left_text.setText(CityPreferences.getString("leftCity", "上海"));
		
		
		return  loadingView;
	}


	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		
		this.station_arrived_click.setOnClickListener(this);
		this.station_leave_click.setOnClickListener(this);
		station_arrived_click.setOnClickListener(this);
		station_date_click.setOnClickListener(this);
		station_select.setOnClickListener(this);
		
		super.onStart();
	}

	
	
	
	
	

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		SharedPreferences.Editor editor=CityPreferences.edit();
		editor.putString("arrivedCity", station_arrived_text.getText().toString().trim());
		editor.putString("leftCity", station_left_text.getText().toString().trim());
		editor.commit();
		super.onPause();
	}


	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
		Intent intent=new Intent(getActivity(), StationSelectActivity.class);
		
		switch (arg0.getId()) {
		case R.id.fragment_station_leave_click:
			
			startActivityForResult(intent,  15);//requestCode==15标识从StationFragment跳往stationSelectActivity
			
			break;
        case R.id.fragment_station_arrived_click:
			
          startActivityForResult(intent, 15);//requestCode==15标识从StationFragment跳往stationSelectActivity
        	
			break;
        case  R.id.fragment_station_selecet://点击查询按钮
        	
           if(TextUtils.isEmpty(station_left_text.getText().toString().trim())||TextUtils.isEmpty(station_arrived_text.getText().toString().trim())){
        	   
                     Toast.makeText(getActivity(), "请填写线路信息", Toast.LENGTH_SHORT).show(); 
                     
           }else{
        	 
        	   handler.sendEmptyMessage(0x100);//开启线程，查询按到站信息
        	   
        	   
           }
        	break;
	     case R.id.fragment_station_data:
			
	    	 utilsClass.getCalendarDate(getActivity(),station_data_show); 
	    	 
			   break;
        	
		}
		
	}


	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		// TODO Auto-generated method stub
        		
		
		switch (requestCode) {
		case 15:
			        switch (resultCode) {
					case 1://数据完整
						ArrayList<String> data=intent.getStringArrayListExtra("CitysName");
						
					    	
						  Log.i("msg","写入执行执行");
						  station_left_text.setText(data.get(0).toString().trim());
						  station_arrived_text.setText(data.get(1).toString().trim());
						break;
		        	case 0://数据不完整
						break;
					}
			break;
		}
		super.onActivityResult(requestCode, resultCode, intent);
		
	}

	
	
}
