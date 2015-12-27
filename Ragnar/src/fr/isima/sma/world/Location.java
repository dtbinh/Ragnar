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
	
	/**
	 * Default constructor, setting everything to 0
	 */
	public Location() {
		this(0, 0, 0, 0, 0, 0);
	}

	/**
	 * Constructor with single coordinates
	 * @param locationX the location on the x axis
	 * @param locationY the location on the y axis
	 */
	public Location(int locationX, int locationY) {
		this(locationX, locationY, 0, 0, 0, 0);
	}
	
	/**
	 * Constructor to set location and max coordinates at the same time
	 * @param locationX the location on the x axis
	 * @param locationY the location on the y axis
	 * @param maxLocationX the max location on the x axis
	 * @param maxLocationY the max location on the y axis
	 */
	public Location(int locationX, int locationY, int maxLocationX, int maxLocationY) {
		this(locationX, locationY, 0, 0, maxLocationX, maxLocationY);
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
			int maxLocationY) {
		this.locationX = locationX;
		this.locationY = locationY;
		this.minLocationX = minLocationX;
		this.minLocationY = minLocationY;
		this.maxLocationX = maxLocationX;
		this.maxLocationY = maxLocationY;
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
		this.locationX = locationX;
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
		this.locationY = locationY;
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
		if(this.locationX >= this.minLocationX && this.locationX <= this.maxLocationX) {
			if(this.locationY >= this.minLocationY && this.locationY <= this.maxLocationY) {
				ret = true;
			}
		}
		
		return ret;
	}
	
}
