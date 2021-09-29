/*
 * The main window of the GUI.
 * It extends JFrame to add components into the frame.
 * We also implement event handling in the form of ActionListener to handle operations performed by user.
 * We also use MouseListener to handle operations performed by user using mouse.
 * @author Salman Haidri
 */
import java.awt.*;
import java.awt.event.*;
import java.io.Serializable;

import javax.swing.*;

import java.util.Random;

public class GameWindow extends JFrame implements ActionListener, MouseListener, Serializable
{
	// Field variables that form components and facilitate in making the frame 
	private JPanel topPanel, bottomPanel;			// top and bottom panels of the window 
	private JLabel instructionLabel;				// text label for instructions to player
	private JLabel currentScore, score;				// text label to display 'Current' label and current score 
	private JButton newGameButton, saveButton, loadButton;					// 'New Game' button that appears in the top panel
	private GridSquare [][] gridSquares;			// squares top appear in grid formation in the bottom panel
	private int rows, columns;						// size of the grid
	private int temp, total, index;					// Temporary variables to assign random values and score 
	private Random rand = new Random();				// random values for the grid
	private String curPlayer;						// the current player 
	private String[] player = new String[] {"Player 1", "Player 2"}; 	// Array with both the players
	FileActions fileActions;
	
	
	/*
	 * Constructor that takes the size of the grid as the input.
	 * Top and bottom panels are created and components are added to them, and they are added to the frame
	 * It also ensures that the GUI is visible.
	 * It also adds the actionListener to the 'New Game' button.
	 */
	public GameWindow(int rows, int columns)
	{
		this.rows = rows;
		this.columns = columns;
		this.setSize(500, 500);
		
		// create panels
		topPanel = new JPanel();
		topPanel.setLayout(new FlowLayout());
		
		bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridLayout(rows, columns));
		bottomPanel.setSize(400,400);
		
		// components of the top panel
		// Icon icon = new ImageIcon("C:\\Users\\engineering\\Desktop\\Malefiz Group Project\\playstation-cross-dark-icon.png");
		// newGameButton = new JButton(icon);
		newGameButton = new JButton("New Game");
		newGameButton.addActionListener(this);		// ActionListener for 'New Game' button
		
		saveButton = new JButton("Save");
		saveButton.addActionListener(this);
		
		loadButton = new JButton("Load");
		loadButton.addActionListener(this);
		instructionLabel = new JLabel("Keep the total below 22. Click 'New Game' to begin.");
		currentScore = new JLabel("Current:");
		score = new JLabel("-");
		
		// adds components to the top panel
		topPanel.add(newGameButton);
		topPanel.add(saveButton);
		topPanel.add(loadButton);
		topPanel.add(instructionLabel);
		topPanel.add(currentScore);
		topPanel.add(score);
		
		fileActions = new FileActions();
		
		// Create squares and add them top the bottom panel
		gridSquares = new GridSquare[rows][columns];
		for (int x = 0; x < columns; x++)
		{
			for (int y = 0; y < rows; y++)
			{
				gridSquares[x][y] = new GridSquare(x,y);
				gridSquares[x][y].setSize(20, 20);				// size of each square
				gridSquares[x][y].setText("-");					// sets initial text in the squares to '-'
				gridSquares[x][y].setBorder(BorderFactory.createLineBorder(Color.lightGray, 2)); // adds a 2 pixel border in the grid
				gridSquares[x][y].setEnabled(false);			// disables the squares before the game starts.
				
				bottomPanel.add(gridSquares[x][y]);				// adds the squares to the bottom panel
			}
		}
		
		// add the top and bottom panel to the main frame with border layout
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(topPanel, BorderLayout.NORTH);
		getContentPane().add(bottomPanel, BorderLayout.CENTER);
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // ends the program once the window has closed
		setResizable(false);							// restricts the size of the window
		setVisible(true);								// makes the frame visible
		
	}
	
	/*
	 *  method to randomly select a player to start the game
	 *  @ return curPlayer the player who has to take the first move
	 */
	public String getRandom()
	{
		index = new Random().nextInt(player.length); // keeps the track of index of current player
		curPlayer = getPlayer();
		return curPlayer;
	}
	
	/*
	 * method to get player for each round
	 * @return player[index] the player who will play the next round 
	 */
	public String getPlayer()
	{
		if (index == 0)
		{
			return player[0];
		}
		else {
			return player[1];
		}
	}
	
	/*
	 * Handles the event when 'New Game' button is clicked
	 */
	public void actionPerformed(ActionEvent aevt)
	{
		// get the object that was selected in the GUI
		Object selected = aevt.getSource();
		
		// if the 'New Game' button is clicked then these actions are performed
		if ( selected.equals(newGameButton) )
		{
			total = 0;		// makes the total from previous game 0
			for ( int x = 0; x < columns; x++)
			{
				for ( int y = 0; y < rows; y++)
				{
					gridSquares[x][y].removeMouseListener(this);		// removes the mouseListener to handle the error of multiple clicks
					gridSquares[x][y].setEnabled(true);					// enables the square for input
					temp = rand.nextInt(5)+1;							// selects a random value between 1 and 5
					gridSquares[x][y].setText(String.valueOf(temp));	// puts the random value in the grid
					gridSquares[x][y].setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
					gridSquares[x][y].addMouseListener(this);			// adds the mouseListener to the grid 
					gridSquares[x][y].setBackground(Color.white);		// sets the background color of the grid to white
				}
			}
			
			instructionLabel.setText(getRandom() + "'s turn...");		// sets the instruction to display the current player
			score.setText("0");											// sets the current score to zero

		}
		
		if(selected.equals(saveButton)) {
			fileActions.addObject(this);
			fileActions.saveGame();
		}
		
		if(selected.equals(loadButton)) {
			this.dispose();
			fileActions.loadGame();
		}
	}
	
	/*
	 * Handles the event when a grid square is clicked
	 */
	public void mouseClicked(MouseEvent mevt)
	{
		// get the object that was selected in the GUI
		Object selected = mevt.getSource();
		
		// if a grid square is clicked then these actions are performed
		if ( selected instanceof GridSquare)
		{
			GridSquare square = (GridSquare) selected;
			total += Integer.parseInt(square.getText()); 	// adds the integer in the grid to the total
			score.setText(String.valueOf(total));			// sets the current to the total
			square.removeMouseListener(this);				// removes mouseListener from that grid
			square.setEnabled(false);						// disables the selected grid
			square.setColor(index);							// sets the color according to the player
		}
		
		// if index is 0 then change it to 1 for Player 2 to play else Player 1 will play
		if (index == 0)
		{
			index = 1;
			curPlayer = getPlayer();
			instructionLabel.setText(curPlayer + "'s turn...");	
		}
		else
		{
			index = 0;
			curPlayer = getPlayer();
			instructionLabel.setText(curPlayer + "'s turn...");
		}
		
		// if total is greater than 21 then it checks for the player who won
		if (total > 21)
		{
		
			if (index == 0)
			{
				instructionLabel.setText(getPlayer() + " Wins!!");
				for ( int x = 0; x < columns; x++)
				{
					for ( int y = 0; y < rows; y++)
					{
						gridSquares[x][y].setEnabled(false);			// Once winner is declared, grid is disabled
						gridSquares[x][y].removeMouseListener(this);	// and mouseListener from that grid is removed
					}
				}
			}
			else {
				instructionLabel.setText(getPlayer() + " Wins!!");
				for ( int x = 0; x < columns; x++)
				{
					for ( int y = 0; y < rows; y++)
					{
						gridSquares[x][y].setEnabled(false);
						gridSquares[x][y].removeMouseListener(this);
					}
				}
			}
		}
	}
	
	// Necessary to write when using mouseListener even when they are not used
	public void mouseEntered(MouseEvent arg0){}
	public void mouseExited(MouseEvent arg0) {}
	public void mousePressed(MouseEvent arg0) {}
	public void mouseReleased(MouseEvent arg0) {}

}
