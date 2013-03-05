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
	String sharingPrivacy;
	String clientKey;
	float locationAccuracy;
	float readingAccuracy;

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
	
	public float getLocationAccuracy() {
		return locationAccuracy;
	}
	public void setLocationAccuracy(float locationAccuracy) {
		this.locationAccuracy = locationAccuracy;
	}
	public float getReadingAccuracy() {
		return readingAccuracy;
	}
	public void setReadingAccuracy(float readingAccuracy) {
		this.readingAccuracy = readingAccuracy;
	}
	public String getClientKey() {
		return clientKey;
	}
	public void setClientKey(String clientKey) {
		this.clientKey = clientKey;
	}
	public String getSharingPrivacy() {
		return sharingPrivacy;
	}
	public void setSharingPrivacy(String sharingPrivacy) {
		this.sharingPrivacy = sharingPrivacy;
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
