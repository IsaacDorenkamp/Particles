package particles;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Line2D;

public class SpawnMarker extends Particle{
	protected int start_x;
	protected int start_y;
	protected int end_x;
	protected int end_y;
	public SpawnMarker(int sx, int sy, int ex, int ey){
		start_x = sx;
		start_y = sy;
		end_x = ex;
		end_y = ey;
	}
	public SpawnMarker(){
		this(-1,-1,-1,-1);
	}
	@Override
	public void onCollision(WorldObject other, WorldAccessor accessor) {}
	public boolean inRange(Point2D p){
		return false; //Never collide
	}
	@Override
	public void draw(Graphics2D gfx) {
		gfx.setColor(Color.BLACK);
		Line2D l2d = new Line2D.Double(start_x, start_y, end_x, end_y);
		gfx.draw(l2d);
	}
	@Override
	public int getWidth() {
		return -1;
	}
	@Override
	public int getHeight() {
		// TODO Auto-generated method stub
		return -1;
	}
	public Line2D[] getSides(){
		return new Line2D[]{ new Line2D.Double(start_x, start_y, end_x, end_y) };
	}
}
