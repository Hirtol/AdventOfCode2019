package advent.day7;

import advent.day7.generator.Itertools;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CombinationGenerator {
	public static CombinationGenerator instance = new CombinationGenerator();
	private List<List<Integer>> codesToUse;


	public static CombinationGenerator getInstance() {
		return instance;
	}

	private CombinationGenerator(){
		this.codesToUse = new ArrayList<>();
		Itertools.permutations(IntStream.range(0,5).boxed().collect(Collectors.toList()),5).forEach(p -> codesToUse.add(p));
	}

	public int size(){
		return codesToUse.size();
	}

	public List<Integer> nextPhaseSettings(){
		if(this.codesToUse.size() == 0)
			return null;
		return this.codesToUse.remove(0);
	}


}
