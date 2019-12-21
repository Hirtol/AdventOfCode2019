package advent.day7;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

	public static final List<Integer> INSTRUCTIONS = Arrays.asList(3,8,1001,8,10,8,105,1,0,0,21,38,47,64,85,106,187,268,349,430,99999,3,9,1002,9,4,9,1001,9,4,9,1002,9,4,9,4,9,99,3,9,1002,9,4,9,4,9,99,3,9,1001,9,3,9,102,5,9,9,1001,9,5,9,4,9,99,3,9,101,3,9,9,102,5,9,9,1001,9,4,9,102,4,9,9,4,9,99,3,9,1002,9,3,9,101,2,9,9,102,4,9,9,101,2,9,9,4,9,99,3,9,1002,9,2,9,4,9,3,9,102,2,9,9,4,9,3,9,1001,9,2,9,4,9,3,9,1001,9,1,9,4,9,3,9,101,1,9,9,4,9,3,9,102,2,9,9,4,9,3,9,101,2,9,9,4,9,3,9,102,2,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,1002,9,2,9,4,9,99,3,9,102,2,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,1001,9,1,9,4,9,3,9,1002,9,2,9,4,9,3,9,101,1,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,1001,9,2,9,4,9,3,9,1001,9,1,9,4,9,3,9,101,2,9,9,4,9,3,9,101,1,9,9,4,9,99,3,9,102,2,9,9,4,9,3,9,102,2,9,9,4,9,3,9,1001,9,1,9,4,9,3,9,1002,9,2,9,4,9,3,9,102,2,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,101,1,9,9,4,9,3,9,101,1,9,9,4,9,3,9,101,1,9,9,4,9,3,9,1002,9,2,9,4,9,99,3,9,1002,9,2,9,4,9,3,9,102,2,9,9,4,9,3,9,101,1,9,9,4,9,3,9,1001,9,1,9,4,9,3,9,1002,9,2,9,4,9,3,9,102,2,9,9,4,9,3,9,102,2,9,9,4,9,3,9,101,2,9,9,4,9,3,9,102,2,9,9,4,9,3,9,1002,9,2,9,4,9,99,3,9,1002,9,2,9,4,9,3,9,101,1,9,9,4,9,3,9,102,2,9,9,4,9,3,9,1001,9,2,9,4,9,3,9,1002,9,2,9,4,9,3,9,1002,9,2,9,4,9,3,9,1001,9,1,9,4,9,3,9,1002,9,2,9,4,9,3,9,1001,9,1,9,4,9,3,9,102,2,9,9,4,9,99);

	public static void main(String[] args){
		part1();
		//part2();
	}

	public static void part1(){
		int highestOutput = 0;
		CombinationGenerator generator = new CombinationGenerator(0, 5);

		while(generator.size() > 0){
			List<Integer> currentCode = generator.nextPhaseSettings();
			List<Integer> input = Arrays.asList(-1, 0);

			for (int i = 0; i < currentCode.size(); i++) {
				input.set(0, currentCode.get(i));
				IntCodePc currentThruster = new IntCodePc(new ArrayList<>(INSTRUCTIONS), new ArrayList<>(input));
				currentThruster.executeProgram();
				input.set(1, Output.getInstance().getQueuedOutput());
			}

			int output = input.get(1);
			if(output > highestOutput)
				highestOutput = output;
		}
		System.out.println(highestOutput);
	}

	public static void part2(){
		int highestOutput = 0;
		CombinationGenerator generator = new CombinationGenerator(5, 10);

		while(generator.size() > 0){
			List<Integer> currentCode = generator.nextPhaseSettings();
			List<Integer> input = Arrays.asList(-1, 0);
			IntCodePc ampA;

			for (int i = 0; i < currentCode.size(); i++) {
				input.set(0, currentCode.get(i));
				IntCodePc currentThruster = new IntCodePc(new ArrayList<>(INSTRUCTIONS), new ArrayList<>(input));
				currentThruster.executeProgram();
				input.set(1, Output.getInstance().getQueuedOutput());
			}

			int output = input.get(1);
			if(output > highestOutput)
				highestOutput = output;
		}
		System.out.println(highestOutput);

	}

}
