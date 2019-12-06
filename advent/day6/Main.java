package advent.day6;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

	public static Orbit findCommonNode(Orbit orbit1, Orbit orbit2){
		Orbit currentOrbit = orbit1;
		while(currentOrbit.orbitsAround != null){
			Orbit currentOrbit2 = orbit2;
			while(currentOrbit2.orbitsAround != null){
				if(currentOrbit2.equals(currentOrbit))
					return currentOrbit;
				currentOrbit2 = currentOrbit2.orbitsAround;
			}
			currentOrbit = currentOrbit.orbitsAround;
		}
		return null;
	}

	public static void main(String[] args) throws FileNotFoundException {
		File file = new File("C:\\Users\\Valentijn\\Nextcloud\\University Work\\Module 2\\Eclipse Projects\\CodeAdvent2019\\src\\advent\\day6\\input.txt");
		Scanner scn = new Scanner(file);

		OrbitFactory factory = OrbitFactory.getInstance();

		//PART 1

		while(scn.hasNextLine()){
			String str = scn.nextLine();
			String[] splitString = str.split("\\)");
			Orbit around = factory.getNewOrbit(splitString[0]);
			Orbit toOrbit = factory.getNewOrbit(splitString[1], around);

			factory.addOrbit(around);
			factory.addOrbit(toOrbit);
		}

		//PART 2
		Orbit YOU = factory.getOrbitFromName("YOU");
		Orbit SAN = factory.getOrbitFromName("SAN");

		Orbit commonNode = findCommonNode(YOU, SAN);

		int length = YOU.getLengthOfPath(commonNode) + SAN.getLengthOfPath(commonNode);
		System.out.println("Length of path: " + length);
		System.out.println("Sum total: " + factory.orbits.stream().mapToInt(Orbit::getOrbitalAmount).sum());
	}
}
