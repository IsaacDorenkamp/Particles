package particles;

public class WorldAccessor {
	private JCanvas canv;
	public WorldAccessor( JCanvas jc ){
		canv = jc;
	}
	
	public void add(Particle p){
		canv.addObject(p);
	}
	
	public void remove(Particle p){
		canv.removeObject(p);
	}
	public void remove(int i){
		canv.removeObject(i);
	}
}
