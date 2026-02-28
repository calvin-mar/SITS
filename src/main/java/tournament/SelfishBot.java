package tournament;

public class SelfishBot extends Participant {

	public SelfishBot() {
		super();
		this.name = "Selfish Bot";
	}

	@Override
	public int makeChoice(int Actions) {
		return 0;
	}

}
