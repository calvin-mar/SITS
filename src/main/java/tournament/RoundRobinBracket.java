package tournament;

import java.util.ArrayList;

public class RoundRobinBracket extends Bracket {
	int firstParticipantIndex;
	int secondParticipantIndex;
	
	public RoundRobinBracket(ArrayList<TourneyPlayer> list) {
		super(list);
		// TODO Auto-generated constructor stub
	}
	public RoundRobinBracket() {
		super();
	}

	@Override
	public TourneyPlayer[] nextPair(State state) {
		if(state == null) {
			this.firstParticipantIndex = 0;
			this.secondParticipantIndex = 0;
		}
		else {
			this.participantList.get(firstParticipantIndex).increaseScore(state.getP1Score());
			this.participantList.get(secondParticipantIndex).increaseScore(state.getP2Score());
		}
		
		this.secondParticipantIndex += 1;
		if(this.secondParticipantIndex == participantList.size()) {
			this.firstParticipantIndex += 1;
			this.secondParticipantIndex = this.firstParticipantIndex + 1;
		}
		if(this.secondParticipantIndex == participantList.size()) {
			return new TourneyPlayer[] {null, null};
		}
		else {
			return new TourneyPlayer[] {participantList.get(this.firstParticipantIndex),
							participantList.get(this.secondParticipantIndex)};
		}
	}

}
