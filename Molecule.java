
public class Molecule {
	private Vector position;
	private Vector velocity;
	private double mass;
	private double radius;
	private double viscosity;
	
	public Molecule(Vector initPos, Vector initVel, double newMass, double newRadius, double newViscosity) {
		position = initPos;
		velocity = initVel;
		mass = newMass;
		radius = newRadius;
		viscosity = newViscosity;
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
	
	public void setPosition(Vector newPos) {
		position = newPos;
	}
	
	public void setVelocity(Vector newVel) {
		velocity = newVel;
	}
	
}
