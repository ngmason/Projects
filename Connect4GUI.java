/**
 * Connect4 GUI to play the Connect4 game on.
 * 
 * @author Nina Mason
 * @version 1.0 6/12/2024
 */

package ui;

import core.Connect4Logic;
import core.Connect4ComputerPlayer;

import javafx.application.Application;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.control.*;
import javafx.geometry.*;
import javafx.event.*;
import javafx.scene.text.*;

public class Connect4GUI extends Application 
{
	/**Final variable that holds number of rows.*/
	final static int ROWS = 6;
	
	/**Final variable that holds number of columns.*/
	final static int COLUMNS = 7;
	
	/**Final variable that holds the message in the bottom left corner of the screen.*/
	public final static Label MESSAGE = new Label(" ");
	
	/**Final variable that holds message that displays the last move performed.*/
	public final static Label MOVE = new Label(" ");
	
	/**Final variable that holds the pieces/layout of the board.*/
	public final static Label[][] piece = new Label[ROWS][COLUMNS];
	
	
	/**Connect4 game logic variable.*/
	Connect4Logic logic;
	/**Connect4 computer player variable.*/
	Connect4ComputerPlayer computerPlayer;
	/**Holds a boolean that is true if the user is playing against a computer player.*/
	boolean computerPlayerBoolean = false;
	/**Game stage variable.*/
	Stage gameStage;
	/**String to hold what move was made.*/
	String move = "";
	/**If a move was successfully performed moveMade is true.*/
	boolean moveMade = false;
	
	/**Character variable to hold current player's piece.*/
	char currentPlayer = 'X';
	/**Character variable to hold next player's piece.*/
	char nextPlayer = 'O';
	
	
	/**
	 * This method launches the program.
	 * 
	 * @param args is used to launch GUI
	 */
	public static void main(String[] args) { 
	    launch(args);
	}
	
	
	/**
	 * Starts GUI application and allows player to choose the way they play the game: console or GUI.
	 * 
	 * @param gameSelectStage shown at beginning of application
	 */
	@Override
	public void start(Stage gameSelectStage) 
	{
		FlowPane gameTypePane = buildMessagePane("Connect4", Color.DARKOLIVEGREEN, 40); 
		
		Button textConsole = new Button("Text Console"); 
		textConsole.setPrefWidth(100); 
		textConsole.setOnAction(new ButtonHandler(gameSelectStage)); 
		
		Button gui = new Button("GUI"); 
		gui.setPrefWidth(100); 
		gui.setOnAction(new ButtonHandler(gameSelectStage)); 
		
		HBox gameSelectButtonsPane = new HBox(20); 
		gameSelectButtonsPane.getChildren().add(textConsole);
		gameSelectButtonsPane.getChildren().add(gui); 
		
		VBox gameSelectPane = new VBox(10); 
		gameSelectPane.setPadding(new Insets(10, 10, 10, 40)); 
		gameSelectPane.getChildren().add(gameTypePane); 
		gameSelectPane.getChildren().add(gameSelectButtonsPane);
		
		gameSelectStage.setTitle("Connect4"); 
		gameSelectStage.setScene(new Scene(gameSelectPane, 300, 110)); 
		gameSelectStage.show(); 
	}
	
	
	/**
	 * Helper function that builds FlowPane with text inside.
	 * 
	 * @param messageIn is text to be displayed
	 * @param color is the color to set text font to
	 * @param fontSize is size to set text font to
	 * @return FlowPane contains text set by function
	 */
	private FlowPane buildMessagePane(String messageIn, Color color, int fontSize) {
		Text message = new Text(messageIn); 
		message.setFont(Font.font("Verdana", FontWeight.MEDIUM, fontSize)); 
		message.setFill(color); 
		
		FlowPane messagePane = new FlowPane();
		messagePane.getChildren().add(message); 
		return messagePane; 
	}
	
	/**
	 * Inner class: handler class for Buttons
	 * @author Nina Mason
	 */
	class ButtonHandler implements EventHandler<ActionEvent> {
		Stage stage;
		String buttonLabel;
		
		/**
		 * Constructor with parameter. Creates ButtonHandler for each button.
		 * 
		 * @param s is the Stage where the button lies.
		 */
		public ButtonHandler(Stage s) {
			stage = s;
		}
		
		/**
		 * Handles buttons
		 * 
		 * @param action: button clicked
		 */
		public void handle(ActionEvent action) {
			buttonLabel = ((Button)action.getSource()).getText();
			
			switch(buttonLabel) { 
			/**The choosing how user wants to play buttons.*/
				case "Text Console": 
					stage.close(); 
					Connect4TextConsole.textConsoleConnect4(); 
					break;
				case "GUI": 
					stage.close(); 
					playGuiGame(); 
					break;
			/**The choosing opponent buttons.*/
				case "The Computer": 
					stage.close(); 
					computerPlayer = new Connect4ComputerPlayer(); 
					computerPlayerBoolean = true; 
					break;
				case "Another Player": 
					stage.close(); 
					computerPlayerBoolean = false; 
					break;
			/**No button has been implemented.*/
				default:
					System.out.println("Error: The button pressed has not been implemented!"); 
					break;
			}
		}
	}
	
	
	/**
	 * Calls methods to create game stage and opponent stage, then shows them.
	 */
	private void playGuiGame() 
	{
		logic = new Connect4Logic(false); 
		MESSAGE.setText("PlayerX - your turn\nClick one of the column buttons.\n"); 
		gameStage = createGameStage(); 
		Stage opponentStage = createOpponentStage();
		gameStage.show(); 
		opponentStage.show(); 
	}
	
	/**
	 * Creates stage for the user to choose their opponent
	 * 
	 * @return Stage the created stage
	 */
	private Stage createOpponentStage() {
		FlowPane opponentPane = buildMessagePane("Pick your opponent", Color.CHARTREUSE, 24); 
		Stage opponentChoiceStage = new Stage(); 
		
		Button player = new Button("Another Player"); 
		player.setPrefWidth(100); 
		player.setOnAction(new ButtonHandler(opponentChoiceStage)); 
		
		
		Button computer = new Button("The Computer"); 
		computer.setPrefWidth(100); 
		computer.setOnAction(new ButtonHandler(opponentChoiceStage));
		
		HBox opponentSelectButtonsPane = new HBox(20); 
		opponentSelectButtonsPane.getChildren().add(player); 
		opponentSelectButtonsPane.getChildren().add(computer); 
		
		VBox opponentSelectPane = new VBox(10); 
		opponentSelectPane.setPadding(new Insets(20, 10, 10, 40)); 
		opponentSelectPane.getChildren().add(opponentPane); 
		opponentSelectPane.getChildren().add(opponentSelectButtonsPane); 
		
		opponentChoiceStage.setTitle("Connect4"); 
		opponentChoiceStage.setScene(new Scene(opponentSelectPane, 300, 110)); 
		
		return opponentChoiceStage; 
	}
	
	/**
	 * Creates stage for Connect4 game play.
	 * 
	 * @return created stage
	 */
	private Stage createGameStage() {
		Pane boardPane = new Pane(); 
		boardPane.setPrefWidth(585); 
		boardPane.setPrefHeight(585); 
		
		Rectangle boardBack = new Rectangle(); 
		boardBack.setX(5); 
		boardBack.setWidth(650); 
		boardBack.setHeight(650); 
		boardBack.setFill(Color.BLACK); 
		
		VBox rowsLabels = new VBox(75); 
		rowsLabels.setAlignment(Pos.CENTER_LEFT); 
		rowsLabels.setPadding(new Insets(45, 0, 0, 30)); 
		
		rowsLabels.getChildren().add(new Label("6")); 
		rowsLabels.getChildren().add(new Label("5")); 
		rowsLabels.getChildren().add(new Label("4")); 
		rowsLabels.getChildren().add(new Label("3")); 
		rowsLabels.getChildren().add(new Label("2")); 
		rowsLabels.getChildren().add(new Label("1")); 
		
		
		GridPane board = createBoard(); 
		
		Button[] colButtons = new Button[COLUMNS];
		
		for(int i = 0; i < COLUMNS; i++)
		{
			colButtons[i] = new Button(Integer.toString(i+1));
			colButtons[i].setPrefWidth(100); 
		}
		
		colButtons[0].setOnAction(bp -> {guiMove(board, 0);}); 
		colButtons[1].setOnAction(bp -> {guiMove(board, 1);}); 
		colButtons[2].setOnAction(bp -> {guiMove(board, 2);}); 
		colButtons[3].setOnAction(bp -> {guiMove(board, 3);}); 
		colButtons[4].setOnAction(bp -> {guiMove(board, 4);}); 
		colButtons[5].setOnAction(bp -> {guiMove(board, 5);});
		colButtons[6].setOnAction(bp -> {guiMove(board, 6);}); 
		
		HBox columnButtons = new HBox(68); 
		columnButtons.setAlignment(Pos.CENTER); 
		columnButtons.setPadding(new Insets(25, 0, 0, 85)); 
		columnButtons.getChildren().add(colButtons[0]);
		columnButtons.getChildren().add(colButtons[1]);
		columnButtons.getChildren().add(colButtons[2]);
		columnButtons.getChildren().add(colButtons[3]);
		columnButtons.getChildren().add(colButtons[4]);
		columnButtons.getChildren().add(colButtons[5]);
		columnButtons.getChildren().add(colButtons[6]);
		
		boardPane.getChildren().add(boardBack);
		boardPane.getChildren().add(board); 
		
		BorderPane gameBoard = new BorderPane(); 
		gameBoard.setMaxWidth(305); 
		gameBoard.setMaxHeight(305); 
		gameBoard.setCenter(boardPane); 
		gameBoard.setBottom(columnButtons);
		gameBoard.setLeft(rowsLabels); 

		
		MESSAGE.setPadding(new Insets(10, 10, 10, 20)); 
		MESSAGE.setFont(Font.font("Verdana", 20)); 
		
		MOVE.setPadding(new Insets(10, 10, 10, 0));
		MOVE.setFont(Font.font("Verdana", 14));
		
		FlowPane messagePane = buildMessagePane("Connect4 Game", Color.DEEPSKYBLUE, 40); 
		messagePane.setPadding(new Insets(10, 10, 10, 75)); 
		
		Button newGame = new Button("New Game");  
		newGame.setPrefWidth(100);
		newGame.setOnAction(bp -> {gameStage.close(); currentPlayer = 'X'; nextPlayer = 'O'; playGuiGame();}); 
		
		Button quit = new Button("Quit"); 
		quit.setPrefWidth(100);;
		
		quit.setOnAction(q -> {gameStage.close(); System.exit(0);});
		
		VBox buttonPane = new VBox(); 
		buttonPane.setPadding(new Insets(10, 20, 10, 10)); 
		buttonPane.setAlignment(Pos.CENTER_RIGHT); 
		buttonPane.getChildren().add(newGame); 
		buttonPane.getChildren().add(quit);
		buttonPane.getChildren().add(MOVE); 
		
		BorderPane gamePane = new BorderPane(); 
		gamePane.setTop(messagePane); 
		gamePane.setLeft(gameBoard); 
		gamePane.setBottom(MESSAGE); 
		gamePane.setRight(buttonPane); 
		
		Stage gameStage = new Stage(); 
		gameStage.setTitle("Connect4 Game"); 
		gameStage.setScene(new Scene(gamePane, 850, 850)); 
		
		return gameStage; 
	}
	
	/**
	 * Creates Connect4 board with pieces, to be included in the game stage
	 * 
	 * @return GridPane of board
	 */
	private GridPane createBoard() {
		GridPane board = new GridPane(); 
		board.setAlignment(Pos.CENTER); 
		board.setPadding(new Insets(10, 10, 10, 15)); 
		board.setHgap(5); 
		board.setVgap(5);
		
		Rectangle[][] square = new Rectangle[ROWS][COLUMNS]; 
		for (int row = 0; row < ROWS; row++) {
			for (int col = 0; col < COLUMNS; col++) {
				
				square[row][col] = new Rectangle(); 
				square[row][col].setX(15 + col * 35); 
				square[row][col].setY(15 + row * 35); 
				square[row][col].setWidth(85); 
				square[row][col].setHeight(85); 
				square[row][col].setFill(Color.ALICEBLUE); 
				
				board.add(square[row][col], col, row); 
				
			}
		}
		
		for (int row = 0; row < ROWS; row++) { 
			for (int col = 0; col < COLUMNS; col++) { 

				piece[row][col] = new Label(" "); 
				
				piece[row][col].setFont(Font.font("Arial", 60)); 
				board.add(piece[row][col], col, row); 
				board.setHalignment(piece[row][col], HPos.CENTER); 
				board.setValignment(piece[row][col], VPos.CENTER); 
			}
		}
		return board; 
	}
	
	/**
	 * Called after one of the column buttons are clicked. Handles game logic and playing the game.
	 * 
	 * @param board Connect4 board
	 * @param col is the column of selected piece/space
	 */
	private void guiMove(GridPane board, int col) {
		
		char colChar;
		switch(col) {
		case(0):
			colChar = '1';
			break;
		case(1):
			colChar = '2';
			break;
		case(2):
			colChar = '3';
			break;
		case(3):
			colChar = '4';
			break;
		case(4):
			colChar = '5';
			break;
		case(5):
			colChar = '6';
			break;
		default:
			colChar = '7';
			break;
	}
		
		move = move + colChar; /**Stores the character of the move just made.*/
	
		MOVE.setText(move); 
		moveMade = logic.playGame(col); /**Calling the playGame method in Connect4Logic which returns true if played through and false if not.*/
		if (moveMade == true) { /**If playGame successfully played through turn this bracket of code will execute.*/
			updateGuiBoard(logic.getBoard()); /**Updates GUI game board.*/
			if (logic.checkWin()) { /**This checks if current player won.*/
				MOVE.setText(""); /**Clears out the MOVE string if they won.*/
				MESSAGE.setText("Player" + currentPlayer + " Won the Game"); /**Sets MESSAGE to announce winner.*/
			}
			else { /**The case of when current player did not win.*/
				logic.nextTurn(); /**Updates to next turn/next player.*/
				currentPlayer = logic.getCurrentTurn();
				if(currentPlayer == 'X') {nextPlayer = 'O'; }
				else {nextPlayer = 'X';}
				if (computerPlayerBoolean == false) { /**If there is no computer player run this bracket of code.*/
					MESSAGE.setText("Player" + currentPlayer + " - your turn!\nClick one of the column buttons."); /**Prompts next player to click a column button.*/
				}
				else { /**If there is a computer player run this bracket of code.*/
					MOVE.setText("");/**Clears out the MOVE string.*/
					Connect4ComputerPlayer computerPlayer = new Connect4ComputerPlayer();
					int column = computerPlayer.getRandomColumn(logic.getBoard());
					logic.playGame(column-1);
					move = Integer.toString(column);
					updateGuiBoard(logic.getBoard()); /**Update game board.*/
					if (logic.checkWin() == true) { /**Checks for computer's win.*/
						MOVE.setText(""); /**Clears out the MOVE string.*/
						MESSAGE.setText("The Computer Won the Game.\nBetter luck next time."); /**If the computer won sets message to announce winner.*/
					}
					else { /**If computer did not win.*/
						logic.nextTurn(); /**Update currentPlayer.*/
						currentPlayer = logic.getCurrentTurn();
						/**Update next player.*/
						if(currentPlayer == 'X') {nextPlayer = 'O'; }
						else {nextPlayer = 'X';}
						MESSAGE.setText("Computer just took turn. The move was " + move + ".\n\nPlayer" + currentPlayer + " - your turn!\nClick one of the column buttons."); /**Prompts next player to take their turn.*/
					}
				}
				moveMade = false; /**moveMade returns back to false.*/
			}
			
		}
		if (logic.isBoardFull() && logic.getCountTurns() >= 42) { /**Checks for a draw if board is full and the 42 turns have been run.*/
			MOVE.setText(""); /**Clears out the MOVE string.*/
			MESSAGE.setText("It's a draw! No winner this time.\nPlease try again!"); /**Sets MESSAGE to announce that its a draw.*/
			}
		move = ""; /**Clears out the MOVE string.*/

	}

	/**
	 * Updates the GUI board after a move has been made.
	 * 
	 * @param pieces is the pieces location on the board
	 */
	public static void updateGuiBoard(char[][] pieces) {
		
		for (int row = 0; row < ROWS; row++) {
			for (int col = 0; col < COLUMNS; col++) {
				
				if (pieces[row][col] == ' ') 
					piece[row][col].setText(" "); 
				else 
					piece[row][col].setText("" + pieces[row][col]); 
			}
		}
	}

}
