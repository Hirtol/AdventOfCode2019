package advent.day7;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.IntConsumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class IntCodePc {

	public List<Integer> intCode;
	private int index;
	private int lastInstrAmount;
	public boolean isFinished;
	public List<Integer> input;


	public IntCodePc(List<Integer> code, List<Integer> input){
		this.intCode = code;
		this.index = 0;
		this.input = input;
		isFinished = false;
	}

	private List<Integer> getParameters(int steps){
		return IntStream.range(1, steps).map(i -> intCode.get(index+i)).boxed().collect(Collectors.toList());
	}

	/**
	 * Gets the values from the parameters. Gets the right ones no matter if location bound or address bound.
	 * @param codes
	 * @param values
	 * @return
	 */
	private List<Integer> getParameterValues(List<Integer> codes, List<Integer> values){
		List<Integer> finals = new ArrayList<>();
		for (int i = 0; i < codes.size(); i++) {
			int code = codes.get(i);
			int value = values.get(values.size()-1-i);
			if(code == 0)
				finals.add(intCode.get(value));
			else
				finals.add(value);
		}
		Collections.reverse(finals);
		return finals;
	}

	private List<Integer> getInstructionSet(){
		int code = intCode.get(index);
		List<Integer> instructionSet = String.valueOf(code).chars().mapToObj(Character::getNumericValue).collect(Collectors.toList());
		int instruction = instructionSet.get(instructionSet.size()-1);
		IntConsumer cons = i -> instructionSet.add(0, 0);
		if(Arrays.asList(1,2,7,8).contains(instruction)){
			IntStream.range(instructionSet.size(), 5).forEach(cons);
		}else if(Arrays.asList(3,4).contains(instruction)){
			IntStream.range(instructionSet.size(), 3).forEach(cons);
		}else{
			IntStream.range(instructionSet.size(), 4).forEach(cons);
		}

		return instructionSet;
	}

	private int getAndDeleteInstruction(List<Integer> instructionSet){
		String temp = String.valueOf(instructionSet.remove(instructionSet.size()-2)) + instructionSet.remove(instructionSet.size()-1);
		return Integer.parseInt(temp);
	}

	private void add(List<Integer> parameterCodes){
		List<Integer> instr = getParameters(4);
		List<Integer> values = getParameterValues(parameterCodes, instr);

		int addVal = values.get(0) + values.get(1);

		intCode.set(instr.get(2), addVal);
	}

	private void multiply(List<Integer> parameterCodes){
		List<Integer> instr = getParameters(4);
		List<Integer> values = getParameterValues(parameterCodes, instr);

		int multiVal = values.get(0) * values.get(1);

		intCode.set(instr.get(2), multiVal);
	}

	private void equalsOp(List<Integer> parameterCodes) {
		List<Integer> instr = getParameters(4);
		List<Integer> values = getParameterValues(parameterCodes, instr);

		intCode.set(instr.get(2), values.get(0) == values.get(1) ? 1 : 0);
	}

	private void lessThan(List<Integer> parameterCodes) {
		List<Integer> instr = getParameters(4);
		List<Integer> values = getParameterValues(parameterCodes, instr);

		intCode.set(instr.get(2), values.get(0) < values.get(1) ? 1 : 0);
	}

	private boolean shouldJump(List<Integer> parameterCodes){
		List<Integer> instr = getParameters(3);
		List<Integer> values = getParameterValues(parameterCodes, instr);
		return values.get(0) != 0;
	}

	private void jump(List<Integer> parameterCodes){
		List<Integer> instr = getParameters(3);
		List<Integer> values = getParameterValues(parameterCodes, instr);
		index = values.get(1);
	}

	private void getInput(){
		List<Integer> instr = getParameters(2);
		intCode.set(instr.get(0), this.input.remove(0));
	}

	private void sendOutput(List<Integer> parameterCodes){
		List<Integer> instr = getParameters(2);
		List<Integer> values = getParameterValues(parameterCodes, instr);
		Output.getInstance().enterOutput(values.get(0));
		System.out.println(values.get(0));
	}


	public void executeProgram(){
		while(!isFinished)
			executeOperation();
	}

	private void executeOperation(){
		List<Integer> instructionSet = getInstructionSet();
		// Remove instruction bits from the instruction set leaving just the parameters
		int instruction = getAndDeleteInstruction(instructionSet);

		switch(instruction){
			case 1:
				add(instructionSet);
				lastInstrAmount = 4;
				break;
			case 2:
				multiply(instructionSet);
				lastInstrAmount = 4;
				break;
			case 3:
				getInput();
				lastInstrAmount = 2;
				break;
			case 4:
				sendOutput(instructionSet);
				lastInstrAmount = 2;
				break;
			case 5:
				if(shouldJump(instructionSet)) {
					jump(instructionSet);
					lastInstrAmount = 0;
				}else
					lastInstrAmount = 3;
				break;
			case 6:
				if(!shouldJump(instructionSet)){
					jump(instructionSet);
					lastInstrAmount = 0;
				}else
					lastInstrAmount = 3;
				break;
			case 7:
				lessThan(instructionSet);
				lastInstrAmount = 4;
				break;
			case 8:
				equalsOp(instructionSet);
				lastInstrAmount = 4;
				break;
			case 99:
				isFinished = true;
				break;
			default:
				return;
		}

		moveCursor(lastInstrAmount);
	}

	private int moveCursor(int amount){
		return index += amount;
	}

}
