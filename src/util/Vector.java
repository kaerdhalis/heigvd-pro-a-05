package util;

public class Vector {
	private double x, y;
	
	/**
	 * Rotation constructor
	 * @param v the original vector
	 * @param angle the angle with which we want to rotate
	 */
	public Vector(Vector v, double angle) {
		this(v.x * Math.cos(angle) - v.y * Math.sin(angle),
				v.x * Math.sin(angle) + v.y * Math.cos(angle));
	}

	/**
	 * Constructor of a vector, taking a starting position and an ending position as parameter.
	 * @param x1 the x coordinate of the starting position
	 * @param x2 the x coordinate of the ending position
	 * @param y1 the y coordinate of the starting position
	 * @param y2 the y coordinate of the ending position
	 */
	public Vector(double x1, double x2, double y1, double y2) {
		this(x2 - x1, y2 - y1);
	}

	/**
	 * Constructor of a 2D vector, taking both coordinate as parameter.
	 * @param x the x coordinate of the vector
	 * @param y the y coordinate of the vector
	 */
	public Vector(double x, double y) {
		this.setX(x);
		this.setY(y);
	}

	/**
	 * Default constructor of a 2D vector
	 */
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

	/**
	 * Getter of the y coordinate
	 * @return the y coordinate of the vector
	 */
	public double getY() {
		return y;
	}

	/**
	 * Setter of the y coordinate of a vector
	 * @param y the new y coordinate
	 */
	public void setY(double y) {
		this.y = y;
	}

	/**
	 * Getter of the x coordinate of a vector
	 * @return the x coordinate of the vector
	 */
	public double getX() {
		return x;
	}

	/**
	 * Setter of the x coordinate of a vector
	 * @param x the new x coordinate
	 */
	public void setX(double x) {
		this.x = x;
	}
}
