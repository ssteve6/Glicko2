package edu.vt.ssteve6.glicko2;

public class Player {
	
	private Double rating;
	private Double deviation;
	private Double volativity;
	private String name;
	private double mu;
	private double phi;

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

		this.mu = (getRating() - 1500) / Glicko2.getScaling();
		this.phi = getDeviation() / Glicko2.getScaling();
	}

	/**
	 * @return the player's name
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * @param name the player's name to set
	 */
	public void setName(String name) {
		this.name = name;
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

	public double getMu()
	{
		return this.mu;
	}

	public void setMu(double mu) {
		this.mu = mu;
	}

	public double getPhi()
	{
		return this.phi;
	}

	public void setPhi(double phi) {
		this.phi = phi;
	}
	
	public double g()
	{
		return 1 / (Math.sqrt(1 + 3 * Math.pow(getPhi(), 2) / Math.pow(Math.PI, 2)));
	}
	
	public double E(double mu, double phi)
	{
		return 1 / (1 + Math.exp(-1 * phi * (getMu() - mu)));
	}

	private double f(double x, double delta, double v, double a) {
		return (Math.exp(x) * (Math.pow(delta, 2) - Math.pow(getPhi(), 2) - v - Math.exp(x)))
				/ (2 * Math.pow(Math.pow(getPhi(), 2) + v + Math.exp(x), 2))
				- (x - a) / Math.pow(Glicko2.getTau(), 2);
	}

	public void calcNewValues(double v, double delta) {
		System.out.println("Player.calcNewValues");
		double a, A, B, epsilon;

		a = Math.log(Math.pow(getVolativity(), 2));

		A = a;

		epsilon = 0.000001;

		if (Math.pow(delta, 2) > Math.pow(getPhi(), 2) + v) {
			B = Math.log(Math.pow(delta, 2)
					- Math.pow(getPhi(), 2)
					- v);
		} else {
			int k = 1;

			System.out.println("\t a = " + a + ", t = " + Glicko2.getTau());
			System.out.println("\t f(a - kt) = " + f(a - k * Glicko2.getTau(), delta, v, a));

			while (f(a - k * Glicko2.getTau(), delta, v, a) < 0) {
				System.out.println("\t k++");
				k += 1;
			}

			B = a - k * Glicko2.getTau();
		}

		Double fA = f(A, delta, v, a);
		Double fB = f(B, delta, v, a);

		System.out.println("\t Entering Loop ...");
		while (Math.abs(B - A) > epsilon) {
			Double fC;
			Double C = A + (A - B) * fA / (fB - fA);
			fC = f(C, delta, v, a);			

			if (fC * fB < 0) {
				A = B;
				fA = fB;
			} else {
				fA = fA / 2;
			}

			B = C;
			fB = fC;
			
			System.out.println("\t A = " + A + ", B = " + B + ", C = " + C);
			System.out.println("\t fA = " + fA + ", fB = " + fB + ", fC = " + fC);
		}

		System.out.println("\tExiting loop ...");

		System.out.println("\t setting volatility");

		double volPrime = Math.exp(A / 2);
		double phiStar = Math.sqrt(Math.pow(getPhi(), 2) + Math.pow(volPrime, 2));
		double phiPrime = Math.pow(Math.sqrt((1 / Math.pow(phiStar, 2)) + (1 / v)),-1);
		double muPrime = getMu() + Math.pow(phiPrime, 2) * ( delta / v );
		
//		System.out.println("\t v = " + v);
		
		System.out.println("\t Phi = " + getPhi());
		System.out.println("\t Phi* = " + phiStar);

		System.out.println("\t New Vol = " + volPrime);
		System.out.println("\t New Phi = " + phiPrime);
		System.out.println("\t New Mu = " + muPrime);

		setVolativity(volPrime);
		setMu(muPrime);
		setPhi(phiPrime);

		setRating(Glicko2.getScaling() * muPrime + 1500);
		setDeviation(Glicko2.getScaling() * phiPrime);

		System.out.println("\t New Rating = " + getRating());
		System.out.println("\t New Deviation = " + getDeviation());

	}

}
