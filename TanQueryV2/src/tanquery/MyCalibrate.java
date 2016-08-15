package tanquery;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PFont;
import TUIO.TuioObject;
import TUIO.TuioProcessing;

@SuppressWarnings("serial")
public class MyCalibrate extends PApplet {
	Calibration_back calibration;

	ArrayList<TuioObject> tuioObjectList;
	TuioProcessing tuioClient;
	PFont font;
	int i=0;


	public void setup() {
		calibration = new Calibration_back();
		size(displayWidth, displayHeight);
		background(0);
		font=createFont("Arial",16,true);

	}

	public void draw() {
	//	this.clear();
		//this.text("Valor de i="+i, 100, 60);
		//i++;
		

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
