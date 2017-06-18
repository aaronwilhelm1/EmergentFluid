public class Vector
{
    private double myX;
    private double myY;
    
    public Vector() {
        myX=0.0;
        myY=0.0;
    }
    
    public Vector(double x, double y) {
        myX=x;
        myY=y;
    }
    
    public double getX() {
        return myX;
    }
    
    public double getY() {
        return myY;
    }
    
    public double getMagnitude() {
    	return Math.sqrt((Math.pow(myX, 2) + Math.pow(myY, 2));
    }
    
    public void setX(double newX) {
        myX=newX;
    }
    
    public void setY(double newY) {
        myY=newY;
    }
    
    public void setCoords(double x, double y) {
        myX=x;
        myY=y;
    }
    
    public double dot(Vector other) {
        return myX*other.getX()+myY*other.getY();
    }
}
