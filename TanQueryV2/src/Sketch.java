import processing.core.PApplet;

public class Sketch extends PApplet{
	public void setup(){
		size(800,600);
	}
	
	public void draw(){
		
	}
	public static void main(String _args[]){
		try{
			PApplet.main(new String[]{Sketch.class.getName()});
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	

}
