package particles;

import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

public abstract class WorldObject {
	private static int CURRENT_OBJECT_ID = 0;
	protected int x;
	protected int y;
	protected boolean collidable = true;
	private int id;
	
	public WorldObject(int x, int y){
		this.x = x;
		this.y = y;
		this.id = WorldObject.CURRENT_OBJECT_ID++;
	}
	
	public abstract void draw( Graphics2D gfx );
	
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	public int getID(){
		return id;
	}
	
	public boolean getCollidable(){
		return collidable;
	}
	
	public abstract int getWidth();
	public abstract int getHeight();
	public abstract void onCollision( WorldObject other, WorldAccessor accessor );
	public abstract boolean inRange(Point2D p);
	public abstract Line2D[] getSides();
}
