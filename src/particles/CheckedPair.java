package particles;

public class CheckedPair {
	int checked1 = 0;
	int checked2 = 0;
	
	public CheckedPair(int c1, int c2){
		checked1 = c1;
		checked2 = c2;
	}
	public boolean compare(int c1, int c2){
		if( c1 == checked1 && c2 == checked2 ){
			return true;
		}else if( c1 == checked2 && c2 == checked1 ){
			return true;
		}else return false;
	}
}
