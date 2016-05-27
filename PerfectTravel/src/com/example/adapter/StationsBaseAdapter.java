package com.example.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.bean.Station;
import com.example.bean.ticket;
import com.example.perfecttravel.R;
import com.example.perfecttravel.TrainResultActivity;
import com.example.utils.utilsClass;
import com.thinkland.sdk.android.DataCallBack;
import com.thinkland.sdk.android.JuheData;
import com.thinkland.sdk.android.Parameters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class StationsBaseAdapter extends BaseAdapter {

    private  ArrayList<Map<String,Object>> data;
	private  Context context;
	private LayoutInflater myInflater;//布局加载器
	
	
	public  StationsBaseAdapter( ArrayList<Map<String,Object>> dataSource ,Context arg1){
		
	      this.context=arg1;//得到上下文环境
		  this.data=dataSource;//得到数据源
		  this.myInflater=LayoutInflater.from(arg1);
		
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final ViewHolder holder;
		
		if (convertView == null) {
			
			convertView=myInflater.inflate(R.layout.station_result_item, null);
            holder = new ViewHolder(); //得到各个控件的对象
            
           holder.station_result_trainnumber=(TextView) convertView.findViewById(R.id.station_result_trainnumber);//车次
           holder.station_result_traintype=(TextView) convertView.findViewById(R.id.station_result_traintype);//车类型
           holder.station_result_sstaion=(TextView) convertView.findViewById(R.id.station_result_sstaion) ;//发站
           holder.station_result_rststion=(TextView) convertView.findViewById(R.id.station_result_rststion);//到站; 
           holder.station_result_stime=(TextView) convertView.findViewById(R.id.station_result_stime) ; //车到达时间
           holder.station_result_rtime=(TextView) convertView.findViewById(R.id.station_result_rtime) ;//发车时间
           holder.station_result_runtime=(TextView) convertView.findViewById(R.id.station_result_runtime) ;//运行时间
          /* holder.station_result_rundistance=(TextView) convertView.findViewById(R.id.station_result_rundistance);//运行里程
*/           holder.station_result_prices=(ListView) convertView.findViewById(R.id.station_result_prices);//价格列表
           holder.station_result_more= (ImageButton) convertView.findViewById(R.id.station_result_more);//点击更多
           
           
                 convertView.setTag(holder); //绑定ViewHolder对象
        }
        else {
            holder = (ViewHolder) convertView.getTag(); //取出ViewHolder对象
        }
		
		holder.station_result_trainnumber.setText(((Station)data.get(position).get("trainInfo")).getTrain_no().toString().trim());
		holder.station_result_traintype.setText(((Station)data.get(position).get("trainInfo")).getTrain_type().toString().trim());
		holder.station_result_sstaion.setText(((Station)data.get(position).get("trainInfo")).getStart_station().toString().trim());
		holder.station_result_rststion.setText(((Station)data.get(position).get("trainInfo")).getEnd_station().toString().trim());
		holder.station_result_stime.setText(((Station)data.get(position).get("trainInfo")).getStart_time().toString().trim());
		holder.station_result_rtime.setText(((Station)data.get(position).get("trainInfo")).getEnd_time().toString().trim());
		holder.station_result_runtime.setText(((Station)data.get(position).get("trainInfo")).getRun_time().toString().trim());
		/*holder.station_result_rundistance.setText(((Station)data.get(position).get("trainInfo")).getRun_distance().toString().trim());*/
		
		List<Map<String,Object>> prices=new ArrayList<Map<String,Object>>();
	    	prices=((Station)data.get(position).get("trainInfo")).getPrice_list();
	    	
		List<Map<String,String>> data=new ArrayList<Map<String,String>>();
		
 	     	for(int i=0;i<prices.size();i++){
 	     		
		              ticket ticket=new ticket();
			  ticket=(com.example.bean.ticket) prices.get(i).get("ticketInfo");
			
			  Map<String,String> map=new HashMap<String, String>();
			        map.put("price_type", ticket.getPrice_type());
			        map.put("price", ticket.getPrice());
			  
			             data.add(map);
		}
	
		holder.station_result_prices.setAdapter(new SimpleAdapter(context,
				data,
				R.layout.station_result_item_priceitem, 
				new String[]{"price_type","price"},
				new int[]{R.id.price_type,R.id.price}));//设置票价列表
		
		//设置更多按钮事件
		holder.station_result_more.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
		   
			final ProgressDialog	  dialog=utilsClass.showProgessDialog(context, "数据查询中，请稍后....");
			      dialog.show();
			  Parameters params = new Parameters();
			  params.add("name", holder.station_result_trainnumber.getText().toString().trim());
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
					
								
								 final	ArrayList<Map<String,Object>>  stationsInfo;	//用来盛放解析后的数据	
								 stationsInfo=utilsClass.analyzeTrain(new JSONObject(result));//对数据进行解析  
									  Bundle bundle=new Bundle();
								      bundle.putString("data", result);
								      bundle.putString("train", holder.station_result_trainnumber.getText().toString().trim());
								      Intent intent=new Intent(context, TrainResultActivity.class);
								      intent.putExtra("trainDate", bundle);
								      context.startActivity(intent);
								      Log.i("msg", reason+":"+result);
								      
						
			     		} catch (JSONException e) {
						// TODO Auto-generated catch block
						     e.printStackTrace();
					Toast.makeText(context, "数据解析失败...", Toast.LENGTH_SHORT).show();
					
					}
						  
					} else {//非成功获取数据
						
						  Toast.makeText(context, "数据获取失败...", Toast.LENGTH_SHORT).show();
						  
					}
				  
			     }
			  });
			}
		});
		
		
		     return convertView;
	}

	 /**
	  * @author hehr
	  */
    public final class ViewHolder{
      
    	public TextView  station_result_trainnumber;//火车次
    	public TextView  station_result_traintype;//车
    	public TextView  station_result_sstaion;//发站
    	public TextView  station_result_rststion;//到达
    	public TextView  station_result_stime;//车达时间
    	public TextView  station_result_rtime;//发车时间
    	public TextView station_result_runtime;//运行时间
/*    	public TextView  station_result_rundistance;//运行里程*/  
  	   public ListView   station_result_prices;//价格列表
    	public  ImageButton station_result_more;//更多
    	 
    	
    }
	
}
