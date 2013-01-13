package ca.cumulonimbus.barometer;

import java.io.Serializable;


/**
 * Hold data on a single barometer reading.
 * @author jacob
 *
 */
public class BarometerReading implements Serializable {
	private static final long serialVersionUID = -4207080607881087063L;
	double latitude;
	double longitude;
	double time;
	double reading;
	int timeZoneOffset;
	String androidId;
	Tendency tendency;
	String sharingPrivacy;
	

	public Tendency createTendency(String ten) {
		Tendency t = new Tendency();
		t.setTendency(ten);
		return t;
	}
	
	public class Tendency {
		String tendency;
		int timeOfMostRecent;
		public String getTendency() {
			return tendency;
		}
		public void setTendency(String tendency) {
			this.tendency = tendency;
		}
		public int getTimeOfMostRecent() {
			return timeOfMostRecent;
		}
		public void setTimeOfMostRecent(int timeOfMostRecent) {
			this.timeOfMostRecent = timeOfMostRecent;
		}
	}
	 
	public Tendency getTendency() {
		return tendency;
	}

	public void setTendency(Tendency tendency) {
		this.tendency = tendency;
	}
	
	public String getSharingPrivacy() {
		return sharingPrivacy;
	}
	public void setSharingPrivacy(String sharingPrivacy) {
		this.sharingPrivacy = sharingPrivacy;
	}
	public String toString() {
		String ret = "Reading: " + reading + "\n" +
					 "Latitude: " + latitude + "\n" + 
				     "Longitude: " + longitude + "\n" +
				     "ID: " + androidId + "\n" +
				     "Time: " + time + "\n" +
				     "TZOffset: " + timeZoneOffset + "\n" +
				     "Share: " + sharingPrivacy + "\n"; 
		
		return ret;
	}
 
	public String getAndroidId() {
		return androidId;
	}
	public void setAndroidId(String androidId) {
		this.androidId = androidId;
	}
	public int getTimeZoneOffset() {
		return timeZoneOffset;
	}
	public void setTimeZoneOffset(int timeZoneOffset) {
		this.timeZoneOffset = timeZoneOffset;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public double getReading() {
		return reading;
	}
	public double getTime() {
		return time;
	}
	public void setTime(double time) {
		this.time = time;
	}
	public void setReading(double reading) {
		this.reading = reading;
	}	
}
