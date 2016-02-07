package particles;

import javax.swing.*;

import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.event.*;
import java.util.ArrayList;

public class ParticleApp extends JFrame implements MouseListener, MouseMotionListener{
	/**
	 * 
	 */
	public static int WIDTH  = 0;
	public static int HEIGHT = 0;
	
	private static final long serialVersionUID = 1L;
	
	private volatile boolean running = true;
	private volatile boolean repainting = true;
	private CollisionHandler ch = new CollisionHandler(){
		public void handle(WorldObject p, WorldObject p2){
			p.onCollision(p2, wa);
		}
	};
	
	private SpawnMarker sm = new SpawnMarker();
	private Thread redraw = new Thread( new Runnable(){
		@Override
		public void run(){
			while(running){
				try{
					Thread.sleep(15);
					if(repainting){
						canvas.repaint();
						Grid g = new Grid(ParticleApp.WIDTH, ParticleApp.HEIGHT);
						ArrayList<WorldObject> alp = new ArrayList<>();
						alp.addAll(canvas.getObjects());
						g.partition(alp);
						g.handle(ch);
					}
				}catch(InterruptedException ie){}
			}
		}
	});
	private JCanvas canvas;
	private WorldAccessor wa;
	public ParticleApp(){
		super("Particle Simulation v0.1");
		setExtendedState( JFrame.MAXIMIZED_BOTH );
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500,500);
		setLayout(new FlowLayout());
		
		canvas = new JCanvas(500, 500);
		canvas.addMouseListener(this);
		canvas.addMouseMotionListener(this);
		
		canvas.addObject(sm);
		
		add(canvas);
		
		wa = new WorldAccessor(canvas);
		
		ParticleApp.WIDTH  = canvas.getWidth();
		ParticleApp.HEIGHT = canvas.getHeight();
		
		setVisible(true);
	}
	
	public void startApp(){
		synchronized(redraw){
			redraw.start();
		}
	}
	public void pauseApp(){
		synchronized(redraw){
			try {
				redraw.wait();
			} catch (InterruptedException e) {
				JOptionPane.showMessageDialog( null, "Could not pause application.", "Error", JOptionPane.ERROR_MESSAGE );
			} 
		}
	}
	public void continueApp(){
		synchronized(redraw){
			redraw.notify();
		}
	}
	public void endApp(){
		synchronized(redraw){
			running = false;
			try {
				redraw.join();
			} catch (InterruptedException e) {} //Ignore interruptions
		}
	}
	
	public static void main(String[] args){
		ParticleApp pa = new ParticleApp();
		pa.startApp();
		pa.pauseApp();
	}

	@Override
	public void mouseClicked(MouseEvent evt) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void mousePressed(MouseEvent evt) {
		// TODO Auto-generated method stub
		if( evt.getButton() == MouseEvent.BUTTON1 ){
			sm.start_x = evt.getX();
			sm.start_y = evt.getY();
			sm.end_x = evt.getX();
			sm.end_y = evt.getY();
			setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		if( e.getButton() == MouseEvent.BUTTON1 ){
			if( Math.abs(Math.abs(e.getX()) - sm.start_x) <= 100 ){
				sm.end_x = e.getX();
			}
			if( Math.abs(Math.abs(e.getY()) - sm.start_y) <= 100 ){
				sm.end_y = e.getY();
			}
			BasicParticle bp = new BasicParticle(sm.end_x,sm.end_y,500);
			bp.accelX = (sm.end_x - sm.start_x) / 15;
			bp.accelY = (sm.end_y - sm.start_y) / 15;
			canvas.addObject(bp);
			setCursor(Cursor.getDefaultCursor());
			sm.end_x = -1;
			sm.end_y = -1;
			sm.start_x = -1;
			sm.start_y = -1;
		}
	}

	@Override
	public void mouseDragged(MouseEvent evt) {
		// TODO Auto-generated method stub
		if( Math.abs(Math.abs(evt.getX()) - sm.start_x) <= 100 ){
			sm.end_x = evt.getX();
		}
		if( Math.abs(Math.abs(evt.getY()) - sm.start_y) <= 100 ){
			sm.end_y = evt.getY();
		}
	}

	@Override
	public void mouseMoved(MouseEvent evt) {
		// TODO Auto-generated method stub
		
	}
}
