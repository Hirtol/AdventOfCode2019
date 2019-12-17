package advent.day1;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main{

    public static double fuelRequired(double mass){
        return Math.floor(mass/3.0)-2;
    }

    public static double fuelRequiredFuel(double mass){
        double newMass = fuelRequired(mass);

        if(newMass <= 0)
            return 0;

        return fuelRequiredFuel(newMass) + newMass;
    }

    public static void main(String[] args) throws IOException {
        AdventFileIO fileIO = new AdventFileIO("C:\\Users\\Valentijn\\Desktop\\Git-Projects\\CodeAdvent2019\\src\\advent\\day1\\input.txt");

        File file = new File("C:\\Users\\Valentijn\\Desktop\\Git-Projects\\CodeAdvent2019\\src\\advent\\day1\\input.txt");
        Scanner scn = new Scanner(file);

        System.out.println(fileIO.hasNextLine());

        Map<String, Integer> test = new HashMap<>();

        test.put("Yosh", 5);
        test.put("Yellow", 10);
        test.put("Bram", 3);
        System.out.println();


        double sum = 0;

        while(scn.hasNextLine()){
            sum += fuelRequiredFuel(Integer.parseInt(scn.nextLine()));
        }

        System.out.println(sum);

    }





}
