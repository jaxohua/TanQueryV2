package tanquery;

import TUIO.TuioObject;
import processing.core.PFont;

public class Display {
	
	public static void dibujaArea(TanQuery frame){
		int inter=frame.interfaz;
		String asigna="Área de Asignación";
		String trabajo="Área de Trabajo";
		String result="Área de Datos";
		String querys="Sentencias Generadas:";
		switch (inter){
			case 0:	asigna="Área de Asignación";
					trabajo="Área de Trabajo";
					result="Área de Datos";
					querys="Sentencias Generadas:";
					
					
					frame.fondo = frame.loadImage("gris02.jpg");
					frame.image(frame.fondo, 0, 0);
					//frame.background(0);
					
					PFont font;
					font = frame.loadFont("Amaranth-42.vlw");
					frame.textFont(font, 22);
			
					
					frame.fill(255);
					frame.text(asigna, 30, 30);
					frame.text(result,30,290);
					frame.text(trabajo, 540, 30);
					frame.text(querys, 30, 580);
			
					
					frame.noFill();
					frame.strokeWeight (3);
					frame.stroke(255);
					
					frame.rect(5, 5, 500, 250, 30);//asignación
					frame.rect(5, 270, 500, 275, 30);//Datos
					frame.rect(520, 5, 750,540, 30);//Trabajo
					frame.rect(5,560,1270,100,30);//Cadenas de SQL y AR
					break;
					
			case 1://	frame.fondo = frame.loadImage("negro.jpg");
					//frame.image(frame.fondo, 0, 0); 
				frame.background(34,34,34);
					asigna="Asigna valor a los tokens";
					trabajo="";
					result="Resultados";
					querys="Algebra Relacional vs SQL:";
					

					//frame.noFill();
					frame.fill(0);
					frame.strokeWeight (3);
					frame.stroke(255);
					frame.rect(5, 5, 500, 250, 30);//asignación
					//frame.rect(5, 270, 500, 275, 30);//Datos
					//frame.rect(520, 5, 750,540, 30);//Trabajo
				//	frame.rect(5,560,1270,100,30);//Cadenas de SQL y AR
					frame.fill(255,255,255);
					frame.text(asigna, 30, 25);
					frame.text(result,30,290);
					frame.text(trabajo, 540, 30);
					frame.text(querys, 30, 580);
				
					
					break;
			
		}
		
		
	}
	
	public static void mensaje(TanQuery frame,String texto, String tipo){
		PFont font;
		font = frame.loadFont("Amaranth-42.vlw");
		
		switch (tipo) {
		case "asignación":
			frame.textFont(font, 13);
			frame.fill(200, 130, 50);
			frame.text(texto, 30, 250);
			break;
		case "trabajo":
			frame.textFont(font, 12);
			frame.text(texto, 30,1000);
			break;
			
		case "resultado":
			frame.textFont(font, 12);
			frame.text(texto, 530, 300);
			break;
			
		case "descripcion":
			frame.textFont(font, 12);
			frame.text(texto, 530, 300);
			break;
		default:
			break;
		}
		
		
	}
	
	public static void mensajeXY(TanQuery frame,String texto, float x, float y){
		PFont font;
		font = frame.loadFont("Amaranth-42.vlw");
		frame.textFont(font, 12);
//		frame.fill(200, 130, 50);
		
		frame.fill(0);
		
		
		frame.text(texto, x, y);
	}
	
	public static void showValor(TanQuery frame,String texto, float x, float y,String tipo){
		switch (tipo) {
		case "relation":
			frame.fill(0, 153, 0);
			break;
		case "attribute":
			frame.fill(51, 51, 255); // Attribute
			break;
		case "constant":
			frame.fill(139, 69, 19);// Constant
			break;

		case "operator":
			frame.fill(255, 255, 0);// Operator
			break;
		case "comparation":
			frame.fill(255, 0, 0);// Comparation
			break;

		}
		
		if(tipo=="operator" || tipo=="comparation"){
			x=x+30;
			y=y+55;
			frame.textSize(32);
		}
		else{
			frame.textFont(frame.font, 18);
		}
//		frame.fill(200, 130, 50);
		
		//frame.fill(0);
		
		
		frame.text(texto, x, y);
	}
	
	public static void mensajeDesc(TanQuery frame,String texto, String tipo,TuioObject objeto){
		//PFont font;
		//font = frame.loadFont("Amaranth-42.vlw");
		int fontSize=12;
		
		switch (tipo) {
		case "advertencia":
			frame.textFont(frame.font, fontSize);
			frame.fill(255, 255, 0);
			frame.text(texto, 30, 300);
			break;
		case "Alerta":
			frame.textFont(frame.font, fontSize);
			frame.text(texto, 30,1000);
			break;
			
		case "resultado":
			frame.textFont(frame.font, fontSize);
			frame.text(texto, 530, 300);
			break;
			
		case "descripcion":
			frame.textFont(frame.font, fontSize);
			frame.text(texto, 530, 300);
			break;
		default:
			break;
		}
		
		
	}

	@SuppressWarnings("static-access")
	public static void dibujaElipse(TanQuery frame, int objetoX, int objetoY, String tipo) {
		//frame.fill(0, 40);
		 // rect(-1, -1, width+1, height+1);
		 
		  //float maxX = map(mouseX, 0, width, 1, 250);
		 
		  //frame.translate(objetoX, objetoY);
		  for (int i = 0; i < 360; i+=frame.amount) {
		    float x = objetoX+(float) (Math.sin(Math.toRadians(i+frame.num)) * 30);
		    float y = objetoY+(float) (Math.cos(Math.toRadians(i+frame.num)) * 30);
		 
		    //float x2 = (float) (Math.sin(Math.toRadians(i+frame.amount-frame.num))*40);
		    //float y2 = (float) (Math.cos(Math.toRadians(i+frame.amount-frame.num))*40);
		    frame.noFill(); 
		    //frame.bezier(x, y, x+x2, y+y2, x2-x, y2-y, x2, y2);
		    //frame.bezier(x, y, x-x2, y-y2, x2+x, y2+y, x2, y2);
		    
		    frame.fill(0, 150, 255);
			switch (tipo) {
			case "relation":
				frame.fill(0, 153, 0);
				break;
			case "attribute":
				frame.fill(51, 51, 255); // Attribute
				break;
			case "constant":
				frame.fill(139, 69, 19);// Constant
				break;

			case "operator":
				frame.fill(255, 255, 0);// Operator
				break;
			case "comparation":
				frame.fill(255, 0, 0);// Comparation
				break;

			}
		    frame.ellipseMode(frame.CORNER);
		    if(!tipo.equals("relation"))
		    	//frame.ellipse(x, y, 5, 5);
		    	frame.rect(x, y, 8, 8);
		    //frame.ellipse(x+x2,y+ y2, 15, 15);
		  }
		  frame.num += 0.5;
	}
	
	public static void dispLeyenda(TanQuery frame){
		int x=-330;
		int xz=30;
		int fontSize=14;
		frame.textFont(frame.font, fontSize);
		frame.noStroke();
		frame.fill(0, 153, 0); //Relation
		frame.rect(350+x, 30, 140-xz, 30,20);
		//frame.fill(51, 51, 255); //Attribute
		frame.fill(59,131,189); //Attribute
		frame.rect(350+x, 60, 140-xz, 30,20);
		frame.fill(139, 69, 19);//Constant
		frame.rect(350+x, 90, 140-xz, 30,20);
		frame.fill(255,255,0);//Operator
		frame.rect(350+x, 120, 140-xz, 30,20);
		frame.fill(255,0,0);//Comparation
		frame.rect(350+x, 150, 140-xz, 30,20);
		frame.smooth(3);
		//frame.fill(238,118,0);//Salvar
		//frame.rect(350+x, 180, 140-xz,30, 20);
		frame.fill(0,0,0);//Base de Datos
		//frame.rect(350+x, 210, 140-xz,30, 20);
		frame.rect(350+x, 180, 140-xz,30, 20);
		frame.fill(0,0,0);
		
		frame.fill(255,255,255);
		frame.text("Atributos", 370+x, 85);
		frame.text("Constante", 370+x, 115);
		//frame.text("Base Datos", 370+x, 235);
		frame.text("Base Datos", 370+x, 205);
		frame.fill(0,0,0);
		frame.text("Relaciones", 370+x, 55);
		frame.text("Operador", 370+x, 145);
		frame.text("Comparación", 360+x, 175);
		//frame.text("Guardar", 370+x, 205);
	}
	
	public static void dispSentencia(TanQuery frame, String sentencia,String tipo){
		frame.textFont(frame.font, 18);
		int x=20,y=600;
		String cadena="";
		switch (tipo){
		case "ar":
					if(frame.interfaz==1)
						frame.fill(255,251,255);
					if(frame.interfaz==0)
						frame.fill(0);
					cadena=cadena.concat("AR: "+sentencia);
					frame.text(cadena, x,y);
					/*for (int i = 0; i < cadena.length(); i++) {
						frame.text(cadena.charAt(i), x,y);
						if(i==150||i==300){
							y+=14;
							x=20;
						}
						x+=9;
					}*/
					
		break;
		case"sql":
					frame.fill(0, 153, 0);
					x=20;
					y=630;
					//frame.text("SQL: "+sentencia, 20,630);
					cadena=cadena.concat("SQL: "+sentencia);
					frame.text(cadena, x,y);
					/*for (int i = 0; i < cadena.length(); i++) {
						frame.text(cadena.charAt(i), x,y);
						if(i==150 || i==300){
							y+=14;
							x=20;
						}
						x+=9;
					}*/
					
		break;
			
		}
		
		
	}
	public static void dispAyuda(TanQuery frame, String descrip){
		@SuppressWarnings("unused")
		int x=0;
		@SuppressWarnings("unused")
		int l=3;
		//frame.fill(255,255,0);//Operator
		//frame.rect(400+x, 10, 700, 20*l,20);
		frame.textFont(frame.font, 18);
		//frame.text(descrip, 402+x, 30);
        frame.myTextarea.setText(descrip);
        frame.myTextarea.show();
	}
	
}
