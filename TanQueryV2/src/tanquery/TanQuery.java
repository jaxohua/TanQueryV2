package tanquery;

import java.util.ArrayList;
import java.lang.System;

//import com.mysql.fabric.xmlrpc.base.Array;

import TUIO.TuioBlob;
import TUIO.TuioCursor;
import TUIO.TuioObject;
import TUIO.TuioProcessing;
import TUIO.TuioTime;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;
//import processing.data.Table;
import controlP5.*;

@SuppressWarnings("serial")
public class TanQuery extends PApplet {
	ControlP5 cp5;
	Textarea myTextarea;
	
	
	Calibration calibration;
	float cursor_size = 15;
	float object_size = 60;//100
	float table_size = 760;
	float scale_factor = 1;
	
	
	int interfaz=1; //1=fondo negro  0=Fondo Blanco
	TuioProcessing tuioClient;
	PFont font;
	float counter;
	PImage fondo = null;
	boolean verbose = true;
	boolean callback = true; // updates only after callbacks
	//DBConnect conex = new DBConnect("localhost", 3306, "escuela", "root", "ax");
	DBConnect conex = new DBConnect("localhost", 3306, "escuela", "root", "ax");
	String dataBase = "escuela";
	ArrayList<Intervalo> intervalos;
	ArrayList<Token> tokens;

	ArrayList<String> dataBases;
	ArrayList<String> relations;
	ArrayList<Operadores> operadores;
	ArrayList<Operadores> comparation;
	ArrayList<TuioObject> tuioObjectList;
	ArrayList<Consulta> consulta; // Array List con tokens que se encuentran dentro
								// del área de trabajo.
	public String query = "AR: ";
	public String queryAR="";
	public String querySQL="";
	public String qTemp="";
	public String AR="";
	ArbolConsulta arbolConsul;
	float contador;
	
	boolean actualizar=false;
	


	boolean DATABASE = true;
	String DB;
	float distY = 120;
	
	//Valores utilziados para la animación de ellipse
	float amount = 20, num;

	

	public void setup() {
		
		cp5 = new ControlP5(this);
		  
		  myTextarea = cp5.addTextarea("txt")
		                  .setPosition(510,50)
		                  .setSize(500,200)
		                  .setFont(createFont("arial",16))
		                  .setLineHeight(14)
		                  .setColor(color(255))
		                  .setColorBackground(color(59,131,189))
		                  .setColorForeground(color(255,0,0));
		                  ;
		
		//calibration=new Calibration(this);
		//this.frameRate(120);
		intervalos = new ArrayList<Intervalo>();
		intervalos = Busquedas.generaIntervalo(11, 360, 20);// A partir de 11º
															// hasta 360º, cada
															// 20 grados
		// System.out.println("Regresando a setup");
		relations = new ArrayList<String>();
		dataBases = new ArrayList<String>();
		tokens = new ArrayList<Token>();
		consulta = new ArrayList<Consulta>();
		arbolConsul=new ArbolConsulta();
		contador=0;
	

		Valores.fillDatabases(this, conex);
		
		operadores = Valores.fillOperadores();
		comparation=Valores.fillComparation();
		
		//size(displayWidth, displayHeight);
		size(1280,1024);
		//font = loadFont("Amaranth-42.vlw");
		font=createFont("Arial",16,true);
		tuioClient = new TuioProcessing(this);
		this.frameRate(120);
		noStroke();
		smooth();

		//Se asignan Tokens temporales
		Pruebas.test(this);
		
		// The font must be located in the sketch's
		// "data" directory to load successfully

	}

	public void draw() {
		/*
		if (!calibration.calibrated){
		    calibration.draw();
		  }*/ 
		this.consulta.clear();
		this.myTextarea.hide();
		
		//Pruebas.addToConsulta(this); Copia los Tokens hacia la lista de Consulta
		this.actualizar=false;

		this.query="";
		this.queryAR="";
		this.querySQL="";
		
		
		//this.arbolConsul=new ArbolConsulta();
		Display.dibujaArea(this);
		Display.dispLeyenda(this);
		//text(this.width + " X " + this.height, 100, 100);
		//this.contador++;
		//text(" Loop " + this.contador, 100, 140);

		tuioObjectList = tuioClient.getTuioObjectList();
		if (dataBase != "")
			// text("Base de Datos: "+dataBase,600,50);
			fill(0, 153, 0);
			this.textSize(32);
			text("BD actual: " + dataBase,750,30);

		if (this.tokens != null && this.tokens.size() > 0) {
			fill(255,255,255);
			//text("Tokens Agregados:", 700, 70);
			Valores.showTokens(this);
		}
		if(Busquedas.objectsInRange(this)>0){ //si hay objetos dentro del area de asignación de valor
			fill(0,153,0);
			textFont(font, 12);
			this.text( "Gira las piezas para cambiar su valor.", 10,240);
		}

		for (TuioObject objeto : tuioObjectList) { //Se obtiene la lista de objetos presentes. y su ubicacion
			int id = objeto.getSymbolID();
			int objetoX = objeto.getScreenX(width);
			int objetoY = objeto.getScreenY(height);
			
			
//			if(Busquedas.objectsInRange(this)>0){ //si hay objetos dentro del area de asignación de valor
//				fill(0,153,0);
//				textFont(font, 12);
//				this.text( "Gira las piezas para cambiar su valor.", 10,240);
//			}
			// Si el objeto esta en el área de asignación
			if (Busquedas.isinRange(objetoX, objetoY)) {
				String descrip="";
				//if (Valores.whatIs(id) != "SAVE") {// si el elemento no corresponde a SAVE
					Valores.setValor(this, objeto);
					Valores.showToken(this, objeto);
				if(Valores.whatIs(objeto.getSymbolID()).equals("operator")){
					descrip=Valores.getDescOper(this.operadores,Valores.getToken(objeto.getSymbolID(),this.tokens).getValorDisp());
					Display.dispAyuda(this, descrip);
				}
				else{
					this.myTextarea.hide();
				}
					
			//	}
			}
			if(id>0){
					Valores.showToken(this, objeto);
			}
			
			//Si esta en la lista de Tokens y dentro del área de trabajo.
			if (Busquedas.isInToken(tokens, id)
					&& Busquedas.isInTrabajo(objetoX, objetoY)) {//manda la posicion X,Y del Objeto
				Consulta.addConsulta(this, id);
			}
			Nodo nodoEncontrado=new Nodo();//4x
			Token token=new Token();
		
			if (this.arbolConsul.getRaiz()!=null) {
				nodoEncontrado = this.arbolConsul.getNodo(objeto.getSymbolID());
				token=Valores.getToken(objeto.getSymbolID(), this.tokens);
				if (nodoEncontrado != null) {
					//frame.arbolConsul.removeNodo(nodoEncontrado); //Si encuentra el nodo en el arbol, lo elimina
					if (token!=null) {
						if(nodoEncontrado.valor!=token.getValorSql())
							nodoEncontrado.valor=token.getValorSql();
					}
				}
				
			}

		}//Fin for recorrido de objetos
		
		//System.out.println("Tamaño de consulta sin ordenar:"+consulta.size());
		Consulta.listaContenido(consulta);
		
		
		if (consulta.size() > 0) {
			Consulta.crearArbol(this);
			
			System.out.println("Contenido del arbol");
			arbolConsul.recorrido(this);
			System.out.println("---Consulta AR---");
			arbolConsul.recorridoAlgebraRelacional(this);
			System.out.println(queryAR);
			System.out.println("\u03A3");
			System.out.println("---Consulta SQL:---");
			arbolConsul.recorridoSQL(this);
			this.actualizar=true;
			System.out.println(this.querySQL);
			Consulta.gestionaConsulta(this);
			this.actualizar=true;
			arbolConsul.conexiones(this);

			
		}
		if(consulta.size()==0){
			this.arbolConsul.setRaiz(null);
		}
		
		Display.dispSentencia(this, queryAR, "ar");
		Display.dispSentencia(this,querySQL, "sql");
		//line(846,152,765,259);
		//line(846,152,903,268);
		
		
		

	}

	
	public void addTuioObject(TuioObject tobj) {
		redraw();
		// fill(0);
		// text("Agregado grados:"+tobj.getAngleDegrees(),500,50);
		// if (verbose)
		//println("add obj " + tobj.getSymbolID() + " (" + tobj.getSessionID()
			//	+ ") " + tobj.getX() + " " + tobj.getY() + " Angulo:"
			//	+ tobj.getAngle() + " grados:" + tobj.getAngleDegrees());

	}

	// called when an object is moved
	public void updateTuioObject(TuioObject tobj) {
		redraw();
		// this.text("UPDATE", 500, 100);
		// background(0);
		// if (Valores.isinRange(tobj.getScreenX(width),
		// tobj.getScreenY(height)))

		/*println("Token:" + tobj.getSymbolID() + " X:" + tobj.getX() + " Y:"
				+ tobj.getY() + " Angle:" + tobj.getAngle() + " VelMot:"
				+ tobj.getMotionSpeed() + " VelRota:" + tobj.getRotationSpeed()
				+ " AcelMot:" + tobj.getMotionAccel() + " AcelRot:"
				+ tobj.getRotationAccel());*/

	}

	// called when an object is removed from the scene
	public void removeTuioObject(TuioObject tobj) {
		// background(0);
		redraw();
		//if (verbose)
			println("del obj " + tobj.getSymbolID() + " ("
					+ tobj.getSessionID() + ")");

	}

	// --------------------------------------------------------------
	// called at the end of each TUIO frame
	public void refresh(TuioTime frameTime) {
		// background(0);
		redraw();

		/*
		 * if (verbose) println("frame #" + frameTime.getFrameID() + " (" +
		 * frameTime.getTotalMilliseconds() + ")"); if (callback) redraw();
		 */
	}

	// --------------------------------------------------------------
	// called when a cursor is added to the scene
	public void addTuioCursor(TuioCursor tcur) {
		redraw();
		if (verbose)
			println("add cur " + tcur.getCursorID() + " ("
					+ tcur.getSessionID() + ") " + tcur.getX() + " "
					+ tcur.getY());
		// redraw();
	}

	// called when a cursor is moved
	public void updateTuioCursor(TuioCursor tcur) {
		redraw();
		if (verbose)
			println("set cur " + tcur.getCursorID() + " ("
					+ tcur.getSessionID() + ") " + tcur.getX() + " "
					+ tcur.getY() + " " + tcur.getMotionSpeed() + " "
					+ tcur.getMotionAccel());
		// redraw();
	}

	// called when a cursor is removed from the scene
	public void removeTuioCursor(TuioCursor tcur) {
		redraw();
		if (verbose)
			println("del cur " + tcur.getCursorID() + " ("
					+ tcur.getSessionID() + ")");
		// redraw()
	}

	// --------------------------------------------------------------
	// called when a blob is added to the scene
	public void addTuioBlob(TuioBlob tblb) {
		if (verbose)
			println("add blb " + tblb.getBlobID() + " (" + tblb.getSessionID()
					+ ") " + tblb.getX() + " " + tblb.getY() + " "
					+ tblb.getAngle() + " " + tblb.getWidth() + " "
					+ tblb.getHeight() + " " + tblb.getArea());
		// redraw();
	}

	// called when a blob is moved
	public void updateTuioBlob(TuioBlob tblb) {
		if (verbose)
			println("set blb " + tblb.getBlobID() + " (" + tblb.getSessionID()
					+ ") " + tblb.getX() + " " + tblb.getY() + " "
					+ tblb.getAngle() + " " + tblb.getWidth() + " "
					+ tblb.getHeight() + " " + tblb.getArea() + " "
					+ tblb.getMotionSpeed() + " " + tblb.getRotationSpeed()
					+ " " + tblb.getMotionAccel() + " "
					+ tblb.getRotationAccel());
		// redraw()
	}

	// called when a blob is removed from the scene
	public void removeTuioBlob(TuioBlob tblb) {
		if (verbose)
			println("del blb " + tblb.getBlobID() + " (" + tblb.getSessionID()
					+ ")");
		// redraw()
	}

	public void keyPressed() {
		tuioObjectList = tuioClient.getTuioObjectList();
		// performs a new calibration
		if (key == 'n') {
			fill(255, 0, 0);
			textFont(font, 22);
			text("Realizar nueva Calibracion", 800, 400);
			calibration.calibrated = false;
			calibration.calPoints = 0;
		}

		// save data points
		if (key == 'c') {
			fill(255, 0, 0);
			textFont(font, 22);
			text("Guardar Punto", 800, 400);
			
			if (tuioObjectList.size() > 0) {
				TuioObject tobj = (TuioObject) tuioObjectList.get(0);
				calibration.getCalibrationPoint(tobj.getScreenX(width),
						tobj.getScreenY(height));
				text("Guardar Punto", 700, 410);
				
				
			}
		}
		if (key == 'h') {
			fill(255, 0, 0);
			textFont(font, 22);
			text("Ayuda de la calibracion:", 800, 420);

		}
	}

	
	public static void main(String _args[]) {

		try {
			PApplet.main(new String[] { tanquery.TanQuery.class.getName() });
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
