package particles;

import java.awt.Point;

public class GridPoint extends Point {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public GridPoint(int x, int y){
		super(x,y);
	}
	@Override
	public int hashCode(){
		return 31 * x + y; //Courtesy Stack Overflow answer by fge
	}
	@Override
	public boolean equals(final Object o){
		if ( o == null ) return false;
		if( this == o ) return true;
		if( getClass() != o.getClass() ) return false;
		
		final Point other = (Point)o;
		if( other.getX() == getX() && other.getY() == getY() ){
			return true;
		}else return false;
	}
}
