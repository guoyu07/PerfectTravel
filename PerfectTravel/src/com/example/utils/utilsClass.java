package com.example.utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.bean.Flight;
import com.example.bean.Station;
import com.example.bean.Train;
import com.example.bean.ticket;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.TextView;

public   class  utilsClass {

	public  static  int  year;  //当前年
	public  static  int  mouth;//当前月
	public  static  int  date;//当前日
    public static  int pickYear=1997;//选中年
    public static int pickmouth=1;//选中月
    public static int pickdate=1;//选中日
	
	/***
	 * 所有的对话框使用方法
	 * @author hehr
	 * @param intent    　　   设置跳转路径intent
	 * @param showString　  输出提示字符串
	 * @param context　　　上下文环境
	 */
  public  static  void    alertDialog(String showString,final Context context,final Intent intent){
		   
		    AlertDialog.Builder builder=new Builder(context);
		       builder.setMessage(showString);
		       
		       builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					  
					   context.startActivity(intent);
					
				}
			});
		       builder.setNegativeButton("取消", null);
		       builder.show();
	  
  }
  
  /***
   * 获取日方法
   * @return　　date
   */
  public   static    int  getDate(){
	  
	  date=Calendar.getInstance().get(Calendar.DATE);
	  
	    return  date;
  }
	
  /***
   * 获取月方法
   * @return　　date
   */
  public   static    int  getMouth(){
	  
	  mouth=Calendar.getInstance().get(Calendar.MONTH);
	  
	    return  mouth;
  }
  
  /***
   * 获取年方法
   * @return　　date
   */
  public   static    int  getYear(){
	  
	  year=Calendar.getInstance().get(Calendar.YEAR);
	  
	    return  year;
  }
  
  
  /***
   * 显示日历控件，并改变选择的日期
   * @param context　　
   * @param view   改变的文本库
   * @return　　　选择的日期,如：“2015-3-27”
   */
   public  static void getCalendarDate(Context context  ,final TextView view){
	   
	  DatePickerDialog  datePickerDialog=new   DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
		
		@Override
		public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
			// TODO Auto-generated method stub
			  
			              pickYear=arg1;
			              pickmouth=arg2;
			              pickdate=arg3;
			
			              Log.i("msg", "pick的事件"+pickYear+"---"+pickmouth+"----"+pickdate);
			     
			              view.setText(String.valueOf(pickYear)+"-"+String.valueOf(pickmouth+1)+"-"+String.valueOf(pickdate));
		}
	}, getYear(), getMouth(), getDate());
	   
	  
	       datePickerDialog.show();
	       
	       
   }  
   
   /**
    * 解析航班信息，并封装好ArrayList<Map<String,Object>>数据，可供adapter直接使用
    * @param data
    * @return
    * @throws JSONException
    */
   public  static  Map<String,String> analyzeFlight(JSONObject data) throws JSONException{
	   
	       JSONArray resultArr=data.getJSONArray("result");//取到结果集
	       
	      Map<String,String>  analyzeData=new  HashMap<String,String>();//准备好盛放数据的容器 
               Log.i("msg","取到航班信息"+resultArr.get(0).toString());
               
	             JSONObject object=(JSONObject) resultArr.get(0);
	             
	             analyzeData.put("name", object.get("name").toString().trim());//航班号
	             analyzeData.put("complany", object.get("complany").toString().trim());//航班公司
	             analyzeData.put("AirModel", object.get("AirModel").toString().trim());//机型
	             analyzeData.put("start", object.get("start").toString().trim());//始发城市
	             analyzeData.put("end", object.get("end").toString().trim());//到达城市
	             analyzeData.put("DepCode", object.get("DepCode").toString().trim());//始发城市三字码
	             analyzeData.put("ArrCode", object.get("ArrCode").toString().trim());//到达城市三字码
	             analyzeData.put("DepAirport", object.get("DepAirport").toString().trim());//始发机场
	             analyzeData.put("ArrAirport", object.get("ArrAirport").toString().trim());//到达机场
	             analyzeData.put("DepWeather", object.get("DepWeather").toString().trim());//始发机场天气
	             analyzeData.put("ArrWeather", object.get("ArrWeather").toString().trim());//到达机场天气
	             analyzeData.put("DepTerminal", object.get("DepTerminal").toString().trim());//始发机场航站楼
	             analyzeData.put("ArrTerminal", object.get("ArrTerminal").toString().trim());//到达机场航站楼
	             analyzeData.put("status", object.get("status").toString().trim());//航班状态
	             analyzeData.put("DepTime", object.get("DepTime").toString().trim());//起飞时间
	             analyzeData.put("ArrTime", object.get("ArrTime").toString().trim());//到达时间
	             analyzeData.put("food", object.get("food").toString().trim());//有无餐食，1有 0无
	             analyzeData.put("OnTimeRate", object.get("OnTimeRate").toString().trim());//航班准点率
	             analyzeData.put("DepTel", object.get("DepTel").toString().trim());//始发机场电话
	             analyzeData.put("ArrTel", object.get("ArrTel").toString().trim());//到达机场电话
               
	   
	           return analyzeData;
	   
   }
   
   
   /**
    * 解析车次json方法
    * @param data　　直接获取的原始数据
    * @return
 * @throws JSONException 
    */
   public  static ArrayList<Map<String,Object>>  analyzeTrain(JSONObject data) throws JSONException{
	   
	     //解析后并封装好的数据
     ArrayList<Map<String,Object>> analyzedDate=new ArrayList<Map<String,Object>>();
     
           JSONObject  result=data.getJSONObject("result");//取出result结果集
           
           JSONArray  station_list=result.getJSONArray("station_list");//取出station_list结果集
           
                 for(int i=0;i<station_list.length();i++){//开始封装数据
                	 
                	     JSONObject object=new JSONObject();
                  Map<String,Object> map=new HashMap<String, Object>();
                          Train train=new Train();
                               object=station_list.getJSONObject(i);//依次取出station_list中的数据
                       //按字段取出每个station_list中每个json对象中的数据，并塞进train对象中
                        train.setTrain_id(object.getString("train_id").toString().trim());
                	    train.setStation_name(object.getString("station_name").toString().trim());
                	    train.setArrived_time(object.getString("arrived_time").toString().trim());
                	    train.setLeave_time(object.getString("leave_time").toString().trim());
                	    train.setMileage(object.getString("mileage").toString().trim());
                	   
                	    
                	          map.put("stationInfo", train);//将每个对象塞进map中，并取名stationInfo
                	          analyzedDate.add(map);  //将map封装进list中
                	          
                	          
                 }
	    
	   
	                       return analyzedDate;
	   
   }
   
   /**
    * 用来解析站到站查询信息的方法，
    * @param data  传递过来未解析的JSON
    * @return    返回已经解析并封装好的ArrayList<Map<String,Object>>  对象
 * @throws JSONException 
    */
   public  static ArrayList<Map<String,Object>>  analyzeStation(JSONObject data) throws JSONException{
	    
	   ArrayList<Map<String,Object>> analyzedDate=new ArrayList<Map<String,Object>>();
	   
	   JSONObject  result=data.getJSONObject("result");//取出result结果集
	    
	   JSONArray  station_data=result.getJSONArray("list");//取出list结果集
	      
	      //解析数据，并封装
	     for(int i=0;i<station_data.length();i++){
	    	 
	    	    Map<String,Object> map=new HashMap<String, Object>();//准备用来盛放对象的map
	    	 
	    	        JSONObject object=new JSONObject();//准备object来接收jsonObject
	    	        
	    	        object=station_data.getJSONObject(i);//遍历依次得到每个车次的信息
	    	        
                    Station station=new Station();//准备实体对象
                    
                    
                    station.setTrain_no(object.getString("train_no"));//车次信息
                    station.setTrain_type(object.getString("train_type"));//车次类型
                    station.setStart_station(object.getString("start_station"));//发站
                    station.setStart_station_type(object.getString("start_station_type"));//发站类型
                    station.setEnd_station(object.getString("end_station"));//到站
                    station.setEnd_station_type(object.getString("end_station_type"));//到站类型
                    station.setStart_time(object.getString("start_time"));//发车时间
                    station.setEnd_time(object.getString("end_time"));//到站时间
                    station.setRun_time(object.getString("run_time"));//运行时间
                    station.setRun_distance(object.getString("run_distance"));//设置运行距离
                    
                    station.setPrice_list(analyPriceList(object.getString("price_list")));
                      
                    
                       map.put("trainInfo", station);
	    	 
                       analyzedDate.add(map);
	     }
	     
	   
	      return analyzedDate;//返回封装好之后的list
   }  
   
   /**
    * 解析票价信息
    * @param PreceList传入包含票价的字符串，
    * @return  List<Map<String, Object>>  返回解析后用map盛放的List
    * @throws JSONException
    */
   private static List<Map<String, Object>> analyPriceList(String PreceList) throws JSONException {
	// TODO Auto-generated method stub
	   List<Map<String,Object>> prices=new ArrayList<Map<String,Object>>();
	   
	     JSONArray array=new JSONArray(PreceList);//得到传递来的票价信息
	     
	      for(int i=0;i<array.length();i++){
	    	  
	    	 Map<String,Object> data=new HashMap<String, Object>();
	    	    JSONObject object=array.getJSONObject(i);
	    	      ticket ticket=new ticket();
	    	  ticket.setPrice(object.getString("price"));  //得到价格
	    	  ticket.setPrice_type(object.getString("price_type"));//得到票类型
	    	    data.put("ticketInfo",ticket );
	    	    prices.add(data);
	      }
	     
	   
	   return prices;
}
   
   
/**
 * 解析查询航线信息传递过来的json数据
 * @param result 未经解析的json数据
 * @return  ArrayList<Map<String,Flight>> 封装好的数据
 * @throws JSONException 
 */
	public   static ArrayList<Map<String, Flight>> analyzeFlight(String result)throws JSONException {

		 ArrayList<Map<String, Flight>> flights = new ArrayList<Map<String, Flight>>();

		JSONObject obj = new JSONObject(result);

		JSONArray resultArr = obj.getJSONArray("result");// 取得result结果集

		for (int i = 0; i < resultArr.length(); i++) {

			JSONObject object = resultArr.getJSONObject(i);
			Map<String, Flight> map = new HashMap<String, Flight>();
			Flight flight = new Flight();
			flight.setFlightNum(object.getString("FlightNum"));
			flight.setDepTime(object.getString("DepTime"));
			flight.setArrTime(object.getString("ArrTime"));
			flight.setDexpected(object.getString("Dexpected"));
			flight.setAexpected(object.getString("Aexpected"));
			map.put("flight", flight);

			flights.add(map);

		}

		return flights;

	}
   
/**
    * 进度条对话框
    * @param context
    */
   public static  ProgressDialog  showProgessDialog(Context context,String showMessage){
	   
	    ProgressDialog  dialog=new ProgressDialog(context);
	        dialog.setMessage(showMessage);
	        dialog.setCancelable(false);
	                  return dialog;
	                  
   }
   
   /**
    * 连网状态检测方法，连网返回true,否返回false
    * @param context 
    * @return  boolean
    */
   public static boolean isConnect(Context context) { // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）

   try {

       ConnectivityManager connectivity = (ConnectivityManager) context

               .getSystemService(Context.CONNECTIVITY_SERVICE);

       if (connectivity != null) {
           // 获取网络连接管理的对象
           NetworkInfo info = connectivity.getActiveNetworkInfo();
           if (info != null&& info.isConnected()) {
               // 判断当前网络是否已经连接
               if (info.getState() == NetworkInfo.State.CONNECTED) {
                   return true;
               }
           }
       }

   } catch (Exception e) {

//TODO: handle exception

   Log.v("error",e.toString());

}
       return false;
   } 
   
   
   
}
