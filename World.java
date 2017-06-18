import java.util.*;
import java.awt.Graphics;
import java.lang.Math;
public class World {
    private ArrayList<Molecule> molecules;
    public final int MOLCOUNT=20;
    public final double GRAVITY=9.8;
    public final int COLLISION=0;
    public final int NEIGHBOR=1;
    public final int OUT_OF_RANGE=2;
    private static int ID=0;
    private long time;
    private int molIDBeingHeld;
    
    public World() {
        molecules=new ArrayList<Molecule>();
        int x = 20;
        int y = (int) (GUI.WINDOW_HEIGHT/2.0);
        Vector startVel=new Vector(0.0, 0.0);
        int numOfMols = 0;
        while((x + 10) < GUI.WINDOW_WIDTH && numOfMols < MOLCOUNT) {
        	molecules.add(new Molecule(new Vector(x, y), startVel, 10, 10, 10, 10));
        	x += 25; //twice the radius and a little extra
        }
        time = System.currentTimeMillis();
        molIDBeingHeld = -1;
    }
    
    public void step(long timeElapsed)
    {
        ArrayList<Molecule> newMols=new ArrayList<Molecule>(); //list of molecules to be edited
        
        for (Molecule old : molecules) {
            newMols.add(new Molecule(old.getPosition(), old.getVelocity(), old.getMass(), old.getRadius(), old.getViscosity(), old.getRange(), old.getId()));
        }
        
        for (Molecule mol : newMols) {
            //account for accelerations
            double newXVel=mol.getVelocity().getX()+GRAVITY*timeElapsed;
            double newYVel=mol.getVelocity().getY()+GRAVITY*timeElapsed;
            mol.setVelocity(new Vector(newXVel, newYVel));

            ArrayList<Molecule> neighbors=new ArrayList<Molecule>();
            //find neighbors
            for (Molecule other : molecules) {
                if (other.getId() != mol.getId()) {//not same molecule
                    if (interact(mol, other)==COLLISION) { //check for collisions
                        collide(mol, other); 
                    }
                    if (interact(mol, other)==NEIGHBOR) { //check for nearby molecules
                        neighbors.add(other); 
                    }
                }
            }
            //change velocities based on vicosity
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
		
		one.setVelocity(new Vector((two.getMass() / one.getMass()) * one.getVelocity().getX(), (two.getMass() / one.getMass()) * one.getVelocity().getY()));
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
        
    public void paint(Graphics g) {
    	//first step the simulation
    	long difference = time - System.currentTimeMillis();
    	time = System.currentTimeMillis();
    	step(difference);
    	
        //go through list and draw each molecule
    	for (Molecule mol : molecules) {
    		int diameter = (int)(2 * mol.getRadius());
    		g.drawOval((int)(mol.getPosition().getX() - mol.getRadius()), (int)(mol.getPosition().getY() - mol.getRadius()), diameter, diameter);
    		
    	}
    }
}
