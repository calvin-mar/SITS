package tournament;
import java.util.ArrayList;

public class Tournament {
	
	ArrayList<TourneyPlayer> scoreboard;
	Game game;
	Bracket bracketType;
	
	public Tournament(Game game, Bracket bracket) {
		this.scoreboard = new ArrayList<TourneyPlayer>();
		this.game = game;
		this.bracketType = bracket;
		this.bracketType.setParticipantList(scoreboard);
		
	}

	public void playTournament() {
		State currState = null;
		TourneyPlayer[] pair = {null, null};
		
		
		pair = bracketType.nextPair(currState);
		while(pair[0] != null) {
			currState = this.game.play(pair[0].player, pair[1].player);
			pair = bracketType.nextPair(currState);
		}
	}

	public void setGame(Game game) {
		this.game = game;
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
