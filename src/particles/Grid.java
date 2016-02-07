package particles;

import java.util.HashMap;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class Grid {
	public static final int CELL_SIZE = 15;
	private HashMap<GridPoint, ArrayList<WorldObject>> grid;
	private int cwidth;
	private int cheight;
	public Grid(int cnvs_width, int cnvs_height){
		grid = new HashMap<>();
		this.cwidth = cnvs_width;
		this.cheight = cnvs_height;
	}
	public int getCanvasWidth(){
		return cwidth;
	}
	public int getCanvasHeight(){
		return cheight;
	}
	public void partition(ArrayList<WorldObject> objects){
		for( WorldObject object: objects ){
			int cellx = (int)Math.floor(object.getX() / Grid.CELL_SIZE);
			int celly = (int)Math.floor(object.getY() / Grid.CELL_SIZE);
			
			int cellx2 = (int)Math.floor((object.getX() + object.getWidth()) / Grid.CELL_SIZE);
			int celly2 = (int)Math.floor((object.getY() + object.getHeight()) / Grid.CELL_SIZE);
			
			for( int y = celly; y <= celly2; y++ ){
				for( int x = cellx; x <= cellx2; x++ ){
					GridPoint add_to = new GridPoint(x, y);
					ArrayList<WorldObject> p = grid.get(add_to);
					if( p != null ){
						grid.get(add_to).add(object);
					}else{
						grid.put(add_to, new ArrayList<WorldObject>());
						grid.get(add_to).add(object);
					}
				}
			}
			
		}
	}
	public void handle(CollisionHandler ch){
		for( GridPoint gp : grid.keySet() ){
			ArrayList<WorldObject> al = grid.get(gp);
			ArrayList<CheckedPair> checked = new ArrayList<>();
			if( al == null ) continue;
			for( WorldObject p : al ){
				for( WorldObject p2 : al ){
					if( p == p2 ) continue;
					boolean skip = !p.collidable || !p2.collidable;
					if( skip ) continue;
					for( CheckedPair cp : checked ){
						if( cp.compare(p.getID(), p2.getID()) ){
							skip = true;
							break;
						}
					}
					if( skip ) continue;
					GridPoint c1 = new GridPoint(p.getX() + p.getWidth() / 2, p.getY() + p.getHeight() / 2  );
					GridPoint c2 = new GridPoint(p2.getX() + p2.getWidth() / 2, p2.getY() + p2.getHeight() / 2  );
					Line2D[] sides1 = p.getSides();
					Line2D[] sides2 = p.getSides();
					
					boolean collision = false;
					
					if( sides1 == null && sides2 == null ){
						BasicParticle bp;
						BasicParticle bp2;
						if( p instanceof BasicParticle && p2 instanceof BasicParticle ){
							bp = (BasicParticle)p;
							bp2 = (BasicParticle)p2;
						}else{
							continue; //Here's what happens if we have a circular non-basic-particle object. This better not happen.
						}
						Line2D axis = new Line2D.Double( c1, c2 );
						double angle = Math.atan2((axis.getY2() - axis.getY1()), (axis.getX2() - axis.getX1()));
						double c1px = bp.getRadius() * Math.cos(angle) + c1.getX();
						double c1py = bp.getRadius() * Math.sin(angle) + c1.getY();
						double c2px = bp.getRadius() * -Math.cos(angle) + c2.getX();
						double c2py = bp.getRadius() * -Math.sin(angle) + c2.getY();
						Point2D middle = new Point2D.Double( (c1px + c2px) / 2, (c1py + c2py) / 2 );
						if( bp.inRange(middle) && bp2.inRange(middle) ){
							collision = true;
						}
					}
					
					if( collision ){ //fix later
						ch.handle(p, p2);
						CheckedPair cp = new CheckedPair(p.getID(), p2.getID());
						checked.add(cp);
					}
				}
			}
		}
	}
}
