package advent.day1;

import java.io.File;
import java.util.Scanner;

public class AdventFileIO {

	private File file;
	private Scanner scn;

	public AdventFileIO(String filename){
		System.out.println(setFile(filename));
	}

	public AdventFileIO(){
		this.file = null;
		this.scn = null;
	}

	private boolean hasScanner(){
		return scn != null;
	}

	public boolean setFile(String fileName) {
		this.file = new File(fileName);
		try(Scanner scan = new Scanner(file)){
			this.scn = scan;
			return true;
		}catch (Exception e){
			scn = null;
			return false;
		}
	}

	public boolean hasNextLine(){
		return hasScanner() && scn.hasNextLine();
	}

	public String getNextLine(){
		if(hasScanner())
			return scn.nextLine();
		return null;
	}


}
