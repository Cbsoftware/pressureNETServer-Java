package ca.cumulonimbus.barometer;

import java.util.ArrayList;

public class TrendWindow {
	String trend;
	Window window;
	ArrayList<BarometerReading> readings;
	public TrendWindow(String trend, Window window, ArrayList<BarometerReading> readings) {
		super();
		this.trend = trend;
		this.window = window;
		this.readings = readings;
	}

	public TrendWindow(String trend, Window window) {
		super();
		this.trend = trend;
		this.window = window;
	}
}