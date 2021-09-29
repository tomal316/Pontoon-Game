/*
 * A GUI component
 * 
 * Used to change the background color of each grid square once
 * it is clicked.
 * @author Salman Haidri
 */
import javax.swing.*;

import java.awt.*;

public class GridSquare extends JButton
{
	private int xcoord, ycoord;
	
	public GridSquare(int xcoord, int ycoord)
	{
		super();
		this.setSize(40,40);
		this.xcoord = xcoord;
		this.ycoord = ycoord;
	}
	
	// if i = 0, then the custom color is chosen or else orange is chosen
	public void setColor(int i)
	{
		 Color custom = new Color(28, 190, 254);
		 Color colour = (i == 0) ? custom : Color.orange;
	     this.setBackground( colour);
	}
	
	// simple setters and getters
    public void setXcoord(int value)    { xcoord = value; }
    public void setYcoord(int value)    { ycoord = value; }
    public int getXcoord()              { return xcoord; }
    public int getYcoord()              { return ycoord; }
	

}
