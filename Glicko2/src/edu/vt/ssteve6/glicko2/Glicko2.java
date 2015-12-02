package edu.vt.ssteve6.glicko2;

public class Glicko2 {
	
	private static double tau;
	private static double defaultStart;
	private static double defaultDeviation;
	private static double defaultVolativity;
	
	private static double scaling = 173.7178;
	
	/**
	 * @return the scaling
	 */
	public static double getScaling() {
		return scaling;
	}
	/**
	 * @param scaling the scaling to set
	 */
	public static void setScaling(double scaling) {
		Glicko2.scaling = scaling;
	}
	public static double getTau() {
		return tau;
	}
	public static void setTau(double tau) {
		Glicko2.tau = tau;
	}
	public static double getDefaultStart() {
		return defaultStart;
	}
	public static double getDefaultVolativity() {
		return defaultVolativity;
	}
	public static void setDefaultStart(double defaultStart) {
		Glicko2.defaultStart = defaultStart;
	}
	public static double getDefaultDeviation() {
		return defaultDeviation;
	}
	public static void setDefaultDeviation(double defaultDeviation) {
		Glicko2.defaultDeviation = defaultDeviation;
	}
	public static void setDefaultVolativity(double defaultVolativity) {
		Glicko2.defaultVolativity = defaultVolativity;
	}
	
	public static Player newPlayer()
	{
		return new Player(null, getDefaultStart(), getDefaultDeviation(), getDefaultVolativity());
	}
	
	public static Player newPlayer(String name, double start, double deviation, double volativity)
	{
		return new Player(name, start, deviation, volativity);
	}
	
	public static Competition newCompetition(String name)
	{
		return new Competition(name);
	}
	
	public static Double g(double phi)
	{
		return 1 / Math.sqrt(1 + 3 * Math.pow(phi, 2) / Math.pow(Math.PI, 2));
	}
	
	public static double E(double mu, double mu_j, double phi_j)
	{
		return 1 / ( 1 + Math.exp(-1 * g(phi_j) * (mu - mu_j) ) );
	}
	
}
