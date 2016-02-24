/**
 * This class allows to locate anything on the map
 * contains min and max coordinates
 * And the actual coordinates of the entity
 */

package fr.isima.sma.world;

public class Location {
	private int locationX;
	private int locationY;
	
	private static int minLocationX = 0;
	private static int minLocationY = 0;
	private static int maxLocationX = 0;
	private static int maxLocationY = 0;
	
	private boolean isInterior; // indicate if the location concerns interior or exterior
	
	/**
	 * Default constructor, setting everything to 0
	 */
	public Location() {
		this(0, 0, false);
	}

	/**
	 * Constructor with single coordinates
	 * @param locationX the location on the x axis
	 * @param locationY the location on the y axis
	 */
	public Location(int locationX, int locationY) {
		this(locationX, locationY, false);
	}

	/**
	 * constructor to set all the parameters at once
	 * @param locationX the location on the x axis
	 * @param locationY the location on the y axis
	 */
	public Location(int locationX, int locationY, boolean isInterior) {
		this.locationX = locationX;
		this.locationY = locationY;
		this.isInterior = isInterior;
	}

	/**
	 * @return the locationX
	 */
	public int getLocationX() {
		return locationX;
	}

	/**
	 * @param locationX the locationX to set
	 */
	public void setLocationX(int locationX) {
		if(locationX >= Location.minLocationX && locationX <= Location.maxLocationX) {
			this.locationX = locationX;
		}
	}

	/**
	 * @return the locationY
	 */
	public int getLocationY() {
		return locationY;
	}

	/**
	 * @param locationY the locationY to set
	 */
	public void setLocationY(int locationY) {
		if(locationY >= Location.minLocationY && locationY <= Location.maxLocationY) {
			this.locationY = locationY;
		}
	}

	/**
	 * @return the minLocationX
	 */
	public static int getMinLocationX() {
		return minLocationX;
	}

	/**
	 * @param minLocationX the minLocationX to set
	 */
	public static void setMinLocationX(int minLocationX) {
		Location.minLocationX = minLocationX;
	}

	/**
	 * @return the minLocationY
	 */
	public static int getMinLocationY() {
		return Location.minLocationY;
	}
	
	public void setLocation(int locationX, int locationY) {
		
		if(isValid(locationX, locationY)) {
			this.locationX = locationX;
			this.locationY = locationY;
		} else {
			System.err.println("Bad location setting, limits X["+minLocationX+"-"+maxLocationX+"] Y["+minLocationY+"-"+maxLocationY+"]");
		}
	}
	
	/**
	 * Set complete min location
	 * @param minLocationX the min location on the x axis
	 * @param minLocationY the min location on the y axis
	 */
	public static void setMinLocation(int minLocationX, int minLocationY) {
		Location.minLocationX = minLocationX;
		Location.minLocationY = minLocationY;
	}

	/**
	 * Set complete max location
	 * @param maxLocationX the max location on the x axis
	 * @param maxLocationY the max location on the y axis
	 */
	public static void setMaxLocation(int maxLocationX, int maxLocationY) {
		Location.maxLocationX = maxLocationX;
		Location.maxLocationY = maxLocationY;
	}
	
	/**
	 * Test if the coordinates are valid or not
	 * @param locationX the location on the x axis
	 * @param locationY the location on the y axis
	 * @return true if the coordinates are ok, false if they're not
	 */
	private boolean isValid(int locationX, int locationY) {
		boolean ret = false;
		// Testing the two coordinates separately
		if(locationX >= Location.minLocationX && locationX <= Location.maxLocationX) {
			if(locationY >= Location.minLocationY && locationY <= Location.maxLocationY) {
				ret = true;
			}
		}
		return ret;
	}

	/**
	 * @return the interior
	 */
	public boolean isInterior() {
		return isInterior;
	}

	/**
	 * @param interior the interior to set
	 */
	public void setInterior(boolean interior) {
		this.isInterior = interior;
	}
	
	public boolean isInside() {
		return this.isInterior;
	}
	
	public boolean isOutside() {
		return !(this.isInterior);
	}
	
	/**
	 * Simply invert the interior boolean to quickly change the state
	 */
	public void toggleInOut() {
		this.isInterior = !(this.isInterior);
	}
	
	public void setInside() {
		this.isInterior = true;
	}
	
	public void setOutside() {
		this.isInterior = false;
	}
	
	/**
	 * Shift the locations with positives or negatives offsets
	 * @param offsetX the shift value for x axis
	 * @param offsetY the shift value for y axis
	 */
	public void shiftLocation(int offsetX, int offsetY) {
		// TODO possible to separately test X and Y and move one of them if
		// the other is bad
		// Right now the entity wont move for some turns if one of the location
		// is bad
		setLocation(this.locationX + offsetX, this.locationY + offsetY);
	}
	
	/**
	 * @return a printable string to see where is the entity
	 */
	@Override
	public String toString() {
		return "x : " + this.locationX + " - y : " + this.locationY + ((this.isInterior)?" (interior)": " (exterior)");
	}

	/**
	 * @return the maxLocationX
	 */
	public static int getMaxLocationX() {
		return Location.maxLocationX;
	}

	/**
	 * @param maxLocationX the maxLocationX to set
	 */
	public static void setMaxLocationX(int maxLocationX) {
		Location.maxLocationX = maxLocationX;
	}

	/**
	 * @return the maxLocationY
	 */
	public static int getMaxLocationY() {
		return Location.maxLocationY;
	}

	/**
	 * @param maxLocationY the maxLocationY to set
	 */
	public static void setMaxLocationY(int maxLocationY) {
		Location.maxLocationY = maxLocationY;
	}

	/**
	 * @param minLocationY the minLocationY to set
	 */
	public static void setMinLocationY(int minLocationY) {
		Location.minLocationY = minLocationY;
	}

} // Class
