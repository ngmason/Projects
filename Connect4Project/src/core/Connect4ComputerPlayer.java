package core;
import java.util.Random;
/**
 * Connect4ComputerPlayer class generates the moves for the computer player.
 * @author Nina Mason
 * @version 1.0 6/12/2024
 * */
public class Connect4ComputerPlayer 
{
	/**Constant that holds the max amount of columns*/
	private final int MAX = 7;
	/**Constant that holds min amount of columns*/
	private final int MIN = 1;
	
	/**Random computer column variable.*/
	private Random randomColumn;
	
	/**Constructor*/
	public Connect4ComputerPlayer()
	{
		randomColumn = new Random();
	}
	
	/**
	 * Returns true if the column can be filled. Helper function to getRandomColumn.
	 * @param 2D character array of board.
	 * @param Integer column.
	 * @return Boolean of whether column is full or not.
	 * */
	private boolean canBeAdded(char[][] board, int column)
	{
		for(int row = 0; row < 6; row++) 
		{
			if(board[row][column] == ' ') 
			{
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Generates a random column index for the computer player and returns it.
	 * @param 2D character array of board.
	 * @return Integer of randomly generated column.
	 * */
	public int getRandomColumn(char[][] board)
	{
		int column = randomColumn.nextInt(MAX - MIN + 1) + MIN;
		while(!canBeAdded(board, column-1))
		{
			column = randomColumn.nextInt(MAX - MIN + 1) + MIN;
		}
		return column;
	}

}
