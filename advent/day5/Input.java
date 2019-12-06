package advent.day5;

import java.util.Scanner;

public class Input {

	public static int getUserIntInput(){
		Scanner inputObj = new Scanner(System.in);
		System.out.println("Parameter demands input!: ");
		int input = inputObj.nextInt();
		return input;
	}

}
