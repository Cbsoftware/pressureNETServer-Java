package ca.cumulonimbus.barometer;


public class Window {
	double minLatitude;
	double maxLatitude;
	double minLongitude;
	double maxLongitude;
	double trend;
	
	@Override
	public String toString() {
		return minLatitude + ", " + maxLatitude + ", " + minLongitude + ", " + maxLongitude;
	}
/*
	@Override
	public int hashCode() {
        return (int)this.m;
    }
	
	*/
	@Override
	public boolean equals(Object otherWindow) {
		Window other = (Window) otherWindow;
		
		return ( (this.minLatitude == other.minLatitude) &&
				 (this.maxLatitude == other.maxLatitude) &&
				 (this.minLongitude == other.minLongitude) &&
				 (this.maxLongitude == other.maxLongitude) );
	}


	public Window(double[] region) {
		minLatitude = region[0];
		maxLatitude = region[1];
		minLongitude = region[2];
		maxLongitude = region[3];
	}
	
	public Window(double[] region, double trend) {
		minLatitude = region[0];
		maxLatitude = region[1];
		minLongitude = region[2];
		maxLongitude = region[3];
		this.trend = trend;
	}
	
	public Window(double minLat, double maxLat, double minLon, double maxLon) {
		minLatitude = minLat;
		maxLatitude = maxLat;
		minLongitude = minLon;
		maxLongitude = maxLon;
	}
	
	public Window(double minLat, double maxLat, double minLon, double maxLon, double trend) {
		minLatitude = minLat;
		maxLatitude = maxLat;
		minLongitude = minLon;
		maxLongitude = maxLon;
		this.trend = trend;
	}
}