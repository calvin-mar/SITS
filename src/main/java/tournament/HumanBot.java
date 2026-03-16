package tournament;

import java.util.Scanner;

public class HumanBot extends Participant {

	Scanner scanner;// = new Scanner(System.in);
	public HumanBot() {
		super();
		this.name = "Human Bot";
		this.scanner = new Scanner(System.in);
	}
	@Override
	public int makeChoice(int actions) {

		
		System.out.print("Enter your choice from " + String.valueOf(actions) + " actions. Your choice should be an integer 0-"  + String.valueOf(actions) + ": ");
		int choice = this.scanner.nextInt();
		//scanner.close();
		return choice;
	}

	@Override
	public void updateMemory(State state) {
		if(state.getP1Name() == this.name) {
			System.out.println("Your choice was " + state.getP1Action() + " and " + state.getP2Name() + " chose " + state.getP2Action() + ".");
			System.out.println("You scored " + state.getP1Score() + " and " + state.getP2Name() + "'s score is " + state.getP2Score() + ".");
		} else {
			System.out.println("Your choice was " + state.getP2Action() + " and " + state.getP1Name() + " chose " + state.getP1Action() + ".");
			System.out.println("You scored " + state.getP2Score() + " and " + state.getP1Name() + "'s score is " + state.getP1Score() + ".");
		}
	}
	
	public void closeScanner() {
        if (this.scanner != null) {
            this.scanner.close(); 
        }
    }
}
