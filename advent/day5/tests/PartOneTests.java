package advent.day5.tests;

import advent.day5.OpCode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Arrays;

public class PartOneTests {

	OpCode opCode;
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
	private final InputStream systemIn = System.in;
	private final PrintStream originalOut = System.out;
	private final PrintStream originalErr = System.err;
	private ByteArrayInputStream testIn;

	@BeforeEach
	public void setUpStreams() {
		opCode = null;
		System.setOut(new PrintStream(outContent));
		System.setErr(new PrintStream(errContent));
	}

	private void provideInput(int data) {
		testIn = new ByteArrayInputStream(String.valueOf(data).getBytes());
		System.setIn(testIn);
	}

	@Test
	public void test1(){
		OpCode opCode = new OpCode(Arrays.asList(1,9,10,3,2,3,11,0,99,30,40,50));
		while(opCode.hasNext() && !opCode.isFinished){
			opCode.executeOp();
		}
		Assertions.assertEquals(3500, opCode.opCode.get(0));
	}

	@Test
	public void test2(){
		opCode = new OpCode(Arrays.asList(1002,4,3,4,33));
		while(opCode.hasNext() && !opCode.isFinished){
			opCode.executeOp();
		}
		Assertions.assertEquals(99, opCode.opCode.get(4));
	}

	@Test
	public void test3(){
		opCode = new OpCode(Arrays.asList(1,1,1,4,99,5,6,0,99));
		while(opCode.hasNext() && !opCode.isFinished){
			opCode.executeOp();
		}
		Assertions.assertEquals(30, opCode.opCode.get(0));
		Assertions.assertEquals(2, opCode.opCode.get(4));
	}

	@Test
	public void test4(){
		opCode = new OpCode(Arrays.asList(2,4,4,5,99,0));
		while(opCode.hasNext() && !opCode.isFinished){
			opCode.executeOp();
		}
		Assertions.assertEquals(9801, opCode.opCode.get(5));
	}

	@Test
	public void test5(){
		provideInput(10);
		OpCode opCode = new OpCode(Arrays.asList(3,21,1008,21,8,20,1005,20,22,107,8,21,20,1006,20,31,
				1106,0,36,98,0,0,1002,21,125,20,4,20,1105,1,46,104,
				999,1105,1,46,1101,1000,1,20,4,20,1105,1,46,98,99));

		while(opCode.hasNext() && !opCode.isFinished){
			opCode.executeOp();
		}

		Assertions.assertTrue(outContent.toString().contains(String.valueOf(1001)));

	}

}
