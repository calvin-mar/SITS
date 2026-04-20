package tournament;
import java.util.ArrayList;

public class Tournament {
	
	volatile ArrayList<TourneyPlayer> scoreboard;
	Game game;
	Bracket bracketType;
	boolean registerable;
	
	public boolean isRegisterable() {
		return registerable;
	}

	public void setRegisterable(boolean registerable) {
		this.registerable = registerable;
	}

	public Tournament(Game game, Bracket bracket) {
		this.scoreboard = new ArrayList<TourneyPlayer>();
		this.game = game;
		this.bracketType = bracket;
		this.bracketType.setParticipantList(scoreboard);
		
	}

	public void run() {
		State currState = null;
		TourneyPlayer[] pair = {null, null};
		
		
		pair = bracketType.nextPair(currState);
		while(pair[0] != null) {
			currState = this.game.play(pair[0].player, pair[1].player);
			pair = bracketType.nextPair(currState);
		}
	}
	
	public void playTournament() {
		new Thread(() -> run()).run();
	}

	public void setGame(Game game) {
		this.game = game;
	}
	
	public Game getGame() {
		return this.game;
	}

	public void setBracketType(Bracket bracketType) {
		this.bracketType = bracketType;
	}
	public void addParticipant(Participant p) {
		TourneyPlayer newPlayer = new TourneyPlayer(p);
		this.scoreboard.add(newPlayer);
	}
	public ArrayList<TourneyPlayer> getScoreboard() {
		return this.scoreboard;
	}
}
