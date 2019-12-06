package advent.day2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {


	public static int add(List<Integer> opCode, int index){
		List<Integer> instr = new LinkedList<>();
		IntStream.range(1,4).forEach(i -> instr.add(opCode.get(index+i)));

		int value1 = opCode.get(instr.get(0));
		int value2 = opCode.get(instr.get(1));
		int finalVal = instr.get(2);

		int addVal = value1 + value2;

		opCode.set(finalVal, addVal);
		return addVal;
	}

	public static int multiply(List<Integer> opCode, int index){
		List<Integer> instr = new LinkedList<>();
		IntStream.range(1,4).forEach(i -> instr.add(opCode.get(index+i)));

		int value1 = opCode.get(instr.get(0));
		int value2 = opCode.get(instr.get(1));
		int finalVal = instr.get(2);

		int multiVal = value1 * value2;

		opCode.set(finalVal, multiVal);
		return multiVal;
	}


	public static boolean executeOp(List<Integer> opcode, int index){
		int instruction = opcode.get(index);

		switch(instruction){
			case 1:
				add(opcode, index);
				break;
			case 2:
				multiply(opcode, index);
				break;
			case 99:
				//System.out.println("Done!");
				break;
			default:
				return false;
		}

		return true;
	}

	public static boolean hasNext(List<Integer> opCode, int index){
		return moveCursor(index) < opCode.size();
	}

	public static int moveCursor(int index){
		return index+4;
	}

	public static void setupDay1(List<Integer> opcode){
		setup(opcode, 12, 2);
	}

	public static void setup(List<Integer> opcode, int pos1, int pos2){
		opcode.set(1, pos1);
		opcode.set(2, pos2);
	}



	public static void main(String[] args) throws FileNotFoundException {
		int[] opcode = new int[130];
		File file = new File("C:\\Users\\Valentijn\\Nextcloud\\University Work\\Module 2\\Eclipse Projects\\CodeAdvent2019\\src\\advant\\day2\\input.txt");
		Scanner scn = new Scanner(file);

		String str = scn.nextLine();
		String[] ints = str.split(",");
		List<Integer> properIntList = Arrays.stream(ints).map(Integer::parseInt).collect(Collectors.toList());
		List<Integer> testList = Arrays.asList(2,4,4,5,99,0);

		int index = 0;
		for (int i = 0; i < 100; i++) {
			index = 0;
			for (int j = 0; j < 100; j++) {
				index = 0;
				List<Integer> tempList = new ArrayList<>(properIntList);
				setup(tempList, i, j);
				while(hasNext(tempList, index)) {
					if (!executeOp(tempList, index))
						continue;
					index = moveCursor(index);
				}
				if(tempList.get(0) == 19690720)
					System.out.println("Values found! i: " + i + " j: " + j);
			}
		}


		System.out.println(properIntList);
		System.out.println(properIntList.get(0));







	}


}
