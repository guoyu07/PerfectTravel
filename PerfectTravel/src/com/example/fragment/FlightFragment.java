package com.example.fragment;


import org.json.JSONException;
import org.json.JSONObject;
import com.example.perfecttravel.FlightResultActivity;
import com.example.perfecttravel.R;
import com.example.utils.ClearEditText;
import com.example.utils.utilsClass;
import com.thinkland.sdk.android.DataCallBack;
import com.thinkland.sdk.android.JuheData;
import com.thinkland.sdk.android.Parameters;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class FlightFragment extends Fragment  implements OnClickListener {

	private  ClearEditText flightNum;//航班编号
	private  TextView  flightDataShow;//日期
	private   ImageButton flightSelectImageButton;//查询按钮
	private  RelativeLayout  fligthtClick;//日期选择
	private  ProgressDialog dialog;//进度条对话框
	
	private Handler handler=new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			
			if(msg.what==0x100){//开启线程，获取航班信息
				
				dialog=utilsClass.showProgessDialog(getActivity(), "数据查询中，请稍后....");
			      dialog.show();
			  Parameters params = new Parameters();
		      params.add("name", flightNum.getText().toString().trim());
		      params.add("date", flightDataShow.getText().toString().trim());
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
								
								Toast.makeText(getActivity(), "航班不存在...", Toast.LENGTH_SHORT).show();
								
							} else{
								
						      //成功获取到数据，在此处仅专递数据，并不对数据进行处理
								
								Bundle bundle=new Bundle();
								bundle.putString("result", result);//将JSON数据直接传递过去
								bundle.putString("date", flightDataShow.getText().toString().trim());//将日期传递过去
								Intent intent=new Intent(getActivity(),FlightResultActivity.class);
								intent.putExtra("FlightBundle", bundle);
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
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View loadingView =inflater.inflate(R.layout.fragment_flight, null);
	  	
		flightNum=(ClearEditText) loadingView.findViewById(R.id.flight_number);//航班编号
		flightDataShow=(TextView) loadingView.findViewById(R.id.flight_date_show);//航班日期
		flightSelectImageButton=(ImageButton) loadingView.findViewById(R.id.flight_select_imagebutton);//查询按钮
		fligthtClick=(RelativeLayout) loadingView.findViewById(R.id.flight_date_click);//日期选择
		
		//设置默认显示当天日期
		flightDataShow.setText(String.valueOf(utilsClass.getYear())+"-"+String.valueOf(utilsClass.getMouth()+1)+"-"+String.valueOf(utilsClass.getDate()));
		
		
		     return loadingView;
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	    flightNum.setFocusable(true);
        flightNum.setFocusableInTouchMode(true);
	    flightNum.requestFocus();
	    flightNum.requestFocusFromTouch();
		
		switch (arg0.getId()) {
		case R.id.flight_select_imagebutton://点击查询按钮
			
			if(TextUtils.isEmpty(flightNum.getText().toString().trim())){
				
				  Toast.makeText(getActivity(), "请输入航班号", Toast.LENGTH_SHORT).show();
		
				     flightNum.setShakeAnimation();//摇晃输入框
				
			}else{
				//异步发起请求，获取航班信息
				handler.sendEmptyMessage(0x100);
				
			}
			
			
			
			break;

		case R.id.flight_date_click://点击日期
			
			 utilsClass.getCalendarDate(getActivity(),flightDataShow);//日历显示
			
			break;
		}
		
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		
	flightSelectImageButton.setOnClickListener(this);
	 fligthtClick.setOnClickListener(this);
		
	}

	
	
}
