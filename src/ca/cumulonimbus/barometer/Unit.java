package ca.cumulonimbus.barometer;

/*
 *  Units
 *  Millibars (mbar)
    Hectopascals (hPa)
    Standard Atmosphere (atm)
    Millimeteres of Mercury (mmHg)
 */
public class Unit {
	double valueInMb;
	String abbrev;
	
	// Conversion factors from http://www.csgnetwork.com/meteorologyconvtbl.html
	public double convertToPreferredUnit() {
		try {
			if(abbrev.contains("mbar")) {
				// No change. reading comes to us in mbar.
				return valueInMb;
			} else if(abbrev.contains("hPa")) {
				// mbar = hpa.
				return valueInMb;
			} else if(abbrev.contains("atm")) {
				return valueInMb * 0.000986923;
			} else if(abbrev.contains("kPa")) {
				return valueInMb * 0.1;
			} else if(abbrev.contains("mmHg")) {
				return valueInMb * 0.75006;
			} else if(abbrev.contains("inHg")) {
				return valueInMb * 0.02961;
			} else {
				return valueInMb;
			}
		} catch(Exception e) {
			return valueInMb;
		}
	}
	
	public String getDisplayText() {
		return convertToPreferredUnit() + abbrev;
	}
	
	public Unit(String abbrev) {
		this.abbrev = abbrev;
	}
	public double getValueInMb() {
		return valueInMb;
	}
	public void setValue(double valueInMb) {
		this.valueInMb = valueInMb;
	}
	public String getAbbreviation() {
		return abbrev;
	}
	public void setAbbreviation(String abbreviation) {
		this.abbrev = abbreviation;
	}
}