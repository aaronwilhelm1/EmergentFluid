
public class Molecule {
	private Vector position;
	private Vector velocity;
	private double mass;
	private double radius;
	private double viscosity; //the amount that neighbors affect it (from 0 to 1)
	private double range; //the radius in which molecules count as neighbors
	
	public Molecule(Vector initPos, Vector initVel, double newMass, double newRadius, double newViscosity, double newRange) {
		position = initPos;
		velocity = initVel;
		mass = newMass;
		radius = newRadius;
		viscosity = newViscosity;
		range = newRange + radius
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
	
	public void setPosition(Vector newPos) {
		position = newPos;
	}
	
	public void setVelocity(Vector newVel) {
		velocity = newVel;
	}
	
	public void collide(Molecule other) {
		Vector initVel = new Vector(velocity.getX(), velocity.getY());
		//m1v1 = m2v2, thus v1 = (m2/m1)v2
		
		velocity = new Velocity((other.getMass() / getMass()) * velocity.getX(), (other.getMass() / getMass()) * velocity.getY());
		other.velocity = new Velocity((getMass() / other.getMass()) * initVel.getX(), (getMass() / other.getMass()) * initVel.getY());
		
	}
	
}
