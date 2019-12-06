package advent.day4;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class Main {


	private static String intString(int number){
		return String.valueOf(number);
	}

	private static boolean isPartOfGroup(int number){
		if(hasDouble(number)){
			char[] charArray = intString(number).toCharArray();
			Character lastNumb = 0;
			int concurrent = 0;
			Map<Character, Integer> record = new HashMap<>();
			for (int i = 0; i < 6; i++) {
				if(lastNumb != charArray[i]) {
					if (record.getOrDefault(charArray[i], 0) < concurrent)
						record.put(lastNumb, concurrent);
					lastNumb = charArray[i];
					concurrent = 1;
				}else {
					concurrent++;
					record.put(lastNumb, concurrent);
				}
			}
			return record.values().stream().anyMatch(i -> i == 2);
		}
		return false;
	}

	private static boolean hasDouble(int number){
		String intString = intString(number);
		return IntStream.of(11,22,33,44,55,66,77,88,99).anyMatch(i -> intString.contains(String.valueOf(i)));
	}

	private static boolean neverDecreases(int number){
		char[] charArray = intString(number).toCharArray();
		return IntStream.range(1,6).noneMatch(i -> charArray[i] < charArray[i-1]);
	}


	public static long amountOfPasswords(int leftRange, int rightRange){
		return IntStream.range(leftRange, rightRange+1).filter(i -> neverDecreases(i) && hasDouble(i) && isPartOfGroup(i)).count();
	}



	public static void main(String[] args) {
		//System.out.println(isPartOfGroup(112233));
		System.out.println(amountOfPasswords(265275, 781584));
	}
}
