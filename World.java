import java.util.*;
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
                //if other in range and not self, add to neighbors
            }
            //check for collisions
            //change velocities based on vicosity
        }
        molecules=newMols; //set list of molecules to updated molecules
    }
    
    public boolean inRange(Molecule one, Molecule two) {
        double xDist=one.getPosition.getX()-two.getPosition.getX();
        double yDist=one.getPosition.getY()-two.getPosition.getY();
    }
    
    public void paint() {
        //go through list and draw each molecule
    }
}
