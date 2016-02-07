package particles;

import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.geom.*;

public class BasicParticle extends Particle {
	public static final int MIN_BOUNCE_THRESHOLD_Y = 5;
	public static final int MIN_FRICTION_THRESHOLD_X = 1;
	
	public static final Color GLOW_BLUE = new Color(0, 0, 255, 128);
	
	protected double accelX = 10;
	protected double accelY = 5;
	protected double bounce = Particle.DEFAULT_BOUNCE;
	protected boolean on_ground = false;
	
	public BasicParticle(int x, int y, int mass){
		super(x,y,mass);
	}
	public BasicParticle(int x, int y){
		this(x, y, 500);
	}
	
	@Override
	public int getWidth(){
		return getRadius() * 2;
	}
	@Override
	public int getHeight(){
		return getRadius() * 2;
	}
	
	private void update(){
		//Basic boundary detection
		if( x + accelX + getWidth() > ParticleApp.WIDTH ){
			double pAccelX = -accelX;
			accelX = pAccelX;
			x = ParticleApp.WIDTH - (getRadius() * 2);
		}
		if( y + accelY + getHeight() > ParticleApp.HEIGHT ){
			double pAccelY = (-( bounce * accelY ));
			if( Math.abs(pAccelY) < BasicParticle.MIN_BOUNCE_THRESHOLD_Y ){
				accelY = 0;
				on_ground = true;
			}else{
				accelY = pAccelY;
			}
			y = ParticleApp.HEIGHT - (getRadius() * 2);
		}
		if( x + accelX < 0 ){
			double pAccelX = -accelX;
			accelX = pAccelX;
			x = 0;
		}
		if( y + accelY < 0 ){
			double pAccelY = (-( bounce * accelY ));
			accelY = (Math.abs(pAccelY) >= BasicParticle.MIN_BOUNCE_THRESHOLD_Y)?pAccelY:0;
			y = 0;
		}
		//End basic boundary detection
		
		//Normal Update Procedure
		if( accelY < Particle.GRAVITY_THRESHOLD && !on_ground ){
			if( accelY + Particle.GRAVITY > Particle.GRAVITY_THRESHOLD ){
				accelY = Particle.GRAVITY_THRESHOLD;
			}else{
				accelY += Particle.GRAVITY;
			}
		}
		
		int old_y = y;
		y += (int)Math.floor(accelY);
		if( old_y != y ){
			on_ground = false;
		}
		if( on_ground ){
			accelX *= Particle.FRICTION;
		}
		if( Math.abs(accelX) < BasicParticle.MIN_FRICTION_THRESHOLD_X ){
			accelX = 0;
		}
		x += (int)Math.floor(accelX);
	}
	
	@Override
	public void onCollision(WorldObject o, WorldAccessor accessor){
		if( o instanceof BasicParticle ){
			BasicParticle other = (BasicParticle)o;
			setMass( mass + other.mass );
			other.setMass(0);
			accessor.remove(other);
			accelX = (mass * accelX) / (mass + other.mass);
			accelY = (mass * accelY) / (mass + other.mass);
		}
	}
	
	@Override
	public void draw(Graphics2D gfx) {
		if( getRadius() <= 0 ) return;
		update();
		gfx.setColor( Color.BLUE );
		Ellipse2D circle = new Ellipse2D.Double( x, y, (getRadius() * 2), (getRadius() * 2) );
		gfx.setStroke(new BasicStroke((getRadius() / 5) * 4));
		gfx.setColor( Color.BLACK );
		RadialGradientPaint rgp = new RadialGradientPaint( new Point2D.Double(x + getRadius(), y + getRadius()),
																getRadius(),
																new float[] { .2f, 1f },
																new Color[] { BasicParticle.GLOW_BLUE, Color.BLUE }  );
		gfx.setPaint(rgp);
		gfx.fill(circle);
		
	}
	@Override
	public boolean inRange(Point2D p) {
		double distance = p.distance(new Point2D.Double(x + getRadius(), y + getRadius()));
		if( distance <= getRadius() ){
			return true;
		}
		return false;
	}
	
	public Line2D[] getSides(){ return null; }
}
