package fr.isima.sma.world;

public abstract class Sector extends AbstractModelObject {

	static public enum SectorType {
		Street, Bank, HeadQuarter, HeroHQ, VilainHQ
	}

	protected SectorType 	type;
	protected Location		location;
	protected int			numberHero;
	protected int			numberVilain;
	protected int			numberCitizen;
	protected int			numberGroup;

	public Sector() {
		this(SectorType.Street, new Location());
	}

	public Sector(SectorType type, Location location) {
		super();
		numberHero = 0;
		numberVilain = 0;
		numberCitizen = 0;
		numberGroup = 0;
		this.type = type;
		this.location = location;
	}

	@Override
	public abstract String toString();

	/**
	 * @return the type
	 */
	public SectorType getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(SectorType type) {
		this.type = type;
	}

	/**
	 * @return the location
	 */
	public Location getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(Location location) {
		this.location = location;
	}

	public int getNumberHero() {
		return numberHero;
	}

	public void setNumberHero(int numberHero) {
		int old = this.numberHero;
		this.numberHero = numberHero;
		firePropertyChange("numberHero", old, numberHero);	// BINDING
	}

	public int getNumberVilain() {
		return numberVilain;
	}

	public void setNumberVilain(int numberVilain) {
		int old = this.numberVilain;
		this.numberVilain = numberVilain;
		firePropertyChange("numberVilain", old, numberVilain);	// BINDING
	}

	public int getNumberCitizen() {
		return numberCitizen;
	}

	public void setNumberCitizen(int numberCitizen) {
		int old = this.numberCitizen;
		this.numberCitizen = numberCitizen;
		firePropertyChange("numberCitizen", old, numberCitizen);	// BINDING
	}

	public int getNumberGroup() {
		return numberGroup;
	}

	public void setNumberGroup(int numberGroup) {
		int old = this.numberGroup;
		this.numberGroup = numberGroup;
		firePropertyChange("numberGroup", old, numberGroup);	// BINDING
	}

}
