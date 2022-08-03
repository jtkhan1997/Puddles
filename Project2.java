/*
 Jalal Khan
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Project2 {
	//wanted us to use a queue so I made a linked-list contructor for it
	static class order{
		int x, y;
		public order(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
	public static int findPuddle(int row, int col, int[][] puddles) {
		int count = 0;
		//Keeps track of a node that has been visited during previous iterations
		//and is also a 0
		boolean[][] hasVisited = new boolean[row][col];
		for(int i = 0; i < row; i++) {
			for(int j = 0; j < col; j++) {
				if(puddles[i][j] == 0 && !hasVisited[i][j]) {
					//using breadth first search to track links
					breadthFirst(puddles, hasVisited, i, j, row, col);
					count++;
				}
			}
		}
		return count;
	}
	public static void breadthFirst(int[][] puddles, boolean[][] hasVisited, int cRow, int cCol, int row, int col) {
		//Use these arrays to be able to check cardinal directions of the given node
		int rowCheck[] = { -1, 0, 0, 1};
	    int colCheck[] = {  0, -1, 1, 0};
	    //Our queue implemented with linked-list
		Queue<order> queue = new LinkedList<order>();
		queue.add(new order(cRow, cCol));
		hasVisited[cRow][cCol] = true;
		//Move through graph by enqueueing next node and dequeueing first node and so on
		while(!queue.isEmpty()) {
			int first = queue.peek().x;
			int next = queue.peek().y;
			queue.remove();
			// i < 4 = checking the 4 cardinal directions only
			for(int i = 0; i < 4; i++) {
				//method 'safe' is called to check if the cardinal direction is == 0
				//and it hasn't been visited yet
				if(safe(puddles, first + rowCheck[i], next + colCheck[i], hasVisited, row, col)) {
					hasVisited[first + rowCheck[i]][next + colCheck[i]] = true;
					queue.add(new order(first + rowCheck[i], next + colCheck[i]));
				}
			}
		}
		
	}
	private static boolean safe(int[][] puddles, int i, int j, boolean[][] hasVisited, int row, int col) {
		return ( i >= 0) && (i < row) && (j >= 0) && ( j < col) && (puddles[i][j] == 0 && !hasVisited[i][j]);
	}
	public static void main(String[] args) {
		Scanner scan = null;
		try {
			scan = new Scanner(new File("input.txt"));
		} catch(FileNotFoundException e) {
			System.out.println("Not found");
			System.exit(1);
		}
		//Getting dimension sizes etc..
		int row = scan.nextInt();
		int col = scan.nextInt();
		int[][] puddles = new int[row][col];
		for(int i = 0; i < row; i++) {
			for(int j = 0; j < col; j++) {
				puddles[i][j] = scan.nextInt();
			}
		}
		//will get the count from another function
		int counter = findPuddle(row, col, puddles);
		System.out.println(counter);

	}

}
