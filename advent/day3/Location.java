package advent.day3;

public class Location {

	public int x;
	public int y;


	public Location(int x, int y){
		this.x = x;
		this.y = y;
	}

	public static Location calculateEndCoord(String instruction, Location startCoord){
		char direction = instruction.charAt(0);
		int length = Integer.parseInt(instruction.substring(1));
		int xt = startCoord.x;
		int yt = startCoord.y;

		switch(direction){
			case 'U':
				yt += length;
				break;
			case 'D':
				yt -= length;
				break;
			case 'L':
				xt -= length;
				break;
			case 'R':
				xt += length;
				break;
			default:
				System.out.println("Something went wrong!");
				break;
		}
		return new Location(xt, yt);
	}

	public String toString(){
		return String.format("X:%s,Y:%s",x,y);
	}

}
