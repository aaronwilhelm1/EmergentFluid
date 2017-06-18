import java.awt.BorderLayout;
import java.awt.Dimension;
import sun.java2d.*;

import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.Timer;

public class GUI extends JFrame implements ActionListener{
	// instance variables
		
		public static final int TIME_PER_TICK = 55;
		
		public static int WINDOW_WIDTH;
		public static int WINDOW_HEIGHT;
		
		public World world;
		private OurPanel panel;
		
		boolean upPressed;
		boolean downPressed;
		boolean leftPressed;
		boolean rightPressed;
		boolean upReleased;
		
		private int counter;		// counts seconds
		
		/** Creates a new instance of gui_test - sets up GUI */
	    public GUI() {
	        // STEP 1: must call super() first
	        super("Emergent Fluid (a.k.a Wasser Program)");
	        
	        
	        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	        double width = screenSize.getWidth();
	        double height = screenSize.getHeight() - 115;
	        
	        WINDOW_WIDTH = (int)width;
	        WINDOW_HEIGHT = (int) height;
	        
	        world = new World();
	        panel = new OurPanel(this, world);
	        this.getContentPane().add(panel);
	    
	    // STEPS 2-5: not needed here because this example does not
	    // include any GUI "components"...
	        // STEP 2: get content pane and set its layout
	        // STEP 3: construct component(s), such as:     
	        // STEP 4: add all components to the Container;
	        // STEP 5: register any needed event handlers
	        addKeyListener( new KeyHandler());
	        addMouseListener( new MouseClickHandler() );

			// need to load up "soldier" image used below
	        ClassLoader cldr = panel.getClass().getClassLoader();
			
	        // DON'T FORGET TO INCLUDE THIS CODE - otherwise you will not
	        // be able to close your application!!!
	        addWindowListener(new java.awt.event.WindowAdapter() {
	            public void windowClosing(WindowEvent evt) {
	                System.exit(0);
	            }
	        });
	        
	        
	        
	        // STEP 6: set window size and show window
	        
	        setVisible(true);
	        this.setResizable(false);
	        
	        panel.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
	        //panel.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
	        panel.setVisible(true);
	        this.pack();
	        //this.setLocation((int)(width/2-this.getSize().width/2), (int)(height/2-this.getSize().height/2));
	        this.setLocation((int)(width/2-WINDOW_WIDTH/2), (int)(height/2-WINDOW_HEIGHT/2));
	        // construction a Swing timer that goes off every 1000 msec (1 sec)
	        
	        Timer timer = new javax.swing.Timer(TIME_PER_TICK, this);
	        timer.start();		// timer starts here
	        
	    }
	    
	    
	     public void paint(Graphics g )
	     {
	     	//super.paint(g);
	    	 panel.paint(g);

	     }

		// this method is called each time the timer goes off     
		public void actionPerformed(ActionEvent evt) 
		{
			repaint();
			// counting the seconds	
			counter++;
		}
		
		 /******** PRIVATE INNER CLASSES FOR EVENT HANDLING ***************/
	    /*
	     * - Provide MouseListener event handlers for mouse events
	     * - Provide KeyListener event handlers for key events
	     *
	     */
	    private class KeyHandler implements KeyListener {
	        // the following 3 methods need to be provided in order to
	        // implement the KeyListener interface: 
	        // keyPressed(), keyReleased(), keyTyped();
	        // if you don't need some of these methods, leave method body
	        // empty
	        
	        public void keyPressed ( KeyEvent e )
	        {
	        	/*if(state == State.GAME) {
	        		world.keyPressed(e);
	        	}*/
	            
	            if (e.getKeyCode() == KeyEvent.VK_ESCAPE){
	            	System.exit(0);
	            }
	            		
	        }
	        
	        public void keyReleased (KeyEvent e )
	        {
	        	/*if(state == State.GAME) {
	        		world.keyReleased(e);
	        	}
	        	else if (state == State.PLAYER_SELECTION) {
	        		playerSelection.keyReleased(e);
	        	}
	        	else if (state == State.MAP_SELECTION) {
	        		mapSelection.keyReleased(e);
	        	}*/
	            // called when key is released after a keyPressed or keyTyped event
	        }
	        

	        
	        public void keyTyped (KeyEvent event )
	        {
	            // only responds to pressing "non-action" keys; (action keys
	            // include arrow key, Home, etc)
	        }
	        
	    }   // end KeyHandler

//	    private class MouseClickHandler implements MouseListener
	            // the following 5 methods are required to implement MouseListener:
	            // mousePressed(), mouseClicked(), mouseReleased(),
	            // mouseEntered(), mouseExited(); to avoid having to provide
	            // all 5, you can extend MouseAdapter instead, and override
	            // only those of the 5 methods that you really need
	    private class MouseClickHandler extends MouseAdapter
	    {
	        
	        public void mousePressed( MouseEvent event)
	        {
	        	if(world.moleculeIsThere(new Vector(event.getX(), event.getY()))){
	        		//drag the molecule around
	        		world.moleculeIsBeingHeld(true, new Vector(event.getX(), event.getY()));
	        	} else {
	        		//we'll try to add a new Molecule there
	        		world.addMolecule(new Molecule(new Vector(event.getX(), event.getY()), new Vector(0,0), 10, 10, 10, 10));
	        	}
	        	
	        }
	        
	        public void mouseReleased( MouseEvent event)
	        {	
	        	world.moleculeIsBeingHeld(false, new Vector());
	        }
	    }   // end MouseClickHandler
	        
	
	     	
	    public static void main(String[] args) {
	        GUI application = new GUI();
	    }
}
