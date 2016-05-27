package com.example.perfecttravel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.os.Build;

public class SettingActivity extends Activity  implements OnClickListener{

	@ViewInject(R.id.setlist) ListView list;
	private ActionBar actionBar;
	private ImageButton actionbarBack;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)  {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
              ViewUtils.inject(this);
              
              actionBar=getActionBar(); 
              actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
              actionBar.setCustomView(R.layout.actionbar_setting);
              
             actionbarBack=(ImageButton) findViewById(R.id.actinbar_setting_result_back); 
              
             actionbarBack.setOnClickListener(this);
             
		List<Map<String,String>> data=new ArrayList<Map<String,String>>();
		
		      Map <String, String>map1=new HashMap<String, String>();
		      map1.put("title","版本更新");
		      data.add(map1);
		      
		      Map <String, String>map2=new HashMap<String, String>();
		      map2.put("title","欢迎页");
		      data.add(map2);
		      
		      Map <String, String>map3=new HashMap<String, String>();
		      map3.put("title","分享有票");
		      data.add(map3);
		      
		      Map <String, String>map４=new HashMap<String, String>();
		      map４.put("title","关于有票");
		      data.add(map４);
		      
		SimpleAdapter adapter=new SimpleAdapter(this, 
				data,
				R.layout.setitem, 
				new String[]{"title"},
				new int[]{R.id.setitem_text});
		
		
		     list.setAdapter(adapter);
	     
		     list.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					
					if(arg2==2){
						
						showShare();
					}
					if(arg2==1){
						
						//先将本地缓存设置为第一次运行
						SharedPreferences sharedPreferences = getSharedPreferences("share", MODE_PRIVATE);
						    Editor editor = sharedPreferences.edit();
						   editor.putBoolean("isFirstRun", true);
						editor.commit();
						   
						//跳转到欢迎页
						Intent intent=new Intent(SettingActivity.this, WelcomeActivity.class);
						  startActivity(intent);
						  SettingActivity.this.finish();
						  
						
					}
					
				}
			});
		     
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
		switch (arg0.getId()) {
		case R.id.actinbar_setting_result_back:
			
			Intent intent=new Intent(SettingActivity.this, MainActivity.class);
			   startActivity(intent);
			      this.finish();
			
			break;

		}
		
		
	}

	private void showShare() {
		 ShareSDK.initSDK(this);
		 OnekeyShare oks = new OnekeyShare();
		 //关闭sso授权
		 oks.disableSSOWhenAuthorize(); 
		 
		// 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
		 //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
		 // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
		 oks.setTitle(getString(R.string.share));
		 // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
		 oks.setTitleUrl("http://sharesdk.cn");
		 // text是分享文本，所有平台都需要这个字段
		 oks.setText("有票分享，测试一下...");
		 // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
		 oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
		 // url仅在微信（包括好友和朋友圈）中使用
		 oks.setUrl("http://sharesdk.cn");
		 // comment是我对这条分享的评论，仅在人人网和QQ空间使用
		 oks.setComment("没有票，不出行！我们都在用有票查火车票，查飞机票。");
		 // site是分享此内容的网站名称，仅在QQ空间使用
		 oks.setSite(getString(R.string.app_name));
		 // siteUrl是分享此内容的网站地址，仅在QQ空间使用
		 oks.setSiteUrl("http://sharesdk.cn");
		 
		// 启动分享GUI
		 oks.show(this);
		 }

	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		      
		 if (keyCode == KeyEvent.KEYCODE_BACK) { 
	         
				Intent intent=new Intent(SettingActivity.this, MainActivity.class);
				   startActivity(intent);
				      this.finish();
			 
	        } 
		return super.onKeyDown(keyCode, event);
	}


}
