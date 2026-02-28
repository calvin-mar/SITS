package tournament;
import java.util.ArrayList;

public abstract class Bracket {
	ArrayList<TourneyPlayer> participantList;
	
	public Bracket(ArrayList<TourneyPlayer> list) {
		this.participantList = list;
	}
	
	public abstract TourneyPlayer[] nextPair(State state);
}
