package advent.day5;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class OpCode {

	public List<Integer> opCode;
	private int index;
	private int lastInstrAmount;
	public boolean isFinished;


	public OpCode(List<Integer> code){
		this.opCode = code;
		this.index = 0;
		isFinished = false;
	}

	private List<Integer> getParameters(int steps){
		return IntStream.range(1, steps).map(i -> opCode.get(index+i)).boxed().collect(Collectors.toList());
	}

	private List<Integer> getParameterValues(List<Integer> codes, List<Integer> values){
		List<Integer> finals = new ArrayList<>();
		for (int i = 0; i < codes.size(); i++) {
			int code = codes.get(i);
			int value = values.get(values.size()-1-i);
			if(code == 0)
				finals.add(opCode.get(value));
			else
				finals.add(value);
		}
		Collections.reverse(finals);
		return finals;
	}

	private List<Integer> getInstructionSet(){
		int code = opCode.get(index);
		List<Integer> instructionSet = String.valueOf(code).chars().mapToObj(Character::getNumericValue).collect(Collectors.toList());
		int instruction = instructionSet.get(instructionSet.size()-1);

		if(Arrays.asList(1,2,7,8).contains(instruction)){
			IntStream.range(instructionSet.size(), 5).forEach(i -> instructionSet.add(0, 0));
		}else if(Arrays.asList(3,4).contains(instruction)){
			IntStream.range(instructionSet.size(), 3).forEach(i -> instructionSet.add(0, 0));
		}else{
			IntStream.range(instructionSet.size(), 4).forEach(i -> instructionSet.add(0, 0));
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

		opCode.set(instr.get(2), addVal);
	}

	private void multiply(List<Integer> parameterCodes){
		List<Integer> instr = getParameters(4);
		List<Integer> values = getParameterValues(parameterCodes, instr);

		int multiVal = values.get(0) * values.get(1);

		opCode.set(instr.get(2), multiVal);
	}

	private void equalsOp(List<Integer> parameterCodes) {
		List<Integer> instr = getParameters(4);
		List<Integer> values = getParameterValues(parameterCodes, instr);

		opCode.set(instr.get(2), values.get(0) == values.get(1) ? 1 : 0);
	}

	private void lessThan(List<Integer> parameterCodes) {
		List<Integer> instr = getParameters(4);
		List<Integer> values = getParameterValues(parameterCodes, instr);

		opCode.set(instr.get(2), values.get(0) < values.get(1) ? 1 : 0);
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

	private void getInput(int input){
		List<Integer> instr = getParameters(2);
		opCode.set(instr.get(0), input);
	}

	private void sendOutput(List<Integer> parameterCodes){
		List<Integer> instr = getParameters(2);
		List<Integer> values = getParameterValues(parameterCodes, instr);
		System.out.println(values.get(0));
	}


	public void executeOp(){
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
				getInput(Input.getUserIntInput());
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
				}else {
					lastInstrAmount = 3;
				}
				break;
			case 6:
				if(!shouldJump(instructionSet)){
					jump(instructionSet);
					lastInstrAmount = 0;
				}else{
					lastInstrAmount = 3;
				}
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

	public boolean hasNext(){
		return true;
	}

	private int moveCursor(int amount){
		return index += amount;
	}

}
