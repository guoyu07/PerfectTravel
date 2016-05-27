package com.example.bean;

import java.util.List;
import java.util.Map;

public class Station {
  
	public String  train_no;//车次
	public String  train_type;//车类型
	public String  start_station;//出发城市
	public String  start_station_type;//出发车站类型
	public  String  end_station;//到达车站
	public  String  end_station_type;//到达车站类型
	public  String  start_time;//发车时间
	public  String  end_time;//到达时间
	public  String  run_time;//运行事件
	public  String  run_distance;//里程
	public List<Map<String,Object>> price_list;//票价信息
	
	
	public String getTrain_no() {
		return train_no;
	}
	public void setTrain_no(String train_no) {
		this.train_no = train_no;
	}
	public String getTrain_type() {
		return train_type;
	}
	public void setTrain_type(String train_type) {
		this.train_type = train_type;
	}
	public String getStart_station() {
		return start_station;
	}
	public void setStart_station(String start_station) {
		this.start_station = start_station;
	}
	public String getStart_station_type() {
		return start_station_type;
	}
	public void setStart_station_type(String start_station_type) {
		this.start_station_type = start_station_type;
	}
	public String getEnd_station() {
		return end_station;
	}
	public void setEnd_station(String end_station) {
		this.end_station = end_station;
	}
	public String getEnd_station_type() {
		return end_station_type;
	}
	public void setEnd_station_type(String end_station_type) {
		this.end_station_type = end_station_type;
	}
	public String getStart_time() {
		return start_time;
	}
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}
	public String getEnd_time() {
		return end_time;
	}
	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}
	public String getRun_time() {
		return run_time;
	}
	public void setRun_time(String run_time) {
		this.run_time = run_time;
	}
	public String getRun_distance() {
		return run_distance;
	}
	public void setRun_distance(String run_distance) {
		this.run_distance = run_distance;
	}
	public List<Map<String, Object>> getPrice_list() {
		return price_list;
	}
	public void setPrice_list(List<Map<String, Object>> price_list) {
		this.price_list = price_list;
	}
	
	
	
}
