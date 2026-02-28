 package tournament;
 
 import java.util.ArrayList;

public class IteratedPrisonerDilemna extends Game implements Subject{
	int numRounds;
	int roundsElapsed;
	ArrayList<Observer> listeners;
	
	public IteratedPrisonerDilemna(int numRounds) {
		super(2);
		this.numRounds = numRounds;
		this.roundsElapsed = -1;
		this.listeners = new ArrayList<Observer>();
	}

	@Override
	public boolean endGame() {
		this.roundsElapsed += 1;
		if(this.roundsElapsed == this.numRounds) {
			this.roundsElapsed = -1;
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public int[] scoreActions(int player1Action, int player2Action) {
		// 0 indicates the selfish choice
		// 1 indicates the selfless choice
		// This method has a series of if statements to make computations for the score
		if(player1Action == 0 && player2Action == 0) {
			return new int[] {1,1};
		}
		else if(player1Action == 0 && player2Action == 1) {
			return new int[] {5,0};
		}
		else if(player1Action == 1 && player2Action == 0) {
			return new int[] {0,5};
		}
		else {
			return new int[] {3,3};
		}
	}
	public void register(Observer observer) {
		this.listeners.add(observer);
	}
	public void deregister(Observer observer) {
		this.listeners.remove(observer);
	}
	public void notifyListeners() {
		for(Observer o: listeners) {
			o.update(this);
		}
	}

	public void hook1() {
		notifyListeners();
	}
	public void hook2() {
		notifyListeners();
	}
}
