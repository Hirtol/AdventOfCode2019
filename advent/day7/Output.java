package advent.day7;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

public class Output {

	private static Output instance;

	private List<Integer> queuedOutput;


	private Output(){
		this.queuedOutput = new ArrayList<>();
	}

	public static Output getInstance() {
		if(instance == null)
			instance = new Output();
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
