import javax.imageio.ImageIO;
import javax.swing.*;

import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.awt.*;
import java.awt.geom.*;

/**
 * @author Lilah Koudelka
 * @author Elizabeth Burke
 * @version March 27, 2017 Sprint 3
 */
public class BoardPanel extends JPanel 
{
	private static final long serialVersionUID = 1L;
	private static final int FRAME_WIDTH = 690;   
	private static final int FRAME_HEIGHT = 690;
	private static final int TOKEN_RADIUS = 8;   
	private static final Color[] PLAYER_COLORS = {Color.RED, Color.CYAN, Color.ORANGE, Color.GREEN, Color.MAGENTA, Color.WHITE};
	private static final String[] TOKEN_NAME = {"red", "cyan", "orange", "green", "magenta", "white"};
	private static final float[] PLAYER_OFFSET = {0, 0.01f, 0.02f, 0.03f, 0.04f, 0.05f}; //ensure players won't overlap
	private static final float[][] CORNER_FROM = { {650, 670}, {5, 640}, {30, 5}, {670, 30} };
	private static final float[][] CORNER_TO = { {40, 670}, {5, 40}, {640, 5}, {670, 640} };

	private Players players;	
	private BufferedImage boardImage;
	private int[][][] squareCoords = new int [Board.NUM_SQUARES][Players.MAX_NUM_PLAYERS][2];

	public BoardPanel(Players players) 
	{
		this.players = players;
		setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
		setBackground(Color.BLACK);

		try 
		{
			boardImage = ImageIO.read(new File("Monopoly.jpg"));
		} 
		catch(IOException ex) 
		{
			System.out.println("Could not find the image file " + ex.toString());
		}

		int sideLength = Board.NUM_SQUARES/4;
		for(int s = 0; s < Board.NUM_SQUARES; s++) 
		{
			for(int p = 0; p < Players.MAX_NUM_PLAYERS; p++) 
			{
				int side = (int) s / sideLength;
				float offset = (float) (s % sideLength) / sideLength + PLAYER_OFFSET[p];
				squareCoords[s][p][0] = Math.round(CORNER_FROM[side][0] + offset * (CORNER_TO[side][0] - CORNER_FROM[side][0]));
				squareCoords[s][p][1] = Math.round(CORNER_FROM[side][1] + offset * (CORNER_TO[side][1] - CORNER_FROM[side][1]));
			}
		}
		return;
	}

	public void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(boardImage, 0, 0, FRAME_WIDTH, FRAME_HEIGHT, this);
        for(int p=0; p<players.numPlayers(); p++) 
        {
	        int square = players.get(p).getPosition();
	        g2.setColor(Color.BLACK);
            Ellipse2D.Double outline = new Ellipse2D.Double(squareCoords[square][p][0],squareCoords[square][p][1],2*TOKEN_RADIUS,2*TOKEN_RADIUS);
            g2.fill(outline);
            Ellipse2D.Double ellipse = new Ellipse2D.Double(squareCoords[square][p][0]+1,squareCoords[square][p][1]+1,2*TOKEN_RADIUS-2,2*TOKEN_RADIUS-2);
            int tokenId = players.get(p).getTokenID();
            g2.setColor(PLAYER_COLORS[tokenId]);
            g2.fill(ellipse);
        }
		return;
	}

	public void refresh() 
	{
		revalidate();
		repaint();
		return;
	} 

	public String getTokenName(int tokenID)
	{
		return TOKEN_NAME[tokenID];
	}
}