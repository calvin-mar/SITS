package tournament;

public class RotatingCaptain extends Team {
	int captainIndex;
	
	public RotatingCaptain(){
		super();
		this.setName("Rotating Captain Bot");
		this.captainIndex = 0;
	}
	@Override
	public int makeChoice(int Actions) {
		int choice = roster.get(captainIndex).makeChoice(Actions);
		this.captainIndex += 1;
		if(captainIndex == roster.size()) {
			captainIndex = 0;
		}
		return choice;
	}

}
