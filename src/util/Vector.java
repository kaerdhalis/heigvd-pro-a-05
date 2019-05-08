package util;

public class Vector {
	private double x, y;
	
	/**
	 * Rotation constructor
	 * 
	 * @param v
	 * @param angle
	 */
	public Vector(Vector v, double angle) {
		this(v.x * Math.cos(angle) - v.y * Math.sin(angle),
				v.x * Math.sin(angle) + v.y * Math.cos(angle));
	}
	
	public Vector(double x1, double x2, double y1, double y2) {
		this(x2 - x1, y2 - y1);
	}
	
	public Vector(double x, double y) {
		this.setX(x);
		this.setY(y);
	}
	
	public Vector() {
		this(0, 0);
	}

	/**
	 * Returns the angle in radians formed by the vector 
	 * 
	 * @return the angle in radians formed by the vector
	 */
	public double getAngle(Vector v) {
		double dotProduct = v.x*x + v.y*y;
		double firstNorm = Math.sqrt(x*x + y*y);
		double secondNorm = Math.sqrt(v.x*v.x + v.y*v.y);
		return Math.acos(dotProduct/(firstNorm * secondNorm));
	}
	
	/**
	 * Returns if a vector if contained between two others.
	 * 
	 * @param v1
	 * @param v2
	 * @return
	 */
	public boolean contained(Vector v1, Vector v2) {
		return (v1.getAngle(this) <= v1.getAngle(v2)) && (v2.getAngle(this) <= v2.getAngle(v1));
	}
	
	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}
}
