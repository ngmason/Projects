/** Connect4Logic is a class containing logic for the game 
 * Connect 4 which is a 2-player based game played on a vertical
 *  board that has seven hollow columns and six rows. Both players have
 *  a set of 21 pieces. One players pieces are 'X' and the others are 'O'.
 *  Each turn they get to drop one piece into one of the seven columns.
 *  The objective of the game is for either player to make a straight
 *  line of four of their own pieces; the line can be either vertical,
 *  horizontal, or diagonal.
 * @author Nina Mason
 * @version 2.0 6/12/2024
 */
package core;

import ui.Connect4GUI;

public class Connect4Logic 
{
	/**Variable to store game board.*/
	private char[][] board;
	
	/**Variable to store current turn.*/
	private char currentTurn;
	
	/**Variable to store currentTurns current row.*/
	private int currentRow;
	
	/**Variable to store currenTurns current column.*/
	private int currentColumn;
	
	/**Variable to store whether text console is being used.*/
	private boolean textConsole;
	
	/**Variable the number of turns.*/
	private int countTurns = 0;
	
	/**This is the constructor for the class. It creates
	 * and stores a 2D array for game board. Sets first
	 * player's turn equal to Player X. Starts a new
	 * game.
	 */
	public Connect4Logic(boolean textConsole)
	{
		board = new char[6][7];
		currentTurn = 'X';
		currentRow = 0;
		currentColumn = 0;
		this.textConsole = textConsole;
		newGame();
	}
	
	/**This will initialize an empty board starting a new game.
	 */
	private void newGame()
	{
		for(int row = 0; row < 6; row++)
		{
			for(int column = 0; column < 7; column++)
			{
				board[row][column] = ' ';
			}
		}
	}
	
	/**This method plays through current player's turn.
	 * @param column is the (column number - 1) that the current player chose.
	 * @return true if player's turn was played through.
	 * @return false if it was an invalid move.
	 */
	public boolean playGame(int column)
	{
		try 
		{
			if(column < 0 || column >= 7 || board[0][column] != ' ')
			{
				if(textConsole == true) { return false;} /**Invalid move Connect4TextConsole handles this.*/
				else 
				{
					if(board[0][column] != ' ')
					{
						Connect4GUI.MESSAGE.setText("The column " + (column+1) + " is full" +
							"\nPlayer" + currentTurn + " please make a different selection.");/**This is for when the column is full in the GUI.*/
					}
				}
				return false;
			}
			
			int row = 5;
			while(row >= 0 && board[row][column] != ' ')
			{
				row--;
			}
			currentRow = row;
			currentColumn = column;
			board[row][column] = currentTurn;
		}
		/**This will catch any index out of bounds exceptions. However, there is also a check at the beginning but this is an extra precaution*/
		catch (IndexOutOfBoundsException e) 
		{
			if(textConsole == true)
			{
				System.out.println("Error: Invalid input. Column must be a number 1-7. Make sure to enter correct values.");
				System.out.println("Player " + currentTurn + " please enter column values between 1 and 7.");
			}
			else
			{
				Connect4GUI.MESSAGE.setText("Invalid move. Player" + currentTurn + " please try again.");
			}
			return false;
		}
		
		countTurns++;
		return true;
	}
	
	/**Checks to see if there is a win either vertically, horizontally, or diagonally.
	 * @return true if currentTurn has a win.
	 * @return false if currentTurn is not a win.
	 */
	public boolean checkWin()
	{
		if(horizontalWin()) {return true;}
		if(verticalWin()) {return true;}
		if(diagonalWin()) {return true;}
		return false;
	}
	
	/**Helper function for checkWin(). This method checks for horizontal win in row currentRow.
	 *@return true if there is a horizontal win in currentRow.
	 *@return false if there is not a horizontal win in currentRow.
	 **/
	private boolean horizontalWin()
	{
		for(int c = 0; c < 4; c++)
		{
			if(board[currentRow][c] == board[currentRow][c + 1] 
				&& board[currentRow][c] == board[currentRow][c + 2] 
				&& board[currentRow][c] == board[currentRow][c + 3] 
				&& board[currentRow][c] != ' ')
			{
				return true;
			}
		}
		return false;
	}
	
	/**Helper function for checkWin(). This method checks for vertical win in column currentColumn.
	 *@return true if there is a vertical win in currentColumn.
	 *@return false if there is not a vertical win in currentColumn.
	 **/
	private boolean verticalWin()
	{
		for(int r = 0; r < 3; r++)
		{
			if(board[r][currentColumn] == board[r+1][currentColumn]
				&& board[r][currentColumn] == board[r+2][currentColumn] 
				&& board[r][currentColumn] == board[r+3][currentColumn] 
				&& board[r][currentColumn] != ' ')
			{
				return true;
			}
		}
		return false;
	}
	
	/**Helper function for checkWin(). This method checks for diagonal win across the board.
	 *@return true if there is a diagonal win.
	 *@return false if there is not a diagonal win.
	 **/
	private boolean diagonalWin()
	{
		/**Checks top left to bottom right diagonal.*/
		for (int r = 0; r < 3; r++) 
		{
			for (int c = 0; c < 4; c++) 
			{
				if (board[r][c] == board[r + 1][c + 1] 
					&& board[r][c] == board[r + 2][c + 2] 
					&& board[r][c] == board[r + 3][c + 3]
					&& board[r][c] == currentTurn) 
				{
					return true;
				}
			}
		}
		
		/**Checks top right to bottom left diagonal.*/
		for (int r = 0; r < 3; r++) 
		{
			for (int c = 3; c < 7; c++) 
			{
				if (board[r][c] == board[r + 1][c - 1] 
				&& board[r][c] == board[r + 2][c - 2] 
				&& board[r][c] == board[r + 3][c - 3]
				&& board[r][c] == currentTurn) 
				{
					return true;
				}
			}
		}
		
		return false;
	}
	
	/**This method updates turns to next player and keeps track of what turn your on.
	 **/
	public void nextTurn()
	{
		if(currentTurn == 'X')
		{
			currentTurn = 'O';
		}
		else
		{
			currentTurn = 'X';
		}
	}
	
	/**This method checks to see whether board is full or not.
	 *@return true if board is full.
	 *@return false if board is not full.
	 **/
	public boolean isBoardFull()
	{
		for(int row = 0; row < 6; row++)
		{
			for(int column = 0; column < 7; column++)
			{
				if(board[row][column] == ' ')
				{
					/**This means there is an empty space.*/
					return false;
				}
			}
		}
		/**There must be no empty spaces on the board.*/
		return true;
	}
	
	/**Returns current board.
	 * @return board.
	 */
	public char[][] getBoard()
	{
		return board;
	}
	
	/**Returns current turn.
	 * @return currentTurn.
	 */
	public char getCurrentTurn()
	{
		return currentTurn;
	}
	
	/**Returns current turns.
	 * @return currentTurns.
	 */
	public int getCountTurns()
	{
		return countTurns;
	}

}
