import java.util.*;
import java.lang.Math;
public class World {
    private ArrayList<Molecule> molecules;
    public final int molCount=20;
    public final double gravity=9.8;

    public World() {
        molecules=new ArrayList<Molecule>();
        Vector startPos=new Vector((double)GUI.WINDOW_WIDTH/2.0, (double)GUI.WINDOW_HEIGHT/2.0);
        Vector startVel=new Vector(0.0, 0.0);
        for (int x=0;x<molCount;x++) {
            molecules.add(new Molecule(startpPos, startVel, 1, 1, 1, 1));
        }
    }
    
    public void step(double timeElapsed)
    {
        ArrayList<Molecule> newMols=molecules;
        for (Molecule mol : newMols) {
            //account for accelerations
            double newXVel=mol.getVelocity.getX()+gravity*timeElapsed;
            double newYVel=mol.getVelocity.getY()+gravity*timeElapsed;
            mol.setVelocity(new Velocity(newXVel, newYVel));

            ArrayList<Molecule> neighbors=new ArrayList<Molecule>();
            //find neighbors
            for (Molecule other : molecules) {
                if (interact(mol, other)==0) {
                   collide(mol, other); 
                }
                if (interact(mol, other)==1) {
                    neighbors.add(other); //MAKE SURE NOT TO ADD IF OTHER MOLECULE IS SELF (does it wokr to do if mol!=other ?)
                }
            }
            //check for collisions
            //change velocities based on vicosity
        }
        molecules=newMols; //set list of molecules to updated molecules
    }
    
    public double getDist(Molecule one, Molecule two) {
        double xDist=one.getPosition.getX()-two.getPosition.getX();
        double yDist=one.getPosition.getY()-two.getPosition.getY();
        return hypot(xDist, yDist);
    }
    
    public int interact(Molecule one, Molecule two) {
        double dist=getDist(one,two);
        if (dist<=(one.getRadius()+two.getRadius())) {
            return 0; //collision
        }
        if (dist<=(one.getRange()+two.getRadius())) {
            return 1; //neighbor
        }
        return 2; //out of range
    }
    
    public void collide(Molecule one, Molecule two) {
		Vector initVel = new Vector(one.getVelocity.getX(), one.getVelocity.getY());
		//m1v1 = m2v2, thus v1 = (m2/m1)v2
		
		one.setVelocity(new Vector((two.getMass() / one.getMass()) * one.getVelocity.getX(), (two.getMass() / one.getMass()) * one.getVelocity.getY()));
		two.setVelocity(new Vector((one.getMass() / two.getMass()) * initVel.getX(), (one.getMass() / two.getMass()) * initVel.getY()));
		
	}
        
    public void paint() {
        //go through list and draw each molecule
    }
}
