/** Connect4TextConsole is a class containing the text-based
 * UI for the Connect 4 game. It will run the general procedure
 * for the game. First it will run Player X's turn and ask the 
 * player to choose a column 1-7. It will then check if the move
 * is valid. It then shows the state of the grid placing an X at 
 * the bottom-most empty row of the specified column. Next it
 * checks for a win state. If it is a win state informs Player X
 * they won and close game. Else it will continue the game and
 * run Player O's turn. Ask them to pick a number 1-7. Continue
 * this till one of the players wins the game or the game results
 * in a draw.
 * @author Nina Mason
 * @version 2.0 6/5/2024
 */

package ui;
import core.Connect4Logic;
import core.Connect4ComputerPlayer;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Connect4TextConsole 
{

	public static void textConsoleConnect4()
	{
		Connect4Logic game = new Connect4Logic(true);
		int countTurns = 0;
		Scanner scanner = new Scanner(System.in);
		char playerVs = ' ';
		boolean invalidPlayerVs = true;
		
		printBoard(game.getBoard());
		
		try
		{
		while (invalidPlayerVs)
		{
			System.out.println("Begin Game. Enter 'P' if you want to play against another player, enter 'C' to play against the computer.\n");
			playerVs = scanner.next().charAt(0);
			if(playerVs == 'P' || playerVs == 'p')
			{
				invalidPlayerVs = false;
				while (countTurns <= 42)
				{
					int column = getColumnFromPlayer(game);
					if(game.playGame(column))
					{
						printBoard(game.getBoard());
						if(game.checkWin())
						{
							System.out.println("Player " + game.getCurrentTurn() + " won the game.");
							break;
						}
						else if (game.isBoardFull())
						{
							System.out.println("It's a draw!");
							break;
						}
						else
						{
							game.nextTurn();
							countTurns++;
						}
					}
					else
					{
						System.out.println("Invalid move. Try again.");
					}
				}
			}
			else if (playerVs == 'C' || playerVs == 'c')
			{
				Connect4ComputerPlayer computer = new Connect4ComputerPlayer();
				invalidPlayerVs = false;
				int column = 0;
				while(countTurns <= 42)
				{
					if(game.getCurrentTurn() == 'X')
					{
						boolean validInput = false;
						while(!validInput)
						{
							System.out.println("Your turn, enter column 1-7:");
							column = scanner.nextInt();
							if(column <= 7 && column >= 1)
							{
								validInput = true;
							}
							else
							{
								System.out.println("Invalid input. Try again.");
							}
						}
					}
					else
					{
						column = computer.getRandomColumn(game.getBoard());
						System.out.println("Computer's turn, selected column: " + column);
					}
					if(game.playGame(column-1))
					{
						printBoard(game.getBoard());
						if(game.checkWin() && game.getCurrentTurn() == 'X')
						{
							System.out.println("You win!");
							break;
						}
						if (game.checkWin() && game.getCurrentTurn() == 'O')
						{
							
							System.out.println("Computer wins!");
							break;
						}
						if(game.isBoardFull())
						{
							System.out.println("It's a draw!");
							break;
						}
						game.nextTurn();
						countTurns++;
						
					}
				}
				
			}
			else
			{
				System.out.println("Invalid input. Try again.");
			}
		}
		}
		catch(Exception InputMismatchException)
		{
			System.out.println("Error: Invalid input. Input must be an integer input or program will exit.");
		}
		scanner.close();
	}
	
	/**This method prints current game board.
	 *@param 2D character array that holds current game board.
	 **/
	private static void printBoard(char[][] board)
	{
		for(int r = 0; r < 6; r++)
		{
			for(int c = 0; c < 7; c++)
			{
				char piece = board[r][c];
				char displayPiece = piece == ' ' ? ' ' : piece;
				System.out.print("|" + displayPiece);
			}
			System.out.println("|");
		}
		
	}
	
	/**This method prompts current player and collects their column input.
	 *@param Connect4Logic variable of a game.
	 *@return column number that was input by player.
	 *@throws InputMismatchException
	 **/
	private static int getColumnFromPlayer(Connect4Logic game) throws InputMismatchException
	{
		boolean invalidInput = true;
		Scanner scanner = new Scanner(System.in);
		int column = 0;
		while(invalidInput)
		{
			System.out.println("Player" + game.getCurrentTurn()+"--your turn. Choose a column number from 1-7.");
			
			column = scanner.nextInt();
			if(column >= 1 && column <= 7)
			{
				invalidInput = false;
				column = column-1;
			}
			else
			{
				System.out.println("Invalid input. Please enter a number between 1 and 7.");
			}
		}
		return column;
		
	}
	

}
