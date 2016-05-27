package com.example.bean;

public class Flight {

	public String FlightNum;//航班号
	public String DepTime;//预计起飞时间
	public String ArrTime;//预计到达时间
	public String Dexpected;//实际起飞时间
	public String Aexpected;//实际到达时间
	
	
	public String getFlightNum() {
		return FlightNum;
	}
	public void setFlightNum(String flightNum) {
		FlightNum = flightNum;
	}
	public String getDepTime() {
		return DepTime;
	}
	public void setDepTime(String depTime) {
		DepTime = depTime;
	}
	public String getArrTime() {
		return ArrTime;
	}
	public void setArrTime(String arrTime) {
		ArrTime = arrTime;
	}
	public String getDexpected() {
		return Dexpected;
	}
	public void setDexpected(String dexpected) {
		Dexpected = dexpected;
	}
	public String getAexpected() {
		return Aexpected;
	}
	public void setAexpected(String aexpected) {
		Aexpected = aexpected;
	}
	
	
}
