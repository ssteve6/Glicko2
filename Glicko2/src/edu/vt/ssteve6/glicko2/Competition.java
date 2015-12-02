/**
 * 
 */
package edu.vt.ssteve6.glicko2;

import java.util.Enumeration;
import java.util.Hashtable;

/**
 * @author u775329
 *
 */
public class Competition {

	public final static double WIN = 1.0;
	public final static double LOSE = 0.0;
	public final static double TIE = 0.5;

	private String compName;

	private Hashtable<Player, Hashtable<Player, Double>> players;

	/**
	 * @return the players
	 */
	public Hashtable<Player, Hashtable<Player, Double>> getPlayers() {
		return players;
	}

	public Competition(String name) {
		setName(name);
		players = new Hashtable<>();
	}

	public String getName() {
		return compName;
	}

	public void setName(String name) {
		this.compName = name;
	}

	public void addPlayer(Player player) {
		players.put(player, new Hashtable<Player, Double>());
	}

	public void addOpponent(Player player, Player opponent) {
		players.get(player).put(opponent, TIE);
	}

	public void recordResult(Player player, Player opponent, double result) {
		recordResult(player, opponent, result, false);
	}
	
	public void recordResult(Player player, Player opponent, double result, boolean autoUpdate) {
		players.get(player).put(opponent, result);
		
		if(autoUpdate){ players.get(opponent).put(player, 1-result); }
	}

	public void roundRobin() {
		Enumeration<Player> keys = players.keys();

		while (keys.hasMoreElements()) {
			Player key = keys.nextElement();
			Hashtable<Player, Double> opponents = players.get(key);

			Enumeration<Player> subKeys = players.keys();

			while (subKeys.hasMoreElements()) {
				Player posOpp = subKeys.nextElement();
				if (posOpp != key && !(opponents.containsKey(posOpp))) {
					opponents.put(posOpp, TIE);
				}
			}

		}
	}
	
	public void process(Player player)
	{
		Enumeration<Player> keys = players.get(player).keys();
		
		Hashtable<Player, Hashtable<String, Double>> values = new Hashtable<>();
	}

}
