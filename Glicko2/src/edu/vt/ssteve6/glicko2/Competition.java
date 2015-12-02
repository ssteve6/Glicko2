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

	public Competition(String name) {
		setName(name);
		players = new Hashtable<Player, Hashtable<Player, Double>>();
	}

    /**
     * @return the players
     */
    public Hashtable<Player, Hashtable<Player, Double>> getPlayers() {
        return players;
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

        if (autoUpdate) {
            players.get(opponent).put(player, 1 - result);
        }
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
    
    private double V(Player player, Player opponent)
    {
        return Math.pow(opponent.getPhi(), 2)
                * player.E(opponent.getMu(), opponent.getPhi())
                * (1 - player.E(opponent.getMu(), opponent.getPhi()));
    }

    private double Delta(Player player, Player opponent) {
        return opponent.getPhi() * (players.get(player).get(opponent) - player.E(opponent.getMu(), opponent.getPhi()));
    }

    public void process(Player player) {
        System.out.println("Competition.process");
        Hashtable<Player, Double> opponents = players.get(player);

        Enumeration<Player> opps = opponents.keys();

		double v = 0;
        double delta = 0;

        System.out.println("\t begin v");
        while (opps.hasMoreElements()) {

            Player key = opps.nextElement();
            
            v += V(player, key);
                 
        }
        System.out.println("\t end v");

        opps = opponents.keys();

        System.out.println("\t begin delta");
        while (opps.hasMoreElements()) {

            Player key = opps.nextElement();

            delta += v * Delta(player, key);

        }
        System.out.println("\t end delta");

        System.out.println("\t calcing");
        player.calcNewVolatility(v, delta);

    }

}
