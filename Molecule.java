
public class Molecule {
	private Vector position;
	private Vector velocity;
	private double mass;
	private double radius;
	private double viscosity; //the amount that neighbors affect it (from 0 to 1)
	private double range; //the distance between boundaries in which molecules count as neighbors
	private int id;
	
	public static final double MIN_VISCOSITY = 0;
	public static final double MAX_VISCOSITY = 0.2;
	
	private static int classID = 0;
	
	public Molecule(Vector initPos, Vector initVel, double newMass, double newRadius, double newViscosity, double newRange, int newId) {
		position = initPos;
		velocity = initVel;
		mass = newMass;
		radius = newRadius;
		viscosity = newViscosity;
		range = newRange + radius;
		id = newId;
	}
	
	public Molecule(Vector initPos, Vector initVel, double newMass, double newRadius, double newViscosity, double newRange) {
		position = initPos;
		velocity = initVel;
		mass = newMass;
		radius = newRadius;
		viscosity = newViscosity;
		range = newRange + radius;
		id = classID;
		classID++;
	}
	
	public Vector getPosition() {
		return position;
	}
	
	public Vector getVelocity() {
		return velocity;
	}
	
	public double getMass() {
		return mass;
	}
	
	public double getRadius() {
		return radius;
	}
	
	public double getViscosity() {
		return viscosity;
	}
	
	public double getRange() {
		return range;
	}
	
	public int getId() {
	    return id;
	}
	
	public void setPosition(Vector newPos) {
		position = newPos;
	}
	
	public void setVelocity(Vector newVel) {
		velocity = newVel;
	}
	
	public void setViscosity(double newVis) {
		viscosity = newVis;
	}
	
}
