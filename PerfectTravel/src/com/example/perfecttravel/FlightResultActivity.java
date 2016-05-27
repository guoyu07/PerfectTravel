package com.example.perfecttravel;

import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.utils.SildingFinishLayout;
import com.example.utils.utilsClass;
import com.example.utils.SildingFinishLayout.OnSildingFinishListener;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class FlightResultActivity extends Activity  implements OnClickListener {

    @ViewInject(R.id.flight_result_name)  TextView name;//航班号
    @ViewInject(R.id.flight_result_start)  TextView start;//始发城市
    @ViewInject(R.id.flight_result_end)  TextView end;//目的城市
    @ViewInject(R.id.flight_result_company)  TextView company;//航空公司
    @ViewInject(R.id.flight_result_OnTimeRate)  TextView OnTimeRate;//准点率
    @ViewInject(R.id.flight_result_food)  TextView food;//有无餐食
    @ViewInject(R.id.flight_result_AirModel)  TextView AirModel;//机型
    @ViewInject(R.id.flight_result_DepAirport)  TextView DepAirport;//始发机场
    @ViewInject(R.id.flight_result_DepWeather)  TextView DepWeather;//始发机场天气
    @ViewInject(R.id.flight_result_DepTime)  TextView DepTime;//预计起飞时间
    @ViewInject(R.id.flight_result_DepTerminal)  TextView DepTerminal ;//预计始发机场航站楼
    @ViewInject(R.id.flight_result_DepTel)  TextView DepTel ;//始发机场电话
    @ViewInject(R.id.flight_result_ArrAirport)  TextView ArrAirport;//到达机场
    @ViewInject(R.id.flight_result_ArrWeather)  TextView ArrWeather;//到达机场天气
    @ViewInject(R.id.flight_result_ArrTime)  TextView ArrTime;//预计到达时间
    @ViewInject(R.id.flight_result_ArrTerminal)  TextView ArrTerminal;//预计到达机场航站楼
    @ViewInject(R.id.flight_result_ArrTel)  TextView ArrTel;//到达机场电话
    @ViewInject(R.id.flight_result_DepDate)  TextView DepDate;//出发日期
    @ViewInject(R.id.flight_result_ArrDate)  TextView ArrDate;//到达日期
    
    private ActionBar actionBar;
    private  ImageButton actionbar_flight_back;//actionbar 返回按钮
    private GestureDetector mGestureDetector;//手势识别器
    private LinearLayout layout;
	@Override
	protected void onCreate(Bundle savedInstanceState)   {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_flight_result);
         ViewUtils.inject(FlightResultActivity.this);//将activity注入
         layout=(LinearLayout) findViewById(R.id.layout_flight);
         DepTel.setOnClickListener(this);
         ArrTel.setOnClickListener(this);
         //自定义actionbar，并设置返回按钮监听器
         actionBar=getActionBar(); 
         actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
         actionBar.setCustomView(R.layout.actionbar_flight_result);
         actionbar_flight_back=(ImageButton) findViewById(R.id.actinbar_flight_result_back);
         actionbar_flight_back.setOnClickListener(this);
         
         
         SildingFinishLayout mSildingFinishLayout = (SildingFinishLayout) findViewById(R.id.sildingFinishLayout_flight);  
	        mSildingFinishLayout .setOnSildingFinishListener(new OnSildingFinishListener() {  
	  
	                    @Override  
	                    public void onSildingFinish() {  
	                      FlightResultActivity.this.finish();  
	                    }  
	                });  
	        mSildingFinishLayout.setTouchView(layout);
	        
		String  passDate=getIntent().getBundleExtra("FlightBundle").getString("date");//拿到日期数据
		String  result=getIntent().getBundleExtra("FlightBundle").getString("result");//拿到所有的航班数据
		
		try {
			//解析数据
			Map<String,String> dataMap=utilsClass.analyzeFlight(new JSONObject(result));
			
			   name.setText(this.checkNullString(dataMap.get("name").toString()));
			   start.setText(this.checkNullString(dataMap.get("start")));
			   end.setText(this.checkNullString(dataMap.get("end")));
			   company.setText(this.checkNullString(dataMap.get("complany")));
			   OnTimeRate.setText(this.checkNullString(dataMap.get("OnTimeRate")));
			   
			   food.setText(this.foodString(dataMap.get("food")));//此处需要对数据进行处理
			   
			   AirModel.setText(this.checkNullString(dataMap.get("AirModel")));
			   DepAirport.setText(this.checkNullString(dataMap.get("DepAirport")));
			   DepWeather.setText(this.checkNullString(dataMap.get("DepWeather")));
			  
			   //此处需截取字符串
			   DepDate.setText(this.dateString(dataMap.get("DepTime")));
			   DepTime.setText(this.timeString(dataMap.get("DepTime")));
			   
			   DepTerminal.setText(this.checkNullString(dataMap.get("DepTerminal")));
			   DepTel.setText(this.checkNullString(dataMap.get("DepTel")));
			   ArrAirport.setText(this.checkNullString(dataMap.get("ArrAirport")));
			   ArrWeather.setText(this.checkNullString(dataMap.get("ArrWeather")));
			   
			   //此处需截取字符串
			   ArrDate.setText(this.dateString(dataMap.get("ArrTime")));
			   ArrTime.setText(this.timeString(dataMap.get("ArrTime")));
			   
			   ArrTerminal.setText(this.checkNullString(dataMap.get("ArrTerminal")));
			   ArrTel.setText(this.checkNullString(dataMap.get(" ArrTel")));
   			  
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	Toast.makeText(FlightResultActivity.this, "数据解析失败...请重试", Toast.LENGTH_SHORT).show();
			
		}
		  
	}
  /**
   * 传入字符串，如果是空字符串返回：--，否则返回：原字符串
   * @return  String
   */
  private   String  checkNullString(String  checkString){
	
	    String retrunString=checkString; 
	
	if(retrunString==null||retrunString.equals("")){
		
	  	retrunString="--";
		
	}
	
	     return retrunString;
	
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
  * 对餐食返回数据进行处理的方法
  * @param str
  * @return
  */
  private  String  foodString(String str){
	  
	  String food=str;
	  
	  if(food.equals("0")){
		  
		  food="无餐食";
		  
	  }else if(food.equals("1")){
		  
		  food="有餐食";
		  
	  }else{
		  
		  food="--";
		  
	  }
	  
	  
	  
	  return  food;
  }
@Override
public void onClick(View arg0) {
	// TODO Auto-generated method stub
	switch (arg0.getId()) {
	case R.id.actinbar_flight_result_back://actionbar返回按钮
		
		FlightResultActivity.this.finish();
		
		break;
    case  R.id.flight_result_DepTel://始发机场电话
	   
    	if(!DepTel.getText().toString().trim().equals("--")){//跳转到拨号界面
    		
    		Intent intent = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:" + DepTel.getText().toString().trim()));
    		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    		startActivity(intent);
    		
    	}
	   
	  break;
    case  R.id.flight_result_ArrTel://到达机场电话
 	   
    	
    	if(!"--".equals(ArrTel.getText().toString().trim())){
    		
    		Intent intent = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:" + DepTel.getText().toString().trim()));
    		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    		startActivity(intent);
    		
    	}
 	   
  	  break;
		
	}
	
}
  
}
