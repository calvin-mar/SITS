package tournament;
import java.util.ArrayList;

public class TourneyPlayer {
	Participant player;
	int totalScore;
	ArrayList<TourneyPlayer> playersPlayed;
	
	public TourneyPlayer(Participant player) {
		this.player = player;
		this.totalScore = 0;
		this.playersPlayed = new ArrayList<TourneyPlayer>();
	}
	
	public void increaseScore(int newScore) {
		this.totalScore += newScore;
	}

	public Participant getPlayer() {
		return player;
	}

	public int getTotalScore() {
		return totalScore;
	}
	
	public void addPlayerPlayed(TourneyPlayer newPlayer) {
		playersPlayed.add(newPlayer);
	}

	public ArrayList<TourneyPlayer> getPlayersPlayed() {
		return playersPlayed;
	}

	public void setPlayer(Participant player) {
		this.player = player;
	}

	public void setTotalScore(int totalScore) {
		this.totalScore = totalScore;
	}
}
