package tournament;
public interface Subject {
	public void registerActions(Observer observer);
	public void deregisterActions(Observer observer);
	public void notifyActionListeners();
	public void registerGames(Observer observer);
	public void deregisterGames(Observer observer);
	public void notifyGameListeners();
}
