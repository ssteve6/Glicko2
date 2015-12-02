package edu.vt.ssteve6.glicko2;

public class Player {
	
	private Double rating;
	private Double deviation;
	private Double volativity;
	private String name;

	/**
	 * @param name
	 * @param rating
	 * @param deviation
	 * @param volativity
	 */
	public Player(String name, Double rating, Double deviation, Double volativity) {
		this.name = name;
		this.rating = rating;
		this.deviation = deviation;
		this.volativity = volativity;
	}

	/**
	 * @return the player's name
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * @return the rating
	 */
	public Double getRating() {
		return rating;
	}
	/**
	 * @param rating the rating to set
	 */
	public void setRating(Double rating) {
		this.rating = rating;
	}
	/**
	 * @return the deviation
	 */
	public Double getDeviation() {
		return deviation;
	}
	
	/**
	 * @param name the player's name to set
	 */
	public void setName(String name)
	{
		this.name = name;
	}
	
	/**
	 * @param deviation the deviation to set
	 */
	public void setDeviation(Double deviation) {
		this.deviation = deviation;
	}
	/**
	 * @return the volativity
	 */
	public Double getVolativity() {
		return volativity;
	}
	/**
	 * @param volativity the volativity to set
	 */
	public void setVolativity(Double volativity) {
		this.volativity = volativity;
	}
	
	public String toString()
	{
		return name;
	}
	
	public double mu()
	{
		return (getRating() - 1500) / Glicko2.getScaling();
	}
	
	public double phi()
	{
		return getDeviation() / Glicko2.getScaling();
	}
	
	public double g()
	{
		return 1 / ( Math.sqrt( 1 + 3 * Math.pow(phi(), 2) / Math.pow(Math.PI, 2) ) );
	}
	
	public double E(double mu, double phi)
	{
		return 1 / ( 1 + Math.exp(-1 * phi() * ( mu() - mu)) );
	}

}
