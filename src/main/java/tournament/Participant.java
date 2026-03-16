package tournament;
import java.util.ArrayList;

public abstract class Participant {
	String name;
	ArrayList<State> memory;
	
	public Participant() {
		memory = new ArrayList<State>();
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void updateMemory(State newState) {
		memory.add(newState);
	}
	public void clearMemory() {
		memory.clear();
	}
	public ArrayList<State> getMemory(){
		return memory;
	}
	
	public abstract int makeChoice(int Actions);
}
