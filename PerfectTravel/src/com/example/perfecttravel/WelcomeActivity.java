package com.example.perfecttravel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import net.youmi.android.AdManager;

import com.example.listenner.myPagerChangeListenner;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import android.os.Build;

public class WelcomeActivity extends Activity {

	@ViewInject(R.id.viewpager_wel)
	private ViewPager pager;
	@ViewInject(R.id.image1_wel)
	private ImageView image1;
	@ViewInject(R.id.image2_wel)
	private ImageView image2;
	@ViewInject(R.id.image3_wel)
	private ImageView image3;
	
	private ArrayList<View> data;// viewpager填充数组
	private GestureDetector gestureDetector; // 用户滑动
	private int currentItem;// 当前的页数
	private int flaggingWidth;// 互动翻页所需滚动的长度是当前屏幕宽度的1/3

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		ViewUtils.inject(this);
        
	    //有米广告初始化
		AdManager.getInstance(this).init("6e2131aebf4ff2c5", "f8af63e2eefa5251", false);
	    
		
		gestureDetector = new GestureDetector(new GuideViewTouch());
		// 获取分辨率
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		flaggingWidth = dm.widthPixels / 3;

		LayoutInflater inflater = getLayoutInflater();

		View view1 = inflater.inflate(R.layout.wel1, null);
		View view2 = inflater.inflate(R.layout.wel2, null);
		View view3 = inflater.inflate(R.layout.wel3, null);

		data = new ArrayList<View>();
		data.add(view1);
		data.add(view2);
		data.add(view3);

		PagerAdapter adapter = new PagerAdapter() {

			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return data.size();
			}

			@Override
			public int getItemPosition(Object object) {
				// TODO Auto-generated method stub
				return super.getItemPosition(object);
			}

			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				// TODO Auto-generated method stub
				return arg0 == arg1;
			}

			public void destroyItem(View arg0, int arg1, Object arg2) {
				((ViewPager) arg0).removeView(data.get(arg1));
			}

			public Object instantiateItem(View arg0, int arg1) {
				((ViewPager) arg0).addView(data.get(arg1));
				return data.get(arg1);
			}

		};
		//判断是否第一次运行
		SharedPreferences sharedPreferences = this.getSharedPreferences("share", MODE_PRIVATE);
		boolean isFirstRun = sharedPreferences.getBoolean("isFirstRun", true);
		Editor editor = sharedPreferences.edit();
		if (isFirstRun)
		{
		Log.d("debug", "第一次运行");
		editor.putBoolean("isFirstRun", false);
		editor.commit();
		pager.setAdapter(adapter);
		
		} else
		{
			
		Log.d("debug", "不是第一次运行");
		 
		Intent intent=new Intent(WelcomeActivity.this, SkipActivity.class);
		     startActivity(intent);
		        this.finish();   
		
		} 
		

		pager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub

				currentItem = arg0;

				switch (arg0) {
				case 0:

					image1.setImageDrawable(getResources().getDrawable(
							R.drawable.pix));
					image2.setImageDrawable(getResources().getDrawable(
							R.drawable.nopix));
					image3.setImageDrawable(getResources().getDrawable(
							R.drawable.nopix));

					break;

				case 1:

					image1.setImageDrawable(getResources().getDrawable(
							R.drawable.nopix));
					image2.setImageDrawable(getResources().getDrawable(
							R.drawable.pix));
					image3.setImageDrawable(getResources().getDrawable(
							R.drawable.nopix));

					break;

				case 2:

					image1.setImageDrawable(getResources().getDrawable(
							R.drawable.nopix));
					image2.setImageDrawable(getResources().getDrawable(
							R.drawable.nopix));
					image3.setImageDrawable(getResources().getDrawable(
							R.drawable.pix));
                    
					//开启旅程的点击事件
					ImageButton button=(ImageButton) findViewById(R.id.wel3_imagebutton);
					button.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							
							Intent intent = new Intent(WelcomeActivity.this,
									MainActivity.class);
							     startActivity(intent);
	                       WelcomeActivity.this.finish();
							
						}
					});
					
					break;
				}

			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
			}
		});

	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		if (gestureDetector.onTouchEvent(event)) {
			event.setAction(MotionEvent.ACTION_CANCEL);
		}
		return super.dispatchTouchEvent(event);
	}

	private class GuideViewTouch extends SimpleOnGestureListener {
		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			if (currentItem == 2) {
				if (Math.abs(e1.getX() - e2.getX()) > Math.abs(e1.getY()
						- e2.getY())
						&& (e1.getX() - e2.getX() <= (-flaggingWidth) || e1
								.getX() - e2.getX() >= flaggingWidth)) {
					if (e1.getX() - e2.getX() >= flaggingWidth) {

						Intent intent = new Intent(WelcomeActivity.this,
								MainActivity.class);
						     startActivity(intent);
                       WelcomeActivity.this.finish();
                       
						return true;
					}
				}
			}
			return false;
		}
	}
	/** 
     * 向sdcard中写入文件 
     * @param filename 文件名 
     * @param content 文件内容 
     */  
    public void saveToSDCard(String filename,String content) throws Exception{  
        File file=new File(Environment.getExternalStorageDirectory(), filename);  
        OutputStream out=new FileOutputStream(file);  
        out.write(content.getBytes());  
        out.close();  
        
    }  
}
