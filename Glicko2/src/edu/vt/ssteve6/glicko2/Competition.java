package edu.vt.ssteve6.glicko2;

import java.util.Enumeration;
import java.util.Hashtable;

import edu.vt.ssteve6.glicko2.Glicko2;
import edu.vt.ssteve6.glicko2.Player;

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
//    	System.out.println("Competition.V()");
    	System.out.println("\t["+opponent+"] mu = "+opponent.getMu() + ", phi = "+opponent.getPhi() + ", "
    			+ "g = "+Glicko2.g(opponent.getPhi()) + ", "
    			+ "E() = "+ Glicko2.E(player.getMu(), opponent.getMu(), opponent.getPhi()));
        return Math.pow(Glicko2.g(opponent.getPhi()), 2)
                * Glicko2.E(player.getMu(), opponent.getMu(), opponent.getPhi())
                * (1 - Glicko2.E(player.getMu(), opponent.getMu(), opponent.getPhi()));
    }

    private double Delta(Player player, Player opponent) {
        return Glicko2.g(opponent.getPhi()) * 
        		(players.get(player).get(opponent) - Glicko2.E(player.getMu(), opponent.getMu(), opponent.getPhi()));
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
            
            Double tV = V(player, key);
            
//            System.out.println("v ("+player+", "+key+") = "+tV);
            
            v += tV;
                 
        }
        
        v = Math.pow(v, -1);
        
        System.out.println("\t end v");
        System.out.println("\t V = " + v);

        opps = opponents.keys();

        System.out.println("\t begin delta");
        while (opps.hasMoreElements()) {

            Player key = opps.nextElement();

            delta += Delta(player, key);

        }
        
        System.out.println("\t Delta w/o V = " + delta);
        
        delta *= v;
        
        System.out.println("\t end delta");
        
        System.out.println("\t delta = " + delta);

        System.out.println("\t calcing");
        player.calcNewValues(v, delta);

    }

}
