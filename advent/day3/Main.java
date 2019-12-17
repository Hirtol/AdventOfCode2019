package advent.day3;

import advent.day3.Line;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.IntStream;

public class Main {


    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("C:\\Users\\Valentijn\\Desktop\\Git-Projects\\CodeAdvent2019\\src\\advent\\day3\\input.txt");
        Scanner scn = new Scanner(file);

        String[] list1 = scn.nextLine().split(",");
        String[] list2 = scn.nextLine().split(",");

        List<Line> own1 = new ArrayList<>();
        List<Line> own2 = new ArrayList<>();

        own1.add(new Line(list1[0], new Location(0,0), 0));
        own2.add(new Line(list2[0], new Location(0,0), 1));

        IntStream.range(1, list1.length).forEach(i -> own1.add(new Line(list1[i], own1.get(i-1).getEndCoord(), 0)));
        IntStream.range(1, list2.length).forEach(i -> own2.add(new Line(list2[i], own2.get(i-1).getEndCoord(), 1)));

        List<Point> points = getIntersectionPoints(own1, own2);

        System.out.println(LineUtil.lowestDelayPoint(points, own1, own2));

        int minDistance = 0;
        Point closestPoint = null;
        for(Point point : points){
            if(minDistance == 0 || Point.manhattenDistance(point) < minDistance){
                minDistance = Point.manhattenDistance(point);
                closestPoint = point;
            }
        }

        System.out.println(closestPoint + " TO MANHATTEN DISTANCE: " + Point.manhattenDistance(closestPoint));

    }

    public static List<Point> getIntersectionPoints(List<Line> l1, List<Line> l2){
        List<Point> points = new ArrayList<>();
        for (Line ls1: l1) {
            for(Line ls2: l2){
                if(ls1.intersect(ls2))
                    points.addAll(ls1.intersectionPoints(ls2));
            }
        }
        return points;
    }
}
