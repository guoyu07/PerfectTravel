package com.example.service;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.perfecttravel.TrainResultActivity;
import com.example.utils.utilsClass;
import com.thinkland.sdk.android.DataCallBack;
import com.thinkland.sdk.android.JuheData;
import com.thinkland.sdk.android.Parameters;

import android.app.Service;
import android.content.Intent;
import android.net.LocalServerSocket;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class UpdataStationsService extends Service {

	private  int  flag = 0;// 返回值
	/**
	 * 给出flag的get方法，方便调用,
	 * 　0 连网失败，
	 * １：连网成功，数据获取成功，
	 * ２连网成功，数据获取异常
	 * @return　　flag
	 */
	public int  getFlag() {
		return flag;
	}


	public void updataStations() {
		
		Log.i("msg","updataStations（）执行");
		
		Parameters params = new Parameters();
		params.add("type", "json");
		JuheData.executeWithAPI(22,
				"http://apis.juhe.cn/train/station.list.php", JuheData.GET,
				params, new DataCallBack() {

					@Override
					public void resultLoaded(int err, String reason,
							String result) {
						// TODO Auto-generated method stub

						if (err == 0) {// 连网成功

							try {

								JSONObject resultObject = new JSONObject(result);

								Log.i("msg","测试resultObject.getJSONObject().length();"+ resultObject);
								
								if (resultObject.isNull("result")) {// 数据为空

									  flag =0;

								} else {

									flag = 1;
									Log.i("msg", "更新数据成功！" + result);
									// ************此处缺少,数据解析和更新数据库操作***************

									
									
								}

							} catch (JSONException e) {
								// TODO Auto-generated catch block
								System.out.println("连网成功，车站信息获取失败");
								e.printStackTrace();
								 flag = 2;

							}
						} else {//没有成功连接网络的

						System.out.println("连网失败，reson:"+reason);
							
							
						}

					}
				});
	}

	
	
	// 供绑定接口
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return new   upStationServiceBinder();
	}

	/**
	 * 这个该死的类会会在绑定的activity中一起销毁，可以和activity共享其中的方法和属性
	 * @author hehr
	 * 
	 */
	public class upStationServiceBinder extends Binder {
		/**
		 * 提供返回当前service实例
		 * @return
		 */
		   public UpdataStationsService getMyService(){
			    
			     return  UpdataStationsService.this;
			    
		   }
	}

}
