/**
 * This class allows to locate anything on the map
 * contains min and max coordinates
 * And the actual coordinates of the entity
 */

package fr.isima.sma.world;

public class Location {
	private int locationX;
	private int locationY;
	
	private int minLocationX;
	private int minLocationY;
	private int maxLocationX;
	private int maxLocationY;
	
	private boolean isInterior; // indicate if the location concerns interior or exterior
	
	/**
	 * Default constructor, setting everything to 0
	 */
	public Location() {
		this(0, 0, 0, 0, 0, 0, false);
	}

	/**
	 * Constructor with single coordinates
	 * @param locationX the location on the x axis
	 * @param locationY the location on the y axis
	 */
	public Location(int locationX, int locationY) {
		this(locationX, locationY, 0, 0, 0, 0, false);
	}
	
	/**
	 * Constructor to set location and max coordinates at the same time
	 * @param locationX the location on the x axis
	 * @param locationY the location on the y axis
	 * @param maxLocationX the max location on the x axis
	 * @param maxLocationY the max location on the y axis
	 */
	public Location(int locationX, int locationY, int maxLocationX, int maxLocationY) {
		this(locationX, locationY, 0, 0, maxLocationX, maxLocationY, false);
	}

	/**
	 * constructor to set all the parameters at once
	 * @param locationX the location on the x axis
	 * @param locationY the location on the y axis
	 * @param minLocationX the min location on x axis
	 * @param minLocationY the min location on y axis
	 * @param maxLocationX the max location on x axis
	 * @param maxLocationY the max location on y axis
	 */
	public Location(int locationX, int locationY, int minLocationX, int minLocationY, int maxLocationX,
			int maxLocationY, boolean isInterior) {
		this.locationX = locationX;
		this.locationY = locationY;
		this.minLocationX = minLocationX;
		this.minLocationY = minLocationY;
		this.maxLocationX = maxLocationX;
		this.maxLocationY = maxLocationY;
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
		if(locationX >= minLocationX && locationX <= maxLocationX) {
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
		if(locationY >= minLocationY && locationY <= maxLocationY) {
			this.locationY = locationY;
		}
	}

	/**
	 * @return the minLocationX
	 */
	public int getMinLocationX() {
		return minLocationX;
	}

	/**
	 * @param minLocationX the minLocationX to set
	 */
	public void setMinLocationX(int minLocationX) {
		this.minLocationX = minLocationX;
	}

	/**
	 * @return the minLocationY
	 */
	public int getMinLocationY() {
		return minLocationY;
	}

	/**
	 * @param minLocationY the minLocationY to set
	 */
	public void setMinLocationY(int minLocationY) {
		this.minLocationY = minLocationY;
	}

	/**
	 * @return the maxLocationX
	 */
	public int getMaxLocationX() {
		return maxLocationX;
	}

	/**
	 * @param maxLocationX the maxLocationX to set
	 */
	public void setMaxLocationX(int maxLocationX) {
		this.maxLocationX = maxLocationX;
	}

	/**
	 * @return the maxLocationY
	 */
	public int getMaxLocationY() {
		return maxLocationY;
	}

	/**
	 * @param maxLocationY the maxLocationY to set
	 */
	public void setMaxLocationY(int maxLocationY) {
		this.maxLocationY = maxLocationY;
	}
	
	public void setLocation(int locationX, int locationY) {
		if(isValid(locationX, locationY)) {
			this.locationX = locationX;
			this.locationY = locationY;
		} else {
			System.err.println("Bad location setting");
		}
	}
	
	/**
	 * Set complete min location
	 * @param minLocationX the min location on the x axis
	 * @param minLocationY the min location on the y axis
	 */
	public void setMinLocation(int minLocationX, int minLocationY) {
		this.minLocationX = minLocationX;
		this.minLocationY = minLocationY;
	}

	/**
	 * Set complete max location
	 * @param maxLocationX the max location on the x axis
	 * @param maxLocationY the max location on the y axis
	 */
	public void setMaxLocation(int maxLocationX, int maxLocationY) {
		this.maxLocationX = maxLocationX;
		this.maxLocationY = maxLocationY;
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
		if(locationX >= this.minLocationX && locationX <= this.maxLocationX) {
			if(locationY >= this.minLocationY && locationY <= this.maxLocationY) {
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
		return "x : " + locationX + " - y : " + locationY + ((isInterior)?" (interior)": " (exterior)");
	}

} // Class
