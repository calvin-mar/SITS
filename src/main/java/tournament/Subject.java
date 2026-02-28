package tournament;
public interface Subject {
	public void register(Observer observer);
	public void deregister(Observer observer);
	public void notifyListeners();
}
