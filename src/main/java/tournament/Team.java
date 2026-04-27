package tournament;

import java.util.ArrayList;

public abstract class Team extends Participant {
	ArrayList<Participant> roster;
	
	public Team() {
		super();
		this.roster = new ArrayList<Participant>();
	}
	
	public void joinTeam(Participant p) {
		this.roster.add(p);
	}
	
	public void leaveTeam(Participant p) {
		this.roster.remove(p);
	}
	
	public void clearMemotry() {
		for(Participant p: roster) {
			p.clearMemory();
		}
	}
	
	public void updateMemory(State s) {
		for(Participant p: roster) {
			p.updateMemory(s);
		}
	}
}
