package advent.day3;

import java.util.ArrayList;
import java.util.List;

public class Line {

	private int owner;
	private String instruction;
	private Location startCoord;
	private Location endCoord;
	private List<Point> points;


	public Line(String instruction, Location startCoord, int owner){
		this.instruction = instruction;
		this.startCoord = startCoord;
		this.owner = owner;
		this.endCoord = Location.calculateEndCoord(instruction, startCoord);
		this.points = Point.instructionsToPoints(instruction, startCoord);
	}


	public Location getEndCoord() {
		return endCoord;
	}

	public int getLength(){
		return Math.abs(startCoord.x-endCoord.x) + Math.abs(startCoord.y-endCoord.y);
	}

	public List<Point> getPoints() {
		return points;
	}

	public boolean isVertical(){
		return instruction.charAt(0) == 'U' || instruction.charAt(0) == 'D';
	}

	public boolean isHorizontal(){
		return !isVertical();
	}

	public int lengthToPoint(Point p){
		return Math.abs(p.x-startCoord.x) + Math.abs(p.y-startCoord.y);
	}

	public List<Point> intersectionPoints(Line l2){
		List<Point> intPoints = new ArrayList<>();

		for (Point point : points) {
			if(point.x == 0 && point.y == 0)
				continue;
			for(Point point2 : l2.points){
				if(point.x == point2.x && point.y == point2.y)
					intPoints.add(point);
			}
		}

		return intPoints;
	}

	public boolean intersect(Line l2) {
		if(owner == l2.owner)
			return false;
		if ((isVertical() && l2.isVertical()) && (endCoord.x != l2.endCoord.x))
			return false;
		if ((isHorizontal() && l2.isHorizontal()) && (endCoord.y != l2.endCoord.y))
			return false;

		List<Point> ints = intersectionPoints(l2);
		return !ints.isEmpty();
	}
}
