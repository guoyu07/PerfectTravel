package com.example.listenner;

import com.example.perfecttravel.MainActivity;
import com.example.perfecttravel.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * @author hehr
 *用于ViewPager页面发生变化的监听器
 */
public class myPagerChangeListenner  implements  OnPageChangeListener {
    
    private  	Context  context;
	private      ImageView   station_image;
	private      ImageView   train_image;
	private      ImageView   flight_image;
	private      ImageView   route_image;
	private     TextView      actionbar_title;
    /**
     * 使用构造函数传进构造函数
     * @param context
     */
	   public myPagerChangeListenner(Context context, 
			   ImageView  station_image,
			   ImageView   train_image, 
			   ImageView   flight_image,
			   ImageView   route_image,
			   TextView     acctionbar_title
	   
			   ){
 
		    this.context=context;
		    this.station_image=station_image;
		    this.train_image=train_image;
		    this.flight_image=flight_image;
		    this.route_image=route_image;
		    this.actionbar_title=acctionbar_title;
		    
	   }
	
	   
	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	
	/**
	 * 当页面发生变化的时候，可在此处设置响应的变化效果
	 */
	@Override
	public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub
     
		
		/**
		 * 截面切换时用来切换对应的导航标签
		 */
		  switch (arg0) {
		case 0:
			
			station_image.setImageDrawable(context.getResources().getDrawable(R.drawable.station_select));
			train_image.setImageDrawable(context.getResources().getDrawable(R.drawable.train));
			flight_image.setImageDrawable(context.getResources().getDrawable(R.drawable.flight));
			route_image.setImageDrawable(context.getResources().getDrawable(R.drawable.route));
			actionbar_title.setText("站站查询");
			
			break;
			
        case 1:
			
        	station_image.setImageDrawable(context.getResources().getDrawable(
					R.drawable.station));
			train_image.setImageDrawable(context.getResources().getDrawable(
					R.drawable.train_select));
			flight_image.setImageDrawable(context.getResources().getDrawable(
					R.drawable.flight));
			route_image.setImageDrawable(context.getResources().getDrawable(
					R.drawable.route));
        	
			actionbar_title.setText("车次查询");
			
			break;
			
        case 2:
			
        	station_image.setImageDrawable(context.getResources().getDrawable(
					R.drawable.station));
			train_image.setImageDrawable(context.getResources().getDrawable(
					R.drawable.train));
			flight_image.setImageDrawable(context.getResources().getDrawable(
					R.drawable.flight_select));
			route_image.setImageDrawable(context.getResources().getDrawable(
					R.drawable.route));
        	
			actionbar_title.setText("航班查询");
			
			break;
  
			
         case 3:
			  
        		station_image.setImageDrawable(context.getResources().getDrawable(
    					R.drawable.station));
    			train_image.setImageDrawable(context.getResources().getDrawable(
    					R.drawable.train));
    			flight_image.setImageDrawable(context.getResources().getDrawable(
    					R.drawable.flight));
    			route_image.setImageDrawable(context.getResources().getDrawable(
    					R.drawable.route_select));
        	 
    			actionbar_title.setText("航线查询");
    			
			break;
		}
 
	}
   
}
