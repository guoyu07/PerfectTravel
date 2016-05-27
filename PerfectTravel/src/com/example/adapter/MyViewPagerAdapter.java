package com.example.adapter;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.view.ViewGroup;



/**
 * @author hehr
 *viewPager填充适配器
 */
public  class MyViewPagerAdapter extends FragmentPagerAdapter {
  
	public ArrayList<Fragment>   fragmentList;
	
	public MyViewPagerAdapter(FragmentManager fm,ArrayList<Fragment> list) {
     		super(fm);
		this.fragmentList=list;
		// TODO Auto-generated constructor stub
	}

	@Override
	public Fragment getItem(int arg0) {
		// TODO Auto-generated method stub
		return fragmentList.get(arg0);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return  fragmentList.size() ;
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		// TODO Auto-generated method stub
		return super.instantiateItem(container, position);
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		// TODO Auto-generated method stub
		return super.isViewFromObject(view, object);
	}
 

}
