package ca.cumulonimbus.barometer;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

public class Analysis {
	
	// private static DatabaseHelper db;
	
	private static String logName = "ca.cumulonimbus.barometer.BarometerServlet";
	private static Logger log = Logger.getLogger(logName);
	
	
	public boolean readingInWindow(Window window, BarometerReading reading) {
		return ((reading.latitude > window.minLatitude) && 
			(reading.latitude < window.maxLatitude) &&	
			(reading.longitude > window.minLongitude) &&
			(reading.longitude < window.maxLongitude));
	}
	
	
	
	
	public ArrayList<TrendWindow> getTrendsInWindows(DatabaseHelper dh) {
		long startTime = System.currentTimeMillis();
		
		ArrayList<TrendWindow> trendWindows = new ArrayList<TrendWindow>();
		
		/**
		 * Take all the readings that we want to analyze.
		 * 
		 * Put them into GPS buckets: phase 1: 1x1 lat lon. (DONE)
		 * 							  phase 2: according to user's map view
		 * 
		 * For each interesting window, clean data and look for a trend
		 * 
		 * Save the trends in a buffer. Return results to users when requested.
		 * 
		 */
		
		/**
		 * Windows
		 */
		
		ArrayList<BarometerReading> allReadings = dh.getFullArchive();
		//log.info("Total readings: " + allReadings.size());
		ConcurrentHashMap<Window, ArrayList<BarometerReading>> mappedWindows = new ConcurrentHashMap<Window, ArrayList<BarometerReading>>();
		
		int count = 0;
		
		for(BarometerReading reading : allReadings) {
			double[] region = {(double)Math.floor(reading.getLatitude()), 
							   (double)Math.floor(reading.getLatitude() + 1),
							   (double)Math.floor(reading.getLongitude()), 
							   (double)Math.floor(reading.getLongitude() + 1)};
			Window currentReadingWindow = new Window(region);
			
			if(count==0) {
				ArrayList<BarometerReading> startThisOff = new ArrayList<BarometerReading>();
				startThisOff.add(reading);
				mappedWindows.put(currentReadingWindow, startThisOff);
			}
			
			for(Window existingWindow : mappedWindows.keySet()) {
				boolean match = readingInWindow(existingWindow, reading); // existingWindow.equals(currentReadingWindow);
				if(match) {
					ArrayList<BarometerReading> windowReads = mappedWindows.get((Window) existingWindow);
					mappedWindows.remove((Window)existingWindow);
					windowReads.add(reading);
					//System.out.print("window match contains: " + windowReads.size());
					mappedWindows.put(existingWindow, windowReads);
					//System.out.println(" now " + mappedWindows.get((Window)existingWindow).size());
				} else {
					// System.out.println("no window match, adding" + existingWindow.minLatitude);
					ArrayList<BarometerReading> startThisOff = new ArrayList<BarometerReading>();
					startThisOff.add(reading);
					mappedWindows.put(currentReadingWindow, startThisOff);
				}
				
			}
			count++;
		}
	
		
		for(Object obj : mappedWindows.keySet()) {
			Window win = (Window)obj;
			ArrayList<BarometerReading> list = mappedWindows.get(win);
			if(list.size()<10) {
				mappedWindows.remove(win);
			} else {
				trendWindows.add(new TrendWindow("--", win));
			}
		}
		long endTime = System.currentTimeMillis();
		long time = endTime - startTime;
		//log.info(mappedWindows.size() + " windows found in " + (time/1000) +" seconds" );
		
		/**
		 * TODO: Clean data
		 */
		
		/**
		 * Find trends
		 */
		
		
		/**
		 * Save the results
		 */
		dh.saveTrends(trendWindows);
		
		//log.info("Saved trends");
		
		
		
		return trendWindows;
	}
	
	

	/*
	public static void main(String[] args) {
		Analysis analysis = new Analysis();
		//analysis.getTrendsInWindows();
	}
	*/
	
	public Analysis() {
		// db = new DatabaseHelper();
		//db.create();
		
	}
	
	
}
