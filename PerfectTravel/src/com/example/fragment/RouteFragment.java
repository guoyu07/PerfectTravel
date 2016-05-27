package com.example.fragment;

import java.util.ArrayList;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.perfecttravel.R;
import com.example.perfecttravel.RouteResultActivity;
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
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class RouteFragment extends Fragment   implements OnClickListener{

	
	private  RelativeLayout   route_leave_click;//出发城市点击
	private  RelativeLayout   route_arrived_click;//出发城市点击
    private  TextView    route_left_text;//出发城市
    private  TextView    route_arrived_text;//到达城市
    private ImageButton route_select;//查询按钮
    private  RelativeLayout   route_date_click;//日期单击选中
    private  TextView   route_data_show;//日期的显示文本框
    private  ProgressDialog dialog;//进度条对话框
    private  ArrayList<Map<String,Object>>  FlightInfo;	//用来盛放解析后的数据
    
   private    Handler handler=new Handler(){

	@Override
	public void handleMessage(Message msg) {
		// TODO Auto-generated method stub
		
		if(msg.what==0x100){//开启线程，请求数据
			
			  
     	   dialog=utilsClass.showProgessDialog(getActivity(), "数据查询中，请稍后....");
			      dialog.show();//显示进度条
			
			      Parameters params = new Parameters();
			      params.add("start", route_left_text.getText().toString().trim());
			      params.add("end", route_arrived_text.getText().toString().trim());
			      params.add("date", route_data_show.getText().toString());
			      params.add("dtype", "json");
			      
			      JuheData.executeWithAPI(20,
			    		  "http://apis.juhe.cn/plan/bc", 
			    		  JuheData.GET, params, 
			    		  new DataCallBack() {
							
							@Override
							public void resultLoaded(int err, String reason, String result) {
								// TODO Auto-generated method stub
								
								dialog.cancel();//进度条消失
								
								if(err == 0){//成功获取数据
									
									  Log.i("msg","成功情况下result"+result);
									  
									  try {
										  
									      JSONObject resultObject=new JSONObject(result);
										
										Log.i("msg","测试resultObject.getJSONObject().length();"+resultObject)  ;
										 if(resultObject.isNull("result")){//hangban是否存在
												
												Toast.makeText(getActivity(), "线路不存在...", Toast.LENGTH_SHORT).show();
												
											} else{//线路存在
												
												//TrainsInfo=utilsClass.analyzeStation(new JSONObject(result));
											
												
												Intent intent=new Intent(getActivity(),RouteResultActivity.class);
												Bundle bundle=new Bundle();
												bundle.putString("date",route_left_text.getText().toString()+"-"+route_arrived_text.getText().toString()+"     "+route_data_show.getText().toString().trim());;
												bundle.putString("FlightInfo", result);
												bundle.putString("passDate",route_data_show.getText().toString().trim() );//把日期数据传递过去
												intent.putExtra("Info", bundle);
												
											 	startActivity(intent);
												
											}             
										
							     		} catch (JSONException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
										
								Toast.makeText(getActivity(), "数据异常...", Toast.LENGTH_SHORT).show();
								
							     		}
									
								}else{
									
									Toast.makeText(getActivity(), "数据获取失败...", Toast.LENGTH_SHORT).show();
									
								}
								
							}
						});
			
		}
		
		
		super.handleMessage(msg);
	}
    	
    };
    
    private  SharedPreferences CityPreferences;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
	View loadingView =inflater.inflate(R.layout.fragment_route, null);
	
	route_leave_click=(RelativeLayout) loadingView.findViewById(R.id.fragment_route_left_click);
	route_arrived_click=(RelativeLayout) loadingView.findViewById(R.id.fragment_route_arrived_click);	
	route_left_text=(TextView) loadingView.findViewById(R.id.fragment_route_leftStion_text);
	route_arrived_text=(TextView) loadingView.findViewById(R.id.fragment_route_arrivedStion_text);	
	route_select=(ImageButton) loadingView.findViewById(R.id.fragment_route_selecet);
	 route_date_click=(RelativeLayout) loadingView.findViewById(R.id.fragment_route_data);
	route_data_show=(TextView) loadingView.findViewById(R.id.fragment_route_dataShow);
	
	//初始化显示当天日期
	route_data_show.setText(String.valueOf(utilsClass.getYear())+"-"+String.valueOf(utilsClass.getMouth()+1)+"-"+String.valueOf(utilsClass.getDate()));
	
	CityPreferences=getActivity().getSharedPreferences("Citys",0);
	
	route_arrived_text.setText(CityPreferences.getString("arrivedCity", "北京"));
	route_left_text.setText(CityPreferences.getString("leftCity", "上海"));
	
	
	return loadingView;
	}

	
	
	
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		
		route_leave_click.setOnClickListener(this);
		route_arrived_click.setOnClickListener(this);
		route_date_click.setOnClickListener(this);
		route_select.setOnClickListener(this); 
		
	}

	
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		
		SharedPreferences.Editor editor=CityPreferences.edit();
		editor.putString("arrivedCity", route_arrived_text.getText().toString().trim());
		editor.putString("leftCity", route_left_text.getText().toString().trim());
		editor.commit();
		
		super.onPause();
	}




	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
		
	Intent intent=new Intent(getActivity(), StationSelectActivity.class);
		
		switch (arg0.getId()) {
		case R.id.fragment_route_data://日历控件显示
			
			utilsClass.getCalendarDate(getActivity(),route_data_show); 
			
			break;
         
		case R.id.fragment_route_arrived_click://到达城市
			
		startActivityForResult(intent,  16);//requestCode==16标识从StationFragment跳往stationSelectActivity
		
			break;
		case R.id.fragment_route_left_click://出发城市
			
			startActivityForResult(intent,  16);//requestCode==16标识从StationFragment跳往stationSelectActivity	
			
			break;
			
		case R.id.fragment_route_selecet://查询
			
			if(TextUtils.isEmpty(route_arrived_text.getText().toString())||TextUtils.isEmpty(route_left_text.getText().toString())){
				
				   Toast.makeText(getActivity(), "请填写线路信息", Toast.LENGTH_SHORT).show(); 
				
			}else{
				
				 handler.sendEmptyMessage(0x100);
				
			}
			
			break;
			
		}
		
	}




	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, intent);
		
		switch (requestCode) {
		case 16:
			
			        switch (resultCode) {
					case 1://数据完整
						
						
						ArrayList<String> data=intent.getStringArrayListExtra("CitysName");
						
						  Log.i("msg","数据接受成功"+data.toString());
						  
						  route_left_text.setText(data.get(0).toString().trim());
						  route_arrived_text.setText(data.get(1).toString().trim());
						   
						break;
		        	case 0://数据不完整
						
		        		
						break;
					}
			
			break;

		}
	}

	
	
}
