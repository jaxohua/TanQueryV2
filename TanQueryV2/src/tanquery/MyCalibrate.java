package tanquery;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PFont;
import TUIO.TuioObject;
import TUIO.TuioProcessing;

@SuppressWarnings("serial")
public class MyCalibrate extends PApplet {
	Calibration calibration;

	ArrayList<TuioObject> tuioObjectList;
	TuioProcessing tuioClient;
	PFont font;

	public void setup() {
		calibration = new Calibration();
		size(displayWidth, displayHeight);
		background(255);
		font=createFont("Arial",16,true);

	}

	public void draw() {

	}

	public void keyPressed() {
		if (key == 'm') {
			fill(255, 0, 0);
			textFont(font, 22);
			text("Mostrar Informacion de archivo", 800, 400);
			calibration.mostrar(this);
			
		}

		// save data points
		if (key == 'e') {
			fill(255, 0, 0);
			textFont(font, 22);
			text("Guardar Datos", 800, 400);
			calibration.escribir();
		
		}
	}

	public static void main(String _args[]) {

		try {
			PApplet.main(new String[] { tanquery.MyCalibrate.class.getName() });
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
