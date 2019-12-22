package advent.IntCode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.IntConsumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class IntCodePc {
	public List<Long> intCode;
	private int index;
	public boolean isFinished;
	public List<Long> input;
	private boolean lastReadSuccessful;
	private int relativeBase;

	public IntCodePc(List<Long> code, List<Long> input){
		this.intCode = code;
		this.index = 0;
		this.input = input;
		this.isFinished = false;
		this.relativeBase = 0;
		this.lastReadSuccessful = true;
		// Add extra memory
		IntStream.range(0, 1024).forEach(t -> intCode.add(0l));
	}

	public IntCodePc(List<Long> code){
		this(code, new ArrayList<>());
	}

	private int moveCursor(int amount){
		return index += amount;
	}

	private List<Long> getParameters(int steps){
		return IntStream.range(1, steps).mapToObj(i -> intCode.get(index+i)).collect(Collectors.toList());
	}

	/**
	 * Gets the values from the parameters. Gets the right ones no matter if location bound or address bound.
	 */
	private List<Long> getTrueParameterValues(List<Long> codes, List<Long> values){
		List<Long> finals = new ArrayList<>();
		for (int i = 0; i < codes.size(); i++) {
			long code = codes.get(i);
			long value = values.get(values.size()-1-i);
			if(code == 0)
				finals.add(intCode.get((int) value));
			else if(code == 1)
				finals.add(value);
			else if(code == 2)
				finals.add(intCode.get((int) (value+relativeBase)));
		}
		Collections.reverse(finals);
		return finals;
	}

	private int getWriteAddress(Long code, Long value){
		return (int) (code == 2 ? value+relativeBase : value);
	}

	private List<Long> getInstructionSet(){
		long code = intCode.get(index);
		List<Long> instructionSet = String.valueOf(code).chars().mapToObj(p -> (long) Character.getNumericValue(p)).collect(Collectors.toList());
		long instruction = instructionSet.get(instructionSet.size()-1);
		IntConsumer cons = i -> instructionSet.add(0, (long) 0);

		if(Arrays.asList(1l,2l,7l,8l).contains(instruction))
			IntStream.range(instructionSet.size(), 5).forEach(cons);
		else if(Arrays.asList(3l,4l,9l).contains(instruction))
			IntStream.range(instructionSet.size(), 3).forEach(cons);
		else
			IntStream.range(instructionSet.size(), 4).forEach(cons);
		return instructionSet;
	}

	private int getAndDeleteInstruction(List<Long> instructionSet){
		String temp = String.valueOf(instructionSet.remove(instructionSet.size()-2)) + instructionSet.remove(instructionSet.size()-1);
		return Integer.parseInt(temp);
	}

	private void add(List<Long> parameterCodes){
		List<Long> instr = getParameters(4);
		List<Long> values = getTrueParameterValues(parameterCodes, instr);

		long addVal = values.get(0) + values.get(1);
		int writeAddress = getWriteAddress(parameterCodes.get(0), instr.get(2));
		intCode.set(writeAddress, addVal);
	}

	private void multiply(List<Long> parameterCodes){
		List<Long> instr = getParameters(4);
		List<Long> values = getTrueParameterValues(parameterCodes, instr);

		long multiVal = values.get(0) * values.get(1);
		int writeAddress = getWriteAddress(parameterCodes.get(0), instr.get(2));
		intCode.set(writeAddress, multiVal);
	}

	private void equalsOp(List<Long> parameterCodes) {
		List<Long> instr = getParameters(4);
		List<Long> values = getTrueParameterValues(parameterCodes, instr);
		int writeAddress = getWriteAddress(parameterCodes.get(0), instr.get(2));
		intCode.set(writeAddress, values.get(0).equals(values.get(1)) ? 1l : 0l);
	}

	private void lessThan(List<Long> parameterCodes) {
		List<Long> instr = getParameters(4);
		List<Long> values = getTrueParameterValues(parameterCodes, instr);
		int writeAddress = getWriteAddress(parameterCodes.get(0), instr.get(2));
		intCode.set(writeAddress, values.get(0) < values.get(1) ? 1l : 0l);
	}

	private boolean shouldJump(List<Long> parameterCodes){
		List<Long> instr = getParameters(3);
		List<Long> values = getTrueParameterValues(parameterCodes, instr);
		return values.get(0) != 0;
	}

	private void jump(List<Long> parameterCodes){
		List<Long> instr = getParameters(3);
		List<Long> values = getTrueParameterValues(parameterCodes, instr);
		index = Math.toIntExact(values.get(1));
	}

	private void getInput(List<Long> parameterCodes) {
		List<Long> instr = getParameters(2);
		int writeAddress = getWriteAddress(parameterCodes.get(0), instr.get(0));
		lastReadSuccessful = !this.input.isEmpty();
		if (lastReadSuccessful)
			intCode.set(writeAddress, this.input.remove(0));
	}

	private void sendOutput(List<Long> parameterCodes){
		List<Long> instr = getParameters(2);
		List<Long> values = getTrueParameterValues(parameterCodes, instr);
		Output.getInstance().enterOutput(values.get(0));
		System.out.println(Output.getInstance().getQueuedOutput());
	}

	private void changeRelativeBase(List<Long> parameterCodes){
		List<Long> instr = getParameters(2);
		List<Long> values = getTrueParameterValues(parameterCodes, instr);
		relativeBase += values.get(0);
	}

	public void executeProgram(){
		while(!isFinished && lastReadSuccessful)
			executeOperation();
		lastReadSuccessful = true;
	}

	private void executeOperation(){
		List<Long> instructionSet = getInstructionSet();
		int instruction = getAndDeleteInstruction(instructionSet);
		int lastInstrAmount = 0;

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
				getInput(instructionSet);
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
			case 9:
				changeRelativeBase(instructionSet);
				lastInstrAmount = 2;
				break;
			case 99:
				isFinished = true;
				break;
			default:
				return;
		}

		if(lastReadSuccessful)
			moveCursor(lastInstrAmount);
	}
}
