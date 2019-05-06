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
		this(v.x * Math.acos(angle) - v.y * Math.asin(angle),
				v.x * Math.asin(angle) + v.y * Math.acos(angle));
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
	public double getAngle() {
		return Math.atan2(y, x);
	}
	
	/**
	 * Returns if a vector if contained between two others.
	 * 
	 * @param v1
	 * @param v2
	 * @return
	 */
	public boolean contained(Vector v1, Vector v2) {
		return (v1.getAngle() <= this.getAngle()) && (v2.getAngle() >= this.getAngle()) ||
				(v2.getAngle() <= this.getAngle()) && (v1.getAngle() >= this.getAngle());
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
