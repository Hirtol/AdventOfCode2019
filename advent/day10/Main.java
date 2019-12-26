package advent.day10;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
import java.util.stream.IntStream;

public class Main {

	static class Location{
		public final int x,y;

		public Location(int y, int x){
			this.x = x;
			this.y = y;
		}

		public String toString(){
			return String.format("Y%s X%s", y, x);
		}
	}

	static class Ray{
		public final int xDir, yDir;
		public boolean blocking;

		public Ray(int x, int y){
			this.xDir = x;
			this.yDir = y;
			this.blocking = false;
		}
	}

	static class Board{
		public char[][] board;

		public Board(char[][] board){
			this.board = new char[board.length][];
			for (int i = 0; i < board.length; i++)
				this.board[i] = board[i].clone();
		}
	}
	private static char[][] puzzle;

	private static List<Location> getAstroids(Board b){
		List<Location> locations = new ArrayList<>();
		for (int i = 0; i < b.board.length; i++)
			for (int j = 0; j < b.board[i].length; j++)
				if(b.board[i][j] == '#')
					locations.add(new Location(i,j));
		return locations;
	}

	public static void castRay(Board board, Ray ray, Location astroidView){
		int currentX = astroidView.x + ray.xDir;
		int currentY = astroidView.y + ray.yDir;
		Character currentPiece = 'P';

		while(currentPiece != null){
			try{
				currentPiece = board.board[currentY][currentX];
			}catch (Exception e){
				currentPiece = null;
				continue;
			}
			if(currentPiece == '#') {
				if (!ray.blocking)
					ray.blocking = true;
				else {
					board.board[currentY][currentX] = 'X';
				}
			}
			currentX += ray.xDir;
			currentY += ray.yDir;
		}
	}

	private static Ray getRay(int x, int y, int times){
		if(times == 0)
			return new Ray(x,y);
		if(times == 3)
			return new Ray(-x,-y);
		return new Ray((int) (Math.pow(-1, times)*x), (int) (Math.pow(-1, times+1)*y));
	}

	private static List<Ray> getRays(){
		List<Ray> rays = new ArrayList<>();
		for (int i = 0; i < puzzle.length; i++) {
			for (int j = 0; j < puzzle[i].length; j++) {
				if(i == 0 && j == 0)
					continue;
				int finalJ = j;
				int finalI = i;
				IntStream.range(0,4).forEach(p -> rays.add(getRay(finalJ, finalI, p)));
			}
		}
		return rays;
	}

	private static void readFile(String filename) throws FileNotFoundException {
		Scanner scn = new Scanner(new FileReader("C:\\Users\\Valentijn\\Desktop\\Git-Projects\\CodeAdvent2019\\src\\advent\\day10\\"+filename));
		List<List<Character>> lines = new ArrayList<>();
		while(scn.hasNextLine()){
			List<Character> currentLine = new ArrayList<>();
			String line = scn.nextLine();
			line.chars().forEach(t -> currentLine.add((char)t));
			lines.add(currentLine);
		}
		puzzle = new char[lines.get(0).size()][lines.size()];
		for (int i = 0; i < lines.size(); i++) {
			for (int j = 0; j < lines.get(i).size(); j++) {
				puzzle[i][j] = lines.get(i).get(j);
			}
		}
	}

	public static void part1(){
		List<Location> asteroids = getAstroids(new Board(puzzle));
		Map<Location, Integer> asteroidMap = new HashMap<>();

		while(!asteroids.isEmpty()){
			Location currentAstroid = asteroids.remove(0);
			List<Ray> castableRays = getRays();
			Board currentBoard = new Board(puzzle);
			for (int i = 0; i < castableRays.size(); i++) {
				Ray currentRay = castableRays.get(i);
				castRay(currentBoard, currentRay, currentAstroid);
			}
			int astroidsInView = getAstroids(currentBoard).size()-1;
			asteroidMap.put(currentAstroid, astroidsInView);
		}

		System.out.println(asteroidMap.entrySet().stream().max(Map.Entry.comparingByValue()).get());
	}

	private static void printBoard(){
		Arrays.stream(puzzle).forEach(System.out::println);
	}

	public static void main(String[] args) throws FileNotFoundException {
		readFile("input.txt");
		printBoard();
		part1();
	}
}
