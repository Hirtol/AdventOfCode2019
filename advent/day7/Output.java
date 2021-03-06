package advent.day7;

import java.util.ArrayList;
import java.util.List;

public class Output {

	private static Output instance = new Output();
	private List<Integer> queuedOutput;

	private Output(){
		this.queuedOutput = new ArrayList<>();
	}

	public static Output getInstance() {
		return instance;
	}

	public int getQueuedOutput() {
		if(queuedOutput.size() > 0)
			return queuedOutput.remove(0);
		return -1;
	}

	public void enterOutput(int value){
		queuedOutput.add(value);
	}
}
