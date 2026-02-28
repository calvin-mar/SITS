package tournament;

public class AlternatingBot extends Participant {
	int previousChoice;
	public AlternatingBot() {
		super();
		this.name = "Alternating Bot";
		previousChoice = -1;
	}

	@Override
	public int makeChoice(int Actions) {
		previousChoice += 1;
		if(Actions == previousChoice) {
			previousChoice = 0;
		}
		return previousChoice;
	}

}
