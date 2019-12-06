package advent.day3;

import java.util.List;

public class LineUtil {


	private static int distanceToStart(Line line, List<Line> lineList){
		int totalDistance = 0;
		for(int i = lineList.indexOf(line)-1; i >= 0; i--){
			Line currentLine = lineList.get(i);
			totalDistance += currentLine.getLength();
		}
		return totalDistance;
	}

	private static int getTotalDelay(List<Line> l, Point point) {
		for (Line ls : l) {
			boolean intersect = ls.getPoints().stream().anyMatch(point1 -> point1.x == point.x && point1.y == point.y);
			if (intersect) {
				return distanceToStart(ls, l) + ls.lengthToPoint(point);
			}
		}
		return 0;
	}

	public static int lowestDelayPoint(List<Point> intersects, List<Line> l1, List<Line> l2){

		int lowestDelay = 0;
		for(Point point : intersects) {
			int totalDelay = getTotalDelay(l1, point) + getTotalDelay(l2, point);

			if (lowestDelay == 0 || totalDelay < lowestDelay)
				lowestDelay = totalDelay;
		}
		return lowestDelay;

	}
}
