package com.example.fragment;
import java.util.ArrayList;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.perfecttravel.R;
import com.example.perfecttravel.TrainResultActivity;
import com.example.utils.ClearEditText;
import com.example.utils.utilsClass;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
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

public class TrainFragment extends Fragment   implements OnClickListener{
  
     private  ClearEditText  train_number;//车次输入框
     private  TextView   train_date_show;//日期显示文本框
     private  RelativeLayout   train_date_click;//日期单击选中
     private  ImageButton  train_select_imagebutton;//查询按钮
     private  ProgressDialog dialog;//进度条对话框
     private  ArrayList<Map<String,Object>>  stationsInfo;	//用来盛放解析后的数据
     
     private   Handler handler=new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			
			if(msg.what==0x100){//收到消息，发起请求
				
				  dialog=utilsClass.showProgessDialog(getActivity(), "数据查询中，请稍后....");
			      dialog.show();
			  Parameters params = new Parameters();
			  params.add("name", train_number.getText().toString().trim());
			  params.add("type", "json");
			  //参数：数据ｉｄ，请求路径，请求方式，携带参数，回调函数
			  JuheData.executeWithAPI(22, 
					  "http://apis.juhe.cn/train/s", 
					  JuheData.GET, params, 
					  new DataCallBack() {
			  @Override
			  public void resultLoaded(int err, String reason, String result) {
			      // TODO Auto-generated method stub
			     
				  dialog.cancel();
				  
				  if (err == 0) {//成功获取数据的情况
					  
					  Log.i("msg","错误数据情况下result"+result);
					  
					  try {
						  
					JSONObject resultObject=new JSONObject(result);
						
						Log.i("msg","测试resultObject.getJSONObject().length();"+resultObject)  ;
						 if(resultObject.isNull("result")){//判断车次是否存在
								
								Toast.makeText(getActivity(), "车次不存在...", Toast.LENGTH_SHORT).show();
								
							} else{
								
								 stationsInfo=utilsClass.analyzeTrain(new JSONObject(result));//对数据进行解析  
									  Bundle bundle=new Bundle();
								      bundle.putString("data", result);
								      bundle.putString("train",  train_number.getText().toString().trim().toUpperCase());
								      Intent intent=new Intent(getActivity(), TrainResultActivity.class);
								      intent.putExtra("trainDate", bundle);
								      startActivity(intent);
								      Log.i("msg", reason+":"+result);
								      
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
     
    
     
     @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View loadingView =inflater.inflate(R.layout.fragment_train, container, false);
		   
		     train_date_show=(TextView) loadingView.findViewById(R.id.train_date_show);
		     train_date_click=(RelativeLayout) loadingView.findViewById(R.id.train_date_click);
		     train_number=(ClearEditText) loadingView.findViewById(R.id.train_number);
		     train_select_imagebutton=(ImageButton) loadingView.findViewById(R.id.train_select_imagebutton);
		     
		     //初始化的时候设置当天显示日期
		     train_date_show.setText(String.valueOf(utilsClass.getYear())+"-"+String.valueOf(utilsClass.getMouth()+1)+"-"+String.valueOf(utilsClass.getDate()));
		     
		     
		     
		     return  loadingView;
	}
 

   

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		
		train_select_imagebutton.setOnClickListener(this);//设置查询按钮的点击事件
		
		train_date_click.setOnClickListener(this);
		
		super.onStart();
	}




	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
		  train_number.setFocusable(true);
		  train_number.setFocusableInTouchMode(true);
		  train_number.requestFocus();
		  train_number.requestFocusFromTouch();
		  
		  switch (arg0.getId()) {
		case R.id.train_select_imagebutton:
			   
			  if(TextUtils.isDigitsOnly(train_number.getText().toString().trim())){
				  
				  Toast.makeText(getActivity(), "请输入车次", Toast.LENGTH_SHORT).show();
				

				  
				  train_number.setShakeAnimation();//摇晃输入框
				
			  }else{
				  
			       //发送消息，请求数据
				  handler.sendEmptyMessage(0x100);
				  
			  }
			
			break;
  
		case R.id.train_date_click:
			  
			 utilsClass.getCalendarDate(getActivity(),train_date_show);
			 
		break;
			
		}
		
	}
	
}
