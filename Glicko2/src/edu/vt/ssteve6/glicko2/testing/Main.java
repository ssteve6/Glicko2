package edu.vt.ssteve6.glicko2.testing;

import edu.vt.ssteve6.glicko2.Competition;
import edu.vt.ssteve6.glicko2.Glicko2;
import edu.vt.ssteve6.glicko2.Player;

public class Main {

	public static void main(String[] args) {

		Glicko2.setTau(0.5);
		Glicko2.setDefaultStart(1500);
		Glicko2.setDefaultDeviation(350);
		Glicko2.setDefaultVolativity(0.6);

		Player p1 = Glicko2.newPlayer("P1", 1500, 200, 0.6);
		Player p2 = Glicko2.newPlayer("P2", 1400, 30, 0.6);
		Player p3 = Glicko2.newPlayer("P3", 1550, 100, 0.6);
		Player p4 = Glicko2.newPlayer("P4", 1700, 300, 0.6);

		Competition comp = Glicko2.newCompetition("Test Comp");

		comp.addPlayer(p1);
		comp.addPlayer(p2);
		comp.addPlayer(p3);
		comp.addPlayer(p4);

		comp.roundRobin();

		System.out.println(comp.getPlayers().toString());
		
		comp.recordResult(p1, p2, Competition.WIN, true);
		comp.recordResult(p1, p3, Competition.LOSE, true);
		comp.recordResult(p1, p4, Competition.LOSE, true);
		
		comp.recordResult(p2,  p3, Competition.TIE, true);
		comp.recordResult(p2,  p4, Competition.TIE, true);
		
		comp.recordResult(p3,  p4, Competition.LOSE, true);
		
		System.out.println(comp.getPlayers().toString());
		

	}

}
