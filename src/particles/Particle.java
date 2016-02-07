package particles;

import java.awt.*;

public abstract class Particle extends WorldObject{
	public static final double DEFAULT_BOUNCE = 0.75; //Percentage of bounce back
	public static final int GRAVITY = 1;
	public static final int GRAVITY_THRESHOLD = 25;
	public static final double FRICTION = 0.95;
	
	protected int mass;
	private int radius;
	public Particle(int x, int y, int mass){
		super(x,y);
		this.mass = mass;
		this.radius = (int)(Math.floor(Math.sqrt(mass / Math.PI)));
	}
	public Particle(){
		this(0, 0, 500);
	}
	protected void setMass(int mass){
		this.mass = mass;
		this.radius = (int)(Math.floor(Math.sqrt(mass / Math.PI)));
	}
	public int getMass(){
		return mass;
	}
	public int getRadius(){
		return radius;
	}
	
	public abstract void draw( Graphics2D gfx );
	public abstract int getWidth();
	public abstract int getHeight();
	
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	
	public void paint(Graphics gfx){
		Graphics2D gfx2d = (Graphics2D) gfx;
		draw(gfx2d);
	}
}
