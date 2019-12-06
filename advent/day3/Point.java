package advent.day3;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Point extends Location {


	public Point(int x, int y) {
		super(x, y);
	}

	public static int manhattenDistance(Point p){
		return Math.abs(p.x) + Math.abs(p.y);
	}

	private static List<Point> pointListGenerator(int length, Location startCoord, boolean x){
		int xt = startCoord.x;
		int yt = startCoord.y;
		List<Point> newPoints = new ArrayList<>();
		IntStream.range(0, Math.abs(length)).
				forEach(i ->
				{
					if(length < 0) {
						if (x)
							newPoints.add(new Point(xt - i, yt));
						else
							newPoints.add(new Point(xt, yt - i));
					}else
						if(x)
							newPoints.add(new Point(xt+i, yt));
						else
							newPoints.add(new Point(xt, yt+i));
				});
		return newPoints;
	}

	public static List<Point> instructionsToPoints(String instruction, Location startCoord){
		char direction = instruction.charAt(0);
		int length = Integer.parseInt(instruction.substring(1));

		switch(direction){
			case 'U':
				return pointListGenerator(length, startCoord, false);
			case 'D':
				return pointListGenerator(-length, startCoord, false);
			case 'L':
				return pointListGenerator(-length, startCoord, true);
			case 'R':
				return pointListGenerator(length, startCoord, true);
			default:
				System.out.println("Something went wrong!");
				break;
		}
		return null;
	}

}
