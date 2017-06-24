import java.util.*;
import java.awt.Color;
import java.awt.Graphics;
import java.lang.Math;
public class World {
    private ArrayList<Molecule> molecules;
    public final int MOLCOUNT=100;
    public final double GRAVITY=10;
    public final int COLLISION=0;
    public final int NEIGHBOR=1;
    public final int OUT_OF_RANGE=2;
    private static int ID=0;
    private long time;
    private int molIDBeingHeld;
    private OurPanel panel;
    private boolean drawDebug = true;
    
    public World() {
        molecules=new ArrayList<Molecule>();
        int x = 20;
        int y = (int) (GUI.WINDOW_HEIGHT/2.0);
        Vector startVel=new Vector(0.0, 0.0);
        int numOfMols = 0;
        while((x + 10) < GUI.WINDOW_WIDTH && numOfMols < MOLCOUNT) {
            molecules.add(new Molecule(new Vector(x, y), startVel, 10, 10, 0.01, 100));
            x += 25; //twice the radius and a little extra
            numOfMols++;
        }
        time = System.currentTimeMillis();
        molIDBeingHeld = -1;
    }
    
    public void step(long timeElapsed)
    {
        ArrayList<Molecule> newMols=new ArrayList<Molecule>(); //list of molecules to be edited
        
        for (Molecule old : molecules) {
            newMols.add(new Molecule(old.getPosition(), old.getVelocity(), old.getMass(), old.getRadius(), old.getViscosity(), old.getRange() - old.getRange(), old.getId()));
        }
        
        for (Molecule mol : newMols) {
            //account for accelerations
            double newYVel=mol.getVelocity().getY()+GRAVITY*((double)timeElapsed/1000);
            mol.setVelocity(new Vector(mol.getVelocity().getX(), newYVel));

            ArrayList<Molecule> neighbors=new ArrayList<Molecule>();
            //find neighbors
            for (Molecule other : molecules) {
                if (other.getId() != mol.getId()) {//not same molecule
                    if (interact(mol, other)==COLLISION) { //check for collisions
                        collide(mol, other); 
                    } else if (interact(mol, other)==NEIGHBOR) { //check for nearby molecules
                        neighbors.add(other); 
                    }
                }
            }
            
            //update velocity based on neighbors
            for (Molecule neighbor : neighbors) {
              
                //find projection of neighbor's velocity vector onto distance vector between mol and neighbor
                double xDiff = neighbor.getPosition().getX()-mol.getPosition().getX();
                double yDiff = neighbor.getPosition().getY()-mol.getPosition().getY();
              
                Vector dist = new Vector(xDiff, yDiff);
                
                double dotvd = neighbor.getVelocity().dot(dist);
                if (dotvd<0) {
                    dotvd = -dotvd;
                }
               
                double distsquared = dist.getMagnitude() * dist.getMagnitude();
                
                double factor = mol.getViscosity() * (dotvd/distsquared);
                
                //multiply the distance vector by the factor
                double xVelChange = 0.75 * factor * xDiff ;
                double yVelChange = 0.75 * factor * yDiff;
                
                mol.setVelocity(new Vector(mol.getVelocity().getX()+xVelChange, mol.getVelocity().getY()+yVelChange));
            }
            
            //check for collision with wall
            double collideXVel= mol.getVelocity().getX();
            double collideYVel=mol.getVelocity().getY();
            if (mol.getPosition().getX() <= mol.getRadius() || mol.getPosition().getX() >= GUI.WINDOW_WIDTH-mol.getRadius()) {
                collideXVel=mol.getVelocity().getX()*(-0.75);
            }
            if (mol.getPosition().getY() <= mol.getRadius() || mol.getPosition().getY() >= GUI.WINDOW_HEIGHT-mol.getRadius()) {
                collideYVel=mol.getVelocity().getY()*(-0.75);
            }
            mol.setVelocity(new Vector(collideXVel, collideYVel));
            //change velocities based on viscosity
            
            //update position
            double newXPos=mol.getPosition().getX()+mol.getVelocity().getX()*((double)timeElapsed/1000);
            double newYPos=mol.getPosition().getY()+mol.getVelocity().getY()*((double)timeElapsed/1000);
            mol.setPosition(new Vector(newXPos, newYPos));
        }
        
        //if a molecule is being held, set its position and velocity to that of the mouse
        if(molIDBeingHeld != -1) {
            Molecule m = null;
            for (Molecule searching : newMols) {
                if(searching.getId() == molIDBeingHeld) {
                    m = searching;
                    break;
                }
            }
            
            Vector mousePos = panel.getMouseCoordinates();
            Vector vel = new Vector(mousePos.getX() - m.getPosition().getX(), mousePos.getY() - m.getPosition().getY());
            m.setPosition(mousePos);
            m.setVelocity(vel);
        }
        
        molecules=newMols; //set list of molecules to updated molecules
    }
    
    public double getDist(Molecule one, Molecule two) {
        double xDist=one.getPosition().getX()-two.getPosition().getX();
        double yDist=one.getPosition().getY()-two.getPosition().getY();
        return (new Vector(xDist, yDist)).getMagnitude();
    }
    
    public int interact(Molecule one, Molecule two) {
        double dist=getDist(one,two);
        if (dist<=(one.getRadius()+two.getRadius())) {
            return COLLISION; 
        }
        if (dist<=(one.getRange()+two.getRadius())) {
            return NEIGHBOR; 
        }
        return OUT_OF_RANGE; 
    }
    
    public void collide(Molecule one, Molecule two) {
        Vector initVel = new Vector(one.getVelocity().getX(), one.getVelocity().getY());
        //m1v1 = m2v2, thus v1 = (m2/m1)v2
        
        one.setVelocity(new Vector((two.getMass() / one.getMass()) * two.getVelocity().getX(), (two.getMass() / one.getMass()) * two.getVelocity().getY()));
        two.setVelocity(new Vector((one.getMass() / two.getMass()) * initVel.getX(), (one.getMass() / two.getMass()) * initVel.getY()));
        
    }
    
    //returns true if you could add a molecule
    public boolean addMolecule(Molecule m) {
        for(Molecule mol : molecules) {
            if(getDist(m, mol) < (m.getRadius() + mol.getRadius())) {
                return false;
            }
        }
        //By here there were no collisions so go ahead and add the molecule
        molecules.add(m);
        return true;
    }
    
    public boolean moleculeIsThere(Vector v) {
        for(Molecule mol : molecules) {
            double dist = (new Vector((v.getX() - mol.getPosition().getX()), (v.getY() - mol.getPosition().getY()))).getMagnitude();
            if(dist < (mol.getRadius())) {
                return true;
            }
        }
        return false;
    }
    
    public void moleculeIsBeingHeld(boolean var, Vector v) {
        if(var == false){
            molIDBeingHeld = -1;
        } else {
            for(Molecule mol : molecules) {
                double dist = (new Vector((v.getX() - mol.getPosition().getX()), (v.getY() - mol.getPosition().getY()))).getMagnitude();
                if(dist < (mol.getRadius())) {
                    molIDBeingHeld = mol.getId();
                    break;
                }
            }
        }
    }
    
    public void setViscosities(double newVis) {
    	for( Molecule mol : molecules) {
    		mol.setViscosity(newVis);
    	}
    }
    
    public void setPanel(OurPanel p) {
        panel = p;
    }
        
    public void paint(Graphics g) {
        //first step the simulation
        long difference = System.currentTimeMillis()-time;
        time = System.currentTimeMillis();
        step(difference);
        
        //go through list and draw each molecule
        for (Molecule mol : molecules) {
            int diameter = (int)(2 * mol.getRadius());
            g.setColor(Color.BLACK);
            g.drawOval((int)(mol.getPosition().getX() - mol.getRadius()), (int)(mol.getPosition().getY() - mol.getRadius()), diameter, diameter);
            if(drawDebug) {
            	g.setColor(Color.BLUE);
            	int rdiameter = (int)(2* mol.getRange());
            	g.drawOval((int)(mol.getPosition().getX() - mol.getRange()), (int)(mol.getPosition().getY()- mol.getRange()), rdiameter, rdiameter);
            }
        }
    }
}
