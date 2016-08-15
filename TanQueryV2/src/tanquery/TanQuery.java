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
import processing.core.PVector;
//import processing.data.Table;
import controlP5.*;

@SuppressWarnings("serial")
public class TanQuery extends PApplet {
	ControlP5 cp5;
	Textarea myTextarea;
	
	//parametros para calibración
	
	//Calibration calibration;
	float cursor_size = 15;
	float object_size = 60;//100
	float table_size = 760;
	float scale_factor = 1;
	
	float a1, b1, c1, a3, b3, a2, b2, c2;
	int calPoints = 0;
	boolean calibrated = false;

	PVector[] cal = new PVector[4];
	PVector[] dots = new PVector[4];
	
	int lineLength = 20; //Se utiliza para dibujar los puntos de calibracion draw
	
	
	
	
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
	ArrayList<Consulta> consulta; // Array List con tokens que se encuentran dentro  del área de trabajo.
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
	float amount = 50, num;

	

	public void setup() {
		font=createFont("Arial",16,true);

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
		this.calPoints=0;
		this.calibration();

		                  
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
		//font=createFont("Arial",16,true);
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
		
	
		
		this.consulta.clear();
		this.myTextarea.hide();
		
		//Pruebas.addToConsulta(this); Copia los Tokens hacia la lista de Consulta

		this.query="";
		this.queryAR="";
		this.querySQL="";
		
		
		//this.arbolConsul=new ArbolConsulta();
		Display.dibujaArea(this);
		Display.dispLeyenda(this);
		
		if (!this.calibrated){
		    //this.drawC();	
			strokeWeight (2);
			stroke(255);
			noFill();
			ellipseMode(CENTER);
		    ellipse( dots[calPoints].x, dots[calPoints].y, lineLength * 2, lineLength * 2 );
		    line(   dots[calPoints].x - lineLength, dots[calPoints].y,
		      dots[calPoints].x + lineLength, dots[calPoints].y );
		    line(   dots[calPoints].x, dots[calPoints].y - lineLength,
		      dots[calPoints].x, dots[calPoints].y + lineLength );
		}
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

	int tx(TuioObject tobj) { 
		return  this.corrected_x(tobj); 
	}

	int ty(TuioObject tobj) { 
		return  this.corrected_y(tobj); 
	}
	
	public void calibration()
	  {
	    //dot is the original calibration image
		System.out.println("Entrando a metodo calibration");
	    int calibInset = 50;
	    dots[0] = new PVector( calibInset, calibInset ); //top left
	    dots[1] = new PVector( width -calibInset, calibInset ); //top right
	    dots[2] = new PVector( calibInset, height -calibInset ); //bot left
	    dots[3] = new PVector( width -calibInset, height -calibInset); //bot right
	    
	    // if we already have some data, then we used the saved configuration
	    String lines[] = loadStrings("src/data/calibration.txt");
	    if(lines != null && lines.length == 8)
	    {
	      System.out.println("Loading calibration file...");
	      b3 = Float.parseFloat(lines[0]);
	      b2 = Float.parseFloat(lines[1]);
	      a2 = Float.parseFloat(lines[2]);
	      c2 = Float.parseFloat(lines[3]);
	      a3 = Float.parseFloat(lines[4]);
	      b1 = Float.parseFloat(lines[5]);
	      a1 = Float.parseFloat(lines[6]);
	      c1 = Float.parseFloat(lines[7]);
	      calibrated = true;
	      System.out.println("R  E  A  D      C  A  L  I  B  R  A  T  I  O  N  Done.");
	    }
	  }

	public void drawC() {
		int lineLength = 10;
	    this.fill( 255 );
	    this.ellipse( dots[calPoints].x, dots[calPoints].y, lineLength * 2, lineLength * 2 );
	    this.line(   dots[calPoints].x - lineLength, dots[calPoints].y,
	      dots[calPoints].x + lineLength, dots[calPoints].y );
	    this.line(   dots[calPoints].x, dots[calPoints].y - lineLength,
	      dots[calPoints].x, dots[calPoints].y + lineLength );
	}
//Método que recono
	public void keyPressed() {
		//java.util.ArrayList tuioObjectList = tuioClient.getTuioObjectList();

		// performs a new calibration
		if (key == 'n') {
			this.calibrated = false;
			this.calPoints = 0;
			text("Nueva Calibracion",300,500);
		}

		// save data points
		if (key == 'g') {
			text("Guardar Punto",300,800);
			if (tuioObjectList.size() > 0) {
				TuioObject tobj = (TuioObject) tuioObjectList.get(0);
				this.getCalibrationPoint(tobj.getScreenX(width),
						tobj.getScreenY(height));
			}
		}
	}
	
	void getCalibrationPoint(int x, int y)
	  {
	    if( calibrated == false )
	    {
	      cal[calPoints ++] = new PVector(x,y);
	  
	      if( calPoints == 4 )
	      {
	        if( calibrate() == 0 ) calibrated = true; 
	        else calPoints = 0;
	      }
	    }
	}
	
	
	int corrected_x(TuioObject tobj) 
	  { 
	    if(tobj != null) 
	      return  (int)((a1 * tobj.getScreenX(width) + b1 * tobj.getScreenY(height) + c1 ) / (a3 * tobj.getScreenX(width) + b3 * tobj.getScreenY(height) + 1 )); 
	    else return -1;
	  }
	int corrected_y(TuioObject tobj) 
	  { 
	    if(tobj != null) 
	      return  (int)((this.a2 * tobj.getScreenX(width) + this.b2 * tobj.getScreenY(height) + this.c2 ) / (this.a3 * tobj.getScreenX(width) + this.b3 * tobj.getScreenY(height) + 1 ));
	    else return -1;
	  }

	int calibrate()
	  {
		System.out.println( "running calibration" );
	  
	  
	    float [][] matrix = { 
	      { -1, -1, -1, -1, 0, 0, 0, 0 },
	    
	     {   -cal[0].x, -cal[1].x, -cal[2].x, -cal[3].x, 0, 0, 0, 0 },
	     { -cal[0].y, -cal[1].y, -cal[2].y, -cal[3].y, 0,0,0,0 },
	     { 0,0,0,0,-1,-1,-1,-1 },
	     { 0,0,0,0, -cal[0].x, -cal[1].x, -cal[2].x, -cal[3].x },
	     { 0,0,0,0, -cal[0].y, -cal[1].y, -cal[2].y, -cal[3].y },
	     { cal[0].x * dots[0].x, cal[1].x * dots[1].x, cal[2].x * dots[2].x, cal[3].x * dots[3].x, cal[0].x * dots[0].y, cal[1].x * dots[1].y, cal[2].x * dots[2].y, cal[3].x * dots[3].y },
	     { cal[0].y * dots[0].x, cal[1].y * dots[1].x, cal[2].y * dots[2].x, cal[3].y * dots[3].x, cal[0].y * dots[0].y, cal[1].y * dots[1].y, cal[2].y * dots[2].y, cal[3].y * dots[3].y },
	      };
	    
	    
	    float [] bb = { -dots[0].x, -dots[1].x, -dots[2].x, -dots[3].x, -dots[0].y, -dots[1].y, -dots[2].y, -dots[3].y };
	    
	    // gauß-elimination
	    
	    for( int j = 1; j < 4; j ++ )
	    {
	    
	       for( int i = 1; i < 8; i ++ )
	       {
	          matrix[i][j] = - matrix[i][j] + matrix[i][0];
	       }
	       bb[j] = -bb[j] + bb[0];
	       matrix[0][j] = 0;
	    
	    }
	    
	    
	    for( int i = 2; i < 8; i ++ )
	    {
	      matrix[i][2] = -matrix[i][2] / matrix[1][2] * matrix[1][1] + matrix[i][1];
	    }
	    bb[2] = - bb[2] / matrix[1][2] * matrix[1][1] + bb[1];
	    matrix[1][2] = 0;
	    
	    
	    for( int i = 2; i < 8; i ++ )
	    {
	      matrix[i][3] = -matrix[i][3] / matrix[1][3] * matrix[1][1] + matrix[i][1];
	    }
	    bb[3] = - bb[3] / matrix[1][3] * matrix[1][1] + bb[1];
	    matrix[1][3] = 0;
	    
	    
	    
	    for( int i = 3; i < 8; i ++ )
	    {
	      matrix[i][3] = -matrix[i][3] / matrix[2][3] * matrix[2][2] + matrix[i][2];
	    }
	    bb[3] = - bb[3] / matrix[2][3] * matrix[2][2] + bb[2];
	    matrix[2][3] = 0;
	    
	    System.out.println( "var57, var56, var55");
	    System.out.println( matrix[4][6] + " " + matrix[4][5] + " " + matrix[4][4] );
	    
	    for( int j = 5; j < 8; j ++ )
	    {
	      for( int i = 4; i < 8; i ++ )
	      {
	         matrix[i][j] = -matrix[i][j] + matrix[i][4];
	      }
	      bb[j] = -bb[j] + bb[4];
	      matrix[3][j] = 0;
	    }
	    
	    
	    for( int i = 5; i < 8; i ++ )
	    {
	      matrix[i][6] = -matrix[i][6] / matrix[4][6] * matrix[4][5] + matrix[i][5];
	    }
	    
	    bb[6] = - bb[6] / matrix[4][6] * matrix[4][5] + bb[5];
	    matrix[4][6] = 0;
	    
	    
	    for( int i = 5; i < 8; i ++ )
	    {
	      matrix[i][7] = -matrix[i][7] / matrix[4][7] * matrix[4][5] + matrix[i][5];
	    }
	    bb[7] = - bb[7] / matrix[4][7] * matrix[4][5] + bb[5];
	    matrix[4][7] = 0;
	    
	    
	    for( int i = 6; i < 8; i ++ )
	    {
	      matrix[i][7] = -matrix[i][7] / matrix[5][7] * matrix[5][6] + matrix[i][6];
	    }
	    bb[7] = - bb[7] / matrix[5][7] * matrix[5][6] + bb[6];
	    matrix[5][7] = 0;
	    
	    
	    
	    matrix[7][7] = - matrix[7][7]/matrix[6][7]*matrix[6][3] + matrix[7][3];
	    bb[7] = -bb[7]/matrix[6][7]*matrix[6][3] + bb[3];
	    matrix[6][7] = 0;
	    
	    
	    System.out.println( "data dump" );
	    for( int i = 0; i < 8 ; i ++ )
	    {
	       for( int j= 0; j < 8 ; j ++ )
	       {
	         print( matrix[i][j] + "," );
	       }
	       System.out.println("");
	    }
	    
	    System.out.println( "bb" );
	     for( int j= 0; j < 8 ; j ++ )
	     {
	       print( bb[j] + "," );
	     }
	    
	    System.out.println("");
	    
	    b3 =  bb[7] /matrix[7][7];
	    b2 = (bb[6]-(matrix[7][6]*b3+matrix[6][6]*a3))/matrix[5][6];
	    a2 = (bb[5]-(matrix[7][5]*b3+matrix[6][5]*a3+matrix[5][5]*b2))/matrix[4][5];
	    c2 = (bb[4]-(matrix[7][4]*b3+matrix[6][5]*a3+matrix[5][4]*b2+matrix[4][4]*a2))/matrix[3][4];
	    a3 = (bb[3]-(matrix[7][3]*b3))/matrix[6][3];
	    b1 = (bb[2]-(matrix[7][2]*b3+matrix[6][2]*a3+matrix[5][2]*b2+matrix[4][2]*a2+matrix[3][2]*c2))/matrix[2][2];
	    a1 = (bb[1]-(matrix[7][1]*b3+matrix[6][1]*a3+matrix[5][1]*b2+matrix[4][1]*a2+matrix[3][1]*c2+matrix[2][1]*b1))/matrix[1][1];
	    c1 = (bb[0]-(matrix[7][0]*b3+matrix[6][0]*a3+matrix[5][0]*b2+matrix[4][0]*a2+matrix[3][0]*c2+matrix[2][0]*b1+matrix[1][0]*a1))/matrix[0][0];
	    
	    if( Float.isNaN( b3 ) ) return 1;
	    if( Float.isNaN( b2 ) ) return 1;
	    if( Float.isNaN( a2 ) ) return 1;
	    if( Float.isNaN( c2 ) ) return 1;
	    if( Float.isNaN( a3 ) ) return 1;
	    if( Float.isNaN( b1 ) ) return 1;
	    if( Float.isNaN( a1 ) ) return 1;
	    if( Float.isNaN( c1 ) ) return 1;
	    
	    System.out.println( "calibrated OK" );
	    
	    String data = ""+b3+";"+b2+";"+a2+";"+c2+";"+a3+";"+b1+";"+a1+";"+c1;
	    System.out.println("String Data="+data);
	    
	    saveStrings("src/data/calibration.txt", split(data, ';'));
	    
	    return 0;
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
