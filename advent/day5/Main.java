package advent.day5;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		File file = new File("C:\\Users\\Valentijn\\Nextcloud\\University Work\\Module 2\\Eclipse Projects\\CodeAdvent2019\\src\\advent\\day5\\input.txt");
		Scanner scn = new Scanner(file);

		String str = scn.nextLine();
		String[] ints = str.split(",");
		List<Integer> properIntList = Arrays.stream(ints).map(Integer::parseInt).collect(Collectors.toList());
		OpCode testCode = new OpCode(Arrays.asList(3,21,1008,21,8,20,1005,20,22,107,8,21,20,1006,20,31,
				1106,0,36,98,0,0,1002,21,125,20,4,20,1105,1,46,104,
				999,1105,1,46,1101,1000,1,20,4,20,1105,1,46,98,99));
		OpCode realCode = new OpCode(properIntList);

		while(testCode.hasNext() && !testCode.isFinished){
			testCode.executeOp();
		}

	}

}
