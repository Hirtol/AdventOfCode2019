package advent.IntCode;

import java.util.ArrayList;
import java.util.List;

public class Output {

	private static Output instance = new Output();
	private List<Long> queuedOutput;

	private Output(){
		this.queuedOutput = new ArrayList<>();
	}

	public static Output getInstance() {
		return instance;
	}

	public long getQueuedOutput() {
		if(queuedOutput.size() > 0)
			return queuedOutput.remove(0);
		return -1;
	}

	public boolean hasOutput(){
		return !queuedOutput.isEmpty();
	}

	public void enterOutput(long value){
		queuedOutput.add(value);
	}
}
