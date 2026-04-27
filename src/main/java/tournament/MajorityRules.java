package tournament;

public class MajorityRules extends Team {
	
	public MajorityRules() {
		super();
		this.setName("Majority Rules Bot");
	}

	@Override
	public int makeChoice(int Actions) {
		int[] decisions = new int[Actions];
		for(Participant p: roster) {
			decisions[p.makeChoice(Actions)] += 1;
		}
		int maxIndex = 0;
		for(int i=1;i<Actions;i++) {
			if(decisions[i] > decisions[maxIndex]) {
				maxIndex = i;
			}
		}
		return maxIndex;
	}

}
