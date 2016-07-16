package tanquery;

import java.lang.reflect.Method;
import java.util.ArrayList;

import TUIO.TuioBlob;
import TUIO.TuioCursor;
import TUIO.TuioObject;
import TUIO.TuioProcessing;
import TUIO.TuioTime;
import processing.core.PApplet;
import processing.core.PFont;
//import processing.core.PImage;
//import processing.data.Table;


@SuppressWarnings("serial")
public class VariasAX extends PApplet {
	TuioProcessing tuioClient;
	PFont font;
	@SuppressWarnings("rawtypes")
	Class temp=TuioObject.class;
	Method[] m;

	public void setup() {
		size(displayWidth, displayHeight);
		font = createFont("Arial", 14);
		tuioClient = new TuioProcessing(this);
		this.frameRate(60);
		m=temp.getMethods();
		System.out.println("Metodos de la Clase TuioObject:");
		for(int i=0; i < m.length; i++){
			System.out.println(m[i].toString());
		}
			
	}

	public void draw() {
		ArrayList<TuioObject> tuioObjectList = tuioClient.getTuioObjectList();
		fill(0, 250, 153);
		textSize(22);
		background(0);
		text("Bienvenido",100,100);
		
		
		for(TuioObject objeto:tuioObjectList){
			System.out.println("Angulo: "+objeto.getAngle());
			System.out.println("Angulo grados: "+objeto.getAngleDegrees());
			System.out.println("Distancia hacia 0,0: "+objeto.getDistance(0,0));
			System.out.println("Aceleración de movimiento: "+objeto.getMotionAccel());
			System.out.println("Velocidad de movimiento: "+objeto.getMotionSpeed());
			System.out.println("Aceleración de rotación: "+objeto.getRotationAccel());
			System.out.println("Ubicación en pantalla X: "+objeto.getScreenX(WIDTH));
			System.out.println("Ubicacion en pantalla Y: "+objeto.getScreenY(HEIGHT));
			System.out.println("SessionID: "+objeto.getSessionID());
			System.out.println("Session_ID_int: "+objeto.getSymbolID());
			System.out.println("TuioState: "+objeto.getTuioState());
			System.out.println("getX: "+objeto.getX());
			System.out.println("velocidadX: "+objeto.getXSpeed());
			System.out.println("getY: "+objeto.getY());
			System.out.println("VelocidadY: "+objeto.getYSpeed());
			System.out.println("HashCode: "+objeto.hashCode());
			System.out.println("ToString: "+objeto.toString());
		}
	}

	public void addTuioObject(TuioObject tobj) {
		redraw();
	}

	// called when an object is moved
	public void updateTuioObject(TuioObject tobj) {
		redraw();
	}

	// called when an object is removed from the scene
	public void removeTuioObject(TuioObject tobj) {
		redraw();
	}

	// --------------------------------------------------------------
	// called at the end of each TUIO frame
	public void refresh(TuioTime frameTime) {
		// background(0);
		redraw();
	}

	// --------------------------------------------------------------
	// called when a cursor is added to the scene
	public void addTuioCursor(TuioCursor tcur) {
	}

	// called when a cursor is moved
	public void updateTuioCursor(TuioCursor tcur) {
	}

	// called when a cursor is removed from the scene
	public void removeTuioCursor(TuioCursor tcur) {
	}

	// --------------------------------------------------------------
	// called when a blob is added to the scene
	public void addTuioBlob(TuioBlob tblb) {
	}

	// called when a blob is moved
	public void updateTuioBlob(TuioBlob tblb) {
			println("set blb " + tblb.getBlobID() + " (" + tblb.getSessionID()
					+ ") " + tblb.getX() + " " + tblb.getY() + " "
					+ tblb.getAngle() + " " + tblb.getWidth() + " "
					+ tblb.getHeight() + " " + tblb.getArea() + " "
					+ tblb.getMotionSpeed() + " " + tblb.getRotationSpeed()
					+ " " + tblb.getMotionAccel() + " "
					+ tblb.getRotationAccel());
	}

	// called when a blob is removed from the scene
	public void removeTuioBlob(TuioBlob tblb) {
			println("del blb " + tblb.getBlobID() + " (" + tblb.getSessionID()
					+ ")");
	}

	public static void main(String _args[]) {
		PApplet.main(new String[] { tanquery.VariasAX.class.getName() });
	}
}
