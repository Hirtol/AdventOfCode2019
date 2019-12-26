package advent.IntCode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.IntConsumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class IntCodePc {
	public List<Long> input;
	public boolean isFinished;
	private List<Long> intCode;
	private int index;
	private int relativeBase;
	private boolean lastReadSuccessful;

	public IntCodePc(List<Long> code, List<Long> input) {
		this.intCode = code;
		this.index = 0;
		this.input = input;
		this.isFinished = false;
		this.relativeBase = 0;
		this.lastReadSuccessful = true;
		// Add extra memory
		IntStream.range(0, 1024).forEach(t -> intCode.add(0L));
	}

	public IntCodePc(List<Long> code) {
		this(code, new ArrayList<>());
	}

	private List<Long> getParameters(int steps) {
		return IntStream.range(1, steps).mapToObj(i -> intCode.get(index + i)).collect(Collectors.toList());
	}

	private List<Long> getTrueParameterValues(int steps, List<Long> codes) {
		List<Long> values = getParameters(steps);
		List<Long> finals = new ArrayList<>();
		for (int i = 0; i < codes.size(); i++) {
			long code = codes.get(i);
			long value = values.get(values.size() - 1 - i);
			if (code == 0)
				finals.add(intCode.get((int) value));
			else if (code == 1)
				finals.add(value);
			else if (code == 2)
				finals.add(intCode.get((int) (value + relativeBase)));
		}
		Collections.reverse(finals);
		return finals;
	}

	private int getWriteAddress(Long code, Long value) {
		return (int) (code == 2 ? value + relativeBase : value);
	}

	private List<Long> getInstructionSet() {
		long code = intCode.get(index);
		List<Long> instructionSet = String.valueOf(code).chars().mapToObj(p -> (long) Character.getNumericValue(p)).collect(Collectors.toList());
		long instruction = instructionSet.get(instructionSet.size() - 1);
		IntConsumer cons = i -> instructionSet.add(0, (long) 0);

		if (Arrays.asList(1L, 2L, 7L, 8L).contains(instruction))
			IntStream.range(instructionSet.size(), 5).forEach(cons);
		else if (Arrays.asList(3L, 4L, 9L).contains(instruction))
			IntStream.range(instructionSet.size(), 3).forEach(cons);
		else
			IntStream.range(instructionSet.size(), 4).forEach(cons);
		return instructionSet;
	}

	private int getAndDeleteInstruction(List<Long> instructionSet) {
		String temp = String.valueOf(instructionSet.remove(instructionSet.size() - 2)) + instructionSet.remove(instructionSet.size() - 1);
		return Integer.parseInt(temp);
	}

	private void add(List<Long> parameterCodes) {
		List<Long> instr = getParameters(4);
		List<Long> values = getTrueParameterValues(4, parameterCodes);

		long addVal = values.get(0) + values.get(1);
		int writeAddress = getWriteAddress(parameterCodes.get(0), instr.get(2));
		intCode.set(writeAddress, addVal);
	}

	private void multiply(List<Long> parameterCodes) {
		List<Long> instr = getParameters(4);
		List<Long> values = getTrueParameterValues(4, parameterCodes);

		long multiVal = values.get(0) * values.get(1);
		int writeAddress = getWriteAddress(parameterCodes.get(0), instr.get(2));
		intCode.set(writeAddress, multiVal);
	}

	private void equalsOp(List<Long> parameterCodes) {
		List<Long> instr = getParameters(4);
		List<Long> values = getTrueParameterValues(4, parameterCodes);
		int writeAddress = getWriteAddress(parameterCodes.get(0), instr.get(2));
		intCode.set(writeAddress, values.get(0).equals(values.get(1)) ? 1L : 0L);
	}

	private void lessThan(List<Long> parameterCodes) {
		List<Long> instr = getParameters(4);
		List<Long> values = getTrueParameterValues(4, parameterCodes);
		int writeAddress = getWriteAddress(parameterCodes.get(0), instr.get(2));
		intCode.set(writeAddress, values.get(0) < values.get(1) ? 1L : 0L);
	}

	private boolean shouldJump(List<Long> parameterCodes) {
		List<Long> values = getTrueParameterValues(3, parameterCodes);
		return values.get(0) != 0;
	}

	private void jump(List<Long> parameterCodes) {
		List<Long> values = getTrueParameterValues(3, parameterCodes);
		index = Math.toIntExact(values.get(1));
	}

	private void getInput(List<Long> parameterCodes) {
		List<Long> instr = getParameters(2);
		int writeAddress = getWriteAddress(parameterCodes.get(0), instr.get(0));
		lastReadSuccessful = !this.input.isEmpty();
		if (lastReadSuccessful)
			intCode.set(writeAddress, this.input.remove(0));
	}

	private void sendOutput(List<Long> parameterCodes) {
		List<Long> values = getTrueParameterValues(2, parameterCodes);
		Output.getInstance().enterOutput(values.get(0));
		System.out.println(Output.getInstance().getQueuedOutput());
	}

	private void changeRelativeBase(List<Long> parameterCodes) {
		List<Long> values = getTrueParameterValues(2, parameterCodes);
		relativeBase += values.get(0);
	}

	public void executeProgram() {
		while (!isFinished && lastReadSuccessful)
			executeOperation();
		lastReadSuccessful = true;
	}

	private void executeOperation() {
		List<Long> instructionSet = getInstructionSet();
		int instruction = getAndDeleteInstruction(instructionSet);

		switch (instruction) {
			case 1:
				add(instructionSet);
				index += 4;
				break;
			case 2:
				multiply(instructionSet);
				index += 4;
				break;
			case 3:
				getInput(instructionSet);
				if (lastReadSuccessful)
					index += 2;
				break;
			case 4:
				sendOutput(instructionSet);
				index += 2;
				break;
			case 5:
				if (shouldJump(instructionSet))
					jump(instructionSet);
				else
					index += 3;
				break;
			case 6:
				if (!shouldJump(instructionSet))
					jump(instructionSet);
				else
					index += 3;
				break;
			case 7:
				lessThan(instructionSet);
				index += 4;
				break;
			case 8:
				equalsOp(instructionSet);
				index += 4;
				break;
			case 9:
				changeRelativeBase(instructionSet);
				index += 2;
				break;
			case 99:
				isFinished = true;
				break;
		}
	}
}
