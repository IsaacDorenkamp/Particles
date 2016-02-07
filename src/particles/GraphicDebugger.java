package particles;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

public class GraphicDebugger {
	public static final void DrawSAT( Graphics2D g2d, WorldObject wo, WorldObject wo2 ){
		if( wo2 instanceof BasicParticle && wo instanceof BasicParticle ){
			BasicParticle bp = (BasicParticle)wo2;
			BasicParticle bp2 = (BasicParticle)wo;
			
			GridPoint c1 = new GridPoint(bp.getX() + bp.getWidth() / 2, bp.getY() + bp.getHeight() / 2  );
			GridPoint c2 = new GridPoint(bp2.getX() + bp2.getWidth() / 2, bp2.getY() + bp2.getHeight() / 2  );
			Line2D axis = new Line2D.Double( c1, c2 );
			double angle = Math.atan2((axis.getY2() - axis.getY1()), (axis.getX2() - axis.getX1()));
			double c1px = bp.getRadius() * Math.cos(angle) + c1.getX();
			double c1py = bp.getRadius() * Math.sin(angle) + c1.getY();
			double c2px = bp.getRadius() * -Math.cos(angle) + c2.getX();
			double c2py = bp.getRadius() * -Math.sin(angle) + c2.getY();
			
			Line2D horiz = new Line2D.Double( 0, c2py, ParticleApp.WIDTH, c2py );
			Line2D vert  = new Line2D.Double( c2px, 0, c2px, ParticleApp.HEIGHT );
			Line2D horiz2 = new Line2D.Double( 0, c1py, ParticleApp.WIDTH, c1py );
			Line2D vert2  = new Line2D.Double( c1px, 0, c1px, ParticleApp.HEIGHT );
			g2d.setPaint(Color.BLACK);
			g2d.setStroke(new BasicStroke(1));
			g2d.draw(horiz);
			g2d.draw(vert);
			g2d.draw(horiz2);
			g2d.draw(vert2);
		}
	}
	public static final void DrawSAT_Middle( Graphics2D g2d, WorldObject wo, WorldObject wo2 ){
		if( wo2 instanceof BasicParticle && wo instanceof BasicParticle ){
			BasicParticle bp = (BasicParticle)wo2;
			BasicParticle bp2 = (BasicParticle)wo;
			
			GridPoint c1 = new GridPoint(bp.getX() + bp.getWidth() / 2, bp.getY() + bp.getHeight() / 2  );
			GridPoint c2 = new GridPoint(bp2.getX() + bp2.getWidth() / 2, bp2.getY() + bp2.getHeight() / 2  );
			Line2D axis = new Line2D.Double( c1, c2 );
			double angle = Math.atan2((axis.getY2() - axis.getY1()), (axis.getX2() - axis.getX1()));
			double c1px = bp.getRadius() * Math.cos(angle) + c1.getX();
			double c1py = bp.getRadius() * Math.sin(angle) + c1.getY();
			double c2px = bp.getRadius() * -Math.cos(angle) + c2.getX();
			double c2py = bp.getRadius() * -Math.sin(angle) + c2.getY();
			
			Point2D middle = new Point2D.Double( (c1px + c2px) / 2, (c1py + c2py) / 2 );
			g2d.setPaint(Color.BLACK);
			g2d.setStroke(new BasicStroke(1));
			g2d.draw( new Line2D.Double( middle.getX(), 0, middle.getX(), ParticleApp.HEIGHT ) );
			g2d.draw( new Line2D.Double( 0, middle.getY(), ParticleApp.WIDTH, middle.getY() ) );
		}
	}
}
