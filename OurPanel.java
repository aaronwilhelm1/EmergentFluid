import java.awt.Graphics;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;

import javax.swing.JPanel;

public class OurPanel extends JPanel{
	
	private World world;
	private GUI gui;
	
	public OurPanel(GUI g, World w){
		gui = g;
		world = w;
		w.setPanel(this);
	}
	
	
	public void paint(Graphics g){
		//super.paintComponent(g);
    	Image offImage = createImage(this.getWidth(), this.getHeight());

    // Creates an off-screen drawable image to be used for

    // double buffering; XSIZE, YSIZE are each of type int 

    Graphics buffer = offImage.getGraphics();

    // Creates a graphics context for drawing to an 

    // off-screen image
    world.paint(buffer);

    g.drawImage(offImage, 0, 0, null); //3, 37 is the offset necessary to keep the image from being drawn
    									//under the header bar of the window
    
	}
	
	public Vector getMouseCoordinates() {
		//int x = MouseInfo.getPointerInfo().getLocation().x - this.getLocationOnScreen().x;
		//int y = MouseInfo.getPointerInfo().getLocation().y - this.getLocationOnScreen().y;
		int x = MouseInfo.getPointerInfo().getLocation().x;
		int y = MouseInfo.getPointerInfo().getLocation().y;
		return new Vector(x, y);
	}
}
