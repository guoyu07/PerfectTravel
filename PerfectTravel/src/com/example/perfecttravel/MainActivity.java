package com.example.perfecttravel;
import java.util.ArrayList;

import net.youmi.android.banner.AdSize;
import net.youmi.android.banner.AdView;
import net.youmi.android.banner.AdViewListener;
import net.youmi.android.spot.SpotManager;

import com.example.adapter.MyViewPagerAdapter;
import com.example.fragment.FlightFragment;
import com.example.fragment.RouteFragment;
import com.example.fragment.StationFragment;
import com.example.fragment.TrainFragment;
import com.example.listenner.myPagerChangeListenner;
import com.example.utils.utilsClass;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends FragmentActivity implements android.view.View.OnClickListener {

	@ViewInject(R.id.viewFlipper) ViewPager pager;
	@ViewInject(R.id.click_station) LinearLayout click_station;// 站站查询
	@ViewInject(R.id.station_image) ImageView station_image;
	@ViewInject(R.id.station_text) TextView station_text;
	@ViewInject(R.id.click_train) LinearLayout click_train;// 车次查询
	@ViewInject(R.id.train_image) ImageView train_image;
	@ViewInject(R.id.train_text) TextView train_text;
	@ViewInject(R.id.click_flight) LinearLayout click_flight;// 航班查询
	@ViewInject(R.id.flight_image) ImageView flight_image;
	@ViewInject(R.id.flight_text) TextView flight_text;
	@ViewInject(R.id.click_route) LinearLayout click_route;// 航线查询
	@ViewInject(R.id.route_image) ImageView route_image;
	@ViewInject(R.id.route_text) TextView route_text;
	@ViewInject(R.id.viewFlipper)ViewPager main_ViewPager;
	
	private   StationFragment    stationFragment;
    private   TrainFragment          trainfragment;
    private   FlightFragment        flightFragment;
    private   RouteFragment        routeFragment;
    
    private  ProgressDialog  dialog;//进度条对话框
    
    private ActionBar actionBar;
    private  ImageButton actionbar_back;
    private  TextView actionbar_title;
    private  ImageButton actionbar_more;
    private  long firstTime=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ViewUtils.inject(this);// 将activity注入
		//初始化actionbar部分
	   actionBar=getActionBar();
	   actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
	   actionBar.setCustomView(R.layout.actionbar_main);
		actionbar_back=(ImageButton) findViewById(R.id.actionbar_main_back);//初始化actionbar返回按钮
		actionbar_title=(TextView) findViewById(R.id.actionbar_main_title);//初始化actionbar标题
	    actionbar_more=(ImageButton) findViewById(R.id.actionbar_main_more);
	   
	   	InitViewPager();//初始化ViewPager
			
	   	
			
	}
	
  @Override
	protected void onStart() {
		// TODO Auto-generated method stub
	  
	  if(utilsClass.isConnect(this)){
		  
		  //如果有网，就加载有米广告条
		  this.showBanner();
		      
	  }else{
		  
		    utilsClass. alertDialog("未接入网络，请检查网络设置...", this, new Intent(android.provider.Settings.ACTION_SETTINGS));
		  
	  }
	  
	  
	  
		setOnClick();// 设置导航点击效果
	  
		super.onStart();
			
	}

  


/**
   * @author hehr
   * 初始化ViewPager
   */
	private void InitViewPager() {
		// TODO Auto-generated method stub
		
		ArrayList<Fragment> fragmentList =new ArrayList<Fragment>();
		           stationFragment=new StationFragment();
		           trainfragment=new TrainFragment();
		           flightFragment=new FlightFragment();
		           routeFragment=new RouteFragment();
		      fragmentList.add(stationFragment);
		      fragmentList.add(trainfragment);
		      fragmentList.add(flightFragment);
		      fragmentList.add(routeFragment);
		      
		      MyViewPagerAdapter adapter=new MyViewPagerAdapter(getSupportFragmentManager(),fragmentList);
		         main_ViewPager.setAdapter(adapter);
		   /*      main_ViewPager.setCurrentItem(0);//设置默认加载第一页
*/		         
		        main_ViewPager.setOnPageChangeListener(new myPagerChangeListenner(this,station_image,train_image,flight_image,route_image,actionbar_title));
		         
		      
	}

	/**
	 * @author hehr 设置底部导航按钮点击效果
	 */
	private void setOnClick() {
		// TODO Auto-generated method stub
		station_image.setImageDrawable(getResources().getDrawable(
				R.drawable.station_select));// 默认进入站到站是选中状态
		click_station.setOnClickListener(this);
		click_train.setOnClickListener(this);
		click_flight.setOnClickListener(this);
		click_route.setOnClickListener(this);
		
		actionbar_back.setOnClickListener(this);
		actionbar_more.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub

		switch (arg0.getId()) {
		case R.id.click_station:// 点击站站查询
   
			 main_ViewPager.setCurrentItem(0);//设置加载第一页
			 
			//导航图片设置
			station_image.setImageDrawable(getResources().getDrawable(
					R.drawable.station_select));
			train_image.setImageDrawable(getResources().getDrawable(
					R.drawable.train));
			flight_image.setImageDrawable(getResources().getDrawable(
					R.drawable.flight));
			route_image.setImageDrawable(getResources().getDrawable(
					R.drawable.route));
 
			actionbar_title.setText("站站查询");
			
			break;

		case R.id.click_train:// 点击车次查询

			 main_ViewPager.setCurrentItem(1);//设置加载第2页
			
			station_image.setImageDrawable(getResources().getDrawable(
					R.drawable.station));
			train_image.setImageDrawable(getResources().getDrawable(
					R.drawable.train_select));
			flight_image.setImageDrawable(getResources().getDrawable(
					R.drawable.flight));
			route_image.setImageDrawable(getResources().getDrawable(
					R.drawable.route));
               
			actionbar_title.setText("车次查询");
			break;

		case R.id.click_flight:// 点击航班查询

			 main_ViewPager.setCurrentItem(2);//设置加载第3页
			
			station_image.setImageDrawable(getResources().getDrawable(
					R.drawable.station));
			train_image.setImageDrawable(getResources().getDrawable(
					R.drawable.train));
			flight_image.setImageDrawable(getResources().getDrawable(
					R.drawable.flight_select));
			route_image.setImageDrawable(getResources().getDrawable(
					R.drawable.route));

			actionbar_title.setText("航班查询");
			break;

		case R.id.click_route:// 点击航线

			 main_ViewPager.setCurrentItem(3);//设置加载第4页
			
			station_image.setImageDrawable(getResources().getDrawable(
					R.drawable.station));
			train_image.setImageDrawable(getResources().getDrawable(
					R.drawable.train));
			flight_image.setImageDrawable(getResources().getDrawable(
					R.drawable.flight));
			route_image.setImageDrawable(getResources().getDrawable(
					R.drawable.route_select));
			actionbar_title.setText("航线查询");
			
			break;
			
		case R.id.actionbar_main_back://返回按钮
			
			   AlertDialog.Builder builder=new  Builder(MainActivity.this);
			   builder.setMessage("确定退出程序？");
			   builder.setPositiveButton("确定", new android.app.AlertDialog.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					
					MainActivity.this.finish();
					
				}
			});
			   builder.setNegativeButton("取消", null);
			   builder.show();
			
			
			
			break;
		
  
		case R.id.actionbar_main_more://更多
			
			 
			Intent intent=new Intent(this, SettingActivity.class);
			   startActivity(intent);
			MainActivity.this.finish();
			
			break;
			
		}

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		      
		 if (keyCode == KeyEvent.KEYCODE_BACK) { 
	            long secondTime = System.currentTimeMillis(); 
	            if (secondTime - firstTime > 800) {//如果两次按键时间间隔大于800毫秒，则不退出 
	                Toast.makeText(MainActivity.this, "再按一次退出程序...", Toast.LENGTH_SHORT).show(); 
	                firstTime = secondTime;//更新firstTime 
	                return true; 
	            } else { 
	            	this.finish();
	            } 
	        } 
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		SpotManager.getInstance(this).onDestroy();
		super.onDestroy();
	}
	
	@Override
	protected void onStop() {
		// 如果不调用此方法，则按home键的时候会出现图标无法显示的情况。
		SpotManager.getInstance(this).onStop();
		super.onStop();
	}
	
	@Override
	public void onBackPressed() {
		// 如果有需要，可以点击后退关闭插播广告。
		if (!SpotManager.getInstance(this).disMiss()) {
			// 弹出退出窗口，可以使用自定义退屏弹出和回退动画,参照demo,若不使用动画，传入-1
			super.onBackPressed();
		}
	}
	
	/**
	 * 有米广告条
	 */
	private void showBanner() {

		// 将广告条adView添加到需要展示的layout控件中
		 LinearLayout adLayout = (LinearLayout) findViewById(R.id.adLayout);
		 AdView adView = new AdView(this, AdSize.FIT_SCREEN);
		adLayout.addView(adView);


		// 监听广告条接口
		adView.setAdListener(new AdViewListener() {

			@Override
			public void onSwitchedAd(AdView arg0) {
				Log.i("YoumiAdDemo", "广告条切换");
			}

			@Override
			public void onReceivedAd(AdView arg0) {
				Log.i("YoumiAdDemo", "请求广告成功");

			}

			@Override
			public void onFailedToReceivedAd(AdView arg0) {
				Log.i("YoumiAdDemo", "请求广告失败");
			}
		});
		
	}
	
}
