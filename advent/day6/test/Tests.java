package advent.day6.test;

import advent.day6.Orbit;
import advent.day6.OrbitFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Tests {


	@BeforeEach
	public void setup(){

	}

	@Test
	public void test1() throws FileNotFoundException {

		File file = new File("C:\\Users\\Valentijn\\Nextcloud\\University Work\\Module 2\\Eclipse Projects\\CodeAdvent2019\\src\\advent\\day6\\input.txt");
		Scanner scn = new Scanner(file);

		OrbitFactory factory = OrbitFactory.getInstance();

		while(scn.hasNextLine()){
			String str = scn.nextLine();
			String[] splitString = str.split("\\)");
			Orbit around = factory.getNewOrbit(splitString[0]);
			Orbit toOrbit = factory.getNewOrbit(splitString[1], around);

			factory.addOrbit(around);
			factory.addOrbit(toOrbit);
		}

		Assertions.assertEquals(122782, factory.orbits.stream().mapToInt(Orbit::getOrbitalAmount).sum());


	}

}
