package advent.day8;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
	public static final int WIDTH = 25;
	public static final int HEIGHT = 6;
	public static final int SIZE = WIDTH * HEIGHT;
	public static List<List<Integer>> twoLayers = new ArrayList<>();

	private static <E> long count(List<E> list, E element){
		return list.stream().filter(p -> p == element).count();
	}

	public static void part1(){
		List<Integer> lowestInts = null;
		for(List<Integer> currentList : twoLayers)
			if(lowestInts == null || count(currentList, 0) < count(lowestInts, 0))
				lowestInts = currentList;
		System.out.println(count(lowestInts, 1)*count(lowestInts, 2));
	}

	public static void part2(){
		List<Integer> pixelList = IntStream.range(0, SIZE).map(number -> twoLayers.stream().filter(list -> list.get(number) != 2).findFirst().map(list -> list.get(number)).get()).boxed().collect(Collectors.toList());
		for (int row = 0; row < HEIGHT; row++) {
			for (int column = 0; column < WIDTH; column++) {
				if(pixelList.get(row*WIDTH + column) == 1)
					System.out.print('#');
				else
					System.out.print(' ');
			}
			System.out.println();
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader read = new BufferedReader(new FileReader("C:\\Users\\Valentijn\\Desktop\\Git-Projects\\CodeAdvent2019\\src\\advent\\day8\\input.txt"));
		String input = read.readLine();
		List<String> layers = new ArrayList<>();
		for (int i = 0; input.length() > SIZE-1+SIZE*i; i++)
			layers.add(input.substring(i* SIZE, SIZE+SIZE*i));
		// Should I have used a for loop? Yes. Will I? No.
		twoLayers = layers.stream().map(String::toCharArray).
				map(ar -> IntStream.range(0, ar.length).map(p -> Character.getNumericValue(ar[p]))
						.boxed().collect(Collectors.toList()))
				.collect(Collectors.toList());
		part2();
	}
}
