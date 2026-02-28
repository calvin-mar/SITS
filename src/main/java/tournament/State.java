package tournament;

public class State {
	String p1Name;
	int p1Score;
	int p1Action;
	String p2Name;
	int p2Score;
	int p2Action;
	public State(String p1Name, String p2Name) {
		this.p1Name = p1Name;
		this.p2Name = p2Name;
		this.p1Score = 0;
		this.p1Action = 0;
		this.p2Score = 0;
		this.p2Action = 0;
	}
	
	public boolean equals(Object o) {
		if(o == this) {
			return true;
		}
		if(!(o instanceof State)) {
			return false;
		}
		State state = (State) o;
		if(this.p1Name == state.p1Name &&
			this.p2Name == state.p2Name &&
			this.p1Score == state.p1Score &&
			this.p2Score == state.p2Score &&
			this.p1Action == state.p1Action &&
			this.p2Action == state.p2Action) {
			return true;
		}
		else {
			return false;
		}
	}
	public String toString() {
		return "--Participant 1-- \n" +
				"Name: " + this.p1Name + 
				"\nScore: " + this.p1Score +
				"\nAction: " + this.p1Action +
				"\n--Participant 2-- \n" +
				"Name: " + this.p2Name + 
				"\nScore: " + this.p2Score +
				"\nAction: " + this.p2Action + "\n";
	}
	public String getP1Name() {
		return p1Name;
	}
	public int getP1Score() {
		return p1Score;
	}
	public int getP1Action() {
		return p1Action;
	}
	public String getP2Name() {
		return p2Name;
	}
	public int getP2Score() {
		return p2Score;
	}
	public int getP2Action() {
		return p2Action;
	}
	public void setP1Name(String p1Name) {
		this.p1Name = p1Name;
	}
	public void setP1Score(int p1Score) {
		this.p1Score = p1Score;
	}
	public void setP1Action(int p1Action) {
		this.p1Action = p1Action;
	}
	public void setP2Name(String p2Name) {
		this.p2Name = p2Name;
	}
	public void setP2Score(int p2Score) {
		this.p2Score = p2Score;
	}
	public void setP2Action(int p2Action) {
		this.p2Action = p2Action;
	}

}
