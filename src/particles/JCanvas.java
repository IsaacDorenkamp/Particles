package particles;

import javax.swing.*;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class JCanvas extends JComponent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<WorldObject> objs = new ArrayList<>();
	public JCanvas(int width, int height){
		setSize(width,height);
	}
	public JCanvas(){
		this(450, 450);
	}
	
	public void addObject(Particle p){
		objs.add(p);
	}
	public WorldObject getObject(int index){
		return objs.get(index);
	}
	public void removeObject(Particle p){
		objs.remove(p);
	}
	public void removeObject(int index){
		objs.remove(index);
	}
	public ArrayList<WorldObject> getObjects(){
		return objs;
	}
	
	@Override
	public void paintComponent(Graphics gfx){
		gfx.setColor( Color.RED );
		gfx.drawRect(0, 0, getWidth()-1, getHeight()-1);
		ArrayList<WorldObject> alp = new ArrayList<>();
		alp.addAll(objs);
		
		Graphics2D g2d = (Graphics2D) gfx;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		WorldObject last = null;
		for( WorldObject wo : alp ){
			if( last != null ){
				GraphicDebugger.DrawSAT(g2d, wo, last);
			}
			wo.draw(g2d);
			last = wo;
		}
	}
	@Override
	public Dimension getPreferredSize(){
		return getSize();
	}
}
