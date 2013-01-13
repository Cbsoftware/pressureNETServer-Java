package ca.cumulonimbus.barometer;


import java.io.IOException;

import java.util.zip.*;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ca.cumulonimbus.barometer.ScienceHandler;

public class BarometerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String logName = "ca.cumulonimbus.barometer.BarometerServlet";
	private static Logger log = Logger.getLogger(logName);
	
	String serverURL = ""; 
	// private static double TENDENCY_HOURS = 12;
	
	private static DatabaseHelper dh;
	
	public BarometerServlet() {
		dh = new DatabaseHelper();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Get and process the parameters
		Map<String, String[]> params = request.getParameterMap();
		
		// What type of request is this? Options are:
		// 1. We're being sent new data
		// 2. This is a download request.
		//log.info("do post");
		if(params.containsKey("download")) {
			// This is #2.
			//log.info("download req " + params.get("download")[0]);
			if(params.get("download")[0].equals("all_data")) {
				//log.info("all data");
				// Download all the things!
				// except don't.
				//ArrayList<BarometerReading> allReadings = dh.getAllReadings();
				
				response.setContentType("text/html");
				PrintWriter out = response.getWriter();
				try {
					//for(BarometerReading br : allReadings) {
						//out.print(barometerReadingToWeb(br));
					//}
				} catch(Exception e) {
					log.info(e.getMessage());
				}

				out.close();
			} else if (params.get("download")[0].equals("recent_data")) {
				log.info(params.containsKey("days") + "");
				if(params.containsKey("days")) {
					String days = params.get("days")[0];
					log.info("recent data: " + days);
					int numOfDays = Integer.valueOf(days);
					
					ArrayList<BarometerReading> recentReadings = dh.getRecentReadings(numOfDays);
					
					response.setContentType("text/html");
					PrintWriter out = response.getWriter();
					try {
						for(BarometerReading br : recentReadings) {
							out.print(barometerReadingToWeb(br));
						}
					} catch(Exception e) {
						log.info(e.getMessage());
					}
					out.close();
				}
			} else if (params.get("download")[0].equals("local_data")) {
				double centerLat = Double.parseDouble(params.get("centerlat")[0]) / 1E6;
				double centerLon = Double.parseDouble(params.get("centerlon")[0]) / 1E6;
				double latSpan = Double.parseDouble(params.get("latspan")[0]) / 1E6;
				double longSpan = Double.parseDouble(params.get("longspan")[0]) / 1E6;
				//log.info("local data: " + centerLat + ", " + centerLon);
				
				ArrayList<Double> regionList = new ArrayList<Double>();
				double lat1 = centerLat - latSpan / 2;
				double lat2 = centerLat + latSpan / 2;
				double lon1 = centerLon - longSpan / 2;
				double lon2 = centerLon + longSpan / 2;
				regionList.add(lat1);
				regionList.add(lat2);
				regionList.add(lon1);
				regionList.add(lon2);
				
				long day = (1000 * 60 * 60 * 24 * 1);
				long sinceWhen = Calendar.getInstance().getTimeInMillis() - day; // one day ago
				//log.info("now: " + Calendar.getInstance().getTimeInMillis() + " minus " + week);
				ArrayList<BarometerReading> recentReadings = dh.getReadingsWithinRegion(regionList, sinceWhen);
				
				
				response.setContentType("text/html");
				PrintWriter out = response.getWriter();
				out.print("local_data return;");
				for(BarometerReading br : recentReadings) {
					out.print(barometerReadingToWeb(br));
				}
				out.close();
				/*	}  else if (params.get("download")[0].equals("local_tendency_data")) {
				double centerLat = Double.parseDouble(params.get("centerlat")[0]) / 1E6;
				double centerLon = Double.parseDouble(params.get("centerlon")[0]) / 1E6;
				double latSpan = Double.parseDouble(params.get("latspan")[0]) / 1E6;
				double longSpan = Double.parseDouble(params.get("longspan")[0]) / 1E6;
				log.info("local tendency data: " + centerLat + ", " + centerLon);
				
				ArrayList<Double> regionList = new ArrayList<Double>();
				double lat1 = centerLat - latSpan / 2;
				double lat2 = centerLat + latSpan / 2;
				double lon1 = centerLon - longSpan / 2;
				double lon2 = centerLon + longSpan / 2;
				regionList.add(lat1);
				regionList.add(lat2);
				regionList.add(lon1);
				regionList.add(lon2);
				
				long tendencyHistory = (long)(1000 * 60 * 60 * TENDENCY_HOURS);  
				long sinceWhen = Calendar.getInstance().getTimeInMillis() - tendencyHistory ; // one week ago
				log.info("now: " + Calendar.getInstance().getTimeInMillis() + " minus " + tendencyHistory );
				ArrayList<BarometerReading> recentReadings = dh.getReadingsWithinRegion(regionList, sinceWhen);
				
				response.setContentType("text/html");
				PrintWriter out = response.getWriter();
				out.print("local_data_tendency return;");
				for(BarometerReading br : recentReadings) {
					out.print(barometerReadingTendencyToWeb(br,dh.getSimpleTendencyFromUserID(br.getAndroidId())));
					log.info(barometerReadingTendencyToWeb(br,dh.getSimpleTendencyFromUserID(br.getAndroidId())));
				}
				out.close(); */
			} else if (params.get("download")[0].equals("full_delete_request")) {
				String userID = params.get("userid")[0];
				log.info("full delete request for: " + userID);
				boolean success = dh.deleteUserData(userID);
				response.setContentType("text/html");
				PrintWriter out = response.getWriter();
				out.print(success);
				out.close();
			} else if(params.get("download")[0].equals("local_data_with_tendencies")) {
				/*
				double centerLat = Double.parseDouble(params.get("centerlat")[0]) / 1E6;
				double centerLon = Double.parseDouble(params.get("centerlon")[0]) / 1E6;
				double latSpan = Double.parseDouble(params.get("latspan")[0]) / 1E6;
				double longSpan = Double.parseDouble(params.get("longspan")[0]) / 1E6;
				//log.info("local data: " + centerLat + ", " + centerLon);
				
				ArrayList<Double> regionList = new ArrayList<Double>();
				double lat1 = centerLat - latSpan / 2;
				double lat2 = centerLat + latSpan / 2;
				double lon1 = centerLon - longSpan / 2;
				double lon2 = centerLon + longSpan / 2;
				regionList.add(lat1);
				regionList.add(lat2);
				regionList.add(lon1);
				regionList.add(lon2);
				
				long week = (1000 * 60 * 60 * 24 * 7);
				long sinceWhen = Calendar.getInstance().getTimeInMillis() - week; // one week ago
				//log.info("now: " + Calendar.getInstance().getTimeInMillis() + " minus " + week);

				ArrayList<BarometerReading> recents = dh.getReadingsAndTendenciesWithinRegion(regionList, sinceWhen);
				
				response.setContentType("text/html");
				PrintWriter out = response.getWriter();
				out.print("local_data_with_tendencies return;");
				for(BarometerReading br : recents) {
					out.print(barometerReadingToWeb(br));
				}
				out.close();
				*/
			}

		} else if(params.containsKey("analysis")) { 
			log.info("analysis " + params.get("analysis")[0]);
			if(params.get("analysis")[0].equals("all_trend_windows")) {
				Analysis analysis = new Analysis();
				
				response.setContentType("text/html");
				PrintWriter out = response.getWriter();
				out.print("analysis_all_trends return;");
			
				out.close();
				
			}
		} else if(params.containsKey("statistics")) {
			log.info("statistics " + params.get("statistics")[0]);
			if(params.get("statistics")[0].equals("by_user")) {
				if(params.containsKey("user_id")) {
					long sinceWhen = 0L;
					String units = "";
					if(params.containsKey("sincewhen")) {
						sinceWhen = Long.valueOf(params.get("sincewhen")[0]);
					}
					if(params.containsKey("units")) {
						units = (params.get("units")[0]);
					}
					String userId = params.get("user_id")[0];
					log.info("userId: " + userId + ", units: " + units);
					String chartData = dh.getChartFromSingleUser(userId, sinceWhen, units);
					String additionalData = "";
					String totalSubmissions = "";
					String lastDaySubmissions = "";
					String percentile = "";	
					String exportLink = "";
					if(params.containsKey("selfstats")) {
						String selfStats = params.get("selfstats")[0];
						if(selfStats.equals("yes")) {
							totalSubmissions = dh.generateStatisticsByUserAndTime(userId, 0);
							
							long now = Calendar.getInstance().getTimeInMillis();
							long day = 1000*60*60*24;
							lastDaySubmissions = dh.generateStatisticsByUserAndTime(userId, now-day);
							
							//percentile = dh.getUserPercentile(userId, totalSubmissions);
							String url = serverURL + "?export=true&userId=" + userId;
							exportLink = "<a href='" + url + "' style='color:#33b5e5'>Download Your Recent Data (CSV)</a>";
							exportLink += " (archive export temporarily disabled, coming back soon)";
							
							
							additionalData = totalSubmissions + "<br/>" + lastDaySubmissions + "<br/>" + exportLink;
						} else {
							additionalData = "";
						}
					}
					//log.info(chartData);
					response.setContentType("text/html");
					PrintWriter out = response.getWriter();
					out.print(chartData + additionalData + "</body></html>");
					
					out.close();
				}
			}
			
		} else if(params.containsKey("export")) {
			String export = params.get("export")[0];
			if(export.equals("true")) {
				String id = params.get("userId")[0];
				// give the user the CSV file
				String headings = "Time,Longitude,Latitude,Reading";
				String data = dh.getUserCSV(id);
				String file = headings + "\n" + data;
				
				response.setContentType("text/html");
				//response.setContentType("application/zip");
				PrintWriter out = response.getWriter();
				out.print(file); // entry
				out.close();
				
			}
		} else { 
			try {
				// This is #1.
				BarometerReading br = getBarometerReadingFromParams(params);
				
				// Store result in database
				dh.addReadingToDatabase(br);
				
				// Response
				response.setContentType("text/html");
				PrintWriter out = response.getWriter();
				
			} catch(Exception e) {
				response.setContentType("text/html");
				PrintWriter out = response.getWriter();
				out.write("There was an error. Please check your request and try again. Error information: " + e.getMessage());
				out.close();
				log.info(e.getMessage());
			}
		}
	}
	
	// Prepare data to send through the web. Decoded by
	// csvToBarometerReadings in the android app.
	public String barometerReadingToWeb(BarometerReading br) {
		return br.getLatitude() + "," + 
			   br.getLongitude() + "," +
			   br.getReading() + "," +
			   br.getTime() + "," +
			   br.getTimeZoneOffset() + "," +
			   br.getAndroidId() + "," +
			   br.getSharingPrivacy() + ";";
	}
	
	// Prepare data to send through the web. Decoded by
	// csvToBarometerReadings in the android app.
	public String barometerReadingTendencyToWeb(BarometerReading br, String tendency) {
		return br.getLatitude() + "," + 
			   br.getLongitude() + "," +
			   br.getReading() + "," +
			   br.getTime() + "," +
			   br.getTimeZoneOffset() + "," +
			   br.getAndroidId() + "," + 
		       tendency + ";";
	}
	
	// Create a Barometer Reading object from a list of parameters 
	public BarometerReading getBarometerReadingFromParams(Map<String, String[]> params) {
		BarometerReading br = new BarometerReading();
		br.setLatitude(Double.parseDouble(params.get("latitude")[0]));
		br.setLongitude(Double.parseDouble(params.get("longitude")[0]));
		br.setTime(Double.parseDouble(params.get("time")[0]));
		br.setTimeZoneOffset(Integer.parseInt(params.get("tzoffset")[0]));
		br.setReading(Double.parseDouble(params.get("reading")[0]));
		br.setAndroidId((params.get("text")[0]));
		br.setSharingPrivacy((params.get("share")[0]));
		
		return br;
	}

    public void log(String text) {

    }
	
}

