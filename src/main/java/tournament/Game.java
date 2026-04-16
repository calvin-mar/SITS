package tournament;

import java.util.ArrayList;

public abstract class Game implements Subject{
	int actions;
	State currState;
	int roundsTaken;
	int timeDelay;
	ArrayList<Observer> actionListeners;
	ArrayList<Observer> gameListeners;
	
	public Game(int numActions) {
		this.actions = numActions;
		this.roundsTaken = 0;
		this.timeDelay = 1;
	}
	public State play(Participant p1, Participant p2){
		int[] scores;
		this.currState = new State( p1.getName(), p2.getName());
		
	    while(!endGame()){
	          this.currState.setP1Action(p1.makeChoice(actions));
	          this.currState.setP2Action(p2.makeChoice(actions));
	          scores = scoreActions(this.currState.p1Action, this.currState.p2Action);
	          this.currState.setP1Score(this.currState.getP1Score() + scores[0]);
	          this.currState.setP2Score(this.currState.getP2Score() + scores[1]);
	          p1.updateMemory(currState);
	          p2.updateMemory(currState);
	          this.hookPerRound();
	          try {
	          Thread.sleep(timeDelay*1000);
	          } catch (InterruptedException e) {
	        	  Thread.currentThread().interrupt();
	          }
	          
	     }
	     this.hookPerGame();
	     return currState; 
	}
	public void hookPerRound() {
	}
	public void hookPerGame() {
	}
	public int getActions() {
		return actions;
	}
	public void registerActions(Observer observer) {
		this.actionListeners.add(observer);
	}
	public void deregisterActions(Observer observer) {
		this.actionListeners.remove(observer);
	}
	
	public void registerGames(Observer observer) {
		this.gameListeners.add(observer);
	}
	public void deregisterGames(Observer observer) {
		this.gameListeners.remove(observer);
	}
	public void notifyActionListeners() {
		for(Observer o: actionListeners) {
			o.update(this);
		}
	}
	public void notifyGameListeners() {
		for(Observer o: gameListeners) {
			o.update(this);
		}
	}
	
	public abstract boolean endGame();
	public abstract int[] scoreActions(int player1Action, int player2Action);
	
	public State getCurrState() {
		return currState;
	}
}
