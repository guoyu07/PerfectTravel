package com.example.perfecttravel;
import com.example.utils.utilsClass;

import net.youmi.android.spot.SplashView;
import net.youmi.android.spot.SpotDialogListener;
import net.youmi.android.spot.SpotManager;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;


public class SkipActivity extends  Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_skip);
	
		if(utilsClass.isConnect(this)){
			
			SpotManager.getInstance(this).loadSplashSpotAds();

			// 开屏的两种调用方式：请根据使用情况选择其中一种调用方式。
			// 1.可自定义化调用：
			// 此方式能够将开屏适应一些应用的特殊场景进行使用。
			// 传入需要跳转的activity
			SplashView splashView = new SplashView(this,MainActivity.class);

			// 开屏也可以作为控件加入到界面中。
			setContentView(splashView.getSplashView());

			SpotManager.getInstance(this).showSplashSpotAds(this, splashView,
					new SpotDialogListener() {

						@Override
						public void onShowSuccess() {
							Log.i("YoumiAdDemo", "开屏展示成功");
						}

						@Override
						public void onShowFailed() {
							Log.i("YoumiAdDemo", "开屏展示失败。");
						}

						@Override
						public void onSpotClosed() {
							Log.i("YoumiAdDemo", "开屏关闭。");
						}
					});
		
			
		}else{
			
			 Handler handler=new Handler();
		     handler.postDelayed(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					Intent intent=new  Intent(SkipActivity.this, MainActivity.class);
				    	startActivity(intent);
				   SkipActivity.this.finish();
				}
			}, 2000);
			
		}
     
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == 10045) {
			Intent intent = new Intent(this, MainActivity.class);
			startActivity(intent);
			finish();
		}
	}
	@Override
	protected void onResume() {

		/**
		 * 设置为竖屏
		 */
		if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		}
		super.onResume();
	}


}
