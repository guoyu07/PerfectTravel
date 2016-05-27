package com.example.perfecttravel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.bean.Flight;
import com.example.utils.utilsClass;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.thinkland.sdk.android.DataCallBack;
import com.thinkland.sdk.android.JuheData;
import com.thinkland.sdk.android.Parameters;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class RouteResultActivity extends Activity {
	
	@ViewInject(R.id.route_result_title_text)  TextView routeResultTitle;
	@ViewInject(R.id.route_result_listview)  ListView  routeListView;
	private String resultDate;//上一页取到的未经解析的数据
    private  ArrayList<Map<String,Flight>>  FlightInfo;	//用来盛放解析后的数据
    private  ProgressDialog dialog;
    private ActionBar actionBar;
    private  ImageButton actionbar_back;
    private ArrayList<Map<String,String>> prearedData;
    private  String passDate;//用来存储截取出来的日期数据，用以请求航班信息数据
    private  String flightNum;//待查询的航班号
    
    private  Handler handler=new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			
			if(msg.what==0x100){//接收到查询航班的消息
				//测试数据
/*				Toast.makeText(RouteResultActivity.this, "航班号："+flightNum+"  "+"时间："+passDate, Toast.LENGTH_LONG).show();
*/				
				//航班号不为空
				if(flightNum.length()==0||flightNum.isEmpty()){
					
					Toast.makeText(RouteResultActivity.this, "数据异常，请重试...", Toast.LENGTH_SHORT).show();
					
				}else{
					dialog=utilsClass.showProgessDialog(RouteResultActivity.this, "数据查询中，请稍后....");
				      dialog.show();
				  Parameters params = new Parameters();
			      params.add("name", flightNum);
			      params.add("date", passDate);
			      params.add("dtype", "json");
	/*		      params.add("key", "c93d20cac58f7dba518d4d9fc9647873");*/
				  //参数：数据ｉｄ，请求路径，请求方式，携带参数，回调函数
				  JuheData.executeWithAPI(20, 
						  "http://apis.juhe.cn/plan/s", 
						  JuheData.GET, params, 
						  new DataCallBack() {
				  @Override
				  public void resultLoaded(int err, String reason, String result) {
				      // TODO Auto-generated method stub
				     
					  dialog.cancel();
					  
					  if (err == 0) {//成功获取数据的情况
						  
						  try {
							  
						JSONObject resultObject=new JSONObject(result);
							
					
							 if(resultObject.isNull("result")){//判断车次是否存在
									
									Toast.makeText(RouteResultActivity.this, "航班不存在...", Toast.LENGTH_SHORT).show();
									
								} else{
									
							      //成功获取到数据，在此处仅专递数据，并不对数据进行处理
									
									Bundle bundle=new Bundle();
									bundle.putString("result", result);//将JSON数据直接传递过去
									bundle.putString("date", passDate);//将日期传递过去
									Intent intent=new Intent(RouteResultActivity.this,FlightResultActivity.class);
									intent.putExtra("FlightBundle", bundle);
									   startActivity(intent);
									   
								}             
							
				     		} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
							  
						} else {//非成功获取数据
							
							  Toast.makeText(RouteResultActivity.this, "数据获取失败...", Toast.LENGTH_SHORT).show();
							  
						}
					  
				  }
				  });
					
				}
				
				
			}
			
			
		}
    	
    };
    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_route_result);
          ViewUtils.inject(RouteResultActivity.this);
        routeResultTitle.setText(getIntent().getBundleExtra("Info").getString("date").toString().trim());
        resultDate=getIntent().getBundleExtra("Info").getString("FlightInfo").toString().trim();
       passDate=getIntent().getBundleExtra("Info").getString("passDate").toString().trim();
        //初始化actionbar部分
        actionBar=getActionBar();
 	    actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
 	    actionBar.setCustomView(R.layout.actionbar_route_result);
 		actionbar_back=(ImageButton) findViewById(R.id.actionbar_route_back);//初始化actionbar返回按钮   
        actionbar_back.setOnClickListener(new OnClickListener() {
        	
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				RouteResultActivity.this.finish();
			}
		});
          
	}
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	/*	
		//测试数据
		Toast.makeText(RouteResultActivity.this, "测试数据"+passDate, Toast.LENGTH_SHORT).show();*/
		
		try {
		
			dialog=utilsClass.showProgessDialog(RouteResultActivity.this, "数据解析中...");
		    dialog.show();
			
			FlightInfo=utilsClass.analyzeFlight(resultDate);
			Log.i("msg","数据解析成功："+FlightInfo.toString());
			
			prearedData=this.prepareDat(FlightInfo);//准备好数据
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			Toast.makeText(RouteResultActivity.this, "数据解析失败...", Toast.LENGTH_SHORT).show();
		
		}
		
		  dialog.cancel();
		
	 routeListView.setAdapter(new SimpleAdapter(RouteResultActivity.this,
			 prearedData, 
			 R.layout.route_result_item,
			 new  String[]{"FlightNum","DepTime","ArrTime","Dexpected","Aexpected","status"}, 
			 new int[]{R.id.route_item_FlightNum,
		 R.id.route_item_DepTime,
		 R.id.route_item_ArrTime,
		 R.id.route_item_Dexpected,
		 R.id.route_item_Aexpected,
		 R.id.route_item_status}));
	
	 //设置item的单击事件
	 routeListView.setOnItemClickListener(new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			
			flightNum=prearedData.get(arg2).get("FlightNum");
			
			//发送消息，查询航班信息
			handler.sendEmptyMessage(0x100);
			
		}
	});
	 
	 
	 
	}

	
	/**
	 * 准备适配器数据
	 * @param data
	 * @return  
	 */
	private ArrayList<Map<String,String>> prepareDat(ArrayList<Map<String,Flight>> passData){
		
		ArrayList<Map<String,String>> returnData=new ArrayList<Map<String,String>>();
		    
		for(int i=0;i<passData.size();i++){
			
		    Flight flight= passData.get(i).get("flight");
		    
		Map<String,String> map=new HashMap<String, String>();
		map.put("FlightNum", flight.getFlightNum().toString());//航班号
		map.put("DepTime", timeString(flight.getDepTime().toString()));//预计起飞时间
		map.put("ArrTime",  timeString(flight.getArrTime()));//预计到达时间
		
		//此处需对数据进行处理
		map.put("Dexpected", checkNullString(flight.getDexpected()));//实际起飞时间
		map.put("Aexpected", checkNullString(flight.getAexpected()));//实际到达时间
   
		//此处需判断得到数据
		map.put("status", statuesString(checkNullString(flight.getDexpected())));
			
		    returnData.add(map);
			
		}
		
		      return  returnData;
	}
	
	
	/**
	   * 传入字符串，如果是空字符串返回：--，否则返回：原字符串
	   * @return  String
	   */
	  private   String  checkNullString(String  checkString){
		
		    
		
		if(checkString==null||checkString.equals("null")){
			
		  	     return "--:--";
			
		}else{
			
			   return this.timeString(checkString);
			
		}
		
	    }
	  
	  /**
	   * 截取字符串，返回日期
	   * @param str
	   * @return
	   */
	  private  String  dateString(String str){
		  
		  String temps =str;
		  String arrays[] = temps.split(" ");
		  
		  return arrays[0];
		  
	  }
	  
	  /**
	   * 截取字符串，返回时间
	   * @param str
	   * @return
	   */
	  private  String  timeString(String str){
		  
			  String temps =str;
			  String arrays[] = temps.split(" ");
			  return  arrays[1];
			  
		  
	  }	
	  /**
	   * 得到航班状态的方法，如果  起飞时间不为空即为计划中，否 执行
	   * @param checkString
	   * @return
	   */
	  private  String  statuesString(String checkString){
		  
		  if(checkString.equals("--:--")){
			
			 return "计划";
			  
		  }else{
			  
			  return "执行";
			  
		  }
		  
	  }	
}
